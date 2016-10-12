/*
 * King Conf
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package de.gunnarmorling.kingconf.internal;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import io.microprofile.config.Key;
import io.microprofile.config.source.ConfigSource;

/**
 * @author Gunnar Morling
 */
public class ConfigInvocationHandler implements InvocationHandler {

	private final Iterable<ConfigSource> sources;

	public ConfigInvocationHandler(Iterable<ConfigSource> sources) {
		this.sources = sources;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		String key = method.getAnnotation( Key.class ).value();
		String value = null;

		for ( ConfigSource source : sources ) {
			value = source.getValue( key );
			if ( value != null ) {
				break;
			}
		}

		if ( value != null ) {
			return convert( value,  method.getReturnType() );
		}
		else if ( method.isDefault() ) {
			return invokeDefaultMethod( proxy, method, args );
		}
		else {
			return null;
		}
	}

	private Object convert(String value, Class<?> returnType) {
		if ( returnType == int.class ||  returnType == Integer.class ) {
			return Integer.valueOf( value );
		}
		else if ( returnType == boolean.class ||  returnType == Boolean.class ) {
			return Boolean.valueOf( value );
		}
		else {
			return value;
		}
	}

	// From https://rmannibucau.wordpress.com/2014/03/27/java-8-default-interface-methods-and-jdk-dynamic-proxies/
	private Object invokeDefaultMethod(Object proxy, Method method, Object[] args) throws Throwable {
		final Class<?> declaringClass = method.getDeclaringClass();

		return Lookup.in( declaringClass )
				.unreflectSpecial( method, declaringClass )
				.bindTo( proxy )
				.invokeWithArguments( args );
	}

	private static class Lookup {

		private static final Constructor<MethodHandles.Lookup> LOOKUP_CONSTRUCTOR = lookupConstructor();

		private static Constructor<MethodHandles.Lookup> lookupConstructor() {
			try {
				Constructor<MethodHandles.Lookup> ctor = MethodHandles.Lookup.class.getDeclaredConstructor( Class.class, int.class );
				ctor.setAccessible( true );
				return ctor;
			}
			catch (NoSuchMethodException e) {
				return null;
			}
		}

		private static MethodHandles.Lookup in(Class<?> requestedLookupClass) throws IllegalAccessException, InvocationTargetException, InstantiationException {
			return LOOKUP_CONSTRUCTOR.newInstance( requestedLookupClass, MethodHandles.Lookup.PRIVATE );
		}
	}
}

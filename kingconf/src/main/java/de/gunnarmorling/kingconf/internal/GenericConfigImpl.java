/*
 * King Conf
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package de.gunnarmorling.kingconf.internal;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import io.microprofile.config.GenericConfig;
import io.microprofile.config.Key;

/**
 * @author Gunnar Morling
 */
public class GenericConfigImpl implements GenericConfig {

	private final Class<?> configType;
	private final Object delegate;

	public GenericConfigImpl(Class<?> configType, Object delegate) {
		this.configType = configType;
		this.delegate = delegate;
	}

	@Override
	public Object get(String key) {
		for ( Method method : configType.getDeclaredMethods() ) {
			Key methodKey = method.getAnnotation( Key.class );
			if ( methodKey != null && methodKey.value().equals( key ) ) {
				try {
					return method.invoke( delegate );
				}
				catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					throw new RuntimeException( e );
				}
			}
		}

		throw new RuntimeException( "Unknown key: " + key );
	}
}

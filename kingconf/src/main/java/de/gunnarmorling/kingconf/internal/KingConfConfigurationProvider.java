/*
 * King Conf
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package de.gunnarmorling.kingconf.internal;

import java.lang.reflect.Proxy;
import java.util.LinkedList;

import io.microprofile.config.Configuration.ConfigBuilderContext;
import io.microprofile.config.Configuration.GenericConfigBuilderContext;
import io.microprofile.config.GenericConfig;
import io.microprofile.config.bootstrap.ConfigurationProvider;
import io.microprofile.config.source.ConfigSource;
import io.microprofile.config.source.ConfigSourceFactory;

/**
 * @author Gunnar Morling
 */
public class KingConfConfigurationProvider implements ConfigurationProvider {

	@Override
	public <T> ConfigBuilderContext<T> getConfig(Class<T> configType) {
		return new ConfigBuilderContextImpl<>( configType );
	}

	@Override
	public GenericConfigBuilderContext getGenericConfig(Class<?> configType) {
		return new GenericConfigBuilderContextImpl( configType );
	}

	@Override
	public ConfigSourceFactory getConfigSourceFactory() {
		return new ConfigSourceFactoryImpl();
	}

	private static class ConfigBuilderContextImpl<T> implements ConfigBuilderContext<T> {

		private final Class<T> configType;
		private final LinkedList<ConfigSource> sources = new LinkedList<>();

		public ConfigBuilderContextImpl(Class<T> configType) {
			this.configType = configType;
		}

		@Override
		public ConfigBuilderContext<T> withSource(ConfigSource source) {
			sources.addFirst( source );
			return this;
		}

		@Override
		@SuppressWarnings("unchecked")
		public T build() {
			return (T) Proxy.newProxyInstance(
					KingConfConfigurationProvider.class.getClassLoader(),
					new Class<?>[]{ configType },
					new ConfigInvocationHandler( sources )
			);
		}
	}

	private static class GenericConfigBuilderContextImpl implements GenericConfigBuilderContext {

		private final Class<?> configType;
		private final ConfigBuilderContextImpl<?> delegate;

		public GenericConfigBuilderContextImpl(Class<?> configType) {
			this.configType = configType;
			this.delegate = new ConfigBuilderContextImpl<>( configType );
		}

		@Override
		public GenericConfigBuilderContext withSource(ConfigSource source) {
			delegate.withSource( source );
			return this;
		}

		@Override
		public GenericConfig build() {
			return new GenericConfigImpl( configType, delegate.build() );
		}
	}
}

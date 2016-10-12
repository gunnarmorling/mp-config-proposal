/*
 * MicroProfile Config
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package io.microprofile.config;

import io.microprofile.config.bootstrap.ConfigurationProviderManager;
import io.microprofile.config.source.ConfigSource;

/**
 * @author Gunnar Morling
 */
public class Configuration {

	public static <T> ConfigBuilderContext<T> getConfig(Class<T> configType) {
		return ConfigurationProviderManager.getDefaultConfigurationProvider()
				.getConfig( configType );
	}

	public static GenericConfigBuilderContext getGenericConfig(Class<?> configType) {
		return ConfigurationProviderManager.getDefaultConfigurationProvider()
				.getGenericConfig( configType );
	}

	public interface ConfigBuilderContext<T> {
		ConfigBuilderContext<T> withSource(ConfigSource source);
		T build();
	}

	public interface GenericConfigBuilderContext {
		GenericConfigBuilderContext withSource(ConfigSource source);
		GenericConfig build();
	}
}

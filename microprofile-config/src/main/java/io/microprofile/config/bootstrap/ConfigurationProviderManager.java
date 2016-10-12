/*
 * MicroProfile Config
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package io.microprofile.config.bootstrap;

import java.util.ServiceLoader;

/**
 * @author Gunnar Morling
 */
public class ConfigurationProviderManager {

	private static volatile ConfigurationProvider defaultProvider;

	public static ConfigurationProvider getDefaultConfigurationProvider() {
		if ( defaultProvider == null ) {
			ServiceLoader<ConfigurationProvider> providers = ServiceLoader.load( ConfigurationProvider.class );
			defaultProvider = providers.iterator().next();
		}

		return defaultProvider;
	}
}

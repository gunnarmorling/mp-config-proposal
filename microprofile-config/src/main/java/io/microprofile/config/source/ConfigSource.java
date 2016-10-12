/*
 * MicroProfile Config
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package io.microprofile.config.source;

import io.microprofile.config.bootstrap.ConfigurationProviderManager;

/**
 * @author Gunnar Morling
 */
public interface ConfigSource {

	static ConfigSource newFileSource(String path) {
		return ConfigurationProviderManager.getDefaultConfigurationProvider()
				.getConfigSourceFactory()
				.getFileSource( path );
	}

	String getValue(String key);
}

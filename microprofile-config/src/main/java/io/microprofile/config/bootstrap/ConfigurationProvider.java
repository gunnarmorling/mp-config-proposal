/*
 * MicroProfile Config
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package io.microprofile.config.bootstrap;

import io.microprofile.config.Configuration;
import io.microprofile.config.source.ConfigSourceFactory;

/**
 * @author Gunnar Morling
 */
public interface ConfigurationProvider {

	<T> Configuration.ConfigBuilderContext<T> getConfig(Class<T> configType);

	Configuration.GenericConfigBuilderContext getGenericConfig(Class<?> configType);

	ConfigSourceFactory getConfigSourceFactory();
}

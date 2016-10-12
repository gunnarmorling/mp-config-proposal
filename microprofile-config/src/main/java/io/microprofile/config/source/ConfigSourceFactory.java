/*
 * MicroProfile Config
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package io.microprofile.config.source;


/**
 * @author Gunnar Morling
 *
 */
public interface ConfigSourceFactory {

	ConfigSource getFileSource(String path);
}

/*
 * King Conf
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package de.gunnarmorling.kingconf.internal;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import io.microprofile.config.source.ConfigSource;
import io.microprofile.config.source.ConfigSourceFactory;


/**
 * @author Gunnar Morling
 *
 */
public class ConfigSourceFactoryImpl implements ConfigSourceFactory {

	@Override
	public ConfigSource getFileSource(String path) {
		return new FileConfigSource(path);
	}

	private static class FileConfigSource implements ConfigSource {

		private final Properties properties;

		public FileConfigSource(String path) {
			try (InputStream stream = ConfigSourceFactoryImpl.class.getResourceAsStream( "/" + path )) {
				this.properties = new Properties();
				this.properties.load( stream );
			}
			catch (IOException e) {
				throw new RuntimeException( e );
			}
		}

		@Override
		public String getValue(String key) {
			return properties.getProperty( key );
		}
	}
}

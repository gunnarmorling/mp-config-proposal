/*
 * King Conf
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package de.gunnarmorling.kingconf.test;

import io.microprofile.config.Config;
import io.microprofile.config.Key;

/**
 * @author Gunnar Morling
 */
@Config
public interface MyConfig {

	@Key("com.example.host")
	String host();

	@Key("com.example.port")
	int port();

	@Key("com.example.enabled")
	public default boolean enabled() {
		return true;
	}
}

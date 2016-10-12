/*
 * MicroProfile Config
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package io.microprofile.config;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Gunnar Morling
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Key {
	String value();
}

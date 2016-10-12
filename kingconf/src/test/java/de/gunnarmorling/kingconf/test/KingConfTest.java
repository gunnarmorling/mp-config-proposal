/*
 * King Conf
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package de.gunnarmorling.kingconf.test;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import io.microprofile.config.Configuration;
import io.microprofile.config.GenericConfig;
import io.microprofile.config.source.ConfigSource;
/**
 * @author Gunnar Morling
 */
public class KingConfTest {

	@Test
	public void typedAccess() {
		MyConfig config = Configuration.getConfig( MyConfig.class )
			.withSource( ConfigSource.newFileSource( "test.properties" ) )
			.build();

		assertThat( config.host() ).isEqualTo( "myhost" );
		assertThat( config.port() ).isEqualTo( 1234 );
		assertThat( config.enabled() ).isEqualTo( true );
	}

	@Test
	public void genericAccess() {
		GenericConfig config = Configuration.getGenericConfig( MyConfig.class )
			.withSource( ConfigSource.newFileSource( "test.properties" ) )
			.build();

		assertThat( config.get( "com.example.host" ) ).isEqualTo( "myhost" );
		assertThat( config.get( "com.example.port" ) ).isEqualTo( 1234 );
		assertThat( config.get( "com.example.enabled" ) ).isEqualTo( true );
	}

	@Test
	public void optionalValueGiven() {
		MyConfig config = Configuration.getConfig( MyConfig.class )
			.withSource( ConfigSource.newFileSource( "test-overriding-optional.properties" ) )
			.build();

		assertThat( config.enabled() ).isEqualTo( false );
	}
}

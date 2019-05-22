package com.jslsolucoes.nginx.admin.database;

import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.UUID;

import org.h2.tools.Server;
import org.junit.After;
import org.junit.Test;

public class DatabaseMigrateTest {
	

	/*@Test
	public void migrateH2() throws SQLException, IOException {
		Server server = Server.createTcpServer("-tcpPort", "9123", "-tcpAllowOthers").start();
		DatabaseMigrationBuilder
			.newBuilder()
			.withDatabase("migrate")
			.withHost("localhost")
			.withPort(9123)
			.withDriver(DatabaseDriver.H2)
			.withLocation(Files.createTempDirectory(UUID.randomUUID().toString()).toAbsolutePath().toString())
			.withUsername("sa")
			.withPassword("sa")
			.withClasspath("/db/migration/h2","/db2")
			.withProperties()
				//.withProperty("AUTO_SERVER", "TRUE")
				.withProperty("DB_CLOSE_DELAY", "-1")
				.withProperty("DB_CLOSE_ON_EXIT", "FALSE")
			.end()
			.migrate()
			.migrate();
		server.stop();
	}*/
	
	@Test
	public void migrateMySql() throws SQLException {
			
		DatabaseMigrationBuilder
			.newBuilder()
			.withDriver(DatabaseDriver.MYSQL)
			.withHost("localhost")
			.withPort(3306)
			.withDatabase("migrate")
			.withUsername("root")
			.withPassword("142857")
			.withProperties()
				.withProperty("useSSL", "false")
			.end()
			.withClasspath("/db/migration/mysql")
			.migrate()
			.migrate();
	}
	
	@After
	public void after() {
		
	}
}

package com.jslsolucoes.nginx.admin.agent.client;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.jslsolucoes.nginx.admin.agent.client.api.NginxAgentClientApis;
import com.jslsolucoes.nginx.admin.agent.client.api.impl.cli.NginxCommandLineInterface;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxAuthenticationFailResponse;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxCommandLineInterfaceResponse;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxExceptionResponse;

public class NginxCommandLineInterfaceTest {

	private NginxAgentClient nginxAgentClient;
	private NginxCommandLineInterface nginxCommandLineInterface;

	@Before
	public void setUp() {
		nginxAgentClient = NginxAgentClientBuilder.newBuilder().build();
		nginxAgentClient.api(NginxAgentClientApis.configure()).withAuthorizationKey("changeit")
				.withEndpoint("http://192.168.1.172:3000").withGzip(true).withMaxPostSize(15).build().configure()
				.join();
		nginxCommandLineInterface = nginxAgentClient.api(NginxAgentClientApis.cli())
				.withAuthorizationKey("changeit").withEndpoint("http://192.168.1.172:3000").build();
	}

	@Test
	public void status() {

		nginxCommandLineInterface.killAll().thenCompose(nginxResponse -> nginxCommandLineInterface.stop())
				.thenCompose(nginxResponse -> nginxCommandLineInterface.start())
				.thenCompose(nginxResponse -> nginxCommandLineInterface.status()).thenAccept(nginxResponse -> {
					if (nginxResponse.error()) {
						NginxExceptionResponse nginxExceptionResponse = (NginxExceptionResponse) nginxResponse;
						Assert.fail(nginxExceptionResponse.getStackTrace());
					} else if (nginxResponse.forbidden()) {
						NginxAuthenticationFailResponse nginxAuthenticationFailResponse = (NginxAuthenticationFailResponse) nginxResponse;
						Assert.fail(nginxAuthenticationFailResponse.getMessage());
					} else {
						NginxCommandLineInterfaceResponse nginxCommandLineInterfaceResponse = (NginxCommandLineInterfaceResponse) nginxResponse;
						Assert.assertTrue(nginxCommandLineInterfaceResponse.getSuccess());
					}
				}).join();
	}

	@Test
	public void killAll() {
		nginxCommandLineInterface.killAll().thenCompose(nginxResponse -> nginxCommandLineInterface.stop())
				.thenCompose(nginxResponse -> nginxCommandLineInterface.start())
				.thenCompose(nginxResponse -> nginxCommandLineInterface.killAll()).thenAccept(nginxResponse -> {
					if (nginxResponse.error()) {
						NginxExceptionResponse nginxExceptionResponse = (NginxExceptionResponse) nginxResponse;
						Assert.fail(nginxExceptionResponse.getStackTrace());
					} else if (nginxResponse.forbidden()) {
						NginxAuthenticationFailResponse nginxAuthenticationFailResponse = (NginxAuthenticationFailResponse) nginxResponse;
						Assert.fail(nginxAuthenticationFailResponse.getMessage());
					} else {
						NginxCommandLineInterfaceResponse nginxCommandLineInterfaceResponse = (NginxCommandLineInterfaceResponse) nginxResponse;
						Assert.assertTrue(nginxCommandLineInterfaceResponse.getSuccess());
					}
				}).join();
	}

	@Test
	public void start() {
		nginxCommandLineInterface.killAll().thenCompose(nginxResponse -> nginxCommandLineInterface.stop())
				.thenCompose(nginxResponse -> nginxCommandLineInterface.start()).thenAccept(nginxResponse -> {
					if (nginxResponse.error()) {
						NginxExceptionResponse nginxExceptionResponse = (NginxExceptionResponse) nginxResponse;
						Assert.fail(nginxExceptionResponse.getStackTrace());
					} else if (nginxResponse.forbidden()) {
						NginxAuthenticationFailResponse nginxAuthenticationFailResponse = (NginxAuthenticationFailResponse) nginxResponse;
						Assert.fail(nginxAuthenticationFailResponse.getMessage());
					} else {
						NginxCommandLineInterfaceResponse nginxCommandLineInterfaceResponse = (NginxCommandLineInterfaceResponse) nginxResponse;
						Assert.assertTrue(nginxCommandLineInterfaceResponse.getSuccess());
					}
				}).join();
	}

	@Test
	public void stop() {
		nginxCommandLineInterface.killAll().thenCompose(nginxResponse -> nginxCommandLineInterface.stop())
				.thenCompose(nginxResponse -> nginxCommandLineInterface.start())
				.thenCompose(nginxResponse -> nginxCommandLineInterface.stop()).thenAccept(nginxResponse -> {
					if (nginxResponse.error()) {
						NginxExceptionResponse nginxExceptionResponse = (NginxExceptionResponse) nginxResponse;
						Assert.fail(nginxExceptionResponse.getStackTrace());
					} else if (nginxResponse.forbidden()) {
						NginxAuthenticationFailResponse nginxAuthenticationFailResponse = (NginxAuthenticationFailResponse) nginxResponse;
						Assert.fail(nginxAuthenticationFailResponse.getMessage());
					} else {
						NginxCommandLineInterfaceResponse nginxCommandLineInterfaceResponse = (NginxCommandLineInterfaceResponse) nginxResponse;
						Assert.assertTrue(nginxCommandLineInterfaceResponse.getSuccess());
					}
				}).join();
	}

	@After
	public void tearDown() {
		nginxAgentClient.close();
	}
}

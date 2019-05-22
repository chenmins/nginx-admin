package com.jslsolucoes.nginx.admin.agent.client;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.jslsolucoes.nginx.admin.agent.client.api.NginxAgentClientApis;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxAuthenticationFailResponse;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxExceptionResponse;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxStatusResponse;

public class NginxStatusTest {

	private NginxAgentClient nginxAgentClient;

	@Before
	public void setUp() {
		nginxAgentClient = NginxAgentClientBuilder.newBuilder().build();
	}

	@Test
	public void status() {

		nginxAgentClient.api(NginxAgentClientApis.status()).withAuthorizationKey("changeit")
				.withEndpoint("http://192.168.1.172:3000")
				.build().status().thenAccept(nginxResponse -> {
					if (nginxResponse.error()) {
						NginxExceptionResponse nginxExceptionResponse = (NginxExceptionResponse) nginxResponse;
						Assert.fail(nginxExceptionResponse.getStackTrace());
					} else if (nginxResponse.forbidden()) {
						NginxAuthenticationFailResponse nginxAuthenticationFailResponse = (NginxAuthenticationFailResponse) nginxResponse;
						Assert.fail(nginxAuthenticationFailResponse.getMessage());
					} else {
						NginxStatusResponse nginxStatusResponse = (NginxStatusResponse) nginxResponse;
						Assert.assertTrue(nginxStatusResponse.getAccepts() > 0);
					}
				}).join();
	}

	@After
	public void tearDown() {
		nginxAgentClient.close();
	}
}

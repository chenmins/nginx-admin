package com.jslsolucoes.nginx.admin.agent.client;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.jslsolucoes.nginx.admin.agent.client.api.NginxAgentClientApis;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxAuthenticationFailResponse;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxConfigureResponse;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxExceptionResponse;

public class NginxConfigureTest {

	private NginxAgentClient nginxAgentClient;

	@Before
	public void setUp() {
		nginxAgentClient = NginxAgentClientBuilder.newBuilder().build();
	}

	@Test
	public void configure() {
		nginxAgentClient.api(NginxAgentClientApis.configure()).withAuthorizationKey("changeit")
				.withEndpoint("http://192.168.1.172:3000").withGzip(true)
				.withMaxPostSize(15).build().configure().thenAccept(nginxResponse -> {
					if (nginxResponse.error()) {
						NginxExceptionResponse nginxExceptionResponse = (NginxExceptionResponse) nginxResponse;
						Assert.fail(nginxExceptionResponse.getStackTrace());
					} else if (nginxResponse.forbidden()) {
						NginxAuthenticationFailResponse nginxAuthenticationFailResponse = (NginxAuthenticationFailResponse) nginxResponse;
						Assert.fail(nginxAuthenticationFailResponse.getMessage());
					} else {
						NginxConfigureResponse nginxConfigureResponse = (NginxConfigureResponse) nginxResponse;
						Assert.assertTrue(nginxConfigureResponse.getSuccess());
					}
				}).join();
	}

	@After
	public void tearDown() {
		nginxAgentClient.close();
	}
}

package com.jslsolucoes.nginxrest.service.impl;

import com.jslsolucoes.nginx.admin.agent.client.NginxAgentClient;
import com.jslsolucoes.nginx.admin.agent.client.NginxAgentClientBuilder;
import com.jslsolucoes.nginx.admin.agent.client.api.NginxAgentClientApis;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxAuthenticationFailResponse;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxExceptionResponse;
import com.jslsolucoes.nginx.admin.agent.model.response.NginxStatusResponse;
import com.jslsolucoes.nginxrest.pojo.NginxNode;
import com.jslsolucoes.nginxrest.pojo.NginxResult;
import com.jslsolucoes.nginxrest.pojo.NginxStatus;
import com.jslsolucoes.nginxrest.service.IndexService;
import com.jslsolucoes.nginxrest.util.Result;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IndexServiceImpl implements IndexService {

    private NginxAgentClient nginxAgentClient = NginxAgentClientBuilder.newBuilder().build();
    public static List<NginxNode> list = new ArrayList<NginxNode>();
    static {
        list.add(new NginxNode(1,"root","http://192.168.1.172:3000","changeit"));
        list.add(new NginxNode(2,"root1","http://192.168.1.172:3001","changeit1"));
    }

    @Override
    public List<NginxNode> selectByExample(NginxNode nginxNode) {
        return list;
    }

    @Override
    public NginxNode selectById(int idNginx) {
        NginxNode result = null;
        for (int i = 0; i < list.size(); i++) {
            if (idNginx==list.get(i).getIdNginx()){
                result = list.get(i);
            }
        }
        return result;
    }

    @Override
    public NginxResult status(int idNginx) {
        NginxNode nginxNode = null;
        for (int i = 0; i < list.size(); i++) {
            if (idNginx==list.get(i).getIdNginx()){
                nginxNode = list.get(i);
            }
        }
        final NginxResult nginxResult = new NginxResult();
        nginxResult.setResult(new Result<String>(0,"",0,null));
        nginxResult.setNginxStatus(new NginxStatus());
        nginxAgentClient.api(NginxAgentClientApis.status()).withAuthorizationKey(nginxNode.getKeyNginx())
                .withEndpoint(nginxNode.getEndPointNginx())
                .build().status().thenAccept(nginxResponse -> {
            if (nginxResponse.error()) {
                NginxExceptionResponse nginxExceptionResponse = (NginxExceptionResponse) nginxResponse;
                nginxResult.getResult().msg = nginxExceptionResponse.getStackTrace();
            } else if (nginxResponse.forbidden()) {
                NginxAuthenticationFailResponse nginxAuthenticationFailResponse = (NginxAuthenticationFailResponse) nginxResponse;
                nginxResult.getResult().msg = nginxAuthenticationFailResponse.getMessage();
            } else {
                NginxStatusResponse nginxStatusResponse = (NginxStatusResponse) nginxResponse;
                nginxResult.getNginxStatus().setAccepts(nginxStatusResponse.getAccepts());
                nginxResult.getNginxStatus().setActive(nginxStatusResponse.getActiveConnection());
                nginxResult.getNginxStatus().setHandled(nginxStatusResponse.getHandled());
                nginxResult.getNginxStatus().setReading(nginxStatusResponse.getReading());
                nginxResult.getNginxStatus().setRequests(nginxStatusResponse.getRequests());
                nginxResult.getNginxStatus().setWaiting(nginxStatusResponse.getWaiting());
                nginxResult.getNginxStatus().setWriting(nginxStatusResponse.getWriting());
            }
        }).join();
        return nginxResult;
    }
}

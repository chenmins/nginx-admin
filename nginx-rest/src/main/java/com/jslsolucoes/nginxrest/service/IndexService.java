package com.jslsolucoes.nginxrest.service;

import com.jslsolucoes.nginxrest.pojo.NginxNode;
import com.jslsolucoes.nginxrest.pojo.NginxResult;
import com.jslsolucoes.nginxrest.util.Result;

import java.util.List;

public interface IndexService {

    public List<NginxNode> selectByExample(NginxNode nginxNode);
    public NginxNode selectById(int idNginx);
    public NginxResult status(int idNginx);

}

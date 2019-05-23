package com.jslsolucoes.nginxrest.pojo;

import com.jslsolucoes.nginx.admin.agent.client.api.impl.status.NginxStatus;
import com.jslsolucoes.nginxrest.util.Result;

public class NginxNode {

    private int idNginx;
    private String nameNginx;
    private String endPointNginx;
    private String keyNginx;

    public NginxNode(int idNginx, String nameNginx, String endPointNginx, String keyNginx) {
        this.idNginx = idNginx;
        this.nameNginx = nameNginx;
        this.endPointNginx = endPointNginx;
        this.keyNginx = keyNginx;
    }

    public String getEndPointNginx() {
        return endPointNginx;
    }

    public void setEndPointNginx(String endPointNginx) {
        this.endPointNginx = endPointNginx;
    }

    public String getKeyNginx() {
        return keyNginx;
    }

    public void setKeyNginx(String keyNginx) {
        this.keyNginx = keyNginx;
    }

    public NginxNode() {
    }

    public int getIdNginx() {
        return idNginx;
    }

    public void setIdNginx(int idNginx) {
        this.idNginx = idNginx;
    }

    public String getNameNginx() {
        return nameNginx;
    }

    public void setNameNginx(String nameNginx) {
        this.nameNginx = nameNginx;
    }
}

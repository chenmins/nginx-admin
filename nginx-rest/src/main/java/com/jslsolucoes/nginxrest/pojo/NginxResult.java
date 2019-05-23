package com.jslsolucoes.nginxrest.pojo;

import com.jslsolucoes.nginxrest.util.Result;

public class NginxResult {
    private Result<String> result;
    private NginxStatus nginxStatus;

    public Result<String> getResult() {
        return result;
    }

    public void setResult(Result<String> result) {
        this.result = result;
    }

    public NginxStatus getNginxStatus() {
        return nginxStatus;
    }

    public void setNginxStatus(NginxStatus nginxStatus) {
        this.nginxStatus = nginxStatus;
    }
}

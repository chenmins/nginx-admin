package com.jslsolucoes.nginxrest.util;

import java.util.List;

public class Result<T> {
    public int code;
    public String msg;
    public long total;
    public List<T> data;

    public Result(int code, String msg, long total, List<T> data) {
        this.code = code;
        this.msg = msg;
        this.total = total;
        this.data = data;
    }

}

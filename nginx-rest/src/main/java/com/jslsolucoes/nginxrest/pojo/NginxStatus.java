package com.jslsolucoes.nginxrest.pojo;

public class NginxStatus {
    private Integer active;
    private Integer accepts;
    private Integer handled;
    private Integer requests;
    private Integer reading;
    private Integer writing;
    private Integer waiting;

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Integer getAccepts() {
        return accepts;
    }

    public void setAccepts(Integer accepts) {
        this.accepts = accepts;
    }

    public Integer getHandled() {
        return handled;
    }

    public void setHandled(Integer handled) {
        this.handled = handled;
    }

    public Integer getRequests() {
        return requests;
    }

    public void setRequests(Integer requests) {
        this.requests = requests;
    }

    public Integer getReading() {
        return reading;
    }

    public void setReading(Integer reading) {
        this.reading = reading;
    }

    public Integer getWriting() {
        return writing;
    }

    public void setWriting(Integer writing) {
        this.writing = writing;
    }

    public Integer getWaiting() {
        return waiting;
    }

    public void setWaiting(Integer waiting) {
        this.waiting = waiting;
    }
}

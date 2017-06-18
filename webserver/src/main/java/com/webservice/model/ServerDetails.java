package com.webservice.model;

/**
 * Created by hagairevah on 6/2/17.
 */
public class ServerDetails {
    private String server;
    private long now;
    private String status;
    private boolean alive;
    private String git_revision;

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public long getNow() {
        return now;
    }

    public void setNow(long now) {
        this.now = now;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public String getGit_revision() {
        return git_revision;
    }

    public void setGit_revision(String git_revision) {
        this.git_revision = git_revision;
    }
//    private List circuits;


}

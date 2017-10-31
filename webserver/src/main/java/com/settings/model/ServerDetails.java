package com.settings.model;

/**
 * Created by hagairevah on 6/2/17.
 */
public class ServerDetails {
    private String server;
    private boolean alive;
    private String git_revision;
    private String commit;

    public String getCommit() {
        return commit;
    }

    public void setCommit(String commit) {
        this.commit = commit;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
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

package com.settings.model;

/**
 * Created by hagairevah on 11/5/17.
 */
public class CIDetails {

    String id;
    String name;
    String git_repo;
    boolean cd;
    String language;
    boolean docker;
    String docker_url;
    boolean enable;
    ServiceOwner owner;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGit_repo() {
        return git_repo;
    }

    public void setGit_repo(String git_repo) {
        this.git_repo = git_repo;
    }

    public boolean isCd() {
        return cd;
    }

    public void setCd(boolean cd) {
        this.cd = cd;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public boolean isDocker() {
        return docker;
    }

    public void setDocker(boolean docker) {
        this.docker = docker;
    }

    public String getDocker_url() {
        return docker_url;
    }

    public void setDocker_url(String docker_url) {
        this.docker_url = docker_url;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public ServiceOwner getOwner() {
        return owner;
    }

    public void setOwner(ServiceOwner owner) {
        this.owner = owner;
    }
}

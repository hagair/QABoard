package com.settings.model;

/**
 * Created by hagairevah on 11/5/17.
 */
public class ServiceOwner {
    int id;
    String name;
    String github_user;
    String slack_user;
    String email;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGithub_user() {
        return github_user;
    }

    public void setGithub_user(String github_user) {
        this.github_user = github_user;
    }

    public String getSlack_user() {
        return slack_user;
    }

    public void setSlack_user(String slack_user) {
        this.slack_user = slack_user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

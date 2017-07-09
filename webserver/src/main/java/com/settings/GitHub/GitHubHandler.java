package com.settings.GitHub;
import org.kohsuke.github.*;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hagairevah on 7/5/17.
 */
public class GitHubHandler {
//a636ce7f6b66f469d50072c97b2d0e3f5010aba2

    private static Map<String, String> searchQuery = new HashMap<String, String>();

    public static void main(String[] args) throws IOException {
        GitHub github = GitHub.connect();
        GHRepository ghRepository = github.getRepository("gtforge/gtforge_server");
        GHCommit ghCommit = ghRepository.getCommit("71479b8802d3c7ff33ba1f2828e95ba399b8b5e2");
        System.out.println(ghCommit.getOwner());

    }

}

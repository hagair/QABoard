package com.settings.GitHub;
import org.kohsuke.github.*;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

/**
 * Created by hagairevah on 7/5/17.
 */
public class GitHubHandler {
//a636ce7f6b66f469d50072c97b2d0e3f5010aba2

    private static Map<String, String> searchQuery = new HashMap<String, String>();

    public static void main(String[] args) throws IOException {
        String charset = "UTF-8";
        GitHub github = GitHub.connect();
        GHRepository ghRepository = github.getRepository("gtforge/gtforge_server");
        Map<String,GHBranch> branches = ghRepository.getBranches();
        Set<String> branchlist = branches.keySet();

        for (String branchname : branchlist) {
            GHBranch ghBranch = branches.get(branchname);
            System.out.println();
        }

//        GHCommit ghCommit = ghRepository.getCommit("4fe42e18984a10c8e98d5fb6942662483bc75560");
        System.out.println(branchlist.size());
//        String url = "https ://github.com/gtforge/gtforge_server/commit/4fe42e18984a10c8e98d5fb6942662483bc75560";
//        String url = "http://www.ynet.co.il/home/0,7340,L-8,00.html";
//        URLConnection connection = new URL(url).openConnection();
//        connection.setRequestProperty("Accept-Charset", charset);
//        BufferedReader br = new BufferedReader(
//                new InputStreamReader(connection.getInputStream()));
//
//        String inputLine;
//        while ((inputLine = br.readLine()) != null) {
//            System.out.println(inputLine);
//        }
//        br.close();

    }


}

package com.example.emmanueladeleke.studentform;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class AuthUser {

//    private String username;
//    private String password;
//
//    public AuthUser(String username, String password) {
//        this.username = username;
//        this.password = password;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public boolean authenticate() {
//
//        String strJson;
//        URL url = null;
//        BufferedReader in = null;
//        try {
//            url = new URL("http://emmanueladeleke.ddns.net:3000/otm/lecturer?query={\"username\":\"" + username + "\",\"password\":\"" + password + "\"}");
//            in = new BufferedReader(new InputStreamReader(url.openStream()));
//        }
//        catch(Exception e) {
//            e.printStackTrace();
//        }
//
//        String inputLine;
//        StringBuilder builder = new StringBuilder();
//        try {
//            while ((inputLine = in.readLine()) != null)
//                builder.append(inputLine);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        try {
//            in.close();
//        } catch (IOException e) {
//
//            e.printStackTrace();
//        }
//
//        strJson = builder.toString().replaceAll("\\s+","");
//        System.out.println();
//
//        if (strJson.equals("[]")) {
//            return false;
//        }
//        else {
//            createFile(strJson);
//            return true;
//        }
//    }
//
//    public void createFile(String json) {
//        try {
//            FileWriter file = new FileWriter("user.json");
//            file.write(json);
//            file.close();
//        } catch (IOException e) {
//            System.out.println("Cannot create file");
//            e.printStackTrace();
//        }
//    }
}
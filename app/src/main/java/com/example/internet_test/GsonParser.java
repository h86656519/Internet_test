package com.example.internet_test;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;


public class GsonParser {
    public ArrayList<Repo> parse(String jsonString){
        Gson gson = new Gson();
        ArrayList<Repo> person = gson.fromJson(jsonString, new TypeToken<ArrayList<Repo>>(){}.getType());
//        for(Repo p : person){
//            System.out.println("ID :" + p.getId());
//            System.out.println("Name :" + p.getName());
//            System.out.println("NodeId :" + p.getNodeId());
//            System.out.println("fullname :" + p.getFullName());
//        }
        return person;
    }

    public ArrayList<HttpHelper.Response> parse1(String jsonString){
        Gson gson = new Gson();
        ArrayList<HttpHelper.Response> person = gson.fromJson(jsonString, new TypeToken<ArrayList<Repo>>(){}.getType());
        return person;
    }

}

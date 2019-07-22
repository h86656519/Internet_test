package com.example.okhttp;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;


public class GsonParser {
    public ArrayList<GithubRepo> parse(String jsonString){
        //Log.i("suvini", "GsonParser : " + jsonString);
        Gson gson = new Gson();
        ArrayList<GithubRepo> person = gson.fromJson(jsonString, new TypeToken<ArrayList<GithubRepo>>(){}.getType());
        return person;
    }
}

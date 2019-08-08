package com.example.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GithubRepo {
    @SerializedName("id") //gson 是透過這個去取得資料
    @Expose
    private String id = "";

    @SerializedName("name")
    @Expose
    private String name = "";
    @SerializedName("node_id")
    @Expose
    private String node_id = "";

    @SerializedName("full_name")
    @Expose
    private String full_name = ""; //用gson 變數不能亂取，要跟資料一樣

    @SerializedName("owner")
    @Expose
    private InsideObject owner;

    public void setfull_name(String full_name) {
        this.full_name = full_name;
    }

    public void setNodeId(String nodeId) {
        this.node_id = nodeId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getId(){
        return id;
    }

    public String getFull_name() {
        return full_name;
    }

    public String getNodeId() {
        return node_id;
    }

    public InsideObject getOwner() {
        return owner;
    }
    @Override
    public String toString() {
       return new StringBuilder().append("id : ").append(getId()).append(",name : ").append(getName()).append(",node_id : ").append(getNodeId()).append(",full_name :").append(getFull_name()).append("\n").append("Owner : ").append(getOwner()).toString();
      //  return new StringBuilder().append("Owner : ").append(getOwner()).toString();
    }
}

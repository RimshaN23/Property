package com.example.property.models;

public class Agents {

    private String client_name;
    private String client_no;

    public Agents() {
    }

    public Agents(String client_name, String client_no) {
        this.client_name = client_name;
        this.client_no = client_no;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public String getClient_no() {
        return client_no;
    }

    public void setClient_no(String client_no) {
        this.client_no = client_no;
    }
}

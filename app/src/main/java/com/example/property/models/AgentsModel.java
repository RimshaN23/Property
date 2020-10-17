package com.example.property.models;

public class AgentsModel {

    private String agent_name;
    private String agent_no;
    private String agent_id;


    public AgentsModel() {
    }

    public AgentsModel(String agent_name, String agent_no, String agent_id) {
        this.agent_name = agent_name;
        this.agent_no = agent_no;
        this.agent_id = agent_id;
    }

    public String getAgent_id() {
        return agent_id;
    }

    public void setAgent_id(String agent_id) {
        this.agent_id = agent_id;
    }

    public String getAgent_name() {
        return agent_name;
    }

    public void setAgent_name(String agent_name) {
        this.agent_name = agent_name;
    }

    public String getAgent_no() {
        return agent_no;
    }

    public void setAgent_no(String agent_no) {
        this.agent_no = agent_no;
    }
}

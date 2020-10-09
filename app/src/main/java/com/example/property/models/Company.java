package com.example.property.models;

public class Company {

    AgentsModel AgentsObject;
    private float companny_id;
    private String company_name;

    public Company() {
    }

    public Company(AgentsModel agentsObject, float companny_id, String company_name) {
        AgentsObject = agentsObject;
        this.companny_id = companny_id;
        this.company_name = company_name;
    }

    public AgentsModel getAgentsObject() {
        return AgentsObject;
    }

    public void setAgentsObject(AgentsModel agentsObject) {
        AgentsObject = agentsObject;
    }

    public float getCompanny_id() {
        return companny_id;
    }

    public void setCompanny_id(float companny_id) {
        this.companny_id = companny_id;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }
}

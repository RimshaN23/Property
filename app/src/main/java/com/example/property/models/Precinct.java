package com.example.property.models;

public class Precinct {

    private String name;
    private String precinct_id;
    private String property_type_id;

    public Precinct() {
    }

    public Precinct(String name, String precinct_id, String property_type_id) {
        this.name = name;
        this.precinct_id = precinct_id;
        this.property_type_id = property_type_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrecinct_id() {
        return precinct_id;
    }

    public void setPrecinct_id(String precinct_id) {
        this.precinct_id = precinct_id;
    }

    public String getProperty_type_id() {
        return property_type_id;
    }

    public void setProperty_type_id(String property_type_id) {
        this.property_type_id = property_type_id;
    }
}

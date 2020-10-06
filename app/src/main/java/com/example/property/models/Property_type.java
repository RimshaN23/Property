package com.example.property.models;

public class Property_type {


    String property_type_id, name;

    public Property_type() {
    }

    public Property_type(String property_type_id, String name) {
        this.property_type_id = property_type_id;
        this.name = name;
    }

    public String getProperty_type_id() {
        return property_type_id;
    }

    public void setProperty_type_id(String property_type_id) {
        this.property_type_id = property_type_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

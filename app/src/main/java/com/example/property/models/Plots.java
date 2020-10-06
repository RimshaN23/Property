package com.example.property.models;

public class Plots {

    private String precinct_id;
    private String property_type_id;
    private String road_id;
    private String name;
    private String address;
    private String sq_yrds;
    private String rooms;
    private String stories;
    private String geo_location;

    public Plots() {
    }

    public Plots(String precinct_id, String property_type_id, String road_id, String name, String address, String sq_yrds, String rooms, String stories, String geo_location) {
        this.precinct_id = precinct_id;
        this.property_type_id = property_type_id;
        this.road_id = road_id;
        this.name = name;
        this.address = address;
        this.sq_yrds = sq_yrds;
        this.rooms = rooms;
        this.stories = stories;
        this.geo_location = geo_location;
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

    public String getRoad_id() {
        return road_id;
    }

    public void setRoad_id(String road_id) {
        this.road_id = road_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSq_yrds() {
        return sq_yrds;
    }

    public void setSq_yrds(String sq_yrds) {
        this.sq_yrds = sq_yrds;
    }

    public String getRooms() {
        return rooms;
    }

    public void setRooms(String rooms) {
        this.rooms = rooms;
    }

    public String getStories() {
        return stories;
    }

    public void setStories(String stories) {
        this.stories = stories;
    }

    public String getGeo_location() {
        return geo_location;
    }

    public void setGeo_location(String geo_location) {
        this.geo_location = geo_location;
    }
}

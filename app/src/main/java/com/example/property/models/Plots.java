package com.example.property.models;

public class Plots {


    private String precinct_id;
    private String property_type_id;
    private String road_id;
    private String name;
    private double latitude;
    private double longitude;
    private String sq_yrds;
    private String rooms;
    private String stories;
    private String company_id;
    private String plot_no;
    private String constructed;
    private String is_sold;
    private String agent_id;
    private String agent_name;
    private String plot_price_range_from;
    private String plot_price_range_to;

    public Plots() {
    }

    public Plots(String precinct_id, String property_type_id, String road_id, String name, double latitude, double longitude, String sq_yrds,
                 String rooms, String stories, String company_id, String plot_no, String constructed, String is_sold,
                 String agent_id, String agent_name, String plot_price_range_from, String plot_price_range_to) {
        this.precinct_id = precinct_id;
        this.property_type_id = property_type_id;
        this.road_id = road_id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.sq_yrds = sq_yrds;
        this.rooms = rooms;
        this.stories = stories;
        this.company_id = company_id;
        this.plot_no = plot_no;
        this.constructed = constructed;
        this.is_sold = is_sold;
        this.agent_id = agent_id;
        this.agent_name = agent_name;
        this.plot_price_range_from = plot_price_range_from;
        this.plot_price_range_to = plot_price_range_to;
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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
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

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public String getplot_no() {
        return plot_no;
    }

    public void setplot_no(String plot_no) {
        this.plot_no = plot_no;
    }

    public String getConstructed() {
        return constructed;
    }

    public void setConstructed(String constructed) {
        this.constructed = constructed;
    }

    public String getIs_sold() {
        return is_sold;
    }

    public void setIs_sold(String is_sold) {
        this.is_sold = is_sold;
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

    public String getPlot_price_range_from() {
        return plot_price_range_from;
    }

    public void setPlot_price_range_from(String plot_price_range_from) {
        this.plot_price_range_from = plot_price_range_from;
    }

    public String getPlot_price_range_to() {
        return plot_price_range_to;
    }

    public void setPlot_price_range_to(String plot_price_range_to) {
        this.plot_price_range_to = plot_price_range_to;
    }

    @Override
    public String toString() {
        return name;
    }

}

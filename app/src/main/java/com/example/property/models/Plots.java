package com.example.property.models;

import java.util.ArrayList;

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
    private String agent_number;
    private String plot_price_range_from;
    private ArrayList<String> imageUrl;

    private ArrayList<String> cnic_images;
    private String client_name;
    private String client_number;
    private String client_cnic;
    private String sold_price;
    String is_file;
    private ArrayList<String> fileUrl;


    String date;

    public Plots(String precinct_id, String property_type_id, String road_id, String name, double latitude, double longitude, String sq_yrds,
                 String rooms, String stories, String company_id, String plot_no, String constructed, String is_sold,
                 String agent_id, String agent_name, String plot_price_range_from, ArrayList<String> imageUrl,
                 ArrayList<String> cnic_images, String client_name, String client_number, String client_cnic, String sold_price,String is_file) {
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
        this.imageUrl = imageUrl;
        this.cnic_images = cnic_images;
        this.client_name = client_name;
        this.client_number = client_number;
        this.client_cnic = client_cnic;
        this.sold_price = sold_price;
        this.is_file= is_file;
    }

    public Plots() {
    }

    public Plots(String precinct_id, String property_type_id, String road_id, String name, double latitude, double longitude, String sq_yrds,
                 String rooms, String stories, String company_id, String plot_no, String constructed, String is_sold,
                 String agent_id, String agent_name,String agent_number, String plot_price_range_from, ArrayList<String> imageUrl,
                 String is_file, ArrayList<String> fileUrl, String date) {
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
        this.agent_number = agent_number;
        this.plot_price_range_from = plot_price_range_from;
        this.imageUrl = imageUrl;
        this.is_file= is_file;
        this.fileUrl= fileUrl;
        this.date= date;
    }


    public Plots(String name, String rooms, String stories, String constructed, String plot_price_range_from) {
        this.name = name;
        this.rooms = rooms;
        this.stories = stories;
        this.constructed = constructed;
        this.plot_price_range_from = plot_price_range_from;
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


    public ArrayList<String> getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(ArrayList<String> imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return name;
    }

    public ArrayList<String> getCnic_images() {
        return cnic_images;
    }

    public void setCnic_images(ArrayList<String> cnic_images) {
        this.cnic_images = cnic_images;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public String getClient_number() {
        return client_number;
    }

    public void setClient_number(String client_number) {
        this.client_number = client_number;
    }

    public String getClient_cnic() {
        return client_cnic;
    }

    public void setClient_cnic(String client_cnic) {
        this.client_cnic = client_cnic;
    }

    public String getSold_price() {
        return sold_price;
    }

    public void setSold_price(String sold_price) {
        this.sold_price = sold_price;
    }

    public String getAgent_number() {
        return agent_number;
    }

    public void setAgent_number(String agent_number) {
        this.agent_number = agent_number;
    }

//    public String getPlot_no() {
//        return plot_no;
//    }

//    public void setPlot_no(String plot_no) {
//        this.plot_no = plot_no;
//    }

    public String getIs_file() {
        return is_file;
    }

    public void setIs_file(String is_file) {
        this.is_file = is_file;
    }

    public ArrayList<String> getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(ArrayList<String> fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

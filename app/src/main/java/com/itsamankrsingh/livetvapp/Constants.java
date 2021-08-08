package com.itsamankrsingh.livetvapp;

public class Constants {
    public static final String CATEGORIES_URL="http://192.168.43.198/mytv/api.php?key=1A4mgi2rBHCJdqggsYVx&id=1&categories=all";
    public static final String ALL_CHANNELS_URL="http://192.168.43.198/mytv/api.php?key=1A4mgi2rBHCJdqggsYVx&id=1&channels=all";
    public static final String NEWS_CHANNELS_URL="http://192.168.43.198/mytv/api.php?key=1A4mgi2rBHCJdqggsYVx&id=1&cat=News";
    public static final String SPORTS_CHANNELS_URL="http://192.168.43.198/mytv/api.php?key=1A4mgi2rBHCJdqggsYVx&id=1&cat=Sports";
    public static final String ENTERTAINMENT_CHANNELS_URL= "http://192.168.43.198/mytv/api.php?key=1A4mgi2rBHCJdqggsYVx&id=1&cat=Entertainment";


    public static final String DETAILS_TYPE="details";
    public static final String SLIDER_TYPE="slider";
    public static final String CATEGORY_TYPE="category";


    public static String getCategoriesUrl(String categoryName){
        String url="http://192.168.43.198/mytv/api.php?key=1A4mgi2rBHCJdqggsYVx&id=1&cat="+categoryName;
        return url;
    }
}

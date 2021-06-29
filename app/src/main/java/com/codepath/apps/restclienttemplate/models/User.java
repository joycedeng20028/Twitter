package com.codepath.apps.restclienttemplate.models;

import androidx.room.ColumnInfo;

import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

@Parcel
public class User {

    public String name;
    public String screenName;
    public String profileImageUrl;

    public User() {} //for parceler

    public static User fromJson(JSONObject jsonObject) throws JSONException {
        User user = new User();
        user.name = jsonObject.getString("name");
        user.screenName = jsonObject.getString("screen_name");
        user.profileImageUrl = jsonObject.getString("profile_image_url_https");
        return user;
    }
//    @ColumnInfo
//    String name;
//
//    // normally this field would be annotated @PrimaryKey because this is an embedded object
//    // it is not needed
//    @ColumnInfo
//    Long twitter_id;

//    public static User parseJSON(JSONObject tweetJson) {
//
//        User user = new User();
//        this.twitter_id = tweetJson.getLong("id");
//        this.name = tweetJson.getString("name");
//        return user;
//    }

}

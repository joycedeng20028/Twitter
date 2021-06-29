//package com.codepath.apps.restclienttemplate.models;
//
//import androidx.room.Dao;
//import androidx.room.Insert;
//import androidx.room.OnConflictStrategy;
//import androidx.room.Query;
//
//import java.util.List;
//
//public interface TwitterDao {
//    // Record finders
//    @Query("SELECT * FROM Tweet WHERE post_id = :tweetId")
//    Tweet byTweetId(Long tweetId);
//
//    @Query("SELECT * FROM Tweet ORDER BY created_at")
//    List<Tweet> getRecentTweets();
//
//    // Replace strategy is needed to ensure an update on the table row.  Otherwise the insertion will
//    // fail.
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    void insertTweet(Tweet... tweets);
//}

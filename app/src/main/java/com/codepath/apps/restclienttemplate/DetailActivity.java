package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class DetailActivity extends AppCompatActivity implements TweetsAdapter.OnTweetClickListener {

    ImageButton btnRetweet;
    ImageButton btnLike;
    ImageButton btnMessage;
    ImageView ivMain;
    Tweet tweet;
    ImageView ivProfileImage;
    TextView tvBody;
    TextView tvScreenName;
    TextView tvName;

    TwitterClient client;
    List<Tweet> tweets;
    TweetsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        client = TwitterApp.getRestClient(this);

        btnRetweet = findViewById(R.id.btnRetweet);
        btnLike = findViewById(R.id.btnLike);
        btnMessage = findViewById(R.id.btnMessage);
        ivMain = findViewById(R.id.imageView);
        ivProfileImage = findViewById(R.id.ivTweetProfile);
        tvBody = findViewById(R.id.tvBody);
        tvScreenName = findViewById(R.id.tvTweetHandle);
        tvName = findViewById(R.id.tvTweetName);

        tweets = new ArrayList<>();
        adapter = new TweetsAdapter(this, this, tweets);

        Intent intent = getIntent();
        tweet = Parcels.unwrap(intent.getParcelableExtra("tweet"));

        Glide.with(this).load(tweet.user.profileImageUrl).into(ivProfileImage);
        Glide.with(this).load(tweet.media).into(ivMain);
        tvBody.setText(tweet.body);
        tvScreenName.setText(String.format("@%s", tweet.user.screenName));
        tvName.setText(tweet.user.name);
        tvBody.setText(tweet.body);
    }

    @Override
    public void onItemClick(View itemView, int position) {

    }

    @Override
    public void onLikeClick(final int pos, boolean isChecked) {
        if (!isChecked) {
            client.likeTweet(tweets.get(pos).id, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Headers headers, JSON json) {
                    tweets.get(pos).like = true;
                }

                @Override
                public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                }
            });
        } else {
            client.unlikeTweet(tweets.get(pos).id, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Headers headers, JSON json) {
                    tweets.get(pos).like = false;
                }

                @Override
                public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                }
            });
        }
    }

    @Override
    public void onRetweet(final int pos, boolean isChecked) {
        if (!isChecked) {
            client.retweet(tweets.get(pos).id, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Headers headers, JSON json) {
                    tweets.get(pos).retweet = true;
                }

                @Override
                public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                }
            });
        }
    }
}
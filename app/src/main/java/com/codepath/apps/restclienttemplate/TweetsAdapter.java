package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.List;

public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder> {
    private OnTweetClickListener listener;
    Context context;
    List<Tweet> tweets;
    // pass in context and list of tweets

    public interface OnTweetClickListener {
        void onItemClick(View itemView, int position);
        void onLikeClick(int pos, boolean isChecked);
        void onRetweet(int pos, boolean isChecked);
    }

    public TweetsAdapter (OnTweetClickListener listener, Context context, List<Tweet> tweets) {
        this.context = context;
        this.tweets = tweets;
        this.listener = listener;
    }

    public void setOnItemClickListener(OnTweetClickListener listener) {
        this.listener = listener;
    }

    public TweetsAdapter(Context context, List<Tweet> tweets) {
        this.context = context;
        this.tweets = tweets;
    }

    // for each row, inflate layout
    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tweet, parent, false);
        return new ViewHolder(view, listener);
    }

    // bind values based on position of element
    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        //get data at position
        Tweet tweet = tweets.get(position);

        //bind tweet with viewholder
        holder.bind(tweet);
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }

    // Clean all elements of the recycler
    public void clear() {
        tweets.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Tweet> list) {
        tweets.addAll(list);
        notifyDataSetChanged();
    }

    // define viewholder
    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivProfileImage;
        TextView tvBody;
        TextView tvScreenName;
        TextView tvTimestamp;
        ImageView ivMedia;
        ImageButton btnRetweet;
        ImageButton btnLike;


        public ViewHolder(@NonNull @NotNull final View itemView, final OnTweetClickListener clickListener) {
            super(itemView);
            ivProfileImage = itemView.findViewById(R.id.ivProfileImage);
            tvBody = itemView.findViewById(R.id.tvBody);
            tvScreenName = itemView.findViewById(R.id.tvScreenName);
            tvTimestamp = itemView.findViewById(R.id.tvTimestamp);
            ivMedia = itemView.findViewById(R.id.ivMedia);
            btnRetweet = itemView.findViewById(R.id.btnRetweet);
            btnLike = itemView.findViewById(R.id.btnLike);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onItemClick(itemView, getAdapterPosition());
                }
            });
        }

        public void bind(final Tweet tweet) {
            tvBody.setText(tweet.body);
            tvScreenName.setText(tweet.user.screenName);
            tvTimestamp.setText(tweet.getRelativeTimeAgo(tweet.createdAt));
            Glide.with(context).load(tweet.user.profileImageUrl).into(ivProfileImage);
            Glide.with(context).load(tweet.media).into(ivMedia);
            if(tweet.like) {
                btnLike.setImageResource(R.drawable.ic_vector_heart_stroke);
            }
            if(tweet.retweet) {
                btnRetweet.setImageResource(R.drawable.ic_vector_retweet);
            }

            btnLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!tweet.like) {
                        listener.onLikeClick(getLayoutPosition(), false);
                        btnLike.setImageResource(R.drawable.ic_vector_heart);
                    } else {
                        listener.onLikeClick(getLayoutPosition(), true);
                        btnLike.setImageResource(R.drawable.ic_vector_heart_stroke);
                    }
                }
            });

            btnRetweet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!tweet.retweet) {
                        listener.onRetweet(getLayoutPosition(), false);
                        btnRetweet.setImageResource(R.drawable.ic_vector_retweet_stroke);
                    }
                }
            });
        }

    }
}

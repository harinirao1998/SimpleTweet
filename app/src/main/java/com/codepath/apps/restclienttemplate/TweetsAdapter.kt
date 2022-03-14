package com.codepath.apps.restclienttemplate

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.widget.TextView
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.codepath.apps.restclienttemplate.models.Tweet
import com.bumptech.glide.Glide
import org.w3c.dom.Text

class TweetsAdapter(val tweets: ArrayList<Tweet>) : RecyclerView.Adapter<TweetsAdapter.viewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TweetsAdapter.viewHolder {
       val context = parent.context
        val inflater = LayoutInflater.from(context)

        //inflate our item layout
        val view = inflater.inflate(R.layout.item_tweet, parent, false)
        return viewHolder(view)
    }

    //populating data into the item through the viewholder
    override fun onBindViewHolder(holder: TweetsAdapter.viewHolder, position: Int) {
       //get the data based on the position
        val tweet: Tweet = tweets.get(position)

        //set the item views based on views and data model
        holder.tvUserName.text=tweet.user?.name
        holder.tvTweetBody.text=tweet.body
        Glide.with(holder.itemView).load(tweet.user?.publicImageUrl).into(holder.ivProfileImage)

    }

    override fun getItemCount(): Int {
       return tweets.size
    }

    fun clear() {
        tweets.clear()
        notifyDataSetChanged()
    }

    fun addAll(tweetList: List<Tweet>) {
        tweets.addAll(tweetList)
        notifyDataSetChanged()
    }


    class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
            val ivProfileImage = itemView.findViewById<ImageView>(R.id.ivProfileImage)
            val tvUserName = itemView.findViewById<TextView>(R.id.tvUsername)
            val tvTweetBody = itemView.findViewById<TextView>(R.id.tvTweetBody)
    }


}
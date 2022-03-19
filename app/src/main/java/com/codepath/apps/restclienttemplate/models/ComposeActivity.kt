package com.codepath.apps.restclienttemplate.models

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.codepath.apps.restclienttemplate.R
import com.codepath.apps.restclienttemplate.TwitterApplication
import com.codepath.apps.restclienttemplate.TwitterClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.google.android.material.textfield.TextInputLayout
import okhttp3.Headers
import org.w3c.dom.Text

class ComposeActivity : AppCompatActivity() {

    lateinit var etCompose: EditText
    lateinit var btnTweet: Button
    lateinit var client: TwitterClient
    lateinit var tweetCount: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compose)
        etCompose = findViewById(R.id.editTextTweet) as EditText
        btnTweet = findViewById(R.id.btnTweet)
        tweetCount = findViewById(R.id.remaining)
        client = TwitterApplication.getRestClient(this)

//        tILayout = findViewById(R.id.tILayout)

        etCompose.addTextChangedListener(object : TextWatcher{
            val tweetContent = etCompose.text
//            val tweetCounter = tweetContent.length
//            val maxChar = 280
//            val charLeft = maxChar

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                if (tweetContent.isNotEmpty() && tweetContent.length <= 280){
//                    btnTweet.isEnabled = true
//                } else { btnTweet.isEnabled = false}
                btnTweet.isEnabled = tweetContent.isNotEmpty() && tweetContent.length <= 280
                tweetCount.text = (280 - etCompose.text.toString().trim().length).toString()
            }})




        btnTweet.setOnClickListener {
            val tweetContent = etCompose.text.toString()

            if (tweetContent.isEmpty()) {
                Toast.makeText(this, "Empty tweets not allowed", Toast.LENGTH_SHORT).show()
            } else
                if (tweetContent.length > 140) {
                    Toast.makeText(
                        this,
                        "Tweet is too long! Limit is 140 characters",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    client.publishTweet(tweetContent, object : JsonHttpResponseHandler() {
                        override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                                val tweet = Tweet.fromJson(json.jsonObject)
                                //val symbols = etCompose.length();
                                //val dis=280-symbols
                                val intent = Intent()
                                intent.putExtra("tweet", tweet)
                                setResult(RESULT_OK, intent)

                                finish()
                            }
                            override fun onFailure(
                                statusCode: Int,
                                headers: Headers?,
                                response: String?,
                                throwable: Throwable?
                            ) {
                                Log.e(TAG, "Failed to publish tweet", throwable)
                            }
                        })

                }

            }
        }
        companion object {
            val TAG = "ComposeActivity"
        }
    }



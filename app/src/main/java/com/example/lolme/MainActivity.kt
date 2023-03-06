package com.example.lolme

import android.content.Intent
import android.graphics.drawable.Drawable
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class MainActivity : AppCompatActivity() {
    var memeUrl: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadMeme();

        val next = findViewById<Button>(R.id.next)
        next.setOnClickListener({
            loadMeme()
        })

        val share = findViewById<Button>(R.id.share)
        share.setOnClickListener({
            shareMeme()
        })
    }


    private fun loadMeme(){
        val tvResult = findViewById<TextView>(R.id.tv_result)
        val memeImage = findViewById<ImageView>(R.id.memeImage)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        progressBar.visibility = View.VISIBLE
// Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url = "https://meme-api.com/gimme"

// Request a string response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
               memeUrl = response.getString("url")
                Glide.with(this).load(memeUrl).listener(object: RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility = View.GONE
                        return false
                    }
                }).into(memeImage)
                // Display the first 500 characters of the response string.
                tvResult.text = getString(R.string.nextInfo)
            },
            { tvResult.text = getString(R.string.errorMessage)})

// Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)
    }

    private fun nextMeme(view: View){
        loadMeme()
    }

    private fun shareMeme(){
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,  "$memeUrl")
        val chooser = Intent.createChooser(intent, "share")
        startActivity(chooser)
    }
}
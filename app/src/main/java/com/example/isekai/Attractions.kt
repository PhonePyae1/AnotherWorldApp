package com.example.isekai

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity

class Attractions : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_attraction)
        setSupportActionBar(findViewById(R.id.toolbar))
        val actionBar = supportActionBar

        if (actionBar != null) {
            actionBar.title = "Attractions"
        }

        actionBar!!.title="Attractions"

        actionBar.setDisplayHomeAsUpEnabled(true)

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            Snackbar.make(view, "Camera", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }
}
package com.example.isekai

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val actionBar = supportActionBar

        if (actionBar != null) {
            actionBar.title = "Attractions"
        }

        actionBar!!.title="Attractions"

        actionBar.setDisplayHomeAsUpEnabled(true)
    }
}
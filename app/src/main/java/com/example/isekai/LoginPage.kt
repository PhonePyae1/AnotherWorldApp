package com.example.isekai

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class LoginPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)
    }

    fun Login_btn_onClick(view: View) {
        val intent = Intent(this,SignupActivity::class.java)
        startActivity(intent)
    }
}
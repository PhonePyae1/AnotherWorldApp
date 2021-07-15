package com.example.isekai.writeNewDiary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.isekai.R
import kotlinx.android.synthetic.main.activity_new_diary1.*

class NewDiary1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_diary1)

        nextButton.setOnClickListener {
            val intent = Intent(this, NewDiary2::class.java)
            startActivity(intent)
        }
    }
}
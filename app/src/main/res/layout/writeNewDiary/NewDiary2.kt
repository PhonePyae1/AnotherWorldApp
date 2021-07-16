package com.example.isekai.writeNewDiary

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.isekai.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_new_diary2.*

class NewDiary2 : AppCompatActivity() {

    private lateinit var database : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_diary2)
        supportActionBar!!.title = "Diary"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        backButton.setOnClickListener {
            val intent = Intent(this, NewDiary1::class.java)
            startActivity(intent)
        }

        val intent = intent
        val str: String? = intent.getStringExtra("sent")




        submitButton.setOnClickListener {
            val location = str.toString()
            val title = title1.text.toString()
            val story = story1.text.toString()

            database = FirebaseDatabase.getInstance().getReference("Diary")
            val Diary = Diary(location, title, story)
            database.child(location).setValue(Diary)
        }
    }
}
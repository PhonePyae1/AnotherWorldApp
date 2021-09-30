package com.example.isekai.Diary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.isekai.R
import kotlinx.android.synthetic.main.activity_diary_details.*

class DiaryDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary_details)

        /**get Data*/
        val intent = intent
        val location = intent.getStringExtra("location")
        val title = intent.getStringExtra("title")
        val story = intent.getStringExtra("story")
        val image = intent.getStringExtra("image")

        /**call text and images*/
        detailTitle.text = title
        detailLocation.text = location
        detailStory.text = story
        detailImage.loadImage(image, getProgessDrawable(this))

        detailAppBar.setNavigationOnClickListener {
            finish()
        }

        detailAppBar.setOnMenuItemClickListener {
            val intent = Intent(this, EditDiaryDetails::class.java)
            startActivity(intent)
            true
        }
    }
}
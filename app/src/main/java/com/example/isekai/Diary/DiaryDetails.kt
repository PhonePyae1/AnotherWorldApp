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
        val title = intent.getStringExtra("title")
        val image = intent.getStringExtra("image")
        val location = intent.getStringExtra("attraction")
        val story = intent.getStringExtra("context")
        val timeStamp = intent.getStringExtra("timeStamp")

        /**call text and images*/
        detailTitle.text = title
        detailLocation.text = location
        detailStory.text = story
        detailImage.loadImage(image, getProgessDrawable(this))
        detailDate.text = timeStamp
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
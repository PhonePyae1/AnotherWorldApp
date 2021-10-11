package com.example.isekai.diaryDetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.isekai.R
import kotlinx.android.synthetic.main.activity_diary_details.*
import kotlinx.android.synthetic.main.activity_diary_details.detailImage
import kotlinx.android.synthetic.main.activity_diary_details.detailLocation
import kotlinx.android.synthetic.main.activity_diary_details.detailStory
import kotlinx.android.synthetic.main.activity_edit_diary_details.*

class EditDiaryDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_diary_details)

//        /**get Data*/
//        val intent = intent
//        val location = intent.getStringExtra("location")
//        val title = intent.getStringExtra("title")
//        val story = intent.getStringExtra("story")
//        val image = intent.getStringExtra("image")
//
//        /**call text and images*/
//        asdfasdf.setText(title)
//        detailLocation.text = location
//        detailStory.text = story
//        detailImage.loadImage(image, getProgessDrawable(this))
        editAppBar.setNavigationOnClickListener {
            finish()
        }

        editAppBar.setOnMenuItemClickListener {
            // save
            true
        }
    }

}
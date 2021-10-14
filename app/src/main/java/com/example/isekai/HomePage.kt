package com.example.isekai

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.isekai.BottomNav3Fragments.DiaryFragment
import com.example.isekai.BottomNav3Fragments.HomeFragment
import com.example.isekai.BottomNav3Fragments.ProfileFragment
import com.example.isekai.writeNewDiary.NewDiary1
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_signup.*


class HomePage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)


        val homeFragment = HomeFragment()
        val diaryFragment = DiaryFragment()
        val profileFragment = ProfileFragment()
        val bottomNav: BottomNavigationView = findViewById(R.id.bottom_navigation)


        makeCurrentFragment(homeFragment)

        bottomNav.setOnNavigationItemSelectedListener{
            when (it.itemId){
                R.id.navigation_map -> makeCurrentFragment(homeFragment)
                R.id.navigation_diary-> makeCurrentFragment(diaryFragment)
                R.id.navigation_profile-> makeCurrentFragment(profileFragment)

            }
            true
        }


        //David: this is to make sure that the diary fragment will jump back/ be displayed
        // correctly when the user finish submit diary
        if (intent.extras?.getInt("fragmentToLoad") != null) {

            when (intent.extras?.getInt("fragmentToLoad")) {
                0 -> {
                    makeCurrentFragment(homeFragment)
                    bottomNav.menu.getItem(0).isChecked = true;
                }

                1 -> {
                    makeCurrentFragment(diaryFragment)
                    bottomNav.menu.getItem(1).isChecked = true;
                }
                else -> {
                }

            }
        }

    }

    fun startNewDiary(view: View) {
        val intent = Intent(this, NewDiary1::class.java)
        startActivity(intent)
    }

    private fun makeCurrentFragment(fragment: Fragment) = supportFragmentManager.beginTransaction().apply {
        replace(R.id.fragment_container,fragment)
        commit()
    }
}
package com.example.isekai

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.view.MenuItem
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import com.example.isekai.BottomNav3Fragments.DiaryFragment
import com.example.isekai.BottomNav3Fragments.HomeFragment
import com.example.isekai.BottomNav3Fragments.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

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

    }

    private fun makeCurrentFragment(fragment: Fragment) = supportFragmentManager.beginTransaction().apply {
        replace(R.id.fragment_container,fragment)
        commit()
    }
}
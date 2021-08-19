package com.example.isekai.BottomNav3Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.isekai.R
import com.example.isekai.SignIn.LoginPage
import com.example.isekai.SignIn.SignupActivity
import com.example.isekai.writeNewDiary.NewDiary1
import com.google.android.gms.maps.SupportMapFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_profile.*


class ProfileFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_profile, container, false)
        // Inflate the layout for this fragment

        return v
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var str1:String? = ""
        var str2:String? = ""
        val data = arguments
        if (data!= null) {
            str1 = data.getString("myData")
            str2 = data.getString("myData1")
        }
        profileName.text = str1
        profileHashtag.text = str2

        btn_logOut.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(activity,SignupActivity::class.java))
            activity?.finish()
        }
    }



}
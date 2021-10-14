package com.example.isekai.BottomNav3Fragments

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.DocumentsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.isekai.EditPages.editProfile
import com.example.isekai.R
import com.example.isekai.SignIn.LoginPage
import com.example.isekai.SignIn.SignupActivity
import com.example.isekai.writeNewDiary.NewDiary1
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.FirebaseError
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.firestore.*
import com.google.firebase.firestore.auth.User
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_profile.*
import java.io.File


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

        layout7.setOnClickListener {
            val preferences = activity?.getSharedPreferences("checkbox", AppCompatActivity.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = preferences!!.edit()
            editor.putString("remember","false")
            editor.apply()

            FirebaseAuth.getInstance().signOut()
            Toast.makeText(
                activity,
                "Logged Out",
                Toast.LENGTH_SHORT).show()

            startActivity(Intent(activity,SignupActivity::class.java))
            activity?.finish()
        }
        editProfileBtn.setOnClickListener {
            startActivity(Intent(activity,editProfile::class.java))
            activity?.finish()
        }

        val user:FirebaseAuth = FirebaseAuth.getInstance()
        val userid = user.uid
        val db:FirebaseFirestore = FirebaseFirestore.getInstance()
        val storageRef = FirebaseStorage.getInstance().reference.child("userProfiles/$userid")
        val localfile = File.createTempFile("tempImage","jpg")
        storageRef.getFile(localfile).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
            profilePic.setImageBitmap(bitmap)
        }

        if (userid != null) {
            db.collection("users").document(userid).addSnapshotListener(object: EventListener<DocumentSnapshot>{
                override fun onEvent(value: DocumentSnapshot?, error: FirebaseFirestoreException?) {
                    var username: String? = value?.getString("name")
                    var bio:String? = value?.getString("bio")
                    var exp:String? = value?.get("exp") as String?
                    if (profileName != null) {
                        profileName.text = username
                    }
                    if (profileHashtag != null) {
                        profileHashtag.text = bio
                    }
                    if (experience != null) {
                        experience.text = exp
                    }
                }
            })
        }
    }
}
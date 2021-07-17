package com.example.isekai.BottomNav3Fragments

import android.content.ContentValues
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.isekai.HomePage
import com.example.isekai.R
import com.example.isekai.writeNewDiary.NewDiary1
import com.example.isekai.writeNewDiary.NewDiary2
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_maps.*
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment(),OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        map_View.onCreate(savedInstanceState)
        map_View.onResume()

        map_View.getMapAsync(this)
        fab_writediary.setOnClickListener{
            val intent = Intent(activity, NewDiary1::class.java)
            startActivity(intent)
        }


    }
    override fun onMapReady(googleMap: GoogleMap?) {
        mMap = googleMap ?: return
        mMap.uiSettings.isTiltGesturesEnabled = false
        mMap.uiSettings.isRotateGesturesEnabled = false

        val thailand = LatLngBounds(
            LatLng(13.529703902966224, 100.19708192091116),  // SW bounds
            LatLng(14.051180377758067, 100.93179255459721) // NE bounds
        )
       mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(thailand.center, 10f))

        mMap.setLatLngBoundsForCameraTarget(thailand)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_home, container, false)
    }


}
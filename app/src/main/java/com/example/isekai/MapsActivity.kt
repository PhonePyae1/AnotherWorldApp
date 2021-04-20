package com.example.isekai

import android.content.ContentValues
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*


class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMapClickListener ,GoogleMap.OnMarkerClickListener{

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            val success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.mapstyle));
            if (!success) {
                Log.e(ContentValues.TAG,"Error")
            }
        } catch (e: Resources.NotFoundException) {
            Log.e(ContentValues.TAG, "Can't find style. Error: ", e);
        }

        mMap = googleMap

        // Add a marker in Sydney and move the camera
        //val bangkok = LatLng(13.736717,100.523186)
        val bangkok = LatLngBounds(
            LatLng(13.736717,100.523186), LatLng(14.33,5.18))
        val city = LatLngBounds(
                LatLng(-35.0, 138.58), LatLng(-34.9, 138.61))

        mMap.setLatLngBoundsForCameraTarget(city)
        mMap.setOnMapClickListener{
            onMapClick(it)
        }
        mMap.setOnMarkerClickListener {
            onMarkerClick(it)
        }
    }

    override fun onMapClick(p0: LatLng) {
        mMap.addMarker(MarkerOptions().position(p0))
    }

    override fun onMarkerClick(p0: Marker): Boolean {
        val intent = Intent(this,Attractions::class.java)
        startActivity(intent)
        return true
    }


}
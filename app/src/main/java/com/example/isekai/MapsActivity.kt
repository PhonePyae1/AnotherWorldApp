package com.example.isekai

import android.content.ContentValues
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*


class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMapClickListener ,GoogleMap.OnInfoWindowClickListener{

    private lateinit var mMap: GoogleMap
    lateinit var marker: Marker
    lateinit var marker1: Marker

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
        val samutprakan = LatLng(13.5954,100.6072)
        val bangna = LatLng(13.6639,100.6132)
        //val bangkok = LatLngBounds(
            //LatLng(13.736717,100.523186), LatLng(14.33,5.18))
        //val city = LatLngBounds(
              //  LatLng(-35.0, 138.58), LatLng(-34.9, 138.61))
        //mMap.setLatLngBoundsForCameraTarget(city)
        marker = mMap.addMarker(MarkerOptions().position(samutprakan).title("Samut Prakan").snippet("Visited: 10"))
        marker1 = mMap.addMarker(MarkerOptions().position(bangna).title("Bangna").snippet("Visited: 5"))
        marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET))
        marker1.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE))

        mMap.setOnInfoWindowClickListener {
            onInfoWindowClick(it)

        }

        //mMap.moveCamera(CameraUpdateFactory.newLatLng(samutprakan))
        mMap.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(
                    13.5954,
                    100.6072
                ), 12.0f
            )
        )
        mMap.setOnMapClickListener{
            onMapClick(it)
        }
       // mMap.setOnMarkerClickListener {
            //onMarkerClick(it)
        //}
    }

    override fun onInfoWindowClick(p0: Marker) {
        Toast.makeText(this,"Info Window",Toast.LENGTH_LONG).show()
        val intent = Intent(this,Attractions::class.java)
        startActivity(intent)
    }

    override fun onMapClick(p0: LatLng) {
        mMap.addMarker(MarkerOptions().position(p0).title("New Area"))

    }
}
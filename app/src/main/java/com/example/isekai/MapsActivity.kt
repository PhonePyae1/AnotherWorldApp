package com.example.isekai

import android.content.ContentValues
import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnPolygonClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*
import kotlin.collections.ArrayList


class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMapClickListener,
    GoogleMap.OnMarkerClickListener, OnPolygonClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var database: DatabaseReference
    // [END declare_database_ref]

    fun initializeDbRef() {
        // [START initialize_database_ref]
        // [END initialize_database_ref]
        database = FirebaseDatabase.getInstance().reference;
    }

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
                    this, R.raw.mapstyle
                )
            );
            if (!success) {
                Log.e(ContentValues.TAG, "Error")
            }
        } catch (e: Resources.NotFoundException) {
            Log.e(ContentValues.TAG, "Can't find style. Error: ", e);
        }

        mMap = googleMap ?: return
        mMap.uiSettings.isTiltGesturesEnabled = false
        mMap.uiSettings.isRotateGesturesEnabled = false

        val thailand = LatLngBounds(
            LatLng(13.529703902966224, 100.19708192091116),  // SW bounds
            LatLng(14.051180377758067, 100.93179255459721) // NE bounds
        )
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(thailand.center, 10f))

        mMap.setMinZoomPreference(10.5f)
        mMap.setMaxZoomPreference(12.0f)

        mMap.setLatLngBoundsForCameraTarget(thailand)

        initializeDbRef()

        database.child("districts").get().addOnSuccessListener {
            Log.i("firebase", "Got value ${it.value} and ${it.value!!::class.simpleName}")

            for (x in it.children) {
                Log.d("firebase",x.value.toString())
                val polygon1 = googleMap.addPolygon(
                    PolygonOptions()
                        .clickable(true)
                        .addAll(
                            convert(x.value as String)
                        )
                )
                // Store a data object with the polygon, used here to indicate an arbitrary type.
                polygon1.tag = "bangna"
                // Style the polygon.
                val rnd = Random()
                val rndcolor: Int =
                    Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
                polygon1.strokeColor = 0x00ffffff
                polygon1.fillColor = rndcolor
            }
        }.addOnFailureListener {
            Log.e("firebase", "Error getting data", it)
        }



        mMap.setOnPolygonClickListener(this)
    }

    private fun convert(x: String): MutableList<LatLng> {

        val array = x.split(",")

        val points: MutableList<LatLng> = ArrayList()

        for (i in array) {
            val something = i.split(" ")
            points.add(LatLng(something[1].toDouble(), something[0].toDouble()))
        }
        return points
    }

    private fun getPolygonCenterPoint(polygonPointsList: List<LatLng>): LatLng? {
        var centerLatLng: LatLng? = null
        val builder: LatLngBounds.Builder = LatLngBounds.Builder()
        for (element in polygonPointsList) {
            builder.include(element)
        }
        val bounds: LatLngBounds = builder.build()
        centerLatLng = bounds.center
        return centerLatLng
    }

    override fun onPolygonClick(polygon: Polygon) {
        // Flip the values of the red, green, and blue components of the polygon's color.
        var color = polygon.strokeColor xor 0x00ffffff
        polygon.strokeColor = color
        color = polygon.fillColor xor 0x00ffffff
        polygon.fillColor = color



        mMap.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                getPolygonCenterPoint(polygon.points),
                12f
            )
        )
        Toast.makeText(this, "Area type ${polygon.tag?.toString()}", Toast.LENGTH_SHORT).show()
    }

    override fun onMapClick(p0: LatLng) {
        mMap.addMarker(MarkerOptions().position(p0))
    }

    override fun onMarkerClick(p0: Marker): Boolean {
        val intent = Intent(this, Attractions::class.java)
        startActivity(intent)
        return true
    }

}
package com.example.isekai.ThreeTabs

import android.content.ContentValues
import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.isekai.Attractions
import com.example.isekai.R

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class MapsFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMapClickListener,
    GoogleMap.OnPolygonClickListener, GoogleMap.OnInfoWindowClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var database: DatabaseReference
    // [END declare_database_ref]

    fun initializeDbRef() {
        // [START initialize_database_ref]
        // [END initialize_database_ref]
        database = FirebaseDatabase.getInstance().reference;
    }
    lateinit var marker: Marker
    lateinit var marker1: Marker
//    val REQUEST_LOCATION_PERMISSION:Int = 1

    override fun onMapReady(googleMap: GoogleMap) {
        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            val success = googleMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    getActivity(), R.raw.mapstyle));
            if (!success) {
                Log.e(ContentValues.TAG,"Error")
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

        mMap.setMinZoomPreference(8f)
//        mMap.setMaxZoomPreference(12.0f)

        mMap.setLatLngBoundsForCameraTarget(thailand)

        initializeDbRef()

//        val samutprakan = LatLng(13.5954,100.6072)
//        val bangna = LatLng(13.6639,100.6132)

//        val bangkok = LatLngBounds(
//            LatLng(13.736717,100.523186), LatLng(14.33,5.18))
//        val city = LatLngBounds(
//                LatLng(-35.0, 138.58), LatLng(-34.9, 138.61))
//        mMap.setLatLngBoundsForCameraTarget(city)
//        marker = mMap.addMarker(MarkerOptions().position(samutprakan).title("Samut Prakan").snippet("Visited: 10"))
//        marker1 = mMap.addMarker(MarkerOptions().position(bangna).title("Bangna").snippet("Visited: 5"))
//        marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET))
//        marker1.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE))

        mMap.setOnInfoWindowClickListener {
            onInfoWindowClick(it)
        }
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
//                polygon1.tag = "bangna"
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

        //mMap.moveCamera(CameraUpdateFactory.newLatLng(samutprakan))
//        mMap.animateCamera(
//            CameraUpdateFactory.newLatLngZoom(
//                LatLng(
//                    13.5954,
//                    100.6072
//                ), 12.0f
//            )
//        )
        mMap.setOnMapClickListener{
            onMapClick(it)
        }



        mMap.setOnPolygonClickListener(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
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
//        Toast.makeText(this, "Area type ${polygon.tag?.toString()}", Toast.LENGTH_SHORT).show()
    }

    override fun onInfoWindowClick(p0: Marker) {
//        Toast.makeText(this,"Info Window", Toast.LENGTH_LONG).show()
        val intent = Intent(getActivity(),Attractions::class.java)
        startActivity(intent)
    }

    override fun onMapClick(p0: LatLng) {
        mMap.addMarker(MarkerOptions().position(p0).title("New Area"))
    }
}
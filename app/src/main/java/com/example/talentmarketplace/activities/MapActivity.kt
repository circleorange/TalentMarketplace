package com.example.talentmarketplace.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.talentmarketplace.R

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.talentmarketplace.databinding.ActivityMapBinding
import com.example.talentmarketplace.models.Location
import com.google.android.gms.maps.model.Marker
import timber.log.Timber.i

class MapActivity: AppCompatActivity(),
    OnMapReadyCallback,
    GoogleMap.OnMarkerDragListener,
    GoogleMap.OnMarkerClickListener {

    private lateinit var map: GoogleMap
    private lateinit var binding: ActivityMapBinding
    private var location = Location()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        location = intent.extras?.getParcelable<Location>("location")!!

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment

        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val markerLocation = LatLng(location.lat, location.lng)
        val options = MarkerOptions()
            .title("Default Job Marker")
            .snippet("GPS: $markerLocation")
            .draggable(true)
            .position(markerLocation)

        map = googleMap
        map.addMarker(options)
        map.setOnMarkerDragListener(this)
        map.setOnMarkerClickListener(this)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(markerLocation, 16f))
        i("OnMapReady() - marker location: $markerLocation")
    }

    override fun onMarkerDrag(p0: Marker) {}
    override fun onMarkerDragStart(p0: Marker) {}

    override fun onMarkerDragEnd(p0: Marker) {
        location.lat = p0.position.latitude
        location.lng = p0.position.longitude
        location.zoom = map.cameraPosition.zoom
        i("onMarkerDragEnd() - marker location: $location")
    }

    override fun onBackPressed() {
        val resultIntent = Intent()
        resultIntent.putExtra("location", location)
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
        super.onBackPressed()
        i("onBackPressed() - marker location: $location")
    }

    override fun onMarkerClick(p0: Marker): Boolean {
        val markerLocation = LatLng(location.lat, location.lng)
        p0.snippet = "GPS: $markerLocation"
        return false
    }
}

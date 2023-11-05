package com.example.talentmarketplace.activities

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

class MapActivity: AppCompatActivity(), OnMapReadyCallback {

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

        mapFragment.getMapAsync(this) }

    override fun onMapReady(googleMap: GoogleMap) {
        val defaultLocation = LatLng(location.lat, location.lng)
        val options = MarkerOptions()
            .title("Default Job Marker")
            .snippet("GPS: $defaultLocation")
            .draggable(true)
            .position(defaultLocation)

        map = googleMap
        map.addMarker(options)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 16f)) }
}
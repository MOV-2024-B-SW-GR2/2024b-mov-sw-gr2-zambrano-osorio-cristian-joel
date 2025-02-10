package com.example.prueba_iib

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.prueba_iib.databinding.ActivityMapsBinding

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val latitud = intent.getDoubleExtra("latitud", 0.0)
        val longitud = intent.getDoubleExtra("longitud", 0.0)
        val nombre = intent.getStringExtra("nombre") ?: "Ubicación Cliente"


        with(mMap.uiSettings) {
            isZoomControlsEnabled = true  // Botones de zoom
            isZoomGesturesEnabled = true   // Zoom con gestos
            isCompassEnabled = true        // Brújula
            isMapToolbarEnabled = true     // Barra de herramientas de Google
        }


        val ubicacion = LatLng(latitud, longitud)


        mMap.addMarker(
            MarkerOptions()
                .position(ubicacion)
                .title(nombre)
                .snippet("Coordenadas: $latitud, $longitud")
        )

        // Mover cámara y aplicar zoom
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacion, 15f))


        mMap.setOnMarkerClickListener { marker ->
            marker.showInfoWindow()
            true
        }
    }
}
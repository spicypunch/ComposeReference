package com.example.composegps

import android.annotation.SuppressLint
import android.app.Application
import android.location.Location
import android.os.Looper
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.example.MapState
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions


class MainViewModel(application: Application) : AndroidViewModel(application), LifecycleObserver {
    private val fusedLocationProviderClient: FusedLocationProviderClient =
        FusedLocationProviderClient(application.applicationContext)
    private val locationRequest: LocationRequest
    private val locationCallback: MyLocationCallBack

    private val _state = mutableStateOf(
        MapState(
            null,
            PolylineOptions().width(5f).color(android.graphics.Color.RED)
        )
    )
    val state: State<MapState> = _state

    init {
        locationCallback = MyLocationCallBack()

        locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 10000
        locationRequest.fastestInterval = 5000
    }

    @SuppressLint("MissingPermission")
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private fun addLocationListener() {
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    private fun removeLocationListener() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

    inner class MyLocationCallBack : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)

            val location = locationResult.lastLocation

            val polylineOptions = state.value.polylineOptions

            _state.value = state.value.copy(
                location = location,
                polylineOptions = polylineOptions.add(LatLng(location.latitude, location.longitude))
            )
        }
    }
}

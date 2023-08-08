package com.example

import android.location.Location
import com.google.android.gms.maps.model.PolylineOptions

data class MapState(
    val location: Location?,
    val polylineOptions: PolylineOptions
)

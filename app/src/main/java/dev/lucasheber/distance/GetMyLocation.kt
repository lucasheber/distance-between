package com.example.myapplication

import android.location.Location
import android.location.LocationListener
import android.os.Bundle

class GetMyLocation : LocationListener {
    companion object {
        var latitude: Double = 0.0
        var longitude: Double = 0.0;
    }

    override fun onLocationChanged(location: Location) {
        latitude = location.latitude
        longitude = location.longitude
    }

    override fun onLocationChanged(locations: MutableList<Location>) {
        super.onLocationChanged(locations)
    }

    override fun onFlushComplete(requestCode: Int) {
        super.onFlushComplete(requestCode)
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        super.onStatusChanged(provider, status, extras)
    }

    override fun onProviderEnabled(provider: String) {
        super.onProviderEnabled(provider)
    }

    override fun onProviderDisabled(provider: String) {
        super.onProviderDisabled(provider)
    }
}
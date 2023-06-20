package dev.lucasheber.distance

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.myapplication.GetMyLocation

data class Point(val lat: Double, val long: Double, val text: String = "No latitude and longitude");

class MainActivity : AppCompatActivity() {
    private lateinit var p1: Point
    private lateinit var p2: Point
    private lateinit var textA: TextView
    private lateinit var textB: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textA = findViewById<TextView>(R.id.textViewA)
        textB = findViewById<TextView>(R.id.textViewB)
        reset(findViewById(R.id.btnReset))
    }

    private fun showGoogleMaps(lat: Double, long: Double) {
        val webView: WebView = findViewById(R.id.webv)
        true.also { webView.settings.javaScriptEnabled = it }
        webView.loadUrl("https://www.google.com/maps/search/?api=1&query=$lat,$long")
    }

    fun reset(view: View) {
        p1 = Point(0.0, 0.0)
        p2 = Point(0.0, 0.0)
        textA.text = ""
        textB.text = ""
    }

    fun readPointA(view: View) {
        p1 = getMyLocation()
        textA.text = p1.text
    }

    fun showPointA(view: View) {
        showGoogleMaps(p1.lat, p1.long)
    }

    fun readPointB(view: View) {
        p2 = getMyLocation()
        textB.text = p2.text
    }

    fun showPointB(view: View) {
        showGoogleMaps(p2.lat, p2.long)
    }

    fun distance(view: View) {
        calcLocationDistance(p1, p2);
    }

    private fun calcLocationDistance(p1: Point, p2: Point) {
        val locationManager: LocationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val result: FloatArray = FloatArray(2)

        Location.distanceBetween(p1.lat, p1.long, p2.lat, p2.long, result)
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            val text = "**** Distancia: " + result[0] + "\n";
            Toast.makeText(this, text, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "GPS DESABILITADO", Toast.LENGTH_LONG).show();
        }
    }

    private fun getMyLocation(): Point {
        var text = "";

        val getMyLocation: GetMyLocation = GetMyLocation();
        val locationManager: LocationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                2
            )
            return Point(0.0, 0.0)
        }


        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, getMyLocation);

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            text =
                "Latitude: " + GetMyLocation.latitude + "\n" + "Longitude: " + GetMyLocation.longitude + "\n";
            Toast.makeText(this, text, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "GPS DESABILITADO", Toast.LENGTH_LONG).show();
        }

        return Point(GetMyLocation.latitude, GetMyLocation.longitude, text)
    }
}
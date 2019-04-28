package br.com.rafaeldias.etracking.ui

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.widget.EditText
import android.widget.Toast
import br.com.rafaeldias.etracking.R
import br.com.rafaeldias.etracking.utils.Permissao

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_editarnf.*
import java.util.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    val permissoesLocalizacao = listOf(Manifest.permission.ACCESS_FINE_LOCATION)

    private lateinit var locationManager: LocationManager

    private lateinit var locationListener: LocationListener

    private lateinit  var enderecoEntrega: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_mapa)

        Permissao.validaPermissao(permissoesLocalizacao.toTypedArray(),this,1)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        enderecoEntrega = intent!!.getStringExtra("endereco")
    }

    private fun initLocationListener() {
        locationListener = object : LocationListener {
            override fun onLocationChanged(location: Location?) {
                //val minhaPosicao = LatLng(location?.latitude!!,location?.longitude)
                //addMarcador(minhaPosicao,"Meu local")
                //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(minhaPosicao,16f))
            }

            override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {

            }

            override fun onProviderEnabled(p0: String?) {

            }

            override fun onProviderDisabled(p0: String?) {

            }
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        for(resposta in grantResults) {
            if (resposta == PackageManager.PERMISSION_DENIED) {
                Toast.makeText(applicationContext, "Sem permissão, sem acesso", Toast.LENGTH_LONG).show()
            } else {
                requestLocationUpdates()
            }
        }
    }

    private fun requestLocationUpdates() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            var locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0.1f, locationListener)
        }
    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    private fun addMarcador(latLng: LatLng, titulo: String) {
        mMap.addMarker(MarkerOptions().position(latLng).title(titulo))
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        initLocationListener()
        requestLocationUpdates()

        val geocoder = Geocoder(applicationContext, Locale.getDefault())
        //val endereco = geocoder.getFromLocation(it.latitude,it.longitude,1)
        val endereco = geocoder.getFromLocationName(enderecoEntrega,1)
        //addMarcador(it, endereco[0].subLocality) //rodar em modo debug para ver todas as opções
        val latitude = endereco[0].latitude
        val longitude = endereco[0].longitude
        //Toast.makeText(applicationContext, LatLng(latitude,longitude).toString(), Toast.LENGTH_LONG).show()
        addMarcador(LatLng(latitude,longitude), endereco[0].getAddressLine(0))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(latitude,longitude),16f))

        val circulo = CircleOptions()
        circulo.center(LatLng(latitude,longitude))
        circulo.radius(200.0)
        circulo.fillColor(Color.argb(128,0,51,102))
        circulo.strokeWidth(10f)
        circulo.strokeColor(Color.argb(128,0,51,102))

        mMap.addCircle(circulo)
    }
}

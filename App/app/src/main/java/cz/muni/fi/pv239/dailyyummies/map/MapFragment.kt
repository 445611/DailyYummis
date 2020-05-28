package cz.muni.fi.pv239.dailyyummies.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import cz.muni.fi.pv239.dailyyummies.R
import cz.muni.fi.pv239.dailyyummies.model.SharedViewModel
import kotlinx.android.synthetic.main.fragment_map.*
import kotlinx.android.synthetic.main.fragment_map.view.*


/**
 * A simple [Fragment] subclass.
 */
class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var mapFragment: SupportMapFragment
    private lateinit var slider: SeekBar
    private lateinit var sliderValue: TextView
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val viewModel: SharedViewModel by activityViewModels()

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    private fun isLocationEnabled(): Boolean {
        var locationManager: LocationManager =
            activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient =
            activity?.let { LocationServices.getFusedLocationProviderClient(it) }!!
        if (context?.let {
                ActivityCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            } != PackageManager.PERMISSION_GRANTED) {
            activity?.let {
                ActivityCompat.requestPermissions(
                    it,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    LOCATION_PERMISSION_REQUEST_CODE
                )
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_map, container, false)

        slider = view.findViewById(R.id.seekBar) as SeekBar
        slider.progress = viewModel.sharedPreferences.getDefaultRadius()
        sliderValue = view.findViewById(R.id.rangeValue) as TextView
        sliderValue.text = slider.progress.toString() + " m"
        slider.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            var pval = 0
            override fun onProgressChanged(
                seekBar: SeekBar,
                progress: Int,
                fromUser: Boolean
            ) {
                pval = progress
                sliderValue.text = pval.toString() + " m"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                sliderValue.text = slider.progress.toString() + " m"
            }
        })

        val centerPosition = view.findViewById(R.id.centerPosition) as Button

        centerPosition.setOnClickListener {
            mMap.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    viewModel.mapCoordinates,
                    16f
                )
            )
        }

        mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment

        if (context?.let {
                ActivityCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            } == PackageManager.PERMISSION_DENIED) {
            if (viewModel.sharedPreferences.getDefaultHome().isNullOrBlank()) {
                //initMapWarning(view)
                view.map1Warning.visibility = View.VISIBLE
                view.map2Warning.visibility = View.VISIBLE
                mapFragment.view?.visibility = View.GONE
            } else {
                view.map1Warning.visibility = View.GONE
                view.map2Warning.visibility = View.GONE
                mapFragment.view?.visibility = View.VISIBLE
                mapFragment.getMapAsync(OnMapReadyCallback {
                    mMap = it
                    getCityCoordinates(view)
                    mMap.addMarker(viewModel.mapCoordinates?.let {
                        MarkerOptions().position(it).title("My position")
                    })
                    mMap.animateCamera(
                        CameraUpdateFactory.newLatLngZoom(
                            viewModel.mapCoordinates,
                            12f
                        )
                    )
                })
            }

        } else {
            view.map1Warning.visibility = View.GONE
            view.map2Warning.visibility = View.GONE
            mapFragment.view?.visibility = View.VISIBLE
            mapFragment.getMapAsync(OnMapReadyCallback {
                mMap = it
                fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                    // Got last known location. In some rare situations this can be null.
                    // 3
                    getLastLocation()

                }
            })
        }
        return view
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        Log.i("LOCATION", "Trying to update location")
        if (isLocationEnabled()) {

            activity?.let {
                fusedLocationClient.lastLocation.addOnCompleteListener(it) { task ->
                    var location: Location? = task.result

                    requestNewLocationData()

                    if (location != null) {
                        viewModel.mapCoordinates =
                            LatLng(location.getLatitude(), location.getLongitude())
                    }
                    mMap.clear()
                    mMap.addMarker(viewModel.mapCoordinates?.let {
                        MarkerOptions().position(it).title("My position")
                    })
                    mMap.animateCamera(
                        CameraUpdateFactory.newLatLngZoom(
                            viewModel.mapCoordinates,
                            16f
                        )
                    )

                }
            }
        } else {
            Toast.makeText(context, "Turn on location", Toast.LENGTH_LONG).show()
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(intent)
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {

        Log.i("LOCATION REQUEST", "Request new location DATA")
        var mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 1000
        mLocationRequest.fastestInterval = 200

        fusedLocationClient =
            activity?.let { LocationServices.getFusedLocationProviderClient(it) }!!
        fusedLocationClient.requestLocationUpdates(
            mLocationRequest, locationCallback,
            Looper.myLooper()
        )
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {

            Log.i("LOCATION RESULT", "new location DATA result")
            var mLastLocation: Location = locationResult.lastLocation
            viewModel.mapCoordinates =
                LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude())
            mMap.clear()
            mMap.addMarker(viewModel.mapCoordinates?.let {
                MarkerOptions().position(it).title("My position")
            })
            Log.i("LOCATION MARKER", "location marker set")
        }
    }

    override fun onMapReady(map: GoogleMap) {
        mMap = map
        mMap.uiSettings.isZoomControlsEnabled = true

        return
    }

    private fun getCityCoordinates(view: View) {
        val geocoder = Geocoder(context)
        val addresses: List<Address> =
            geocoder.getFromLocationName(viewModel.sharedPreferences.getDefaultHome(), 1)
        val address: Address = addresses[0]
        viewModel.mapCoordinates = LatLng(address.getLatitude(), address.getLongitude())
    }
}
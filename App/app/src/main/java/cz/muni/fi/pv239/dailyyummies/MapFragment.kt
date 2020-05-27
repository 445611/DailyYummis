package cz.muni.fi.pv239.dailyyummies

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Camera
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import cz.muni.fi.pv239.dailyyummies.model.SharedPreferences
import cz.muni.fi.pv239.dailyyummies.model.SharedViewModel
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.fragment_map.view.*


/**
 * A simple [Fragment] subclass.
 */
class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var mapFragment: SupportMapFragment
    private lateinit var lastLocation: Location
    private lateinit var currentLatLng: LatLng
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val viewModel: SharedViewModel by activityViewModels()

//    private var hasGps = false
//    private var hasNetwork = false
//    private var locationGps: Location? = null
//    private var locationNetwork: Location? = null

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    private fun isLocationEnabled(): Boolean {
        var locationManager: LocationManager = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient = activity?.let { LocationServices.getFusedLocationProviderClient(it) }!!
//        mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
//
//        mapFragment.getMapAsync(OnMapReadyCallback {
//            mMap = it
//        })

        if (context?.let {
                ActivityCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_FINE_LOCATION)
            } != PackageManager.PERMISSION_GRANTED) {
            activity?.let {
                ActivityCompat.requestPermissions(
                    it,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    LOCATION_PERMISSION_REQUEST_CODE)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_map, container, false)

//        mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
//
//        mapFragment.getMapAsync(OnMapReadyCallback {
//            it
//        })

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



//
//                // FUNGUJE NA AKTUALNU POLOHU PIN
//                fusedLocationClient.lastLocation.addOnSuccessListener { location ->
//                    // Got last known location. In some rare situations this can be null.
//                    // 3
//                    if (location != null) {
//                        lastLocation = location
//                        viewModel.mapCoordinates = LatLng(location.latitude, location.longitude)
//                        mMap.addMarker(viewModel.mapCoordinates?.let {
//                            MarkerOptions().position(it).title("My position")
//                        })
//                        mMap.animateCamera(
//                            CameraUpdateFactory.newLatLngZoom(
//                                viewModel.mapCoordinates,
//                                12f
//                            )
//                        )
//                    }
//                }
            })
        }

//        if (viewModel.sharedPreferences.getDefaultHome().isNullOrBlank()) {
//            initMapWarning(view)
//        } else {
//            mapFragment.getMapAsync(OnMapReadyCallback {
//                mMap = it
//                initMapWarning(view)
//                if (context?.let {
//                        ActivityCompat.checkSelfPermission(
//                            it,
//                            Manifest.permission.ACCESS_FINE_LOCATION
//                        )
//                    } == PackageManager.PERMISSION_DENIED) {
//                    initMapWarning(view)
//                    getCityCoordinates(view)
//                    mMap.addMarker(viewModel.mapCoordinates?.let {
//                        MarkerOptions().position(it).title("My position")
//                    })
//                    mMap.animateCamera(
//                        CameraUpdateFactory.newLatLngZoom(
//                            viewModel.mapCoordinates,
//                            12f
//                        )
//                    )
//                } else {
//                    fusedLocationClient.lastLocation.addOnSuccessListener { location ->
//                        // Got last known location. In some rare situations this can be null.
//                        // 3
//                        if (location != null) {
//                            lastLocation = location
//                            viewModel.mapCoordinates = LatLng(location.latitude, location.longitude)
//                            mMap.addMarker(viewModel.mapCoordinates?.let {
//                                MarkerOptions().position(it).title("My position")
//                            })
//                            mMap.animateCamera(
//                                CameraUpdateFactory.newLatLngZoom(
//                                    viewModel.mapCoordinates,
//                                    12f
//                                )
//                            )
//                        } else {
//                            getCityCoordinates(view)
//                        }
//                    }
//                }
//            })
//        }



//        mMap.addMarker(viewModel.mapCoordinates?.let {
//            MarkerOptions().position(it).title("My position")
//        })
//        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(viewModel.mapCoordinates, 12f))
//
//        mapFragment.getMapAsync(OnMapReadyCallback {
//            mMap = it
//
//            // getStartLocation()
//
//            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
//                // Got last known location. In some rare situations this can be null.
//                // 3
//                if (location != null) {
//                    lastLocation = location
//                    currentLatLng = LatLng(location.latitude, location.longitude)
//                    mMap.addMarker(MarkerOptions().position(currentLatLng).title("Marker almost at home"))
//                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))
//                }
//            }
//        })

        return view
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        if (isLocationEnabled()) {

            activity?.let {
                fusedLocationClient.lastLocation.addOnCompleteListener(it) { task ->
                    var location: Location? = task.result
                    if (location == null) {
                        requestNewLocationData()
                    } else {
                        viewModel.mapCoordinates = LatLng(location.getLatitude(), location.getLongitude())
                        mMap.addMarker(viewModel.mapCoordinates?.let {
                            MarkerOptions().position(it).title("My position")
                        })
                        mMap.moveCamera(
                            CameraUpdateFactory.newLatLngZoom(
                                viewModel.mapCoordinates,
                                16f
                            )
                        )
                    }
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
        var mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 2000
        mLocationRequest.fastestInterval = 1000
//        mLocationRequest.interval = 0
//        mLocationRequest.fastestInterval = 0
//        mLocationRequest.numUpdates = 1

        //fusedLocationClient = activity?.let { LocationServices.getFusedLocationProviderClient(it) }!!
        fusedLocationClient!!.requestLocationUpdates(
            mLocationRequest, locationCallback,
            Looper.myLooper()
        )
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            var mLastLocation: Location = locationResult.lastLocation
            viewModel.mapCoordinates = LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude())
        }
    }

    override fun onMapReady(map: GoogleMap) {
        mMap = map
        mMap.uiSettings.isZoomControlsEnabled = true

        return
    }

    private fun initMapWarning(view: View) {
//        view.map1Warning.visibility = View.VISIBLE
//        view.map2Warning.visibility = View.VISIBLE
//        mapFragment.view?.visibility = View.GONE

        view.map1Warning.visibility =
            if (viewModel.sharedPreferences.getDefaultHome().isNullOrBlank()) {
                mapFragment.view?.visibility = View.GONE
                View.VISIBLE
            } else {
                mapFragment.view?.visibility = View.VISIBLE
                View.GONE
            }
        view.map2Warning.visibility =
            if (viewModel.sharedPreferences.getDefaultHome().isNullOrBlank()) {
                mapFragment.view?.visibility = View.GONE
                View.VISIBLE
            } else {
                mapFragment.view?.visibility = View.VISIBLE
                View.GONE
            }
    }


    private fun initStartCoordinates(view: View) {


    }

    private fun getCityCoordinates(view: View) {
//        view.mapLoading.visibility = if (
//            viewModel.sharedPreferences.getDefaultHome().isNullOrBlank()) View.VISIBLE else View.GONE

        val geocoder = Geocoder(context)
        val addresses: List<Address> =
            geocoder.getFromLocationName(viewModel.sharedPreferences.getDefaultHome(), 1)
        val address: Address = addresses[0]
        // currentLatLng = LatLng(address.getLatitude(), address.getLongitude())
        viewModel.mapCoordinates = LatLng(address.getLatitude(), address.getLongitude())
    }
}
package com.alvarols01.capatapp2

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CustomCap
import com.google.android.gms.maps.model.Dot
import com.google.android.gms.maps.model.Gap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions

class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var map: GoogleMap

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 0
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar el diseño del fragmento
        val view = inflater.inflate(R.layout.fragment_map, container, false)

        createFragment()

        return view
    }

    private fun createFragment() {
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        createMaker()
        enableLocation()
        createPolyline()
    }

    private fun createMaker() {
        val iconSize = 50 // Tamaño deseado de la imagen del marcador

        val cordinates = LatLng(37.34984390388584, -5.942909872195713)
        val iesHnosMachado = MarkerOptions().position(cordinates).title("IES Hermanos Machado")
            .icon(BitmapDescriptorFactory.fromBitmap(resizeBitmap(R.drawable.cruzdeguia, iconSize)))

        val pasocoordenadas = LatLng(37.347095772571194, -5.942611395920494)
        val paso = MarkerOptions().position(pasocoordenadas).title("PASO")
            .icon(BitmapDescriptorFactory.fromBitmap(resizeBitmap(R.drawable.paso, iconSize)))

        val cordinatesAnexo = LatLng(37.34350420769776, -5.938057206631157)
        val anexoHermanosMachado = MarkerOptions().position(cordinatesAnexo).title("ANEXO - IES Hermanos Machado")
            .icon(BitmapDescriptorFactory.fromBitmap(resizeBitmap(R.drawable.virgen, iconSize)))



        map.addMarker(iesHnosMachado)
        map.addMarker(anexoHermanosMachado)
        map.addMarker(paso)
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(cordinates, 16f), 4000, null)
    }

    private fun resizeBitmap(resourceId: Int, newSize: Int): Bitmap {
        val originalBitmap = BitmapFactory.decodeResource(resources, resourceId)
        return Bitmap.createScaledBitmap(originalBitmap, 80, 80, false)
    }


    private fun createPolyline() {
        val polylineOptions = PolylineOptions()
            .add(LatLng(37.34984390388584, -5.942909872195713))
            .add(LatLng(37.34963871558523, -5.942696646207992))
            .add(LatLng(37.34948705430736, -5.942730313469127))
            .add(LatLng(37.34884472077732, -5.943740331314132))
            .add(LatLng(37.348666293825275, -5.9438525555184185))
            .add(LatLng(37.34850570920602, -5.9438525555184185))
            .add(LatLng(37.348229146001216, -5.9436617743723446))
            .add(LatLng(37.34721209835678, -5.942662978958765))
            .add(LatLng(37.34378749783524, -5.939969594252716))
            .add(LatLng(37.34374288813234, -5.939588031960625))
            .add(LatLng(37.343564449056345, -5.939419695654891))
            .add(LatLng(37.343394931541695, -5.939318693871456))
            .add(LatLng(37.34278823414297, -5.9376465532348845))
            .add(LatLng(37.34344846387505, -5.937579218711221))
            .add(LatLng(37.34349307346058, -5.938061782788168))
            .width(8f)
            .color(R.color.fondoprincipal)

        val polyline = map.addPolyline(polylineOptions)
        val pattern = listOf(Dot(), Gap(15f))
        polyline.pattern = pattern
        //polyline.startCap = CustomCap(BitmapDescriptorFactory.fromResource(R.drawable.cruzdeguia), 130f)
        //polyline.endCap = CustomCap(BitmapDescriptorFactory.fromResource(R.drawable.paso), 130f)
    }




    private fun isLocationPermissionGranted() = ContextCompat.checkSelfPermission(
        requireContext(),
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    private fun enableLocation() {
        if (!::map.isInitialized) return
        if (isLocationPermissionGranted()) {
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            map.isMyLocationEnabled = true
        } else {
            requestLocationPermission()
        }
    }

    private fun requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        ) {
            Toast.makeText(requireContext(), "Ve a ajustes y acepta los permisos", Toast.LENGTH_SHORT).show()
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            LOCATION_PERMISSION_REQUEST_CODE -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return
                }
                map.isMyLocationEnabled = true
            } else {
                Toast.makeText(
                    requireContext(),
                    "Para activar la localización ve a ajustes y acepta los permisos",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else -> { // Ignore all other requests.
            }
        }
    }
}

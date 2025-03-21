package com.mapbox.maps.interview

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.asynclayoutinflater.view.AsyncLayoutInflater
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.ViewAnnotationAnchor
import com.mapbox.maps.ViewAnnotationOptions
import com.mapbox.maps.interview.models.MapPhoto
import kotlinx.coroutines.launch

private const val TAG = "MainActivity"

class MainActivity : ComponentActivity() {
    private lateinit var mapView: MapView
    private val viewModel: PhotoMapViewModel by viewModels<PhotoMapViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // TODO once migrated to compose use Scaffold and get rid of this
        val rootView = findViewById<View>(android.R.id.content)
        ViewCompat.setOnApplyWindowInsetsListener(rootView) { view, insets ->
            val statusBarHeight = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top
            view.updatePadding(top = statusBarHeight)
            insets
        }

        // TODO migrate to compose
        mapView = findViewById(R.id.mapView)
        mapView.getMapboxMap().setCamera(
            CameraOptions.Builder()
                .center(Point.fromLngLat(-98.0, 39.5))
                .pitch(0.0)
                .zoom(2.0)
                .bearing(0.0)
                .build()
        )

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    Log.d(TAG, "state: $it")
                    when (it) {
                        PhotoMapUiState.Loading -> Log.i(TAG, "onCreate: Loading...")
                        is PhotoMapUiState.Success -> it.result.forEach(::addPhotoToMap)
                    }
                }
            }
        }
    }

    private fun addPhotoToMap(mapPhoto: MapPhoto) {
        val options = ViewAnnotationOptions.Builder()
            .geometry(Point.fromLngLat(mapPhoto.longitude, mapPhoto.latitude))
            .anchor(ViewAnnotationAnchor.BOTTOM)
            .allowOverlap(true)
            .build()
        mapView.viewAnnotationManager.addViewAnnotation(
            R.layout.map_photo,
            options,
            AsyncLayoutInflater(mapView.context)
        ) {
            val imageView = it.findViewById<ImageView>(R.id.image)
            imageView.setImageBitmap(mapPhoto.bitmap)
        }
    }
}
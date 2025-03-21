package com.mapbox.maps.interview

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.asynclayoutinflater.view.AsyncLayoutInflater
import androidx.core.graphics.createBitmap
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.ViewAnnotationAnchor
import com.mapbox.maps.ViewAnnotationOptions
import com.mapbox.maps.domain.search.api.MapPhoto
import com.mapbox.maps.plugin.gestures.gestures
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private lateinit var mapView: MapView
    private val viewModel: PhotoMapViewModel by viewModel()

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

        val searchButton = findViewById<ImageButton>(R.id.searchBt)
        val searchBox = findViewById<EditText>(R.id.editTextSearch)
        searchButton.setOnClickListener {
            viewModel.onSearchClicked(searchBox.text.toString())
        }

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    Log.d(TAG, "state: $it")
                    when (it) {
                        PhotoMapUiState.Loading -> Log.i(TAG, "onCreate: Loading...")
                        is PhotoMapUiState.Success -> {
                            mapView.viewAnnotationManager.removeAllViewAnnotations()
                            it.result.forEach(::addPhotoToMap)
                        }
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
            Glide.with(this)
                .load(mapPhoto.bitmapUrl)
                .into(imageView)
        }
    }

    private fun Bitmap.convertToMonochrome(): Bitmap {
        val paint = Paint().apply {
            val ma = ColorMatrix().apply { setSaturation(0f) }
            setColorFilter(ColorMatrixColorFilter(ma))
        }
        val bmpMonochrome = createBitmap(width, height)
        Canvas(bmpMonochrome).drawBitmap(this, 0F, 0F, paint)
        return bmpMonochrome
    }
}
package com.example.arcgistest

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.esri.arcgisruntime.geometry.Point
import com.esri.arcgisruntime.geometry.SpatialReferences
import com.esri.arcgisruntime.layers.WmtsLayer
import com.esri.arcgisruntime.mapping.ArcGISMap
import com.esri.arcgisruntime.mapping.Basemap
import com.esri.arcgisruntime.mapping.Viewpoint
import com.esri.arcgisruntime.mapping.view.Graphic
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay
import com.esri.arcgisruntime.mapping.view.MapView
import com.esri.arcgisruntime.symbology.*
import com.example.arcgistest.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private val activityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val mapView: MapView by lazy {
        activityMainBinding.mapView
    }
    private fun setupMap() {
        val layer = WmtsLayer("https://tiles.geoservice.dlr.de/service/wmts", "hillshade")
        // set the viewpoint, Viewpoint(latitude, longitude, scale)
        val basemap = Basemap(layer)
        val map = ArcGISMap(basemap)
        mapView.map = map
        mapView.setViewpoint(Viewpoint(31.271959, 34.817018, 72000.0))

    }

    private fun addGraphics() {

        // create a graphics overlay and add it to the map view
        val graphicsOverlay = GraphicsOverlay()
        mapView.graphicsOverlays.add(graphicsOverlay)

        // create a point geometry with a location and spatial reference
        // Point(latitude, longitude, spatial reference)
        val point = Point(34.817018,31.271959, SpatialReferences.getWgs84())

        val dra = resources.getDrawable(R.drawable.soldier)

        val im = PictureMarkerSymbol.createAsync(resources.getDrawable(R.drawable.soldier) as BitmapDrawable)
        im.get().height = 40f
        im.get().width = 40f
        val im2 = PictureMarkerSymbol.createAsync(resources.getDrawable(R.drawable.select) as BitmapDrawable)
        im2.get().height = 50f
        im2.get().width = 50f
        val simpleMarkerSymbol = SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, -0xa8cd, 20f)
        val simple2MarkerSymbol = SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, -0xff9c01, 5f)
        val list = listOf<Symbol>(im2.get(), im.get())

        val symb = CompositeSymbol(list)

        val blueOutlineSymbol = SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, -0xff9c01, 2f)
        simpleMarkerSymbol.outline = blueOutlineSymbol

        // create a graphic with the point geometry and symbol
        val pointGraphic = Graphic(point, symb)

        // add the point graphic to the graphics overlay
        graphicsOverlay.graphics.add(pointGraphic)

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityMainBinding.root)

        setupMap()
        addGraphics()
    }

    override fun onPause() {
        mapView.pause()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        mapView.resume()
    }

    override fun onDestroy() {
        mapView.dispose()
        super.onDestroy()
    }
}
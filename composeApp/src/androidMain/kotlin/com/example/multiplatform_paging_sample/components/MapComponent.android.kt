package com.example.multiplatform_paging_sample.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.CameraPositionState.Companion.invoke
import com.google.maps.android.compose.GoogleMap

@SuppressLint("UnrememberedMutableState")
@Composable
actual fun MapComponent() {


    val defaultCameraPosition =  CameraPosition.fromLatLngZoom( LatLng(37.5665,126.9780), 10f)
    val cameraPositionState =  CameraPositionState(position = defaultCameraPosition)

    Box(
        modifier = Modifier
            .fillMaxSize()

    ) {
        // Add GoogleMap here
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
        )
    }
}
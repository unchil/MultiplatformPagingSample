package com.example.multiplatform_paging_sample


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.SwingPanel
import androidx.compose.ui.graphics.BlendMode.Companion.Color

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext



@Composable
actual fun WebView(modifier: Modifier, url: String) {

       openUrlInBrowser(url)

/*
    var initialized by remember { mutableStateOf(false) }
    var download by remember { mutableStateOf(-1) }

    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            KCEF.init(
                builder = {
                    progress {
                        onInitialized {
                            initialized = true
                        }
                        onDownloading {
                            download = it.toInt()
                        }
                    }
                },
                onError = {
                    it?.printStackTrace()
                }
            )
        }
    }

    if (initialized) {
        // CEF is definitely initialized here, so we can use the blocking method without produceState
        val client = remember { KCEF.newClientBlocking() }
        val browser = remember {
            client.createBrowser(
                url,
                CefRendering.OFFSCREEN,
                true
            )
        }

        SwingPanel(
            factory = {
                browser.uiComponent
            },
            modifier = Modifier.fillMaxSize(),
        )

    } else {
        if (download > -1) {
            Text("Downloading: $download%")
        } else {
            Text("Initializing please wait...")
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            KCEF.disposeBlocking()
        }
    }
*/


}


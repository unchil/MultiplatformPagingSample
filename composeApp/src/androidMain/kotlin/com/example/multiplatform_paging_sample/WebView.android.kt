package com.example.multiplatform_paging_sample

import android.annotation.SuppressLint
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@SuppressLint("SetJavaScriptEnabled")
@Composable
actual fun WebView(modifier: Modifier, url: String) {

  //  openUrlInBrowser(url, LocalContext.current)

    AndroidView(
        modifier = modifier,
        factory = { context ->

            WebView(context).apply {
                settings.javaScriptEnabled = true
                webViewClient = WebViewClient()
                loadUrl(url)
            }

        },
        update = { webView ->
            webView.loadUrl(url)
        }
    )

}

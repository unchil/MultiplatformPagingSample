package com.example.multiplatform_paging_sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.multiplatform_paging_sample.components.MapComponent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {

            val context = LocalContext.current

            /*
            AppPagingSample(
                onExploreItemClicked = { repositories ->
                    openUrlInBrowser(repositories.html_url, context)
                }
            )

             */
            MapComponent()



        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}
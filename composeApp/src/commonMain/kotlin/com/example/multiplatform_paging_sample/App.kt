package com.example.multiplatform_paging_sample

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
@Preview
fun App() {
    MaterialTheme {
        var showContent by remember { mutableStateOf(false) }
        Column(
            modifier = Modifier
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(onClick = { showContent = !showContent }) {
                Text("Click me!")
            }
            AnimatedVisibility(showContent) {
                /*
                val greeting = remember { Greeting().greet() }
                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(painterResource(Res.drawable.compose_multiplatform), null)
                    Text("Compose: $greeting")
                }

                 */

                WebView( modifier = Modifier.fillMaxSize(), url = "https://www.google.com")

            }
        }
    }
}


@Composable
@Preview
fun AppPagingSample(onExploreItemClicked : OnExploreItemClicked) {

    val events = MutableSharedFlow<Event>(extraBufferCapacity = Int.MAX_VALUE)
    val viewModels = MutableStateFlow<ViewModel>(ViewModel.Empty)
    val viewModel by viewModels.collectAsState()
    val presenter = RepoSearchPresenter()
    val coroutineScope = rememberCoroutineScope()

    coroutineScope.launch {
        viewModels.emitAll(presenter.transformViewModel(events))
    }

    MaterialTheme {
        RepoSearchContent(
            viewModel = viewModel,
            onEvent = { event ->
                events.tryEmit(event)
            },
            onExploreItemClicked = onExploreItemClicked,
        )
    }

}




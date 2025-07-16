package com.example.multiplatform_paging_sample

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import app.cash.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.launch
import multiplatformpagingsample.composeapp.generated.resources.Res
import multiplatformpagingsample.composeapp.generated.resources.compose_multiplatform
import multiplatformpagingsample.composeapp.generated.resources.ic_git_branch
import org.jetbrains.compose.resources.painterResource
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
                val greeting = remember { Greeting().greet() }
                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(painterResource(Res.drawable.compose_multiplatform), null)
                    Text("Compose: $greeting")
                }
            }
        }
    }
}


@Composable
@Preview
fun AppPagingSamples() {

    val events = MutableSharedFlow<Event>(extraBufferCapacity = Int.MAX_VALUE)
    val viewModels = MutableStateFlow<ViewModel>(ViewModel.Empty)
    val viewModel by viewModels.collectAsState()
    val presenter = RepoSearchPresenter()
    val scope = rememberCoroutineScope()

    scope.launch {
        viewModels.emitAll(presenter.transformViewModel(events))
    }

    MaterialTheme {
        RepoSearchContent(
            viewModel = viewModel,
            onEvent = { event ->
                events.tryEmit(event)
            },
        )
    }

}


@Composable
fun AppPagingSample2(){
    val events = remember{MutableSharedFlow<Event>(extraBufferCapacity = Int.MAX_VALUE)}
    val viewModels =  MutableStateFlow<ViewModel>(ViewModel.Empty)
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyGridState()
    var isVisibleFooter by rememberSaveable { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }

    val viewModel by viewModels.collectAsState()

    coroutineScope.launch {
        viewModels.emitAll(RepoSearchPresenter().transformViewModel(events))
    }

    MaterialTheme {
        Surface {
            Column(modifier = Modifier.padding( 10.dp)) {

                SearchTextField(actionHandler = { event ->
                    events.tryEmit(event)
                    coroutineScope.launch {
                        viewModels.emitAll(RepoSearchPresenter().transformViewModel(events))
                    }
                })

                Scaffold (
                    bottomBar = { GridProgressIndicator(isVisibility = isVisibleFooter) },
                    snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
                    floatingActionButton = { UpButton(listState = listState, coroutineScope = coroutineScope) }
                ) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        when(viewModel){
                            ViewModel.Empty -> {
                                Box(modifier = Modifier.fillMaxSize()) {
                                    Text(
                                        modifier = Modifier.align(Alignment.Center),
                                        text = "NotFound Data",
                                        style = MaterialTheme.typography.headlineMedium,
                                        color = Color.Black
                                    )
                                }
                            }
                            is ViewModel.SearchResults -> {
                                val repositories = (viewModel as ViewModel.SearchResults).repositories.collectAsLazyPagingItems()
                                repositories.apply {

                                    when (loadState.refresh) {
                                        is LoadState.NotLoading -> {
                                            if (repositories.itemCount > 0) {
                                                LazyVerticalGrid(
                                                    columns = GridCells.Fixed(1),
                                                    state = listState,
                                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                                ) {
                                                    items(repositories.itemCount) {
                                                        repositories[it]?.let {
                                                            ItemCard(
                                                                item = it
                                                            )
                                                        }
                                                    }
                                                }
                                            } else {
                                                Box(modifier = Modifier.fillMaxSize()) {
                                                    Text(
                                                        modifier = Modifier.align(Alignment.Center),
                                                        text = "NotFound Data",
                                                        style = MaterialTheme.typography.headlineMedium,
                                                        color = Color.Black

                                                    )
                                                }
                                            }
                                        }
                                        // Show loading spinner during initial load or refresh.
                                        is LoadState.Loading -> {
                                            Box(modifier = Modifier.fillMaxSize()) {
                                                CircularProgressIndicator(
                                                    color = Color.Red,
                                                    modifier = Modifier.align(Alignment.Center)
                                                )
                                            }
                                        }
                                        // Show the retry state if initial load or refresh fails.
                                        is LoadState.Error -> {
                                            coroutineScope.launch {
                                                val result = snackbarHostState.showSnackbar(
                                                    message =  "Data Loading Error",
                                                    actionLabel = "Retry",
                                                    duration = SnackbarDuration.Long
                                                )
                                                when(result){
                                                    SnackbarResult.ActionPerformed -> {
                                                        viewModels.emitAll(RepoSearchPresenter().transformViewModel(events))
                                                    }
                                                    else -> { }
                                                }
                                            }
                                        }
                                    }

                                    when(loadState.append){
                                        is LoadState.NotLoading -> {
                                            isVisibleFooter = false
                                        }
                                        is LoadState.Loading -> {
                                            isVisibleFooter = true
                                        }
                                        is LoadState.Error -> {
                                            isVisibleFooter = false
                                            coroutineScope.launch {
                                                snackbarHostState.showSnackbar(
                                                    message = "Scroll Loading Error"
                                                    ,duration = SnackbarDuration.Short
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GridProgressIndicator(isVisibility:Boolean){
    if(isVisibility) {
        CenterAlignedTopAppBar(title = {
            CircularProgressIndicator(color = Color.LightGray)
        })
    }
}


@Composable
fun UpButton(listState: LazyGridState,  coroutineScope: CoroutineScope){

    val showButton by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex > 0
        }
    }

    if( showButton) {
        FloatingActionButton(
            onClick = {
                coroutineScope.launch {
                    listState.animateScrollToItem(0)
                }
            }
        ) { Text("Up") }
    }
}




@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchTextField(actionHandler: (Event) -> Unit) {

    var title by remember { mutableStateOf("") }

    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        value = title
        ,onValueChange = { title = it}
        ,leadingIcon = {
            Icon( imageVector = Icons.Filled.Search, contentDescription = "search")
        }
        ,label = { Text("Search GitHub Repository") }
        ,singleLine = true
        ,keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search)
        ,keyboardActions = KeyboardActions(
            onSearch = {
                title.trim().let {
                    if (it.isNotEmpty()) {
                        actionHandler(Event.SearchTerm(searchTerm = it))
                    }
                }
                keyboardController?.hide()
            }
        )
        ,modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemCard(
    item:Repository){

    Card(
        onClick = { } ,
        modifier = Modifier,
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation()
    ) {

        Column(modifier = Modifier.padding(10.dp)) {

            Text(
                text = item.full_name
                ,style = MaterialTheme.typography.titleMedium
                ,color = Color.Blue
            )

            item.description?.let {
                Text(
                    text = it
                    ,style = MaterialTheme.typography.bodySmall
                    ,color = Color.Black
                    , modifier = Modifier.padding(vertical = 6.dp)
                )
            }

            Row {

                item.language?.let {
                    Text(
                        text = "Language:${it}",
                        style = MaterialTheme.typography.bodySmall ,
                        modifier = Modifier
                            .weight(10f)
                            .wrapContentWidth(Alignment.Start)
                    )
                }

                Icon(
                    imageVector = Icons.Filled.Star
                    ,contentDescription = "start"
                    , tint = Color.Black
                    ,modifier = Modifier
                        .weight(10f)
                        .wrapContentWidth(Alignment.End)
                        .height(16.dp)
                )

                Text(
                    text = "${item.stargazers_count}"
                    ,style = MaterialTheme.typography.bodySmall
                )

                Icon(
                    painter = painterResource(Res.drawable.ic_git_branch)
                    ,contentDescription = "forks"
                    , tint = Color.Black
                    ,modifier = Modifier.height(16.dp)
                )

                Text(
                    text = "${item.forks_count}"
                    ,style = MaterialTheme.typography.bodySmall
                )

            }
        }
    }
}




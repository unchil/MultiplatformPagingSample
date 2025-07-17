package com.example.multiplatform_paging_sample


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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
import app.cash.paging.compose.LazyPagingItems
import app.cash.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import multiplatformpagingsample.composeapp.generated.resources.Res
import multiplatformpagingsample.composeapp.generated.resources.ic_git_branch
import org.jetbrains.compose.resources.painterResource


@Composable
fun RepoSearchContent(
    viewModel: ViewModel,
    onEvent: (Event) -> Unit,
    modifier: Modifier = Modifier,
) {
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyGridState()
    val snackbarHostState = remember { SnackbarHostState() }
    val isVisibleFooter = rememberSaveable { mutableStateOf(false) }

    when (viewModel) {
        ViewModel.Empty -> {
            Scaffold(
                modifier = modifier,
                topBar = { SearchTextField(actionHandler = onEvent) },
                bottomBar = { GridProgressIndicator(isVisibility = isVisibleFooter.value) },
                snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
                floatingActionButton = { UpButton(listState = listState, coroutineScope = coroutineScope) },
                content = {}
            )
        }

        is ViewModel.SearchResults -> {

            val repositories = viewModel.repositories.collectAsLazyPagingItems()

            Scaffold(
                modifier = modifier,
                topBar = { SearchTextField(
                    actionHandler = onEvent,
                    onRefreshList = {repositories.refresh()},
                    searchTerm = viewModel.searchTerm
                )},
                bottomBar = { GridProgressIndicator(isVisibility = isVisibleFooter.value) },
                snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
                floatingActionButton = { UpButton(listState = listState, coroutineScope = coroutineScope) }
            ){ paddingValues ->
                SearchResults(
                    repositories,
                    isVisibleFooter,
                    listState = listState,
                    snackbarHostState = snackbarHostState
                )
            }
        }
    }


}


@Composable
fun SearchResults(
    repositories: LazyPagingItems<Repository>,
    isVisibleFooter: MutableState<Boolean>,
    listState: LazyGridState,
    snackbarHostState:SnackbarHostState
) {
    val coroutineScope = rememberCoroutineScope()


    val topBarVerticalDp = 80.dp

    when (repositories.loadState.refresh) {
        is LoadState.NotLoading -> {
            if (repositories.itemCount > 0) {
                LazyVerticalGrid(
                    modifier = Modifier.padding(top = topBarVerticalDp).padding(horizontal = 10.dp),
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
                        repositories.refresh()
                    }
                    else -> { }
                }
            }
        }
    }

    when(repositories.loadState.append){
        is LoadState.NotLoading -> {
            isVisibleFooter.value = false
        }
        is LoadState.Loading -> {
            isVisibleFooter.value = true
        }
        is LoadState.Error -> {
            isVisibleFooter.value = false
            coroutineScope.launch {
                snackbarHostState.showSnackbar(
                    message = "Scroll Loading Error"
                    ,duration = SnackbarDuration.Short
                )
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
fun SearchTextField(
    actionHandler: (Event) -> Unit,
    onRefreshList: (() -> Unit)? = null,
    searchTerm:String = ""

) {

    var title by remember { mutableStateOf(searchTerm) }

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
                        onRefreshList?.invoke()
                    }
                }
                keyboardController?.hide()
            }
        )
        ,modifier = Modifier
            .fillMaxWidth()
            .padding( 10.dp)
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

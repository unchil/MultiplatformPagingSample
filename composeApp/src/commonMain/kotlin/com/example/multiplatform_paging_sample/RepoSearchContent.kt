package com.example.multiplatform_paging_sample


import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.cash.paging.LoadStateError
import app.cash.paging.LoadStateLoading
import app.cash.paging.LoadStateNotLoading
import app.cash.paging.compose.LazyPagingItems
import app.cash.paging.compose.collectAsLazyPagingItems


@Composable
fun RepoSearchContent(
    viewModel: ViewModel,
    onEvent: (Event) -> Unit,
    modifier: Modifier = Modifier,
) {
    when (viewModel) {
        ViewModel.Empty -> {
            Scaffold(
                topBar = {
                    SearchField(
                        searchTerm = "",
                        onEvent = onEvent,
                        onRefreshList = {

                        },
                    )
                },
                content = {},
                modifier = modifier,
            )
        }

        is ViewModel.SearchResults -> {
            val repositories = viewModel.repositories.collectAsLazyPagingItems()
            Scaffold(
                topBar = {
                    SearchField(
                        searchTerm = viewModel.searchTerm,
                        onEvent = onEvent,
                        onRefreshList = { repositories.refresh() },
                    )
                },
                content = {
                    SearchResults(repositories)
                },
                modifier = modifier,
            )
        }
    }
}

@Composable
private fun SearchResults(repositories: LazyPagingItems<Repository>) {
    LazyColumn(
        Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        when (val loadState = repositories.loadState.refresh) {
            LoadStateLoading -> {
                item {
                    CircularProgressIndicator()
                }
            }

            is LoadStateNotLoading -> {
                items(repositories.itemCount) { index ->
                    val repository = repositories[index]
                    Row(Modifier.fillMaxWidth()) {
                        Text(
                            repository!!.full_name,
                            Modifier.weight(1f),
                        )
                        Text(repository.stargazers_count.toString())
                    }
                }
            }

            is LoadStateError -> {
                item {
                    Text(loadState.error.message!!)
                }
            }

            else -> error("when should be exhaustive")
        }
    }
}

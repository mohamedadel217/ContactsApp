package com.example.feature.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.feature.R
import com.example.feature.ui.contract.HomeContract
import com.example.feature.extension.OnBottomReached
import com.example.feature.models.ContactsItemUiModel
import com.example.feature.ui.vm.HomeViewModel
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onItemClick: (ContactsItemUiModel) -> Unit,
) {
    if (viewModel.currentState.homeState is HomeContract.HomeState.Idle) {
        viewModel.setEvent(HomeContract.Event.FetchData)
    }

    Scaffold(
        backgroundColor = Color.White,
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                backgroundColor = Color.Blue,
                title = {
                    when (viewModel.uiState.collectAsState().value.homeState) {
                        is HomeContract.HomeState.Success -> {
                            Text(
                                text = if (viewModel.isFavoriteListShown) stringResource(id = R.string.home_screen_favorite_title) else stringResource(
                                    id = R.string.home_screen_title
                                ),
                                color = Color.White
                            )
                        }

                        else -> {}
                    }
                },
                actions = {
                    IconButton(onClick = {
                        viewModel.setEvent(HomeContract.Event.GetFavoriteList)
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.icon_filter),
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .padding(padding)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Top
            ) {
                DataContent(
                    onItemClick = { onItemClick.invoke(it) },
                    onFavoriteClick = { viewModel.setEvent(HomeContract.Event.OnFavoriteClicked(it)) },
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun DataContent(
    viewModel: HomeViewModel = hiltViewModel(),
    onItemClick: (ContactsItemUiModel) -> Unit,
    onFavoriteClick: (ContactsItemUiModel) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    var isRefreshing by remember { mutableStateOf(false) }
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = {
            isRefreshing = true
            coroutineScope.launch {
                viewModel.setEvent(HomeContract.Event.OnRefresh)
                isRefreshing = false
            }
        }
    )
    when (val state = viewModel.uiState.collectAsState().value.homeState) {
        is HomeContract.HomeState.Idle -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            )
        }

        is HomeContract.HomeState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                CircularProgressIndicator(
                    color = Color.Blue,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

        is HomeContract.HomeState.Empty -> {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
            ) {
                Text(
                    text = stringResource(R.string.home_screen_empty_text),
                    color = Color.White,
                    fontSize = 20.sp,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }


        is HomeContract.HomeState.Success -> {
            val listState = rememberLazyListState()
            Box(
                modifier = Modifier
                    .pullRefresh(pullRefreshState),
                contentAlignment = Alignment.Center
            ) {
                ShowContacts(
                    listState = listState,
                    items = state.contacts.data,
                    onItemClick = onItemClick,
                    onFavoriteClick = onFavoriteClick,
                )

                PullRefreshIndicator(
                    refreshing = isRefreshing,
                    state = pullRefreshState,
                )
                listState.OnBottomReached {
                    viewModel.setEvent(HomeContract.Event.LoadMoreData)
                }
            }
        }
    }
}

@Composable
private fun ShowContacts(
    listState: LazyListState,
    items: List<ContactsItemUiModel>,
    onItemClick: (ContactsItemUiModel) -> Unit,
    onFavoriteClick: (ContactsItemUiModel) -> Unit,
) {
    LazyColumn(
        state = listState,
        modifier = Modifier
            .background(Color.LightGray)
            .fillMaxSize()
    ) {
        items(
            items = items,
            key = { item -> item.login.uuid }
        ) { model ->
            ContactCardItem(
                model,
                onItemClick = { onItemClick.invoke(it) },
                onFavoriteClick = { onFavoriteClick.invoke(it) })
        }
    }
}

@Composable
private fun ContactCardItem(
    model: ContactsItemUiModel,
    onItemClick: (ContactsItemUiModel) -> Unit,
    onFavoriteClick: (ContactsItemUiModel) -> Unit
) {
    var isFavorite by remember { mutableStateOf(model.isFavorite) }

    LaunchedEffect(model) {
        isFavorite = model.isFavorite
    }
    Card(
        backgroundColor = Color.White,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable {
                onItemClick.invoke(model)
            }
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(model.picture?.thumbnail)
                    .crossfade(true)
                    .build(),
                contentDescription = stringResource(R.string.contact_image),
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color.Gray, CircleShape)
            )

            Column(modifier = Modifier.padding(top = 8.dp, start = 8.dp)) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(
                        text = model.name?.title + " " + model.name?.first.orEmpty(),
                        color = Color.Gray,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center
                    )
                    Icon(
                        modifier = Modifier
                            .padding(top = 2.dp)
                            .size(24.dp)
                            .clickable { onFavoriteClick.invoke(model) },
                        imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = null,
                        tint = Color.Gray
                    )

                }

                Text(
                    text = model.location?.city + " , " + model.location?.country.orEmpty(),
                    color = Color.Gray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(top = 8.dp),
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = model.email.orEmpty(),
                    color = Color.Gray,
                    modifier = Modifier
                        .padding(top = 8.dp),
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )
            }

        }
    }
}

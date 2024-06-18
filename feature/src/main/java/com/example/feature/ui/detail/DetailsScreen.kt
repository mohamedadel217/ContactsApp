package com.example.feature.ui.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.feature.R
import com.example.feature.models.ContactsItemUiModel

@Composable
fun DetailScreen(
    contactUiModel: ContactsItemUiModel,
    onBackClicked: () -> Unit,
    onImageClicked: () -> Unit
) {
    Scaffold(
        backgroundColor = Color.LightGray,
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {
                        onBackClicked.invoke()
                    }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                },
                backgroundColor = Color.Blue,
                title = {
                    Text(
                        text = contactUiModel.name?.first.orEmpty(),
                        color = Color.White
                    )
                }
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(padding)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ContactCardItem(model = contactUiModel, onImageClicked = onImageClicked)
            }
        }
    )
}

@Composable
private fun ContactCardItem(
    model: ContactsItemUiModel,
    onImageClicked: () -> Unit
) {

    Card(
        backgroundColor = Color.White,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(model.picture?.medium)
                    .crossfade(true)
                    .build(),
                contentDescription = stringResource(R.string.contact_image),
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(100.dp)
                    .clickable {
                        onImageClicked()
                    }
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
                    maxLines = 1,
                    modifier = Modifier
                        .padding(top = 8.dp),
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )
            }

        }
    }
}
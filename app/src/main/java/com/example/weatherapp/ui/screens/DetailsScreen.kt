package com.example.weatherapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.weatherapp.R
import com.example.weatherapp.model.WeatherCity
import com.example.weatherapp.utils.decimalFormat
import com.example.weatherapp.utils.getIcon
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Composable
fun DetailsScreen(
    weatherUiState: WeatherUiState,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val progressItem = remember {
        ProgressIndicator()
    }

    LaunchedEffect(
        progressItem
    ) {
        coroutineScope {
            launch {
                progressItem.loading()
            }
            launch {
                progressItem.updateText()
            }
        }
    }

    when(weatherUiState) {
        is WeatherUiState.Loading -> LoadingScreen(progressItem = progressItem, modifier = modifier.fillMaxSize())
        is WeatherUiState.Success -> WeatherList(weathers = weatherUiState.weather, onClick = {
            progressItem.reset()
            onClick()
        })
        is WeatherUiState.Error -> ErrorScreen(retryAction = {}, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun WeatherList(
    weathers: List<WeatherCity>,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(modifier = Modifier.weight(0.9f)) {
            LazyColumn(
                modifier = modifier.padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(items = weathers, key = { weather -> weather.id}) { weather ->
                    WeatherCard(weather)
                }
            }
        }
        Box(
            modifier = Modifier
                .weight(0.1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Button(
                onClick = onClick,
                modifier = modifier.widthIn(min = 250.dp)
            ) {
                Text(stringResource(R.string.start_again_button))
            }
        }
    }
}

@Composable
fun WeatherCard(
    weather: WeatherCity,
    modifier: Modifier = Modifier
) {
    val temperature = decimalFormat(weather.main.temp)
    val icon = getIcon(weather.weather[0].icon)
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = weather.name,
                    style = MaterialTheme.typography.displayLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = temperature + stringResource(R.string.degrees),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(icon)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.width(70.dp)
            )
        }
    }
}

@Composable
fun LoadingScreen(
    progressItem: ProgressIndicator,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(progressItem.currentText),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(16.dp)
            )
            LinearProgressIndicator(
                progress = progressItem.progressFactor,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp)
                    .clip(RoundedCornerShape(16.dp))
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.loader_percentage, progressItem.currentProgress),
                    textAlign = TextAlign.Start,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = stringResource(R.string.loader_percentage, progressItem.maxProgress),
                    textAlign = TextAlign.End,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
        )
        Text(text = stringResource(R.string.loader_failed_message), modifier = Modifier.padding(16.dp))
        Button(
            onClick = retryAction,
            modifier = Modifier.widthIn(min = 250.dp)
        ) {
            Text(stringResource(R.string.loader_failed_button))
        }
    }
}
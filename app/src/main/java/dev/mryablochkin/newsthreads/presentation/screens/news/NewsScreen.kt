@file:OptIn(ExperimentalMaterial3Api::class)

package dev.mryablochkin.newsthreads.presentation.screens.news

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import dev.mryablochkin.newsthreads.R
import dev.mryablochkin.newsthreads.data.remote.model.SourceDto
import dev.mryablochkin.newsthreads.domain.model.Article
import dev.mryablochkin.newsthreads.presentation.theme.NewsThreadsTheme
import dev.mryablochkin.newsthreads.presentation.theme.paddings

@Composable
internal fun NewsRoute(
    modifier: Modifier = Modifier,
    onSettingsClick: () -> Unit,
    viewModel: NewsMainViewModel = hiltViewModel()
) {
    val newsUiState by viewModel.newsUiState.collectAsState()
    NewsScreen(
        modifier = modifier,
        onSettingsClick = onSettingsClick,
        onRefreshNews = viewModel::refresh,
        newsUiState = newsUiState
    )
}

@Composable
private fun NewsScreen(
    modifier: Modifier = Modifier,
    onSettingsClick: () -> Unit,
    onRefreshNews: () -> Unit,
    newsUiState: NewsUiState
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            NewsTopAppBar(
                onSettingsClick = onSettingsClick,
                scrollBehavior = scrollBehavior,
                onRefreshNews = onRefreshNews
            )
        }
    ) { innerPadding ->

        AnimatedVisibility(
            visible = newsUiState.isLoading,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            LinearProgressIndicator(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxWidth(),
            )
        }

        if (newsUiState.error.isNotBlank()) {
            Box(
                modifier = Modifier.padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text(text = newsUiState.error, textAlign = TextAlign.Center)
            }
        }

        newsUiState.data?.let { articles ->
            LazyColumn(
                modifier = Modifier.padding(innerPadding),
                contentPadding = PaddingValues(20.dp),
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                items(articles) { article ->
                    key(article.id) {
                        ArticleItem(article = article)
                    }
                }
            }
        }
    }
}

@Composable
private fun NewsTopAppBar(
    modifier: Modifier = Modifier,
    onSettingsClick: () -> Unit,
    onRefreshNews: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior? = null,
) {
    TopAppBar(
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        title = { Text(text = stringResource(R.string.app_name)) },
        actions = {
            IconButton(onClick = onRefreshNews) {
                Icon(imageVector = Icons.Default.Refresh, contentDescription = null)
            }
            IconButton(onClick = onSettingsClick) {
                Icon(imageVector = Icons.Default.Settings, contentDescription = null)
            }
        }
    )
}

@Composable
private fun ArticleItem(
    modifier: Modifier = Modifier,
    article: Article
) {
    ArticleCard(
        modifier = modifier,
        title = article.title,
        urlToImage = article.urlToImage,
        source = article.source,
        /*publishedAt = getTimeAgo(article.publishedAt)*/
        publishedAt = article.publishedAt
    )
}

@Composable
private fun ArticleCard(
    modifier: Modifier = Modifier,
    title: String,
    urlToImage: String,
    source: SourceDto,
    publishedAt: String
) {
    ElevatedCard(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        onClick = { }
    ) {
        Column(modifier = Modifier.padding(MaterialTheme.paddings.large)) {
            AsyncImage(
                modifier = Modifier
                    .height(170.dp)
                    .clip(RoundedCornerShape(20.dp)),
                model = urlToImage,
                contentDescription = "image",
                contentScale = ContentScale.Crop,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title.take(50),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.W500,
                maxLines = 3,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = source.name,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = publishedAt,
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1
                    )
                }
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Default.BookmarkBorder,
                        contentDescription = "save",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewArticleCard(modifier: Modifier = Modifier) {
    NewsThreadsTheme {
        ArticleCard(
            title = "The WonderFul Architectures of This Winter Season",
            urlToImage = "https://i.kinja-img.com/image/upload/c_fill,h_675,pg_1,q_80,w_1200/763ba4ecb1ef569d8a515af6eed5290e.jpg",
            source = SourceDto(null, "Gizmodo.com"),
            publishedAt = "1970-01-01T00:00:00Z"
        )
    }
}
package com.example.finalproject.ui.view.review

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalproject.R
import com.example.finalproject.model.Reservasi
import com.example.finalproject.model.Review
import com.example.finalproject.ui.viewmodel.reservasiviewmodel.PageReservasiUiState
import com.example.finalproject.ui.viewmodel.reservasiviewmodel.PageReservasiViewModel
import com.example.finalproject.ui.viewmodel.reservasiviewmodel.ReservasiPenyediaViewModel
import com.example.finalproject.ui.viewmodel.reviewviewmodel.PageReviewUiState
import com.example.finalproject.ui.viewmodel.reviewviewmodel.PageReviewViewModel
import com.example.finalproject.ui.viewmodel.reviewviewmodel.ReviewPenyediaViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PageReviewScreen(
    modifier: Modifier = Modifier,
    navigateToItemEntryReview: () -> Unit,
    onDetailClick: (String) -> Unit,
    viewModel: PageReviewViewModel = viewModel(factory = ReviewPenyediaViewModel.Factory)
) {
    LaunchedEffect(Unit) {
        viewModel.getReview()
    }

    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntryReview,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Tambah Review")
            }
        },
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Daftar Review Terkait Villa",
                            style = MaterialTheme.typography.titleLarge.copy(color = Color.White)
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color(0xFF003153)
                )
            )
        }
    ) { innerPadding ->
        PageReviewStatus(
            pageReviewUiState = viewModel.reviewiUiState,
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick
        )
    }
}

@Composable
fun PageReviewStatus(
    pageReviewUiState: PageReviewUiState,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit
) {
    when (pageReviewUiState) {
        is PageReviewUiState.Loading -> {
            OnLoading(modifier = modifier.fillMaxSize())
        }
        is PageReviewUiState.Success -> {
            if (pageReviewUiState.review.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data")
                }
            } else {
                reviewLayout(
                    review = pageReviewUiState.review,
                    modifier = modifier.fillMaxWidth(),
                    onDetailCLick = { onDetailClick(it.idReview.toString()) }
                )
            }
        }
        is PageReviewUiState.Error -> {
            OnError(modifier = modifier.fillMaxSize())
        }
    }
}

@Composable
fun OnLoading(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun OnError(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.cardreview),
            contentDescription = ""
        )
        Text(
            text = stringResource(R.string.loading_failed),
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
fun reviewLayout(
    review: List<Review>,
    modifier: Modifier = Modifier,
    onDetailCLick: (Review) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(review) { review ->
            ReviewCard(
                review = review,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailCLick(review)
                    }
            )
        }
    }
}

@Composable
fun ReviewCard(
    review: Review,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Icon or Image placeholder
            Image(
                painter = painterResource(id = R.drawable.cardreview),
                contentDescription = "Icon",
                modifier = Modifier
                    .size(60.dp)
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = review.namaPelanggan.toString(),
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = review.namaVilla.toString(),
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = review.komentar,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "Nilai: ${review.nilai}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
        }
    }
}

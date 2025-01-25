package com.example.finalproject.ui.view.review

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalproject.model.Pelanggan
import com.example.finalproject.model.Review
import com.example.finalproject.ui.viewmodel.pelangganviewmodel.DetailPelangganUiState
import com.example.finalproject.ui.viewmodel.pelangganviewmodel.DetailPelangganViewModel
import com.example.finalproject.ui.viewmodel.pelangganviewmodel.PelangganPenyediaViewModel
import com.example.finalproject.ui.viewmodel.reviewviewmodel.DetailReviewUiState
import com.example.finalproject.ui.viewmodel.reviewviewmodel.DetailReviewViewModel
import com.example.finalproject.ui.viewmodel.reviewviewmodel.ReviewPenyediaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailViewReview(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    onEditClick: (String) -> Unit,
    detailReviewViewModel: DetailReviewViewModel = viewModel(factory = ReviewPenyediaViewModel.Factory),
    idReview: Int
) {
    val reviewuiState by detailReviewViewModel.reviewuiState.collectAsState()
    LaunchedEffect(idReview) {
        detailReviewViewModel.getReviewById(idReview)
    }

    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEditClick(idReview.toString()) },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Villa")
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .offset(y = (-600).dp)
                .clip(RoundedCornerShape(bottomEnd = 60.dp, bottomStart = 60.dp))
                .background(Color(0xFF003153)),
            contentAlignment = Alignment.BottomCenter
        ) {
            Text(
                text = "Detail Review",
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                color = Color.White,
                modifier = Modifier.padding(bottom = 150.dp)
            )
            Text(
                text = "Silahkan cek untuk detail Review yang anda pilih:",
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
                color = Color.White,
                modifier = Modifier.padding(bottom = 130.dp)
            )
        }
        DetailRevivewContent(
            reviewUiState = reviewuiState,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(top = 60.dp, start = 20.dp, end = 20.dp),
            onDeleteClick = {
                detailReviewViewModel.deleteReview(idReview)
                navigateBack()
            }
        )
    }
}


@Composable
fun DetailRevivewContent(
    reviewUiState: DetailReviewUiState,
    modifier: Modifier = Modifier,
    onDeleteClick: () -> Unit
) {
    var deleteConfirmationRequired by remember { mutableStateOf(false) }

    when (reviewUiState) {
        is DetailReviewUiState.Loading -> {
            Box(modifier = modifier, contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is DetailReviewUiState.Error -> {
            Box(modifier = modifier, contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Terjadi kesalahan.")
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
        is DetailReviewUiState.Success -> {
            Column(
                modifier = modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                DetailReviewinCard(review = reviewUiState.review)
                Button(
                    onClick = { deleteConfirmationRequired = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Hapus Review")
                }
            }
        }
    }

    if (deleteConfirmationRequired) {
        DeleteConfirmationDialog(
            onDeleteConfirm = {
                deleteConfirmationRequired = false
                onDeleteClick()
            },
            onDeleteCancel = { deleteConfirmationRequired = false }
        )
    }
}

@Composable
fun DetailReviewinCard(
    review: Review,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ComponentDetailReview(judul = "ID Review", isinya = review.idReview.toString())
            ComponentDetailReview(judul = "ID Reservasi", isinya = review.Reservasi.toString())
            ComponentDetailReview(judul = "Nilai Terkait Villa", isinya = review.nilai)
            ComponentDetailReview(judul = "Komentar Terhadap Villa", isinya = review.komentar)
        }
    }
}

@Composable
fun ComponentDetailReview(
    judul: String,
    isinya: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = judul,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        )
        Text(
            text = isinya,
            style = MaterialTheme.typography.bodyLarge.copy(
                color = MaterialTheme.colorScheme.onSurface
            )
        )
    }
}


@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = { },
        title = { Text("Delete Data") },
        text = { Text("Apakah anda yakin ingin menghapus data?") },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(text = "Cancel")
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(text = "Yes")
            }
        }
    )
}
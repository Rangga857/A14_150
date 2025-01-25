package com.example.finalproject.ui.view.villa

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalproject.model.Review
import com.example.finalproject.model.Villa
import com.example.finalproject.ui.view.pelanggan.DetailPelangganinCard
import com.example.finalproject.ui.viewmodel.pelangganviewmodel.DetailPelangganUiState
import com.example.finalproject.ui.viewmodel.villaviewmodel.VillaDetailViewModel
import com.example.finalproject.ui.viewmodel.villaviewmodel.VillaPenyediaViewModel
import com.example.finalproject.ui.viewmodel.villaviewmodel.VillaUiState

@Composable
fun DetailView(
    idVilla: String,
    navigateBack: () -> Unit,
    //navigateDetailReview: (String) -> Unit,
    onEditVilla: (String) -> Unit = {},
    onAddReservasi: () -> Unit = {},
    viewModel: VillaDetailViewModel = viewModel(factory = VillaPenyediaViewModel.Factory),
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(idVilla) {
        viewModel.getVillaById(idVilla)
    }

    Scaffold(
        modifier = Modifier,
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .offset(y = (-600).dp)
                .clip(RoundedCornerShape(bottomEnd = 100.dp, bottomStart = 100.dp))
                .background(Color(0xFF003153)),
            contentAlignment = Alignment.BottomCenter
        ) {
            Text(
                text = "Detail Villa",
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                color = Color.White,
                modifier = Modifier.padding(bottom = 150.dp)
            )
            Text(
                text = "Silahkan cek untuk detail villa yang anda pilih:",
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
                color = Color.White,
                modifier = Modifier.padding(bottom = 130.dp)
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .padding(top = 40.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.Start
        ) {
            DetailVillaContent(
                villaUiState = uiState,
                modifier = Modifier.fillMaxWidth(),
                onDeleteClick = {
                    viewModel.deleteVilla(idVilla)
                    navigateBack()
                },
                onEditClick = {
                    onEditVilla(idVilla)
                },
                onAddReservasi = onAddReservasi,
                //onNavigate = navigateDetailReview
            )
        }
    }
}

@Composable
fun DetailVillaContent(
    villaUiState: VillaUiState,
    modifier: Modifier = Modifier,
    onDeleteClick: () -> Unit,
    onEditClick: () -> Unit,
    //onNavigate:(String) -> Unit,
    onAddReservasi: () -> Unit
) {
    var deleteConfirmationRequired by remember { mutableStateOf(false) }

    when (villaUiState) {
        is VillaUiState.Loading -> {
            Box(modifier = modifier, contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is VillaUiState.Error -> {
            Box(modifier = modifier, contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Terjadi kesalahan.")
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
        is VillaUiState.Success -> {
            Column(
                modifier = modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Detail Villa
                ItemDetailVilla(
                    villa = villaUiState.villa,
                    onDeleteClick = onDeleteClick,
                    onEditClick = onEditClick
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Section Reviews
                Text(
                    text = "Reviews:",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    )
                )

                if (villaUiState.reviews.isEmpty()) {
                    Text("Belum ada review.")
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .padding(8.dp)
                    ) {
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            items(villaUiState.reviews) { review ->
                                Log.d("Review ID", "ID: ${review.idReview}")
                                ItemReview(
                                    review = review,
                                    modifier = Modifier.fillMaxWidth(),
                                    //onClick = { onNavigate(review.idReview.toString()) }
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text("Klik di bawah ini untuk menambahkan data reservasi")
                Button(
                    onClick = onAddReservasi,
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF003153))
                ) {
                    Text("Tambah Data Reservasi", color = Color.White)
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
fun ItemDetailVilla(
    modifier: Modifier = Modifier,
    villa: Villa,
    onDeleteClick: () -> Unit,
    onEditClick: () -> Unit,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(modifier = Modifier.padding(16.dp)) {

            IconButton(
                onClick = onEditClick,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(y = (-10).dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = "Edit Villa",
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            IconButton(
                onClick = onDeleteClick,
                modifier = Modifier.align(Alignment.TopEnd)
                    .offset(y = (30).dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Hapus Villa",
                    tint = MaterialTheme.colorScheme.error
                )
            }


            Column(modifier = Modifier.padding(end = 32.dp)) { // Padding tambahan untuk memberi ruang untuk ikon
                ComponentDetailVilla(
                    judul = "ID Villa",
                    isinya = villa.idVilla
                )
                Spacer(modifier = Modifier.height(8.dp))
                ComponentDetailVilla(
                    judul = "Nama Villa",
                    isinya = villa.namaVilla
                )
                Spacer(modifier = Modifier.height(8.dp))
                ComponentDetailVilla(
                    judul = "Alamat",
                    isinya = villa.alamat
                )
                Spacer(modifier = Modifier.height(8.dp))
                ComponentDetailVilla(
                    judul = "Kamar Tersedia",
                    isinya = villa.kamarTersedia.toString()
                )
            }
        }
    }
}

@Composable
fun ComponentDetailVilla(
    modifier: Modifier = Modifier,
    judul: String,
    isinya: String,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "$judul : ",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 20.sp
            )
        )
        Text(
            text = isinya,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 18.sp
            )
        )
    }
}

@Composable
fun ItemReview(
    modifier: Modifier = Modifier,
    review: Review,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            /*.clickable(onClick = onClick)*/,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Nilai: ${review.nilai}",
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Komentar: ${review.komentar}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
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

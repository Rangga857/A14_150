package com.example.finalproject.ui.view

import android.text.style.BackgroundColorSpan
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalproject.R
import com.example.finalproject.model.Review
import com.example.finalproject.model.Villa
import com.example.finalproject.repository.VillaRepository
import com.example.finalproject.ui.viewmodel.DashboardPenyediaViewModel
import com.example.finalproject.ui.viewmodel.DashboardUiState
import com.example.finalproject.ui.viewmodel.DashboardViewModel
import kotlinx.coroutines.launch

@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    navigateToItemEntryVilla: () -> Unit,
    onDetailClick: (Villa, List<Review>) -> Unit,
    onClickReview: () -> Unit,
    onClickPelanggan: () -> Unit,
    onClickReservasi: () -> Unit,
    viewModel: DashboardViewModel = viewModel(factory = DashboardPenyediaViewModel.Factory)
) {
    LaunchedEffect(Unit) {
        viewModel.getVilla()
    }

    val dashboardUiState = viewModel.villaUiState

    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntryVilla,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Tambah Kontak")
            }
        }
    ) { innerPadding ->
        DashboardStatus(
            dashboardUiState = dashboardUiState,
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,
            villaRepository = viewModel.villaRepo,
            onClickReview = onClickReview,
            onClickPelanggan = onClickPelanggan,
            onClickReservasi = onClickReservasi
        )
    }
}

@Composable
fun DashboardStatus(
    dashboardUiState: DashboardUiState,
    modifier: Modifier = Modifier,
    onDetailClick: (Villa, List<Review>) -> Unit,
    villaRepository: VillaRepository,
    onClickReview: () -> Unit,
    onClickPelanggan: () -> Unit,
    onClickReservasi: () -> Unit
) {
    when (dashboardUiState) {
        is  DashboardUiState.Loading -> {
            OnLoading(modifier = modifier.fillMaxSize())
        }
        is  DashboardUiState.Success -> {
            if (dashboardUiState.villa.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data villa")
                }
            } else {
                DashboardLayout(
                    villadetail = dashboardUiState.villa,
                    villaRepository = villaRepository,
                    onDetailClick = onDetailClick,
                    onClickReview = onClickReview,
                    onClickPelanggan = onClickPelanggan,
                    onClickReservasi = onClickReservasi

                )
            }
        }
        is  DashboardUiState.Error -> {
            OnError(modifier = modifier.fillMaxSize())
        }
    }
}

@Composable
fun OnLoading(modifier: Modifier = Modifier) {
    Box (
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        CircularProgressIndicator()
    }
}

@Composable
fun OnError(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_errpr),
            contentDescription = ""
        )
        Text(
            text = stringResource(R.string.loading_failed),
            modifier = Modifier.padding(16.dp)
        )
    }
}


@Composable
fun DashboardLayout(
    villadetail: List<Villa>,
    villaRepository: VillaRepository,
    modifier: Modifier = Modifier,
    onDetailClick: (Villa, List<Review>) -> Unit,
    onClickReview: () -> Unit,
    onClickPelanggan: () -> Unit,
    onClickReservasi: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(16.dp)
                .size(50.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Dashboard Villa",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Start)
                )
                Text(
                    text = "Welcome to Dashboard Villa",
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color.DarkGray),
                    modifier = Modifier.align(Alignment.Start)
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                .background(Color(0xFF003153))
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                ) {
                    // Review Button
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                            .background(Color.White)
                            .clip(RoundedCornerShape(16.dp))
                            .clickable { onClickReview() }
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(8.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.review),
                                contentDescription = "Image placeholder",
                                modifier = Modifier
                                    .size(58.dp)
                                    .align(Alignment.CenterHorizontally)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = "Review", style = MaterialTheme.typography.titleMedium)
                        }
                    }

                    //Pelanggan
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                            .background(Color.White)
                            .clip(RoundedCornerShape(16.dp))
                            .clickable { onClickPelanggan() }
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(8.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.pelanggan),
                                contentDescription = "Image placeholder",
                                modifier = Modifier
                                    .size(58.dp)
                                    .align(Alignment.CenterHorizontally)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Pelanggan",
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    }
                    //Reservasi
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                            .background(Color.White)
                            .clip(RoundedCornerShape(16.dp))
                            .clickable { onClickReservasi() }
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(8.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.reservasi),
                                contentDescription = "Image placeholder",
                                modifier = Modifier
                                    .size(58.dp)
                                    .align(Alignment.CenterHorizontally)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Reservasi",
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.White)
                        .padding(8.dp)
                ) {
                    Column {
                        Text(
                            text = "Daftar Villa Yang Tersedia : ",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF003153),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(villadetail) { villa ->
                                VillaCard(
                                    villa = villa,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            coroutineScope.launch {
                                                val (detailVilla, reviews) = villaRepository.getVillaById(villa.idVilla)
                                                onDetailClick(detailVilla, reviews)
                                            }
                                        }
                                )
                            }
                        }
                    }

                }
            }
        }
    }
}

@Composable
fun VillaCard(
    villa: Villa,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF3F4F6)) // Soft gray background
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.villa),
                contentDescription = "Villa Icon",
                tint = Color(0xFF003153),
                modifier = Modifier
                    .size(48.dp)
                    .padding(end = 16.dp)
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = villa.namaVilla,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color(0xFF003153)
                    )
                )
                Text(
                    text = villa.alamat,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.DarkGray,
                        fontSize = 16.sp
                    )
                )
            }
        }
    }
}


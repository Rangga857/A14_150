package com.example.finalproject.ui.view.reservasi

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
import androidx.compose.foundation.layout.offset
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalproject.R
import com.example.finalproject.model.Reservasi
import com.example.finalproject.ui.viewmodel.pelangganviewmodel.PagePelangganViewModel
import com.example.finalproject.ui.viewmodel.pelangganviewmodel.PelangganPenyediaViewModel
import com.example.finalproject.ui.viewmodel.reservasiviewmodel.PageReservasiUiState
import com.example.finalproject.ui.viewmodel.reservasiviewmodel.PageReservasiViewModel
import com.example.finalproject.ui.viewmodel.reservasiviewmodel.ReservasiPenyediaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PageReservasiScreen(
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    viewModel: PageReservasiViewModel = viewModel(factory = ReservasiPenyediaViewModel.Factory)
) {

    LaunchedEffect(Unit) {
        viewModel.getReservasi()
    }

    Scaffold(
        modifier = modifier,
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .offset(y = (-632).dp)
                .clip(RoundedCornerShape(bottomEnd = 60.dp, bottomStart = 60.dp))
                .background(Color(0xFF003153)),
            contentAlignment = Alignment.BottomCenter
        ) {
            Text(
                text = "Daftar Reservasi",
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                color = Color.White,
                modifier = Modifier.padding(bottom = 120.dp)
            )
            Text(
                text = "Di bawah ini merupakan data data reservasi:",
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
                color = Color.White,
                modifier = Modifier.padding(bottom = 100.dp)
            )
        }
        PageVillaStatus(
            pageReservasiUiState = viewModel.reservasiUiState,
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,
        )
    }
}

@Composable
fun PageVillaStatus(
    pageReservasiUiState: PageReservasiUiState,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit
) {
    when (pageReservasiUiState) {
        is PageReservasiUiState.Loading -> {
            OnLoading(modifier = modifier.fillMaxSize())
        }
        is PageReservasiUiState.Success -> {
            if (pageReservasiUiState.reservasi.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data")
                }
            } else {
                reservasiLayout(
                    reservasi = pageReservasiUiState.reservasi,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = { onDetailClick(it.idReservasi.toString()) },
                )
            }
        }
        is PageReservasiUiState.Error -> {
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
fun reservasiLayout(
    reservasi: List<Reservasi>,
    modifier: Modifier = Modifier,
    onDetailClick: (Reservasi) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(reservasi) { reservasi ->
            ReservasiCard(
                reservasi = reservasi,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(reservasi) }
            )
        }
    }
}
@Composable
fun ReservasiCard(
    reservasi: Reservasi,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(top = 70.dp),
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
            Image(
                painter = painterResource(id = R.drawable.cardpelanggan),
                contentDescription = "Icon Villa",
                modifier = Modifier
                    .size(48.dp)
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = reservasi.namaPelanggan,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = reservasi.namaVilla,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Jumlah Kamar: ${reservasi.jumlahKamar}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
package com.example.finalproject.ui.view.reservasi

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalproject.ui.costumwidget.DynamicSelectedTextField
import com.example.finalproject.ui.viewmodel.DashboardPenyediaViewModel
import com.example.finalproject.ui.viewmodel.DashboardViewModel
import com.example.finalproject.ui.viewmodel.pelangganviewmodel.PagePelangganViewModel
import com.example.finalproject.ui.viewmodel.pelangganviewmodel.PelangganPenyediaViewModel
import com.example.finalproject.ui.viewmodel.reservasiviewmodel.FormErrorStateReservasi
import com.example.finalproject.ui.viewmodel.reservasiviewmodel.InsertReservasiModel
import com.example.finalproject.ui.viewmodel.reservasiviewmodel.InsertReservasiUiEvent
import com.example.finalproject.ui.viewmodel.reservasiviewmodel.InsertReservasiUiState
import com.example.finalproject.ui.viewmodel.reservasiviewmodel.PageReservasiUiState
import com.example.finalproject.ui.viewmodel.reservasiviewmodel.PageReservasiViewModel
import com.example.finalproject.ui.viewmodel.reservasiviewmodel.ReservasiPenyediaViewModel
import com.example.finalproject.ui.viewmodel.villaviewmodel.VillaDetailViewModel
import com.example.finalproject.ui.viewmodel.villaviewmodel.VillaPenyediaViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun InsertReservasiView(
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertReservasiModel = viewModel(factory = ReservasiPenyediaViewModel.Factory)
) {
    val uiState = viewModel.reservasiuiState
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()



    LaunchedEffect(uiState.snackBarMessage) {
        uiState.snackBarMessage?.let { message ->
            coroutineScope.launch {
                snackbarHostState.showSnackbar(message)
                viewModel.resetSnackBarMessage()
            }
        }
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .wrapContentHeight()
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.White)
                    .border(2.dp, Color.Gray, RoundedCornerShape(20.dp))
                    .padding(16.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Insert Reservasi",
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color(0xFF003153),
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    InsertBodyReservasi(
                        uiState = uiState,
                        onValueChange = { updateEvent ->
                            viewModel.updateInsertVillaState(updateEvent)
                        },
                        onClick = {
                            coroutineScope.launch {
                                if (viewModel.validateFields()) {
                                    viewModel.saveData()
                                    delay(500)
                                    withContext(Dispatchers.Main) {
                                        onNavigate()
                                    }
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun InsertBodyReservasi(
    modifier: Modifier = Modifier,
    onValueChange: (InsertReservasiUiEvent) -> Unit,
    uiState: InsertReservasiUiState,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FormReservasi(
            insertReservasiUiEvent = uiState.insertReservasiUiEvent,
            onValueChange = onValueChange,
            errorState = uiState.isEntryValid,
            modifier = Modifier.fillMaxWidth(),
        )
        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF003153))
        ) {
            Text("Simpan", color = Color.White)
        }
    }
}
@Composable
fun FormReservasi(
    insertReservasiUiEvent: InsertReservasiUiEvent = InsertReservasiUiEvent(),
    onValueChange: (InsertReservasiUiEvent) -> Unit = {},
    errorState: FormErrorStateReservasi = FormErrorStateReservasi(),
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(text = "Masukkan Nama Villa:")
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = insertReservasiUiEvent.namaVilla,
            onValueChange = {
                onValueChange(insertReservasiUiEvent.copy(namaVilla = it))
            },
            isError = errorState.namaVilla != null,
            placeholder = { Text("Masukkan Nama Villa") }
        )
        Text(
            text = errorState.namaVilla ?: "",
            color = Color.Red
        )
        Text(text = "Masukkan Nama Pelanggan:")
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = insertReservasiUiEvent.namaPelanggan,
            onValueChange = {
                onValueChange(insertReservasiUiEvent.copy(namaPelanggan = it))
            },
            isError = errorState.namaPelanggan != null,
            placeholder = { Text("Masukkan Nama Pelanggan") }
        )

        Text(
            text = errorState.namaPelanggan ?: "",
            color = Color.Red
        )
        Text(text = "Masukkan Tanggal Check-In Reservasi:")
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = insertReservasiUiEvent.checkIn,
            onValueChange = {
                onValueChange(insertReservasiUiEvent.copy(checkIn = it))
            },
            isError = errorState.checkIn != null,
            placeholder = { Text("Masukkan Tanggal Check-in") }
        )
        Text(
            text = errorState.checkIn ?: "",
            color = Color.Red
        )
        Text(text = "Masukkan Tanggal Check-Out Reservasi:")
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = insertReservasiUiEvent.CheckOut,
            onValueChange = {
                onValueChange(insertReservasiUiEvent.copy(CheckOut = it))
            },
            isError = errorState.checkOut != null,
            placeholder = { Text("Masukkan Tanggal Check-out") }
        )
        Text(
            text = errorState.checkOut ?: "",
            color = Color.Red
        )
        Text(text = "Masukkan Jumlah Kamar:")
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = insertReservasiUiEvent.jumlahKamar.toString(),
            onValueChange = {
                onValueChange(insertReservasiUiEvent.copy(jumlahKamar = it.toIntOrNull() ?: 0))
            },
            isError = errorState.jumlahKamar != null,
            placeholder = { Text("Masukkan Jumlah Kamar") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Text(
            text = errorState.jumlahKamar ?: "",
            color = Color.Red
        )
    }
}

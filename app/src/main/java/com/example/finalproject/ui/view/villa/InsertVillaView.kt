package com.example.finalproject.ui.view.villa

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
import androidx.compose.material3.TopAppBar
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
import com.example.finalproject.navigation.DestinasiInsertVilla
import com.example.finalproject.ui.viewmodel.villaviewmodel.FormErrorStateVilla
import com.example.finalproject.ui.viewmodel.villaviewmodel.InsertViewModel
import com.example.finalproject.ui.viewmodel.villaviewmodel.InsertVillaUiEvent
import com.example.finalproject.ui.viewmodel.villaviewmodel.InsertVillaUiState
import com.example.finalproject.ui.viewmodel.villaviewmodel.VillaPenyediaViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun InsertVillaView(
    navigateBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertViewModel = viewModel(factory = VillaPenyediaViewModel.FactoryInsert)
) {
    val uiState = viewModel.uiState
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
                        text = "Insert Villa",
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color(0xFF003153),
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    InsertBodyVilla(
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
fun InsertBodyVilla(
    modifier: Modifier = Modifier,
    onValueChange: (InsertVillaUiEvent) -> Unit,
    uiState: InsertVillaUiState,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FormVilla(
            insertVillaUiEvent = uiState.insertVillaUiEvent,
            onValueChange = onValueChange,
            errorState = uiState.isEntryValid,
            modifier = Modifier.fillMaxWidth()
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
fun FormVilla(
    insertVillaUiEvent: InsertVillaUiEvent = InsertVillaUiEvent(),
    onValueChange: (InsertVillaUiEvent) -> Unit = {},
    errorState: FormErrorStateVilla = FormErrorStateVilla(),
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(text = "Masukkan Nama Villa:")
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = insertVillaUiEvent.namaVilla,
            onValueChange = {
                onValueChange(insertVillaUiEvent.copy(namaVilla = it))
            },
            isError = errorState.namaVilla != null,
            placeholder = { Text("Masukkan Nama Villa") }
        )
        Text(
            text = errorState.namaVilla ?: "",
            color = Color.Red
        )
        Text(text = "Masukkan ID Villa:")
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = insertVillaUiEvent.idVla,
            onValueChange = {
                onValueChange(insertVillaUiEvent.copy(idVla = it))
            },
            isError = errorState.idVilla != null,
            placeholder = { Text("Masukkan ID Villa") }
        )
        Text(
            text = errorState.idVilla ?: "",
            color = Color.Red
        )
        Text(text = "Masukkan Alamat Villa:")
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = insertVillaUiEvent.alamat,
            onValueChange = {
                onValueChange(insertVillaUiEvent.copy(alamat = it))
            },
            isError = errorState.alamat != null,
            placeholder = { Text("Masukkan Alamat") }
        )
        Text(
            text = errorState.alamat ?: "",
            color = Color.Red
        )

        Text(text = "Masukkan jumlah Kamar:")
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = insertVillaUiEvent.kamarTersedia.toString(),
            onValueChange = {
                onValueChange(insertVillaUiEvent.copy(kamarTersedia = it.toIntOrNull() ?: 0))
            },
            isError = errorState.kamarTersedia != null,
            placeholder = { Text("Masukkan Jumlah Kamar") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Text(
            text = errorState.kamarTersedia ?: "",
            color = Color.Red
        )
    }
}

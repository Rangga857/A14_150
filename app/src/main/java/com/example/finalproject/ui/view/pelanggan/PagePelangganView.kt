package com.example.finalproject.ui.view.pelanggan

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalproject.R
import com.example.finalproject.model.Pelanggan
import com.example.finalproject.ui.viewmodel.pelangganviewmodel.FormErrorStatePelanggan
import com.example.finalproject.ui.viewmodel.pelangganviewmodel.InsertPelangganUiEvent
import com.example.finalproject.ui.viewmodel.pelangganviewmodel.InsertPelangganUiState
import com.example.finalproject.ui.viewmodel.pelangganviewmodel.PagePelangganUiState
import com.example.finalproject.ui.viewmodel.pelangganviewmodel.PagePelangganViewModel
import com.example.finalproject.ui.viewmodel.pelangganviewmodel.PelangganPenyediaViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Composable
fun InsertPelangganView(
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    viewModel: PagePelangganViewModel = viewModel(factory = PelangganPenyediaViewModel.Factory)
) {
    val pelangganuiState = viewModel.pelangganuiState
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(pelangganuiState.snackBarMessage) {
        pelangganuiState.snackBarMessage?.let { message ->
            coroutineScope.launch {
                snackbarHostState.showSnackbar(message)
                viewModel.resetSnackBarMessage()
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.getPelanggan()
    }

    Scaffold(
        modifier = modifier
            .background(Color(0xFF003153)),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .offset(y = (30).dp)
                .clip(RoundedCornerShape(topStart = 60.dp, topEnd = 60.dp))
                .background(Color(0xFF003153)),
            contentAlignment = Alignment.BottomCenter
        ) {
            Text(
                text = "Insert Pelanggan",
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                color = Color.White,
                modifier = Modifier.padding(bottom = 750.dp)
            )
            Text(
                text = "Silahkan masukkan data dengan sesuai dan tekan tombol simpan!",
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
                color = Color.White,
                modifier = Modifier.padding(bottom = 720.dp)
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .padding(top = 100.dp),
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
                    InsertBodyPelanggan(
                        pelangganuiState = pelangganuiState,
                        onValueChange = { updateEvent ->
                            viewModel.updateInsertPelangganState(updateEvent)
                        },
                        onClick = {
                            coroutineScope.launch {
                                if (viewModel.validateFields()) {
                                    viewModel.saveData()
                                    delay(500)
                                }
                            }
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.padding(top = 20.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.TopCenter
            ) {
                Text(
                    text = "Di bawah ini merupakan data data pelanggan:",
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp,
                    color = Color.White,
                )
                PagePelangganStatus(
                    pagePelangganUiState = viewModel.pelangganUiState,
                    onDetailClick = onDetailClick,
                )
            }
        }
    }
}

@Composable
fun InsertBodyPelanggan(
    modifier: Modifier = Modifier,
    onValueChange: (InsertPelangganUiEvent) -> Unit,
    pelangganuiState: InsertPelangganUiState,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        FormPelanggan(
            insertPelangganUiEvent = pelangganuiState.insertPelangganUiEvent,
            onValueChange = onValueChange,
            errorState = pelangganuiState.isEntryValid,
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
fun FormPelanggan(
    insertPelangganUiEvent: InsertPelangganUiEvent = InsertPelangganUiEvent(),
    onValueChange: (InsertPelangganUiEvent) -> Unit = {},
    errorState: FormErrorStatePelanggan = FormErrorStatePelanggan(),
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(text = "Masukkan Nama Pelanggan:")
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = insertPelangganUiEvent.namaPelanggan,
            onValueChange = {
                onValueChange(insertPelangganUiEvent.copy(namaPelanggan = it))
            },
            label = { Text("Nama Pelanggan") },
            isError = errorState.namaPelanggan != null,
            placeholder = { Text("Masukkan Nama Pelanggan") }
        )
        Text(
            text = errorState.namaPelanggan ?: "",
            color = Color.Red
        )
        Text(text = "Masukkan Nomor HP Pelanggan:")
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = insertPelangganUiEvent.noHp,
            onValueChange = {
                onValueChange(insertPelangganUiEvent.copy(noHp = it))
            },
            label = { Text("Nomor Hp") },
            isError = errorState.noHp != null,
            placeholder = { Text("Masukkan Nomor HP") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Text(
            text = errorState.noHp ?: "",
            color = Color.Red
        )
    }
}


@Composable
fun PagePelangganStatus(
    pagePelangganUiState: PagePelangganUiState,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit
) {
    when (pagePelangganUiState) {
        is PagePelangganUiState.Loading -> {
            OnLoading(modifier = modifier.fillMaxSize())
        }
        is PagePelangganUiState.Success -> {
            if (pagePelangganUiState.pelanggan.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data")
                }
            } else {
                PelangganLayout(
                    pelanggan = pagePelangganUiState.pelanggan,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = { onDetailClick(it.idPelanggan.toString()) },
                )
            }
        }
        is PagePelangganUiState.Error -> {
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
fun PelangganLayout(
    pelanggan: List<Pelanggan>,
    modifier: Modifier = Modifier,
    onDetailClick: (Pelanggan) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(pelanggan) { pelanggan ->
                PelangganCard(
                    pelanggan = pelanggan,
                    onDetailClick = onDetailClick,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}


@Composable
fun PelangganCard(
    pelanggan: Pelanggan,
    modifier: Modifier = Modifier,
    onDetailClick: (Pelanggan) -> Unit
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = pelanggan.namaPelanggan,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = pelanggan.noHp,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                IconButton(
                    onClick = { onDetailClick(pelanggan) }
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Info"
                    )
                }
            }
        }
    }
}

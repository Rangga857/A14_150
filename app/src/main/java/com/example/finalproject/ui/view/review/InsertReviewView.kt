package com.example.finalproject.ui.view.review

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalproject.ui.costumwidget.DynamicSelectedTextField
import com.example.finalproject.ui.viewmodel.reviewviewmodel.FormErrorStateReview
import com.example.finalproject.ui.viewmodel.reviewviewmodel.InsertReviewUiEvent
import com.example.finalproject.ui.viewmodel.reviewviewmodel.InsertReviewUiState
import com.example.finalproject.ui.viewmodel.reviewviewmodel.InsertReviewViewModel
import com.example.finalproject.ui.viewmodel.reviewviewmodel.ReviewPenyediaViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun InsertReviewView(
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertReviewViewModel = viewModel(factory = ReviewPenyediaViewModel.Factory)
) {
    val reviewuiState = viewModel.reviewuiState
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()


    LaunchedEffect(reviewuiState.snackBarMessage) {
        reviewuiState.snackBarMessage?.let { message ->
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .offset(y = (30).dp)
                .clip(RoundedCornerShape(topStart = 60.dp, topEnd = 60.dp))
                .background(Color(0xFF003153)),
            contentAlignment = Alignment.BottomCenter
        ) {
            Text(
                text = "Insert Review",
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                color = Color.White,
                modifier = Modifier.padding(bottom = 700.dp)
            )
            Text(
                text = "Silahkan masukkan data dengan sesuai dan tekan tombol simpan!",
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
                color = Color.White,
                modifier = Modifier.padding(bottom = 675.dp)
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .padding(bottom = 100.dp),
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
                    InsertBodyReview(
                        reviewuiState = reviewuiState,
                        onValueChange = { updateEvent ->
                            viewModel.updateInsertReviewState(updateEvent)
                        },
                        onClick = {
                            coroutineScope.launch {
                                if (viewModel.validateFields()) {
                                    viewModel.saveData()
                                    delay(500)
                                    withContext(Dispatchers.Main) {
                                        onNavigate()
                                    }
                                } else {
                                    // Debugging: Log jika validasi gagal
                                    Log.d("InsertReviewView", "Validation failed. Fields are not valid.")
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
fun InsertBodyReview(
    modifier: Modifier = Modifier,
    onValueChange: (InsertReviewUiEvent) -> Unit,
    reviewuiState: InsertReviewUiState,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FormReview(
            insertReviewUiEvent = reviewuiState.insertReviewUiEvent,
            onValueChange = onValueChange,
            errorState = reviewuiState.isEntryValid,
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
fun FormReview(
    insertReviewUiEvent: InsertReviewUiEvent = InsertReviewUiEvent(),
    onValueChange: (InsertReviewUiEvent) -> Unit = {},
    errorState: FormErrorStateReview = FormErrorStateReview(),
    modifier: Modifier = Modifier
) {
    val nilai = listOf("Sangat Puas", "Puas", "Biasa", "Tidak Puas", "Sangat Tidak Puas")

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(text = "Masukkan ID Reservasi:")
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = insertReviewUiEvent.reservasi.toString(),
            onValueChange = {
                onValueChange(insertReviewUiEvent.copy(reservasi = it.toIntOrNull() ?: 0))
            },
            isError = errorState.Reservasi != null,
            placeholder = { Text("Masukkan Id Reservasi") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Text(
            text = errorState.Reservasi ?: "",
            color = Color.Red
        )

        DynamicSelectedTextField(
            selectedValue = insertReviewUiEvent.nilai,
            options = nilai,
            label = "nilai",
            onValueChangedEvent = { onValueChange(insertReviewUiEvent.copy(nilai = it)) },
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = errorState.nilai ?: "",
            color = Color.Red
        )
        Text(text = "Masukkan Komentar Terkait Villa Tersebut:")
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = insertReviewUiEvent.komentar,
            onValueChange = {
                onValueChange(insertReviewUiEvent.copy(komentar = it))
            },
            isError = errorState.komentar != null,
            placeholder = { Text("Masukkan Komentar Villa") }
        )
        Text(
            text = errorState.komentar ?: "",
            color = Color.Red
        )
    }
}

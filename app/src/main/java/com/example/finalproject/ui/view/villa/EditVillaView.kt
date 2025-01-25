package com.example.finalproject.ui.view.villa

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Updater
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalproject.ui.view.pelanggan.InsertBodyPelanggan
import com.example.finalproject.ui.viewmodel.pelangganviewmodel.EditPelangganViewModel
import com.example.finalproject.ui.viewmodel.pelangganviewmodel.PelangganPenyediaViewModel
import com.example.finalproject.ui.viewmodel.villaviewmodel.EditVillaViewModel
import com.example.finalproject.ui.viewmodel.villaviewmodel.VillaPenyediaViewModel
import kotlinx.coroutines.launch

@Composable
fun UpdateVillaView(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    idVilla: String,
    modifier: Modifier = Modifier,
    viewModel: EditVillaViewModel = viewModel(factory = VillaPenyediaViewModel.FactoryUpdate),
) {

    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = modifier,
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .offset(y = (70).dp)
                .clip(RoundedCornerShape(topEnd = 100.dp))
                .background(Color(0xFF003153))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp, top = 16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Update Villa",
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                    color = Color.White,
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Silahkan isi form berikut untuk update Villa",
                    fontWeight = FontWeight.Light,
                    fontSize = 18.sp,
                    color = Color.White,
                )
            }

        }
        Box(
            modifier = Modifier
                .padding(16.dp)
                .padding(top =170.dp)
                .background(color = Color.White, shape = RoundedCornerShape(30.dp))
                .padding(16.dp)
        ) {
            InsertBodyVilla(
                uiState = viewModel.uiVillaState,
                onValueChange = viewModel::updateVillaState,
                onClick = {
                    coroutineScope.launch {
                        viewModel.updateVilla()
                        navigateBack()
                    }
                },
                modifier = Modifier
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .fillMaxWidth()
            )
        }
    }

}
package com.example.texttalk.presentation

import androidx.compose.runtime.Composable
import com.example.texttalk.data.OcrRepositoryImpl

@Composable
fun MainScreen(viewModel: OcrViewModel, ocrRepository: OcrRepositoryImpl) {


    MainScreenContent(
        drawerIcon = {
        }, viewModel = viewModel
    )
}



package com.br.relatorio.Telas

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import androidx.compose.foundation.lazy.items

@Composable
fun TelaPrincipal() {

    var selectedIcon by remember {
        mutableStateOf<List<Uri?>>(emptyList())
    }


    val pickMedia = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> selectedIcon = listOf(uri) }

    )

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(
                Color(242, 231, 208, 255)
            ),
            horizontalAlignment = Alignment.CenterHorizontally

    ) {

        var nome by remember {mutableStateOf("")}
        var end by remember {mutableStateOf("")}

        Row (

        ){
            OutlinedTextField(
                value = nome,
                onValueChange = { nome = it },
                label = { Text("Nome Cliente") },
                modifier = Modifier
                    .fillMaxWidth(0.4f)
                    .padding(end = 10.dp)

            )
            OutlinedTextField(
                value = end,
                onValueChange = { end = it },
                label = { Text("Endereço") },
                modifier = Modifier
                    .fillMaxWidth(0.6f)
            )
        }

        Button(
            onClick = { pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) },
            modifier = Modifier
                .padding(top = 10.dp),

        ) {
            Text("Logo Empresa")
        }

    ImageLayoutView(selectedIcon)

    }



}

@Composable
fun ImageLayoutView(selectedImages: List<Uri?>) {
    LazyRow {
        items(selectedImages) { uri ->
            AsyncImage(
                model = uri,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Fit
            )
        }
    }
}

@Preview
@Composable
fun PreviewTelaPrincipal() {
    TelaPrincipal();
}
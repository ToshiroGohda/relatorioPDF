package com.br.relatorio.Telas

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.os.Environment
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.br.relatorio.components.UriUtils
import java.io.File
import java.io.FileOutputStream


data class Ambiente(
    val nome: String,
    val imagens: List<Imagem>
)

data class Imagem(
    val imagem: Bitmap,
    val descricao: String
)



@Composable
fun TelaPrincipal() {

    val context = LocalContext.current

    var logo by remember {
        mutableStateOf<ImageBitmap?>(null)
    }



    val pickMedia = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            uri?.let {
                logo = UriUtils().uriToBitmap(context, it)?.asImageBitmap()
            }
        }

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

        Button(
            onClick = {
                logo?.let { paint(null, it, nome, end) }
            }
        ) {
            Text("Fazer PDF")
        }
    }



}

fun paint(amb: Ambiente?, icon: ImageBitmap, name: String, end: String) {

    var pdfDocument: PdfDocument = PdfDocument();
    var title: Paint = Paint()
    var myPageInfo: PdfDocument.PageInfo? =
        PdfDocument.PageInfo.Builder(1120, 792, 1).create()
    var myPage: PdfDocument.Page = pdfDocument.startPage(myPageInfo);

    var canvas: Canvas = myPage.canvas;

    canvas.drawBitmap(icon.asAndroidBitmap(), 54F, 40F, null);

    title.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL))
    title.textSize = 15F

    canvas.drawText("Nome: $name", 209F, 100F, title)
    canvas.drawText("Endereço: $end", 209F, 80F, title)

    pdfDocument.finishPage(myPage);

    val file: File = File(Environment.getExternalStorageDirectory(), "Relatorio.pdf");


    try {
        pdfDocument.writeTo(FileOutputStream(file))

    } catch (e: Exception) {
        e.printStackTrace()
    }

    pdfDocument.close()

}





@Preview
@Composable
fun PreviewTelaPrincipal() {
    TelaPrincipal();
}



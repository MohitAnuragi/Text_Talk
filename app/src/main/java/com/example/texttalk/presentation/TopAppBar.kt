package com.example.texttalk.presentation

import android.content.Context
import android.net.Uri
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.texttalk.R
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfReader
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor
import java.io.InputStream
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenTopBar(drawerIcon: () -> Unit) {
    val context = LocalContext.current
    var pdfText by remember { mutableStateOf("") }
    var isSpeaking by remember { mutableStateOf(false) }
    var speechRate by remember { mutableStateOf(1.0f) }
    val ttsHelper = remember { mutableStateOf(TTSHelper(context)) }
    val gradientColors = listOf(
        MaterialTheme.colorScheme.primaryContainer,
        MaterialTheme.colorScheme.secondaryContainer
    )

    val motivationalQuote =
        "Breaking barriers, giving voice to every word!\uD83D\uDD0A"

    val pdfPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            uri?.let {
                pdfText = extractTextFromPdf(context, it)
                // Read the file name when selected
                ttsHelper.value.speak(
                    "PDF selected. Ready to play.",
                    onComplete = {},
                )
            }
        }
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color(0xFF9788CE)
            )
    ) {
        Image(
            painter = painterResource(id = R.drawable.audio_waves), // Add your own background image
            contentDescription = "Background waves",
            contentScale = ContentScale.FillBounds,
            alpha = 0.3f,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = gradientColors,
                            startX = 0f,
                            endX = Float.POSITIVE_INFINITY
                        ),
                        shape = RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp)
                    )
            ) {
                Text(
                    text = "TEXT  TALK",
                    color = Color.Black,
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = FontFamily.Serif,
                    fontSize = 36.sp,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(top = 16.dp)
                )
            }
            // Motivational Quote Card
            Spacer(modifier = Modifier.height(30.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp, vertical = 16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFBBB4E1)
                ),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = motivationalQuote,
                        color = Color.Black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        lineHeight = 22.sp,
                        modifier = Modifier.padding(horizontal = 4.dp)
                    )
                }
            }

            // Main Content Card
            Spacer(modifier = Modifier.height(30.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
                    .clip(RoundedCornerShape(24.dp)),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // PDF Selection Button
                    Button(
                        onClick = { pdfPickerLauncher.launch("application/pdf") },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF6A11CB),
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            "Select PDF Document",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    // Speech Rate Control
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "Speech Speed",
                            color = Color.DarkGray,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                "Slow",
                                color = Color.Gray,
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.Start
                            )
                            Slider(
                                value = speechRate,
                                onValueChange = { newRate ->
                                    speechRate = newRate
                                    ttsHelper.value.setSpeechRate(speechRate)
                                },
                                valueRange = 0.5f..2.0f,
                                steps = 6,
                                modifier = Modifier.weight(2f)
                            )
                            Text(
                                "Fast",
                                color = Color.Gray,
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.End
                            )
                        }
                        Text(
                            "${"%.1f".format(speechRate)}x",
                            color = Color(0xFF6A11CB),
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    }

                    // Audio Controls
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Button(
                            onClick = {
                                if (pdfText.isNotEmpty()) {
                                    isSpeaking = true
                                    ttsHelper.value.speak(pdfText) {
                                        isSpeaking = false
                                    }
                                } else {
                                    ttsHelper.value.speak(
                                        "Please select a PDF file first",
                                        onComplete = {}
                                    )
                                }
                            },
                            enabled = !isSpeaking,
                            modifier = Modifier
                                .weight(1f)
                                .height(56.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF2575FC),
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("Play Audio", fontSize = 16.sp)
                        }

                        Button(
                            onClick = {
                                ttsHelper.value.stopSpeaking()
                                isSpeaking = false
                            },
                            enabled = isSpeaking,
                            modifier = Modifier
                                .weight(1f)
                                .height(56.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFFF5252),
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("Stop", fontSize = 16.sp)
                        }
                    }
                }
            }
        }
    }
}


// Helper class for Text-to-Speech
class TTSHelper(context: Context) : TextToSpeech.OnInitListener {
    private var tts: TextToSpeech = TextToSpeech(context, this)
    private var isInitialized = false

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            tts.language = Locale("en", "IN") // 🇮🇳 Indian Accent
            isInitialized = true
            tts.setSpeechRate(1.0f)
        }
        else {
            Log.e("TTSHelper", "Initialization failed")
        }
    }

    fun speak(text: String, onComplete: () -> Unit) {
        if (isInitialized) {
            val sentences = splitTextIntoChunks(text, 3900) // Split into chunks of ~4000 chars
            for (sentence in sentences) {
                tts.speak(sentence, TextToSpeech.QUEUE_ADD, null, null)
            }
        }
    }

    fun stopSpeaking() {
        if (isInitialized) {
            tts.stop()
        }
    }

    fun setSpeechRate(rate: Float) {
        if (isInitialized) {
            tts.setSpeechRate(rate)
        }
    }


    private fun splitTextIntoChunks(text: String, chunkSize: Int): List<String> {
        val sentences = text.split(Regex("(?<=\\.)")) // Split at sentence endings
        val chunks = mutableListOf<String>()
        var currentChunk = StringBuilder()

        for (sentence in sentences) {
            if (currentChunk.length + sentence.length > chunkSize) {
                chunks.add(currentChunk.toString())
                currentChunk = StringBuilder()
            }
            currentChunk.append(sentence).append(" ")
        }
        if (currentChunk.isNotEmpty()) {
            chunks.add(currentChunk.toString())
        }
        return chunks
    }
}

// Function to extract text from PDF
fun extractTextFromPdf(context: Context, uri: Uri): String {
    return try {
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
        val pdfReader = PdfReader(inputStream)
        val pdfDocument = PdfDocument(pdfReader)

        val extractedText = StringBuilder()
        for (page in 1..pdfDocument.numberOfPages) {
            extractedText.append(PdfTextExtractor.getTextFromPage(pdfDocument.getPage(page)))
            extractedText.append("\n\n") // Separate pages with newlines
        }

        pdfDocument.close()
        pdfReader.close()
        inputStream?.close()

        val text = extractedText.toString().trim()
        if (text.isEmpty()) "No text found in the PDF" else text
    } catch (e: Exception) {
        Log.e("PDFExtraction", "Error extracting text: ${e.message}")
        "Failed to extract text"
    }
}


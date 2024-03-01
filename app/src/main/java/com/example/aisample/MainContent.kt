package com.example.aisample

import android.graphics.Picture
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.draw
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.example.aisample.model.DrawScreenModel
import com.example.aisample.model.Line

@Composable
fun MainContent(component: MainComponent) {

    val model by component.models.subscribeAsState()

    Box {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                Log.e("@@@", "pointerInput called Column")
            }) {
            DrawingScreen(model, component::onLineAdded, component::onPictureUpdated)
            Spacer(modifier = Modifier.height(4.dp))
            DrawingInfo(model.predictionInfo)
        }

        TextButton(onClick = { component.clearScreen() }, modifier = Modifier.align(Alignment.BottomCenter)) {
            Text("CLEAR")
        }
    }
}

@Composable
fun DrawingInfo(prediction: String) {
    Text(text = prediction)
}

@Composable
fun DrawingScreen(drawScreenModel: DrawScreenModel, onLineAdded: (Line) -> Unit, onPictureUpdated: (Picture) -> Unit) {
    val picture = remember {
        Picture()
    }
    Canvas(modifier = Modifier
        .height(400.dp)
        .fillMaxWidth()
        .border(1.dp, Color.DarkGray)
        .pointerInput(Unit) {
            Log.e("@@@", "PointerInput called Canvas")
            detectDragGestures { change, dragAmount ->
                Log.e(
                    "@@@",
                    "detectDragGestures called change is $change dragAmount is $dragAmount"
                )
                change.consume()
                val line = Line(
                    start = change.position - dragAmount,
                    end = change.position
                )
                onLineAdded(line)
            }
        }
        .drawWithCache {
            val width = this.size.width.toInt()
            val height = this.size.height.toInt()

            onDrawWithContent {
                val pictureCanvas =
                    androidx.compose.ui.graphics.Canvas(
                        picture.beginRecording(
                            width,
                            height
                        )
                    )
                // requires at least 1.6.0-alpha01+
                draw(this, this.layoutDirection, pictureCanvas, this.size) {
                    this@onDrawWithContent.drawContent()
                }
                picture.endRecording()

                onPictureUpdated(picture)

                drawIntoCanvas { canvas -> canvas.nativeCanvas.drawPicture(picture) }
            }
        }) {
        drawRect(color = Color.Black, size = Size(size.width, size.height))
        drawScreenModel.linesList.forEach { line ->
            drawLine(
                color = line.color,
                start = line.start,
                end = line.end,
                strokeWidth = line.strokeWidth.toPx(),
                cap = StrokeCap.Round
            )
        }


    }
}
package com.example.aisample

import android.graphics.Bitmap
import android.graphics.Picture
import android.os.Build
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.getAndUpdate
import com.arkivanov.decompose.value.update
import com.example.aisample.model.DrawScreenModel
import com.example.aisample.model.Line
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DefaultMainComponent(
    componentContext: ComponentContext,
    private val dispatchers: AppDispatchers,
    private val digitClassifier: DigitClassifier
) : MainComponent, ComponentContext by componentContext {

    private val state = MutableValue(DrawScreenModel())

    override val models: Value<DrawScreenModel>
        get() = state

    override fun onLineAdded(line: Line) {
        val lines = state.value.linesList.toMutableList()
        lines.add(line)
        state.update {
            DrawScreenModel(lines)
        }
    }

    override fun onPictureUpdated(picture: Picture) {
        val bitmap = createBitmapFromPicture(picture)
        val predictionInfo = digitClassifier.classify(bitmap)
        state.getAndUpdate { previousState ->
            DrawScreenModel(previousState.linesList, predictionInfo)
        }
        // crash при добавлении корутин
//        CoroutineScope(dispatchers.default).launch {
//            withContext(dispatchers.default) {
//                val bitmap = createBitmapFromPicture(picture)
//                val predictionInfo = digitClassifier.classify(bitmap)
//                state.getAndUpdate { previousState ->
//                    DrawScreenModel(previousState.linesList, predictionInfo)
//                }
//            }
//        }
    }

    override fun clearScreen() {
        state.update {
            DrawScreenModel()
        }
    }

    private fun createBitmapFromPicture(picture: Picture): Bitmap {
        // [START android_compose_draw_into_bitmap_convert_picture]
        val immutableBitmap = Bitmap.createBitmap(
            picture.width,
            picture.height,
            Bitmap.Config.ARGB_8888,
        )
        val mutableBitmap = immutableBitmap.copy(Bitmap.Config.ARGB_8888, true)
        val canvas = android.graphics.Canvas(mutableBitmap)
        canvas.drawColor(android.graphics.Color.WHITE)
        canvas.drawPicture(picture)
        return mutableBitmap
    }

}
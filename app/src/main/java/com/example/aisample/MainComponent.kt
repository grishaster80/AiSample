package com.example.aisample

import android.graphics.Bitmap
import android.graphics.Picture
import com.arkivanov.decompose.value.Value
import com.example.aisample.model.DrawScreenModel
import com.example.aisample.model.Line

interface MainComponent {
    val models: Value<DrawScreenModel>

    fun onLineAdded(line: Line)

    fun onPictureUpdated(picture: Picture)

    fun clearScreen()
}
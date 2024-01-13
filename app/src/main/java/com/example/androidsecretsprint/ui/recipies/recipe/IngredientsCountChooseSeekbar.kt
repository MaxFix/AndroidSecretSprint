package com.example.androidsecretsprint.ui.recipies.recipe

import android.widget.SeekBar

class IngredientsCountChooseSeekbar(
    private val onProgressChanged: (Int) -> Unit
) : SeekBar.OnSeekBarChangeListener {
    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        onProgressChanged.invoke(progress)
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
        // Обработать начало взаимодействия с ползунком
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
        // Обработать окончание взаимодействия с ползунком
    }
}
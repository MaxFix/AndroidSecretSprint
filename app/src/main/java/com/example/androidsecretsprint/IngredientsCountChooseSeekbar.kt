package com.example.androidsecretsprint

import android.widget.SeekBar

class IngredientsCountChooseSeekbar(
    private val onProgressChanged: ((SeekBar?, Int, Boolean) -> Unit)? = null,
) : SeekBar.OnSeekBarChangeListener {
    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        onProgressChanged?.invoke(seekBar, progress, fromUser)
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
        // Обработать начало взаимодействия с ползунком
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
        // Обработать окончание взаимодействия с ползунком
    }
}
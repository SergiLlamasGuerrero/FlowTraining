package com.sergillamas.flowtraining

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class MainActivity : Activity() {

    private val _stateFlow = MutableStateFlow("")
    private val stateFlow: StateFlow<String> get() = _stateFlow
    private var i = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.button)
        val textView = findViewById<TextView>(R.id.textView)

        GlobalScope.launch(Dispatchers.Main) { stateFlow.collect { textView.text = it } }

        button.setOnClickListener {
            GlobalScope.launch {
                makeFlow().collect { text ->
                    i++
                    _stateFlow.value = "${textView.text}\n$text"
                }
            }
        }
    }

    private fun makeFlow() = flow {
        delay(1_000)
        val text = when (i) {
            1 -> "FIRST PART OF TEXT"
            2 -> "SECOND PART"
            3 -> "LAST PART"
            else -> ""
        }
        emit(text)
    }
}

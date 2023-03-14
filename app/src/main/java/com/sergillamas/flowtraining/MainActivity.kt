package com.sergillamas.flowtraining

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.button)
        val textView = findViewById<TextView>(R.id.textView)

        button.setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) {
                makeFlow().collect { number ->
                    textView.text = "${textView.text} $number"
                }
            }
        }
    }

    private fun makeFlow() = flow {
        for (i in 0..10) {
            delay(1_000)
            emit(i)
        }
    }
}

package com.sergillamas.flowtraining

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.button)
        val textView = findViewById<TextView>(R.id.textView)
        val textView2 = findViewById<TextView>(R.id.textView2)

        GlobalScope.launch(Dispatchers.Main) {
            exampleFlow.collect {
                textView.text = textView.text.toString() + " " + it
            }
        }

        button.setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) {
                exampleFlow.collect {
                    textView2.text = textView2.text.toString() + " " + it
                }
            }
        }
    }

    private val exampleFlow = flow {
        var i = 0
        while (i < 10) {
            delay(1_000)
            i++
            emit(i.toString())
        }
    }.shareIn(GlobalScope, SharingStarted.Lazily, replay = 4)
}

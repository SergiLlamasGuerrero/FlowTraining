package com.sergillamas.flowtraining

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
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
        var aux = 0
        while (i < 10) {
            delay(1_000)
            aux++
            if (aux % 2 == 0) i += 2
            emit(i.toString())
        }
    }.shareIn(GlobalScope, SharingStarted.Lazily, replay = 2)
        .onEach {
            Toast.makeText(applicationContext, "Now we have this value: $it", Toast.LENGTH_SHORT).show()
        }
        .onStart {
            Toast.makeText(applicationContext, "Let's start!!!!", Toast.LENGTH_SHORT).show()
        }
}

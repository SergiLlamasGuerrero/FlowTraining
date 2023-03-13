package com.sergillamas.flowtraining

import android.app.Activity
import android.os.Bundle
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Transform list to flow
        val flow1 = listOf(1, 2, 3, 4, 5, 6).asFlow()

        // Create flow directly as list of elements
        val flow2 = flowOf(1, 2, 3, 4, 5, 6)

        // Create flow with block scope
        val flow3 = makeFlow()

        GlobalScope.launch {
            println("@@ FLOW 1..............................")
            flow1.collect {
                println("@@ Value of emision: $it")
            }
            println("@@")
            println("@@ FLOW 2..............................")
            flow2.collect {
                println("@@ Value of emision: $it")
            }
            println("@@")
            println("@@ FLOW 3..............................")
            flow3.collect {
                println("@@ Value of emision: $it")
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

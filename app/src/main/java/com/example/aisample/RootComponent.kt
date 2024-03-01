package com.example.aisample

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value

interface RootComponent {
    val stack: Value<ChildStack<*, Child>>

    fun onBackClicked(toIndex: Int)


    sealed class Child {
        class Main(val component: MainComponent) : Child()
    }

}
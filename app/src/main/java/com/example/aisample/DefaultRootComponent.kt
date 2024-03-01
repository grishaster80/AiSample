package com.example.aisample

import android.content.Context
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.popTo
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

class DefaultRootComponent(
    componentContext: ComponentContext,
    private val context: Context
) : RootComponent, ComponentContext by componentContext{

    private val navigation = StackNavigation<Config>()

    override val stack: Value<ChildStack<*, RootComponent.Child>>
        get() = childStack(
            source = navigation,
            initialConfiguration = Config.Main,
            handleBackButton = true,
            childFactory = ::child,
        )

    private fun child(config: Config, childComponentContext: ComponentContext): RootComponent.Child =
        when (config) {
            is Config.Main -> RootComponent.Child.Main(mainComponent(childComponentContext))
        }

    private fun mainComponent(componentContext: ComponentContext): MainComponent =
        DefaultMainComponent(
            componentContext = componentContext,
            dispatchers = AppDispatchersImpl(),
            digitClassifier = DigitClassifier(context)
        )

    override fun onBackClicked(toIndex: Int) {
        navigation.popTo(index = toIndex)
    }

    private sealed interface Config : Parcelable {
        @Parcelize
        data object Main : Config
    }
}
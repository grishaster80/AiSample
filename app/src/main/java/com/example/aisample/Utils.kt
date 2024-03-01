package com.example.aisample

import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.logging.store.LoggingStoreFactory
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.arkivanov.mvikotlin.timetravel.store.TimeTravelStoreFactory

val storeFactoryInstance: StoreFactory =
    LoggingStoreFactory(delegate = TimeTravelStoreFactory())
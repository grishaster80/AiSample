import com.android.build.api.dsl.AaptOptions

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("kotlin-parcelize")
}

android {
    namespace = "com.example.aisample"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.aisample"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    androidResources {
        noCompress("tflite")
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    //tensorflow
    implementation("org.tensorflow:tensorflow-lite-task-vision-play-services:0.4.2")
    implementation("com.google.android.gms:play-services-tflite-gpu:16.2.0")
    implementation("org.tensorflow:tensorflow-lite:2.14.0")

    //mviKotlin
    implementation("com.arkivanov.mvikotlin:mvikotlin:3.3.0")
    implementation("com.arkivanov.mvikotlin:mvikotlin-main:3.3.0")
    implementation("com.arkivanov.mvikotlin:mvikotlin-timetravel:3.3.0")
    implementation("com.arkivanov.mvikotlin:mvikotlin-logging:3.3.0")
    implementation("com.arkivanov.mvikotlin:mvikotlin-extensions-coroutines:3.3.0")

    implementation("com.arkivanov.decompose:decompose:2.2.2")
    implementation("com.arkivanov.decompose:extensions-compose-jetbrains:2.2.2")
    implementation("com.arkivanov.essenty:lifecycle:1.3.0")

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
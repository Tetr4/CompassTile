plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "de.klimek.compass"
    compileSdk = 34

    signingConfigs {
        create("release") {
            keyAlias = "CompassKey"
            keyPassword = System.getenv("SIGNING_PASSWORD") ?: ""
            storeFile = file("../keystore.jks")
            storePassword = System.getenv("SIGNING_PASSWORD") ?: ""
        }
    }

    defaultConfig {
        applicationId = "de.klimek.compass"
        minSdk = 26
        targetSdk = 34
        versionCode = 8
        versionName = "1.4.1"
        proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            signingConfig = signingConfigs.getByName("release")
        }
    }

    kotlin {
        jvmToolchain(17)
    }

    dependenciesInfo {
        // Disable proprietary dependency metadata
        includeInApk = false
        includeInBundle = false
    }
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.7.0")
}

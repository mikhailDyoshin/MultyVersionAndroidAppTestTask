plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.example.multiverseapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.multiverseapp"
        minSdk = 24
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

    flavorDimensions += ("client")
    productFlavors {
        create("OperatorA") {
            dimension = "client"
            applicationIdSuffix = ".operatorA"
            versionNameSuffix = "1.0-OperatorA"
        }
        create("OperatorB") {
            dimension = "client"
            applicationIdSuffix = ".operatorB"
            versionNameSuffix = "1.0-OperatorB"
        }
        create("OperatorC") {
            dimension = "client"
            applicationIdSuffix = ".operatorC"
            versionNameSuffix = "1.0-OperatorC"
        }
        create("OperatorD") {
            dimension = "client"
            applicationIdSuffix = ".operatorD"
            versionNameSuffix = "1.0-OperatorD"
        }
        create("OperatorE") {
            dimension = "client"
            applicationIdSuffix = ".operatorE"
            versionNameSuffix = "1.0-OperatorE"
        }
        create("OperatorF") {
            dimension = "client"
            applicationIdSuffix = ".operatorF"
            versionNameSuffix = "1.0-OperatorF"
        }
        create("OperatorG") {
            dimension = "client"
            applicationIdSuffix = ".operatorG"
            versionNameSuffix = "1.0-OperatorG"
        }
        create("OperatorH") {
            dimension = "client"
            applicationIdSuffix = ".operatorH"
            versionNameSuffix = "1.0-OperatorH"
        }
        create("OperatorI") {
            dimension = "client"
            applicationIdSuffix = ".operatorI"
            versionNameSuffix = "1.0-OperatorI"
        }
        create("OperatorJ") {
            dimension = "client"
            applicationIdSuffix = ".operatorJ"
            versionNameSuffix = "1.0-OperatorJ"
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
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
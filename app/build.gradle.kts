plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.videostreaming"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.videostreaming"
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
    implementation(libs.firebase.auth)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation ("androidx.navigation:navigation-compose:2.5.3")


    //RetroFit API
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

    //Load Image
    implementation("io.coil-kt:coil-compose:2.2.2")

    //Volley API
    implementation ("com.android.volley:volley:1.2.1")

    //Exo Player
//    implementation("com.google.android.exoplayer:exoplayer:2.18.0")
    implementation("io.sanghun:compose-video:1.2.0")
    implementation("androidx.media3:media3-exoplayer:1.1.0") // [Required] androidx.media3 ExoPlayer dependency
    implementation("androidx.media3:media3-session:1.1.0") // [Required] MediaSession Extension dependency
    implementation("androidx.media3:media3-ui:1.1.0") // [Required] Base Player UI
    implementation ("androidx.media3:media3-exoplayer-hls:1.0.0")


    implementation ("androidx.compose.material:material:1.4.0")


}
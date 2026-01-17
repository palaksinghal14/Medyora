plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("com.google.gms.google-services")
    id("org.jetbrains.kotlin.plugin.compose")
    kotlin("kapt") //can work with both
}

android {
    namespace = "com.example.medyora"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.medyora"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        buildConfigField(
            "String",
            "GEMINI_API_KEY",
            "\"${providers.gradleProperty("GEMINI_API_KEY").get()}\""
        )
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
        buildConfig=true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.9.0")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")


    // extended icons library
    implementation("androidx.compose.material:material-icons-extended")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    // ✅ ViewModel (Compose-compatible)
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")

    // ✅ Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // ✅ Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // ✅ Optional: Logging with Retrofit
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    // ✅ Navigation for Compose
    implementation("androidx.navigation:navigation-compose:2.7.7")

    // ✅ Hilt
    implementation("com.google.dagger:hilt-android:2.48")
    kapt("com.google.dagger:hilt-android-compiler:2.48")

    // ✅ Hilt Navigation (for Compose)
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    // firebase bom
    implementation(platform("com.google.firebase:firebase-bom:34.5.0")) //33.7.0
    // firebase authentication
    implementation("com.google.firebase:firebase-auth")

    // firebase firestore
    implementation("com.google.firebase:firebase-firestore")

    //gemini
    implementation("com.google.ai.client.generativeai:generativeai:0.7.0")

    //for json parisng
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")





}
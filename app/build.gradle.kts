plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("com.google.gms.google-services")
}
android {
    namespace = "com.nictko.services"
    compileSdk = 35
    defaultConfig {
        applicationId = "com.nictko.services"
        minSdk = 24
        targetSdk = 34
        versionCode =9
        versionName = "1.0.7"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

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
        verbose = true             // show full compiler info
        allWarningsAsErrors = true // o
    }
    buildFeatures{
        dataBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

//RetroFit Dependencies
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.google.code.gson:gson:2.8.9")
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.1")

    //Coroutains"

    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.2")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.1") //viewModel scope
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.4.1") //lifecycle scope
    implementation ("androidx.fragment:fragment-ktx:1.4.1")

    //Lifecycle
    implementation ("androidx.lifecycle:lifecycle-common:2.4.1")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.4.1")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.4.1")
//size dp/sp

    implementation ("com.intuit.sdp:sdp-android:1.0.6")
    implementation ("com.google.android.material:material:1.12.0")

    implementation ("androidx.preference:preference-ktx:1.2.1")
    implementation ("androidx.cardview:cardview:1.0.0")
    implementation ("androidx.recyclerview:recyclerview:1.3.2")
    implementation ("com.github.f0ris.sweetalert:library:1.5.6")

    implementation ("io.github.chaosleung:pinview:1.4.4")


    //firebase
    implementation(platform("com.google.firebase:firebase-bom:32.7.1"))
//    implementation platform('com.google.firebase:firebase-bom:32.7.1') // Latest stable BOM

    implementation("com.google.firebase:firebase-analytics")

//    implementation 'com.google.firebase:firebase-analytics'
    implementation ("com.google.firebase:firebase-auth")
    implementation ("com.google.firebase:firebase-messaging")
    implementation ("com.google.firebase:firebase-firestore")
    implementation ("com.github.bumptech.glide:glide:4.16.0")

    implementation ("com.github.bumptech.glide:glide:4.16.0")


}
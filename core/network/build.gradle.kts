plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.secrets)
}

android {
    namespace = "com.shu.network"
    compileSdk = 34

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_18
        targetCompatibility = JavaVersion.VERSION_18
    }
    kotlinOptions {
        jvmTarget = "18"
    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {

    implementation(project(":core:models"))

    //OkHttp
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)


    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    //Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)


    testImplementation(libs.junit)
}
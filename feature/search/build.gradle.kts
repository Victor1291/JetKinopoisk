plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.roborazzi) apply false
}

android {
    namespace = "com.shu.mylibrary"
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.get()
    }
}

dependencies {

    implementation(project(":core:models"))
    implementation(project(":core:design_system"))

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    //Hilt
    implementation(libs.bundles.hilt)
    implementation(libs.androidx.compose.foundation)
    ksp(libs.bundles.hilt.ksp)

    //Coil
    implementation(libs.coil.compose)

    //Paging
    implementation("androidx.paging:paging-compose:3.3.2")

    //Swipe to refresh
    implementation("com.google.accompanist:accompanist-swiperefresh:0.34.0")

    implementation("androidx.compose.material:material:1.7.5")
    // Needed to get a view model reference in Jetpack Compose
    //implementation "androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycle_version"

    testImplementation(libs.hilt.android.testing)
    testImplementation(libs.robolectric)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.espresso.core)
  //  testDemoImplementation(libs.roborazzi)
}
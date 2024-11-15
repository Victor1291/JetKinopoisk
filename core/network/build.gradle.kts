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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {

    implementation(project(":core:models"))

    implementation(project(mapOf("path" to ":feature:home")))
    implementation(project(mapOf("path" to ":feature:posts")))
    implementation(project(mapOf("path" to ":feature:detail_movie")))
    implementation(project(mapOf("path" to ":feature:detail_person")))
    implementation(project(mapOf("path" to ":feature:list_movies")))
    implementation(project(mapOf("path" to ":feature:bottom_sheet")))
    implementation(project(mapOf("path" to ":feature:gallery")))
    implementation(project(mapOf("path" to ":feature:filter")))

    //OkHttp
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)


    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    //Hilt
    implementation(libs.hilt.android)
    implementation(project(":feature:search"))
    implementation(project(":core:database"))

    //Paging
    implementation("androidx.paging:paging-compose:3.3.0")
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)
    ksp(libs.hilt.android.compiler)


    testImplementation(libs.junit)
}
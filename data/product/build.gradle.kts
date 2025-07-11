plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.org.jetbrains.kotlin.kapt)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.android.junit)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.example.challengemock.data.product"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation(project(":common"))
    implementation(project(":data:core"))
    implementation(project(":data:database"))
    implementation(project(":domain:product"))

    kapt(libs.dagger.hilt.android.compiler)
    ksp(libs.androidx.room.compiler)

    implementation(libs.androidx.paging.compose)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.paging)
    implementation(libs.androidx.room.runtime)
    implementation(libs.dagger.hilt.android)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.squareup.retrofit2)
    implementation(libs.squareup.okhttp3.logging.interceptor)
    implementation(libs.kotlinx.serialization.converter)

    implementation(libs.kotlin.reflect)
}
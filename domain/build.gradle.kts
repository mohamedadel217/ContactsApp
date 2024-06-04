plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.domain"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":base"))
    implementation(project(":common"))
    implementation(libs.hilt)
    kapt(libs.hiltCompiler)
    // Test
    testImplementation(libs.junit)
    testImplementation(libs.junitKtx)
    testImplementation(libs.coreTesting)
    testImplementation(libs.coroutinesTest)
    testImplementation(libs.truth)
    testImplementation(libs.testRunner)
    testImplementation(libs.mockk)
    testImplementation(libs.turbine)
}
kapt {
    correctErrorTypes = true
}
import com.google.common.collect.FluentIterable.from

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}
versionCatalogs {
    libs {
        from(files("gradle/libs.versions.toml"))
    }
}
android {
    namespace = "com.example.contactsapp"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.example.contactsapp"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = libs.versions.versionMajor.get().toInt() * 10000 +
                libs.versions.versionMinor.get().toInt() * 100 +
                libs.versions.versionPatch.get().toInt()
        versionName = "${libs.versions.versionMajor.get()}.${libs.versions.versionMinor.get()}.${libs.versions.versionPatch.get()}"

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
        buildConfig = true
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
    implementation(project(":base"))
    implementation(project(":common"))
    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(project(":local"))
    implementation(project(":remote"))
    implementation(project(":feature"))

    implementation(libs.hilt)
    kapt(libs.hiltCompiler)
    // Test
    testImplementation(libs.junit)
    testImplementation(libs.junitKtx)
}
kapt {
    correctErrorTypes = true
}
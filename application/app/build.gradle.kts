plugins {
    id("com.android.application")
    id("dagger.hilt.android.plugin")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdk = ConfigData.compileSdkVersion
    buildToolsVersion = ConfigData.buildToolsVersion

    defaultConfig {
        applicationId = "com.example.code"
        minSdk = ConfigData.minSdkVersion
        targetSdk = ConfigData.targetSdkVersion
        versionCode = ConfigData.versionCode
        versionName = ConfigData.versionName
        
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled=false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions { kotlinOptions.jvmTarget = "1.8" }
    buildFeatures { viewBinding=true }
}

dependencies {
    implementation(Deps.coreKtx)
    implementation(Deps.appCompact)
    implementation(Deps.material)
    implementation(Deps.constraintlayout)
    implementation(Deps.timber)
    implementation(Deps.fragmentKtx)
    implementation(Deps.hiltAndroid)

    kapt(Deps.hiltCompiler)

    testImplementation(Deps.junit)

    androidTestImplementation(Deps.junitAndroid)
    androidTestImplementation(Deps.espressoCore)
}
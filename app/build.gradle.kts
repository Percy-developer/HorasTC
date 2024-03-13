plugins {
    id("com.android.application")
    id ("com.google.gms.google-services")
}

android {
    namespace = "com.example.horastcu"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.horastcu"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

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
}

dependencies {

    // Import the BoM for the Firebase platform
    implementation(platform("com.google.firebase:firebase-bom:32.4.0"))

    // Add the dependency for the Cloud Storage library
    // When using the BoM, you don't specify versions in Firebase library dependencies
    implementation ("com.github.bumptech.glide:glide:4.12.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0")
    implementation ("com.squareup.picasso:picasso:2.71828")
    implementation("com.google.firebase:firebase-storage")
    implementation ("com.squareup.picasso:picasso:2.8")
    implementation ("com.google.firebase:firebase-bom:32.3.1")
    implementation ("com.google.firebase:firebase-analytics:19.0.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-database:20.2.2")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //Implementaciones para conectar a phpmyadmin
    implementation ("androidx.appcompat:appcompat:1.3.1")
    implementation ("com.google.android.material:material:1.4.0")
    implementation ("androidx.constraintlayout:constraintlayout:2.1.1")
    testImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("androidx.test.ext:junit:1.1.3")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.4.0")
    implementation ("com.android.volley:volley:1.2.1")

    implementation ("com.google.android.material:material:1.8.0")
    implementation ("androidx.constraintlayout:constraintlayout:2.1.4")
    coreLibraryDesugaring ("com.android.tools:desugar_jdk_libs:2.0.2")
}
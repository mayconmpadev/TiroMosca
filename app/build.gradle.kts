plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
// plugin para o Firebase
    id("com.google.gms.google-services")

}

android {
    namespace = "com.mpasistemas.tiromosca"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.mpasistemas.tiromosca"
        minSdk = 24
        targetSdk = 31
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
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.activity:activity:1.8.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
// Dependencia de atualizacão automatica firebase
    implementation(platform("com.google.firebase:firebase-bom:33.2.0"))
// Dependencia do firebase analytics
    implementation("com.google.firebase:firebase-analytics")

    // Dependencia do banco de dados firestore do firebase
    implementation("com.google.firebase:firebase-firestore-ktx")

    // Dependencia do starage do firebase
    implementation("com.google.firebase:firebase-storage-ktx")

    // Dependencia do autenticação firebase
    implementation("com.google.firebase:firebase-auth-ktx")

    implementation("androidx.work:work-runtime-ktx:2.9.1")


    implementation ("com.google.firebase:firebase-messaging:24.0.1") // Verifique se está usando a versão mais recente


}
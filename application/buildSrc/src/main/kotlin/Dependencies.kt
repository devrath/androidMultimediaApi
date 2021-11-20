object Deps {
    val coreKtx by lazy { "androidx.core:core-ktx:${Versions.coreKtx}" }
    val appCompact by lazy { "androidx.appcompat:appcompat:${Versions.appcompat}" }
    val material by lazy { "com.google.android.material:material:${Versions.material}" }
    val constraintlayout by lazy { "androidx.constraintlayout:constraintlayout:${Versions.material}" }
    val timber by lazy { "com.jakewharton.timber:timber:${Versions.timber}" }
    val fragmentKtx by lazy { "androidx.fragment:fragment-ktx:${Versions.fragmentKtx}" }
    val hiltAndroid by lazy { "com.google.dagger:hilt-android:${Versions.hiltAndroid}" }
    val junit by lazy { "junit:junit:${Versions.jUnit}" }
    val junitAndroid by lazy { "androidx.test.ext:junit:${Versions.junitAndroid}" }
    val espressoCore by lazy { "androidx.test.espresso:espresso-core:${Versions.espressoCore}" }
    val hiltCompiler by lazy { "com.google.dagger:hilt-compiler:${Versions.hiltAndroid}" }
}

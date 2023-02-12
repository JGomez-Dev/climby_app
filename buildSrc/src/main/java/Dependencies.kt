object Versions {
    const val core = "1.9.0"
    const val appcompat = "1.5.1"
    const val androidMaterial = "1.6.1"
    const val androidMaterialx = "1.3.0"
    const val androidMaterial3 = "1.1.0-alpha04"
    const val constraintLayout = "2.1.4"
    const val navigationFragment = "2.3.5"
    const val navigationUi = "2.3.5"
    const val preference = "1.1.1"
    const val fragment = "1.4.0"
    const val kotlinStdlib = "1.5.0"

    const val testImplJunit = "4.13.2"
    const val androidTestImplJunit = "1.1.3"
    const val androidTestEspresso = "3.4.0"

    const val retrofit = "2.9.0"
    const val gsonConvertor = "2.9.0"
    const val okHttp = "4.9.0"
    const val scalerConvertor = "2.1.0"

    const val volley = "1.2.1"

    const val kotlinCoroutines = "1.6.1"

    const val coroutineLifecycleScope = "2.5.1"
    const val navigationRuntime = "2.4.1"
    const val viewmodelCompose = "2.5.1"

    const val glide = "4.12.0"

    const val fadingEdgeLayout = "1.0.0"

    const val circleImageview = "3.1.0"
    const val drawablePainter = "0.27.0"

    const val swipeRefreshLayout = "1.1.0"

    const val viewModelDeligate = "1.6.0"

    const val dagger = "2.40.5"
    const val hiltCompiler = "1.0.0"
    const val hiltNavigationCompose = "1.0.0-alpha02"

    const val roomVersion = "2.4.3"

    const val swipeRefresh = "1.1.0"

    const val lottieAnimations = "4.2.2"

    const val selecTableRoundedImageview = "1.0.1"

    const val playServicesLocation = "19.0.1"

    const val activityCompose = "1.3.1"
    const val compose = "1.3.1"
    const val composePreview = "1.3.2"
    const val runtime = "1.4.0-alpha04"
    const val navigationCompose = "2.5.3"

    const val playServicesAuth = "20.0.0"
    const val firebaseBom = "30.0.1"
    const val firebaseMessaging = "20.1.0"
    const val firebaseAuth = "21.1.0"
}

object Deps {
    const val core = "androidx.core:core-ktx:${Versions.core}"
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val androidMaterial = "com.google.android.material:material:${Versions.androidMaterial}"
    const val androidMaterialx = "androidx.compose.material:material:${Versions.androidMaterialx}"

    const val androidMaterial3 = "androidx.compose.material3:material3:${Versions.androidMaterial3}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val navigationFragment = "androidx.navigation:navigation-fragment-ktx:${Versions.navigationFragment}"
    const val navigationUi = "androidx.navigation:navigation-ui-ktx:${Versions.navigationUi}"
    const val preference = "androidx.preference:preference-ktx:${Versions.preference}"
    const val fragment = "androidx.fragment:fragment-ktx:${Versions.fragment}"
    const val kotlinStdlib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlinStdlib}"
}

object Compose {
    const val activityCompose = "androidx.activity:activity-compose:${Versions.activityCompose}"
    const val composeUi = "androidx.compose.ui:ui:${Versions.compose}"
    const val uiToolingPreview = "androidx.compose.ui:ui-tooling-preview:${Versions.composePreview}"

    const val uiTestJunit4 = "androidx.compose.ui:ui-test-junit4:${Versions.compose}"
    const val uiTooling = "androidx.compose.ui:ui-tooling:${Versions.compose}"
    const val uiTestManifest = "androidx.compose.ui:ui-test-manifest:${Versions.compose}"

    const val runtime = "androidx.compose.runtime:runtime:${Versions.runtime}"

    const val navigationCompose = "androidx.navigation:navigation-compose:${Versions.navigationCompose}"
}

object TestImplementation {
    const val junit = "junit:junit:${Versions.testImplJunit}"
}

object AndroidTestImplementation {
    const val junit = "androidx.test.ext:junit:${Versions.androidTestImplJunit}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.androidTestEspresso}"
}

object Retrofit {
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val gsonConvertor = "com.squareup.retrofit2:converter-gson:${Versions.gsonConvertor}"
    const val okHttp = "com.squareup.okhttp3:okhttp:${Versions.okHttp}"
    const val scalersConvertors = "com.squareup.retrofit2:converter-scalars:${Versions.scalerConvertor}"
}
object Volley {
    const val volley = "com.android.volley:volley:${Versions.volley}"
}

object Coroutines {
    const val coroutineCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinCoroutines}"
    const val coroutineAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.kotlinCoroutines}"
}

object CoroutinesLifecycleScope {
    const val lifecycleViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.coroutineLifecycleScope}"
    const val lifeCycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.coroutineLifecycleScope}"
    const val lifeCycleLivedata = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.coroutineLifecycleScope}"
    const val navigationRuntime = "androidx.navigation:navigation-runtime-ktx:${Versions.navigationRuntime}"
    const val viewmodelCompose = "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.viewmodelCompose}"
}

object Ui {
    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val annotationProcessor = "com.github.bumptech.glide:compiler:${Versions.glide}"
    const val lottieAnimations = "com.airbnb.android:lottie:${Versions.lottieAnimations}"
    const val fadingEdgeLayout = "com.github.bosphere.android-fadingedgelayout:fadingedgelayout:${Versions.fadingEdgeLayout}"
    const val swipeRefreshLayout = "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swipeRefreshLayout}"
    const val selecTableRoundedImageview = "com.joooonho:selectableroundedimageview:${Versions.selecTableRoundedImageview}"
    const val circleImageview = "de.hdodenhof:circleimageview:${Versions.circleImageview}"
    const val drawablePainter = "com.google.accompanist:accompanist-drawablepainter:${Versions.drawablePainter}"
}

object ViewModelDelegate {
    const val viewModelDeligate = "androidx.activity:activity-ktx:${Versions.viewModelDeligate}"
}

object DaggerHilt {
    const val hilt = "com.google.dagger:hilt-android:${Versions.dagger}"
    const val hiltAndroidCompiler = "com.google.dagger:hilt-android-compiler:${Versions.dagger}"
    const val hiltCompiler = "androidx.hilt:hilt-compiler:${Versions.hiltCompiler}"
    const val hiltNavigationCompose = "androidx.hilt:hilt-navigation-compose:${Versions.hiltNavigationCompose}"
}

object Room {
    const val roomCompiler = "androidx.room:room-compiler:${Versions.roomVersion}"
    const val room = "androidx.room:room-ktx:${Versions.roomVersion}"
}

object CircularProgressBar {
    const val swipeRefresh = "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swipeRefresh}"
}


object PlayServicesLocation {
    const val playServicesLocation = "com.google.android.gms:play-services-location:${Versions.playServicesLocation}"
}

object Firebase {
    const val firebaseAuth = "com.google.firebase:firebase-auth-ktx"
    const val playServicesAuth = "com.google.android.gms:play-services-auth:${Versions.playServicesAuth}"
    const val firebaseDatabase = "com.google.firebase:firebase-database-ktx"
    const val firebaseBom = "com.google.firebase:firebase-bom:${Versions.firebaseBom}"
    const val firebaseAnalytics = "com.google.firebase:firebase-analytics"
    const val firebaseStorage = "com.google.firebase:firebase-storage-ktx"
    const val firebaseMessaging = "com.google.firebase:firebase-messaging:${Versions.firebaseMessaging}"
}
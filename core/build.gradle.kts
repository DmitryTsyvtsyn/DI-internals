plugins {
    id("compose-library-convention")
}

android {
    namespace = "io.github.dmitrytsyvtsyn.core"
}

dependencies {
    api(libs.coroutines.core)
    api(platform(libs.androidx.compose.bom))
    api(libs.androidx.ui)
    api(libs.androidx.ui.graphics)
    api(libs.androidx.material3)
    api(libs.kotlinx.datetime)

    implementation(libs.androidx.fragment)
    implementation(libs.room.runtime)
}
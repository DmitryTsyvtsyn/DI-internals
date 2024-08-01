plugins {
    id("compose-library-convention")
}

android {
    namespace = "io.github.dmitrytsyvtsyn.post_list"
}

dependencies {
    implementation(project(":core"))
    implementation(project(":post_detail"))

    implementation(libs.androidx.fragment)
    implementation(libs.androidx.fragment.compose)
    implementation(libs.kotlin.immutable.collections)
    implementation(libs.androidx.lifecycle.viewmodel)
}
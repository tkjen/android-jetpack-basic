plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    id("androidx.navigation.safeargs.kotlin") version "2.7.7" apply false
    id("com.google.devtools.ksp") version "2.0.21-1.0.27" apply false
}

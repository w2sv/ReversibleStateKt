import org.gradle.kotlin.dsl.libs

plugins {
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.android.library)
    `maven-publish`
}

kotlin {
    jvmToolchain(libs.versions.java.get().toInt())
}

android {
    namespace = "com.w2sv.reversiblestate.datastore.android"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
//    @Suppress("UnstableApiUsage")
//    testOptions {
//        unitTests {
//            isIncludeAndroidResources = true
//            isReturnDefaultValues = true
//        }
//    }
    buildFeatures {
        buildConfig = false
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = "com.w2sv.reversiblestate"
            artifactId = "datastore.android"
            version = version.toString()
            afterEvaluate {
                from(components["release"])
            }
            pom {
                developers {
                    developer {
                        id.set("w2sv")
                        name.set("Janek Zangenberg")
                    }
                }
                description.set("Reversible state holders.")
                url.set("https://github.com/w2sv/ReversibleStateKt")
                licenses {
                    license {
                        name.set("The Apache Software License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
            }
        }
    }
}

dependencies {
    implementation(project(":reversiblestate:core"))
    implementation(libs.w2sv.datastoreutils.datastoreflow)
    implementation(libs.w2sv.datastoreutils.preferences)
    implementation(libs.kotlinx.coroutines)
}
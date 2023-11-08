import org.gradle.api.initialization.resolve.RepositoriesMode.FAIL_ON_PROJECT_REPOS

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    val sqlDelightVersion: String by settings
    plugins {
        id("com.google.gms.google-services") version "4.3.15"
        id("com.google.firebase.crashlytics") version "2.9.6"
        id("app.cash.sqldelight") version sqlDelightVersion
    }
}

plugins {
    id("com.gradle.enterprise") version "3.13.3"
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositoriesMode = FAIL_ON_PROJECT_REPOS
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
    }
}

gradleEnterprise {
    buildScan {
        termsOfServiceUrl = "https://gradle.com/terms-of-service"
        termsOfServiceAgree = "yes"
    }
}

rootProject.name = "TPMS Advanced"
include(":core:common")
include(":core:test")
include(":core:android-test")
include(":core:ui")
include(":core:debug-ui")
include(":core:database")
include(":data:unit")
include(":data:app")
include(":data:vehicle")
include(":feature:core")
include(":feature:unit")
include(":feature:qrcode")
include(":feature:background")
include(":feature:shortcut")
include(":app:phone")

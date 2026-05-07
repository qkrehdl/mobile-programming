pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "mp2601"
include(":app")
include(":mp04_stopwatch")
include(":mp05_resources")
include(":mp05_kakao")
include(":mp06")
include(":mp0701_ANR")
include(":mp0702_ToDo")
include(":mp0703_jukebox_player")
include(":mp0703_jukebox_service")
include(":mp0704")
include(":mp0705")
include(":mp0801")
include(":mp0802")

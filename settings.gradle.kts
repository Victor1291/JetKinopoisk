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
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "JetCinema"
include(":app")
include(":core:models")
include(":core:network")
include(":feature:home")
include(":feature:detail_movie")
include(":feature:list_movies")
include(":feature:detail_person")
include(":feature:search")
include(":core:database")
include(":feature:bottom_sheet")
include(":feature:profile")

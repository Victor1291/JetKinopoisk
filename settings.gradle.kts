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
include(":feature:gallery")
include(":shared:cinema_elements")
include(":feature:filter")
include(":core:design_system")
include(":core:testing")
include(":feature:my_list")
include(":feature:posts")

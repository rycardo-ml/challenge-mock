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

rootProject.name = "ChallengeMock"
include(":app")
include(":ui:core")
include(":feature:product")
include(":data:core")
include(":data:product")
include(":domain:product")
include(":common")
include(":data:database")
include(":feature:form")
include(":domain:form")

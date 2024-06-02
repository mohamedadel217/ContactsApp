pluginManagement {
    repositories {
        google()
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

rootProject.name = "Contacts App"
include(":app")
include(":common")
include(":base")
include(":data")
include(":domain")
include(":feature")
include(":local")
include(":remote")

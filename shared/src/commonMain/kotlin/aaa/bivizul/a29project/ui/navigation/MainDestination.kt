package aaa.bivizul.a29project.ui.navigation

sealed class MainDestination(val route: String) {
    object Spbkhome : MainDestination(route = "Spbkhome")
    object Home : MainDestination(route = "Home")
    object Detail : MainDestination(route = "Detail")
}

package com.example.goalkeeper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.goalkeeper.nav.Routes
import com.example.goalkeeper.screen.MainScreen
import com.example.goalkeeper.screen.RegisterScreen
import com.example.goalkeeper.screen.WelcomeScreen
import com.example.goalkeeper.ui.theme.GoalKeeperTheme
import com.example.goalkeeper.viewmodel.GoalKeeperViewModel
import com.example.goalkeeper.viewmodel.GoalKeeperViewModelFactory
import com.google.firebase.Firebase
import com.google.firebase.database.database

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GoalKeeperTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyApp()
                }
            }
        }
    }
}

@Composable
fun rememberViewModelStoreOwner(): ViewModelStoreOwner {
    val context = LocalContext.current
    return remember(context) { context as ViewModelStoreOwner }
}

val LocalNavGraphViewModelStoreOwner =
    staticCompositionLocalOf<ViewModelStoreOwner> {
        error("Undefined")
    }

@Composable
fun MyApp() {
    val navController = rememberNavController()
    val navStoreOwner = rememberViewModelStoreOwner()

    val goalKeeperDB = Firebase.database.getReference("goalkeeper")

    val viewModel: GoalKeeperViewModel = viewModel(factory = GoalKeeperViewModelFactory(goalKeeperDB))

    CompositionLocalProvider(
        LocalNavGraphViewModelStoreOwner provides navStoreOwner
    ) {
        NavHost(navController, startDestination = Routes.Welcome.route) {
            composable(Routes.Welcome.route) { WelcomeScreen(navController) }
            composable(Routes.Main.route) { MainScreen() }
            composable(Routes.Register.route) { RegisterScreen(navController) }
        }
    }
}
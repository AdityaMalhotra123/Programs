package hu.ait.highlowgame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import hu.ait.highlowgame.navigation.MainNavigation
import hu.ait.highlowgame.screen.GameScreen
import hu.ait.highlowgame.screen.MainMenuScreen
import hu.ait.highlowgame.ui.theme.HighLowGameTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HighLowGameTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainNavHost()
                }
            }
        }
    }
}

@Composable
fun MainNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = MainNavigation.MainScreen.route
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(MainNavigation.MainScreen.route) { MainMenuScreen(
            {navController.navigate(MainNavigation.GameScreen.createRoute(10))}
        ) }
        composable(MainNavigation.GameScreen.route,
            arguments = listOf(
                navArgument("upperBound") {
                    defaultValue=3
                    type= NavType.IntType}
            )) {
            // 10...
            //val upperBound = it.arguments?.getInt("upperBound")


            GameScreen()
        }

    }
}

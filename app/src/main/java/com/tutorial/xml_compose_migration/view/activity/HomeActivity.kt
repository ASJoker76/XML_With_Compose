package com.tutorial.xml_compose_migration.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.tutorial.xml_compose_migration.R
import com.tutorial.xml_compose_migration.databinding.ActivityHomeBinding
import com.tutorial.xml_compose_migration.databinding.ActivityMainBinding
import com.tutorial.xml_compose_migration.ui.navigation.EnterAnimation
import com.tutorial.xml_compose_migration.ui.navigation.NavigationScreen
import com.tutorial.xml_compose_migration.ui.theme.BottomNavWithBadgesTheme
import com.tutorial.xml_compose_migration.ui.theme.ComposeTheme
import com.tutorial.xml_compose_migration.utils.BottomNavItem
import com.tutorial.xml_compose_migration.view.screen1.Screen1
import com.tutorial.xml_compose_migration.view.screen1.Screen1ViewModel
import com.tutorial.xml_compose_migration.view.screen2.Screen2
import com.tutorial.xml_compose_migration.view.screen2.Screen2ViewModel
import com.tutorial.xml_compose_migration.view.screen_home.ScreenHome
import com.tutorial.xml_compose_migration.view.screen_home.ScreenHomeViewModel
import com.tutorial.xml_compose_migration.view.screen_login.ScreenLogin
import com.tutorial.xml_compose_migration.view.screen_login.ScreenLoginViewModel
import com.tutorial.xml_compose_migration.viewmodel.HomeViewModel

class HomeActivity : ComponentActivity() {

    private lateinit var binding: ActivityHomeBinding

    private val screen1ViewModel: Screen1ViewModel by viewModels()
    private val screen2ViewModel: Screen2ViewModel by viewModels()
    private val homeViewModel: ScreenHomeViewModel by viewModels()
    private val loginViewModel: ScreenLoginViewModel by viewModels()

    @ExperimentalMaterialApi
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cvView.setContent {
            ComposeTheme {

                BottomNavWithBadgesTheme {
                    val navController = rememberNavController()
                    Scaffold(
                        bottomBar = {
                            BottomNavigationBar(
                                items = listOf(
                                    BottomNavItem(
                                        name = NavigationScreen.SCREEN_HOME.name,
                                        route = NavigationScreen.SCREEN_HOME.name,
                                        icon = Icons.Default.Home
                                    ),
                                    BottomNavItem(
                                        name = NavigationScreen.SCREEN_1.name,
                                        route = NavigationScreen.SCREEN_1.name,
                                        icon = Icons.Default.Notifications,
                                        badgeCount = 23
                                    ),
                                    BottomNavItem(
                                        name = NavigationScreen.SCREEN_2.name,
                                        route = NavigationScreen.SCREEN_2.name,
                                        icon = Icons.Default.Settings,
                                        badgeCount = 0
                                    ),
                                ),
                                navController = navController,
                                onItemClick = {
                                    navController.navigate(it.route)
                                }
                            )
                        }
                    ) {
                        NavHost(navController = navController, startDestination = NavigationScreen.SCREEN_LOGIN.name) {
                            composable(NavigationScreen.SCREEN_LOGIN.name) {
                                EnterAnimation {
                                    ScreenLogin(navController, loginViewModel, applicationContext)
                                }
                            }
                            composable(NavigationScreen.SCREEN_HOME.name) {
                                EnterAnimation {
                                    ScreenHome(navController, homeViewModel, movieList = homeViewModel.movieListResponse)
                                    homeViewModel.getMovieList()
                                }
                            }
                            composable(NavigationScreen.SCREEN_1.name) {
                                EnterAnimation {
                                    Screen1(navController, screen1ViewModel, homeViewModel.movieListResponse)
                                }
                            }

                            composable(NavigationScreen.SCREEN_2.name) {
                                EnterAnimation {
                                    Screen2(navController, screen2ViewModel, homeViewModel.movieListResponse)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun BottomNavigationBar(
    items: List<BottomNavItem>,
    navController: NavController,
    modifier: Modifier = Modifier,
    onItemClick: (BottomNavItem) -> Unit
) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    BottomNavigation(
        modifier = modifier,
        backgroundColor = Color.DarkGray,
        elevation = 5.dp
    ) {
        items.forEach { item ->
            val selected = item.route == backStackEntry.value?.destination?.route
            BottomNavigationItem(
                selected = selected,
                onClick = { onItemClick(item) },
                selectedContentColor = Color.Green,
                unselectedContentColor = Color.Gray,
                icon = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        if (item.badgeCount > 0) {
                            BadgeBox(
                                badgeContent = {
                                    Text(text = item.badgeCount.toString())
                                }
                            ) {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.name
                                )
                            }
                        } else {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.name
                            )
                        }
                        if (selected) {
                            Text(
                                text = item.name,
                                textAlign = TextAlign.Center,
                                fontSize = 10.sp
                            )
                        }
                    }
                }
            )
        }
    }
}

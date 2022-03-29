package com.tutorial.xml_compose_migration.view.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.*

import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings

import androidx.compose.ui.platform.ViewCompositionStrategy

import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import com.tutorial.xml_compose_migration.databinding.FragmentMyBinding
import com.tutorial.xml_compose_migration.ui.theme.BottomNavWithBadgesTheme

import android.os.Bundle
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import coil.size.Scale
import coil.transform.CircleCropTransformation
import com.tutorial.xml_compose_migration.utils.BottomNavItem
import com.tutorial.xml_compose_migration.R
import com.tutorial.xml_compose_migration.model.ResMovie
import com.tutorial.xml_compose_migration.viewmodel.HomeViewModel

class MyFragment : Fragment(R.layout.fragment_my) {

    private var _binding: FragmentMyBinding? = null
    private val binding: FragmentMyBinding
        get() = _binding!!

    private val homeViewModel by viewModels<HomeViewModel>()

    @ExperimentalMaterialApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyBinding.inflate(inflater, container, false)
        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                BottomNavWithBadgesTheme {
                    val navController = rememberNavController()
                    Scaffold(
                        bottomBar = {
                            BottomNavigationBar(
                                items = listOf(
                                    BottomNavItem(
                                        name = "Home",
                                        route = "home",
                                        icon = Icons.Default.Home
                                    ),
                                    BottomNavItem(
                                        name = "Chat",
                                        route = "chat",
                                        icon = Icons.Default.Notifications,
                                        badgeCount = 23
                                    ),
                                    BottomNavItem(
                                        name = "Settings",
                                        route = "settings",
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
                        Navigation(navController = navController, homeViewModel)
                    }
                }
            }
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

@Composable
fun Navigation(navController: NavHostController, homeViewModel: HomeViewModel) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            MovieList(movieList = homeViewModel.movieListResponse)
            homeViewModel.getMovieList()
        }
        composable("chat") {
            ChatScreen(movieList = homeViewModel.movieListResponse)
        }
        composable("settings") {
            SettingsScreen(movieList = homeViewModel.movieListResponse)
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
                    Column(horizontalAlignment = CenterHorizontally) {
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

@Composable
fun MovieList(movieList: List<ResMovie>) {
    var selectedIndex by remember { mutableStateOf(-1) }
    LazyColumn {
        itemsIndexed(items = movieList) { index, item ->
            MovieItem(movie = item, index, selectedIndex) { i ->
                selectedIndex = i
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun MovieItem() {
    val movie = ResMovie(
        "Coco",
        "https://howtodoandroid.com/images/coco.jpg",
        "Coco is a 2017 American 3D computer-animated musical fantasy adventure film produced by Pixar",
        "Latest"
    )

    MovieItem(movie = movie, 0, 0) { i ->

    }
}

@Composable
fun MovieItem(movie: ResMovie, index: Int, selectedIndex: Int, onClick: (Int) -> Unit) {

    val backgroundColor =
        if (index == selectedIndex) MaterialTheme.colors.primary else MaterialTheme.colors.background
    Card(
        modifier = Modifier
            .padding(8.dp, 4.dp)
            .fillMaxWidth()
            .clickable { onClick(index) }
            .height(110.dp), shape = RoundedCornerShape(8.dp), elevation = 4.dp
    ) {
        Surface(color = backgroundColor) {

            Row(
                Modifier
                    .padding(4.dp)
                    .fillMaxSize()
            ) {

                Image(
                    painter = rememberImagePainter(
                        data = movie.imageUrl,

                        builder = {
                            scale(Scale.FILL)
                            placeholder(R.drawable.ic_launcher_background)
                            transformations(CircleCropTransformation())

                        }
                    ),
                    contentDescription = movie.desc,
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(0.2f)
                )


                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxHeight()
                        .weight(0.8f)
                ) {
                    Text(
                        text = movie.name,
                        style = MaterialTheme.typography.subtitle1,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = movie.category,
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier
                            .background(
                                Color.LightGray
                            )
                            .padding(4.dp)
                    )
                    Text(
                        text = movie.desc,
                        style = MaterialTheme.typography.body1,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                }
            }
        }
    }

}

@Composable
fun ChatScreen(movieList: List<ResMovie>) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn() {
            itemsIndexed(
                items = movieList
            )
            { index, item ->
                Text(
                    text = item.name,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp)
                )
            }
        }
    }
}

@Composable
fun SettingsScreen(movieList: List<ResMovie>) {
    val constraints = ConstraintSet {
        val greenBox = createRefFor("greenbox")
        val redBox = createRefFor("redbox")
        val gambar = createRefFor("gambar")
        val judul = createRefFor("judul")

        constrain(judul){
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            width = Dimension.fillToConstraints
            height = Dimension.wrapContent
        }

        constrain(gambar){
            top.linkTo(judul.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            width = Dimension.value(250.dp)
            height = Dimension.value(250.dp)
        }

        constrain(greenBox) {
            top.linkTo(gambar.bottom)
            start.linkTo(parent.start)
            width = Dimension.value(100.dp)
            height = Dimension.value(100.dp)
        }
        constrain(redBox) {
            top.linkTo(gambar.bottom)
            start.linkTo(greenBox.end)
            end.linkTo(parent.end)
            width = Dimension.fillToConstraints
            height = Dimension.value(100.dp)
        }

    }


    ConstraintLayout(constraints, modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Agus",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp)
                .layoutId("judul")
        )
        
        Image(
            painter = rememberImagePainter(
                data = movieList.get(0).imageUrl,

                builder = {
                    scale(Scale.FILL)
                    placeholder(R.drawable.ic_launcher_background)
                    transformations(CircleCropTransformation())

                }
            ),
            contentDescription = movieList.get(0).desc,
            modifier = Modifier
                .padding(vertical = 24.dp)
                .fillMaxHeight()
                .layoutId("gambar")
        )

        Box (modifier = Modifier
            .background(Color.Green)
            .layoutId("greenbox"))
        Box (modifier = Modifier
            .background(Color.Red)
            .layoutId("redbox"))
    }
}
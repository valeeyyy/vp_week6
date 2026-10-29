package com.valerie.vp_week6.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp as dpUnit
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.PriorityHigh
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.sp
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.TextButton
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.valerie.vp_week6.navigation.AppNavGraph
import com.valerie.vp_week6.navigation.Routes
import com.valerie.vp_week6.util.UiEvent
import com.valerie.vp_week6.viewmodel.CampusViewModel

private val HomeFilled: ImageVector = ImageVector.Builder("HomeFilled", 24.dpUnit, 24.dpUnit, 24f, 24f).apply {
    path(
        fill = SolidColor(Color.Black),
        stroke = SolidColor(Color.Black),
        strokeLineWidth = 1.8f,
        strokeLineJoin = StrokeJoin.Round
    ) {
        moveTo(12f, 3.8f); lineTo(19.8f, 10.4f); verticalLineTo(19.2f)
        horizontalLineTo(14.6f); verticalLineTo(14.6f); horizontalLineTo(9.4f); verticalLineTo(19.2f)
        horizontalLineTo(4.2f); verticalLineTo(10.4f); close()
    }
}.build()

private data class TopLevelItem(val route: String, val label: String, val icon: ImageVector)

private val topLevelItems = listOf(
    TopLevelItem(Routes.DASHBOARD, "Dashboard", HomeFilled),
    TopLevelItem(Routes.COMMUNITIES, "Communities", Icons.Filled.People)
)

@Composable
fun MainScreen(viewModel: CampusViewModel = viewModel()) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(viewModel) {
        viewModel.events.collect { event ->
            if (event is UiEvent.ShowMessage) {
                snackbarHostState.currentSnackbarData?.dismiss()
                snackbarHostState.showSnackbar(event.message, actionLabel = "OK")
            }
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        snackbarHost = {
            SnackbarHost(snackbarHostState) { data ->
                Surface(
                    modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    color = Color(0xFF1B2433)
                ) {
                    Row(
                        Modifier.padding(start = 16.dp, end = 4.dp, top = 6.dp, bottom = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(Modifier.size(28.dp).background(Color(0xFFF5A623), CircleShape), contentAlignment = Alignment.Center) {
                            Text("!", color = Color(0xFF1B2433), fontSize = 17.sp, lineHeight = 17.sp, fontWeight = FontWeight.ExtraBold)
                        }
                        Text(
                            data.visuals.message,
                            modifier = Modifier.weight(1f).padding(horizontal = 12.dp),
                            color = Color.White,
                            fontSize = 13.sp,
                            lineHeight = 17.sp,
                            softWrap = true
                        )
                        TextButton(onClick = { data.dismiss() }) {
                            Text("OK", color = Color(0xFF8AB4FF), fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        },
        bottomBar = {
            if (currentRoute in Routes.topLevel) {
                NavigationBar(containerColor = Color.White) {
                    topLevelItems.forEach { item ->
                        NavigationBarItem(
                            selected = currentRoute == item.route,
                            onClick = {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = { Icon(item.icon, contentDescription = item.label) },
                            label = { Text(item.label, fontWeight = FontWeight.Bold) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Color(0xFF1E3A8A),
                                selectedTextColor = Color(0xFF0F172A),
                                indicatorColor = Color(0xFFDCE6FF),
                                unselectedIconColor = Color(0xFF64748B),
                                unselectedTextColor = Color(0xFF64748B)
                            )
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        AppNavGraph(
            navController = navController,
            viewModel = viewModel,
            innerPadding = innerPadding
        )
    }
}

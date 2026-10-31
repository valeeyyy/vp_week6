package com.valerie.vp_week6.view.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.valerie.vp_week6.view.screens.ClubProjectsScreen
import com.valerie.vp_week6.view.screens.CommunitiesScreen
import com.valerie.vp_week6.view.screens.DashboardScreen
import com.valerie.vp_week6.view.screens.ProjectDetailScreen
import com.valerie.vp_week6.viewmodel.CampusViewModel

@Composable
fun AppNavGraph(
    navController: NavHostController,
    viewModel: CampusViewModel,
    innerPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Routes.DASHBOARD,
        modifier = modifier
    ) {
        composable(Routes.DASHBOARD) {
            DashboardScreen(
                viewModel = viewModel,
                contentPadding = innerPadding,
                onProjectClick = { navController.navigate(Routes.projectDetail(it)) }
            )
        }
        composable(Routes.COMMUNITIES) {
            CommunitiesScreen(
                viewModel = viewModel,
                contentPadding = innerPadding,
                onOpenClub = { navController.navigate(Routes.clubProjects(it)) }
            )
        }
        composable(
            route = Routes.CLUB_PROJECTS,
            arguments = listOf(navArgument(Routes.ARG_CLUB_ID) { type = NavType.StringType })
        ) { entry ->
            ClubProjectsScreen(
                clubId = entry.arguments?.getString(Routes.ARG_CLUB_ID).orEmpty(),
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onProjectClick = { navController.navigate(Routes.projectDetail(it)) }
            )
        }
        composable(
            route = Routes.PROJECT_DETAIL,
            arguments = listOf(navArgument(Routes.ARG_PROJECT_ID) { type = NavType.StringType })
        ) { entry ->
            ProjectDetailScreen(
                projectId = entry.arguments?.getString(Routes.ARG_PROJECT_ID).orEmpty(),
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}

package com.example.finalproject.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.finalproject.ui.view.DashboardScreen
import com.example.finalproject.ui.view.pelanggan.DetailViewPelanggan
import com.example.finalproject.ui.view.pelanggan.InsertPelangganView
import com.example.finalproject.ui.view.pelanggan.UpdatePelangganView
import com.example.finalproject.ui.view.reservasi.DetailViewReservasi
import com.example.finalproject.ui.view.reservasi.InsertReservasiView
import com.example.finalproject.ui.view.reservasi.PageReservasiScreen
import com.example.finalproject.ui.view.reservasi.UpdateReservasiView
import com.example.finalproject.ui.view.review.DetailViewReview
import com.example.finalproject.ui.view.review.InsertReviewView
import com.example.finalproject.ui.view.review.PageReviewScreen
import com.example.finalproject.ui.view.review.UpdateReviewView
import com.example.finalproject.ui.view.villa.DetailView
import com.example.finalproject.ui.view.villa.InsertVillaView
import com.example.finalproject.ui.view.villa.UpdateVillaView

@Composable
fun PengelolaHalaman(
    modifier: Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiDashboard.route,
        modifier = modifier
    ) {
        composable(DestinasiDashboard.route) {
            DashboardScreen(
                navigateToItemEntryVilla = {
                    navController.navigate(DestinasiInsertVilla.route)
                },
                onClickReview = {
                    navController.navigate(
                        DestinasiPageReview.route
                    )
                },
                onClickPelanggan = {
                    navController.navigate(
                        DestinasiPagePelanggan.route
                    )
                },
                onClickReservasi = {
                    navController.navigate(
                        DestinasiPageReservasi.route
                    )
                },
                onDetailClick = { detailVilla, reviews ->
                    navController.navigate("${DestinasiDetailVilla.route}/${detailVilla.idVilla}")
                }
            )
        }


        //Villa
        composable(DestinasiInsertVilla.route) {
            InsertVillaView(
                navigateBack = { navController.popBackStack() },
                onNavigate = {
                    navController.popBackStack()
                },
                modifier = modifier
            )
        }
        composable(
            route = DestinasiDetailVilla.routesWithArg,
            arguments = listOf(navArgument(DestinasiDetailVilla.idVilla) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val idVilla = backStackEntry.arguments?.getString(DestinasiDetailVilla.idVilla) ?: return@composable

            DetailView(
                idVilla = idVilla,
                navigateBack = { navController.popBackStack() },
                onAddReservasi = {
                    navController.navigate(DestinasiInsertReservasi.route)
                },
                onEditVilla = {
                    navController.navigate("${DestinasiEditVilla.route}/$idVilla")
                },
                modifier = Modifier.fillMaxSize()
            )
        }

        composable(
            route = DestinasiEditVilla.routesWithArg,
            arguments = listOf(navArgument(DestinasiEditVilla.idVilla) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val idVilla = backStackEntry.arguments?.getString(DestinasiEditVilla.idVilla)
                ?: return@composable

            UpdateVillaView(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() },
                idVilla = idVilla
            )
        }

        //Pelanggan
        composable(DestinasiPagePelanggan.route) {
            InsertPelangganView(
                onDetailClick = {
                    navController.navigate("${DestinasiDetailPelanggan.route}/$it")
                }

            )
        }

        composable(
            route = DestinasiDetailPelanggan.routesWithArg,
            arguments = listOf(navArgument(DestinasiDetailPelanggan.idPelanggan) {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            val idPelanggan = backStackEntry.arguments?.getInt(DestinasiDetailPelanggan.idPelanggan) ?: return@composable

            DetailViewPelanggan(
                idPelanggan = idPelanggan, // Pastikan idReservasi diteruskan dengan benar
                navigateBack = { navController.popBackStack() },
                onEditClick = {
                    navController.navigate("${DestinasiEditPelanggan.route}/$idPelanggan")
                },
            )
        }

        composable(
            route = DestinasiEditPelanggan.routesWithArg,
            arguments = listOf(navArgument(DestinasiEditPelanggan.idPelanggan) {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            val idPelanggan = backStackEntry.arguments?.getInt(DestinasiEditPelanggan.idPelanggan)
                ?: return@composable

            // Layar Edit Reservasi
            UpdatePelangganView(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() },
                idPelanggan = idPelanggan
            )
        }


        //Reservasi
        composable(DestinasiPageReservasi.route) {
            PageReservasiScreen(
                onDetailClick = {
                    navController.navigate("${DestinasiDetailReservasi.route}/$it")
                }
            )
        }

        composable(DestinasiInsertReservasi.route) {
            InsertReservasiView(
                onNavigate = {
                    navController.popBackStack()
                },
                modifier = modifier
            )
        }

        composable(
            route = DestinasiDetailReservasi.routesWithArg,
            arguments = listOf(navArgument(DestinasiDetailReservasi.idReservasi) {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            val idReservasi = backStackEntry.arguments?.getInt(DestinasiDetailReservasi.idReservasi) ?: return@composable

            DetailViewReservasi(
                idReservasi = idReservasi,
                navigateBack = { navController.popBackStack() },
                onEditClick = {
                    navController.navigate("${DestinasiEditReservasi.route}/$idReservasi")
                },
            )
        }
        composable(
            route = DestinasiEditReservasi.routesWithArg,
            arguments = listOf(navArgument(DestinasiEditReservasi.idReservasi) {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            val idReservasi = backStackEntry.arguments?.getInt(DestinasiEditReservasi.idReservasi)
                ?: return@composable

            // Layar Edit Reservasi
            UpdateReservasiView(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() },
                idReservasi = idReservasi
            )
        }

        //Review
        composable(DestinasiPageReview.route) {
            PageReviewScreen(
                navigateToItemEntryReview = {
                    navController.navigate(DestinasiInsertReview.route)
                },
                onDetailClick = {
                    navController.navigate("${DestinasiDetailReview.route}/$it")
                }
            )
        }
        composable(DestinasiInsertReview.route) {
            InsertReviewView(
                onNavigate = {
                    navController.popBackStack()
                },
                modifier = modifier
            )
        }

        composable(
            route = DestinasiDetailReview.routesWithArg,
            arguments = listOf(navArgument(DestinasiDetailReview.idReview) {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            val idReview = backStackEntry.arguments?.getInt(DestinasiDetailReview.idReview) ?: return@composable

            DetailViewReview(
                idReview = idReview,
                navigateBack = { navController.popBackStack() },
                onEditClick = {
                    navController.navigate("${DestinasiEditReview.route}/$idReview")
                },
            )
        }
        composable(
            route = DestinasiEditReview.routesWithArg,
            arguments = listOf(navArgument(DestinasiEditReview.idReview) {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            val idReview = backStackEntry.arguments?.getInt(DestinasiEditReview.idReview)
                ?: return@composable

            UpdateReviewView(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() },
                idReview = idReview
            )
        }

    }
}
package com.example.finalproject.navigation

interface DestinasiNavigasi {
    val route: String
    val titleRes: String
}

//dashboard
object DestinasiDashboard : DestinasiNavigasi{
    override val route: String = "home"
    override val titleRes: String ="home"
}

//villa
object DestinasiDetailVilla : DestinasiNavigasi{
    override val route ="detailvilla"
    override val titleRes: String ="detailvilla"
    const val idVilla = "id_villa"
    val routesWithArg = "$route/{$idVilla}"
}
object DestinasiEditVilla : DestinasiNavigasi{
    override val route ="editvilla"
    override val titleRes: String ="edit villa"
    const val idVilla = "id_villa"
    val routesWithArg = "$route/{$idVilla}"
}

object DestinasiInsertVilla: DestinasiNavigasi{
    override val route: String = "insert villa"
    override val titleRes: String ="Insert Villa"
}

//Review
object DestinasiPageReview: DestinasiNavigasi {
    override val route: String = "pagereview"
    override val titleRes: String = "Review"
}

object DestinasiInsertReview: DestinasiNavigasi{
    override val route: String = "insertreview"
    override val titleRes: String ="Insert Review"
}

object DestinasiEditReview : DestinasiNavigasi{
    override val route ="editreview"
    override val titleRes: String ="edit Review"
    const val idReview = "id_review"
    val routesWithArg = "$route/{$idReview}"
}

object DestinasiDetailReview : DestinasiNavigasi {
    override val route = "detailreview"
    override val titleRes: String = "detailreview"
    const val idReview = "id_review"
    val routesWithArg = "$route/{$idReview}"
}

//Pelanggan

object DestinasiPagePelanggan: DestinasiNavigasi {
    override val route: String = "pagepelanggan"
    override val titleRes: String = "Review"
}

object DestinasiDetailPelanggan : DestinasiNavigasi{
    override val route ="detailpelanggan"
    override val titleRes: String ="detailpelanggan"
    const val idPelanggan = "id_pelanggan"
    val routesWithArg = "$route/{$idPelanggan}"
}

object DestinasiEditPelanggan : DestinasiNavigasi{
    override val route ="editpelanggan"
    override val titleRes: String ="editpelanggan"
    const val idPelanggan = "id_pelanggan"
    val routesWithArg = "$route/{$idPelanggan}"
}

object DestinasiInsertPelanggan: DestinasiNavigasi{
    override val route: String = "insertpelanggan"
    override val titleRes: String ="Insert Pelanggan"
}

//Reservasi
object DestinasiPageReservasi: DestinasiNavigasi {
    override val route: String = "pagereservasi"
    override val titleRes: String = "Review"
}

object DestinasiDetailReservasi : DestinasiNavigasi {
    override val route = "detailreservasi"
    override val titleRes: String = "detailreservasi"
    const val idReservasi = "id_reservasi"
    val routesWithArg = "$route/{$idReservasi}"
}

object DestinasiEditReservasi : DestinasiNavigasi {
    override val route = "editreservasi"
    override val titleRes: String = "editreservasi"
    const val idReservasi = "id_reservasi"
    val routesWithArg = "$route/{$idReservasi}"
}
object DestinasiInsertReservasi: DestinasiNavigasi{
    override val route: String = "insertreservasi"
    override val titleRes: String ="Insert Reservasi"
}



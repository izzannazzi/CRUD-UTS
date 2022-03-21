package com.tauhidizzan.crud_uts

data class DataPeminjam(
    val id : String,
    val nama: String,
    val umur : Int

){
    constructor(): this("", "", 0){

    }
}
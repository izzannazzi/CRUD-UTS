package com.tauhidizzan.crud_uts

data class Peminjam(
    val id : String,
    val nama: String,
    val asal : String

){
    constructor(): this("", "", ""){

    }
}
package com.example.carousel

import java.io.Serializable
import java.util.*
import kotlin.collections.ArrayList

data class Product(
    val id: Int,
    val vendorId: String = "Belethor",
    val title: String,
    val description: String = "",
    val amountLeft: Int = 0,
    val price: Double,
    val rating: Double = 3.0,
    val numberOfRatings: Int = 0,
    val comments: ArrayList<Comment> = ArrayList<Comment>(),
    //val photoUrl: String,
    val photoUrl: Int, //temporarily
    val tags: String = "",
    val brand: String = "",
    val shipmentPrice: Double = 0.0,
    val soldAmount: Int = 0,
    val releaseDate: Date = Date(),
    val cargoCompany: String = "",
    val category: String = "",
    //val isConfirmed: Boolean
    ) : Serializable
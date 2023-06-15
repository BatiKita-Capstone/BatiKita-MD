package com.example.batikkita.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Batik(
    val name: String,
    val thumbnail: String,
    val description: String,
    val similar1: String,
    val similar2: String,
    val similar3: String,
    val similar4: String,
    val similar5: String,
    val similar6: String,
    val similar7: String,
    val similar8: String,
    val similar9: String,
    val similar10: String,
    ): Parcelable

package com.example.myapplication.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable
@Parcelize
enum class level:Parcelable {
    TEST, EASY,NORMAL, HARD

}
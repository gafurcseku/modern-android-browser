package com.android.webbrowser.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class History(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    val image:String,
    val urlString:String,
    val date:String
):Serializable

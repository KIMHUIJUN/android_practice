package com.example.ch16_provider.db
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream
import java.util.*

@Entity
class ImageEntity (
    @PrimaryKey(autoGenerate = true) var id:Int? = null,
    @ColumnInfo(name = "title") val title :String,
    @ColumnInfo(name = "image") var image: Bitmap? = null,
)

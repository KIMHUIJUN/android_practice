package com.cookandroid.a20182376

import android.content.ContentUris
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.cookandroid.a20182376.databinding.ActivityMainBinding
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private fun getAllPhotos(){
        val uris = mutableListOf<Uri>()

        contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,null,null,null,
            "${MediaStore.Images.ImageColumns.DATE_TAKEN} Desc"
        )?.use{cursor->
            while (cursor.moveToNext()){
                val id = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID))

                val contentUri = ContentUris.withAppendedId(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,id
                )
                uris.add(contentUri)
            }
        }
        Log.d("김희준20182376","getAllPhotos: $uris")
        val adapter = MyPagerAdapter(supportFragmentManager,lifecycle)
        adapter.uris = uris

        binding.viewPager.adapter = adapter
        val viewpager = binding.viewPager
        timer(period = 3000){
            runOnUiThread{
                if(viewpager.currentItem < adapter.itemCount -1){
                   viewpager.currentItem = viewpager.currentItem +1
                }else{
                    viewpager.currentItem = 0
                }
            }
        }
    }
    private val requestPremissionLancher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()){
            isGranted ->
        if (isGranted){
            getAllPhotos()

        }else{
            Toast.makeText( this,"권한이 거부됨", Toast.LENGTH_SHORT).show()
        }
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if(ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            )!= PackageManager.PERMISSION_GRANTED
        ){
            if(ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                )
            ){
                AlertDialog.Builder(this).apply {
                    setTitle("권한이 필요한 이유")
                    setMessage("사진 정보를 얻으려면 외부 저장소 권한이 필요합니다.")
                    setPositiveButton("권한 요청"){
                        _,_, -> requestPremissionLancher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    }
                    setNegativeButton("거부",null)
                }.show()
            }else{
                requestPremissionLancher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            }
            return
        }else{
            getAllPhotos()
        }

    }
}
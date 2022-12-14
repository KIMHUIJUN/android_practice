package com.example.naver

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback

class MainActivity : AppCompatActivity(), OnMapReadyCallback  {
    lateinit var nMap : NaverMap
    lateinit var mapFrag : MapFragment
    lateinit var button1 : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        registerForContextMenu(button1)

    }
    override fun onCreateContextMenu(menu: ContextMenu?, v: View?,menuInfo : ContextMenu.ContextMenuInfo?){
        super.onCreateContextMenu(menu,v,menuInfo)
        var mInflater = this.menuInflater
        if (v === button1){
            menu!!.setHeaderTitle("배경색 변경")
            mInflater.inflate(R.menu.menu1,menu)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu1, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){

            R.id.item1 -> {
                val seoul = LatLng(37.553586,126.971398)

            }
            R.id.item2 -> {
                var dlg = AlertDialog.Builder(this@MainActivity)
                Toast.makeText(applicationContext,"네이버지도도도도도도도",Toast.LENGTH_SHORT).show()
            }
            R.id.home -> {
                var dlg = AlertDialog.Builder(this@MainActivity)
                dlg.setTitle("뭐하지뭐하지")
                dlg.setMessage("부산부산부산부산부산부산부산부산")
                dlg.setIcon(R.mipmap.ic_launcher)
                dlg.show()
            }

        }

        return super.onOptionsItemSelected(item)
    }

    override fun onMapReady(naverMap: NaverMap) {
        nMap = naverMap

        // Add a marker in Sydney and move the camera
        val seoul = LatLng(37.553586, 126.971398)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
    }
}

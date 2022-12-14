package com.cookandroid.gpsmap

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.cookandroid.gpsmap.databinding.ActivityMapsBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    // (1)위치 정보를 주기적으로 얻기위한 객체
    private val fusedLocationProviderClient by lazy {
        FusedLocationProviderClient(this)
    }

    // (2)위치 정보 요청에 대한 세부 정보 설정
    private val locationRequest by lazy{
        LocationRequest.create().apply {
            //GPS를 사용하여 가장 정확한 위치 요구
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = 10000 //10초마다 위치 정보 갱신
            fastestInterval = 5000  //다른 앱에서 위치를 갱신했다면 5초마다 확인
        }
    }

    //(3)위치 정보를 얻으면 해야할 행동이 정의된 콜백 객체
    private val locationCallback = MyLocationCallBack()

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()){
                isGranted ->
            if(isGranted) {
                //권한이 허락되면 위치 정보 갱신
                addLocationListener()
            } else {
                //권한 거부
                Toast.makeText(this, "권한이 거부되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 프래그먼트 매니저로부터 SupportMapFragment를 얻음
        val mapFragment = supportFragmentManager //구글 맵 객체의 생명주기를 관리하는 객체
            .findFragmentById(R.id.map) as SupportMapFragment
        //getMapAsync() 메서드로 지도가 준비되면 알림을 받음
        //mapFragment에 OnMapReadyCallback 인터페이스 등록
        //그래야 지도가 준비되면 onMapReady() 함수가 자동으로 실행
        mapFragment.getMapAsync(this)

    }

    //지도가 준비되었을 때 실행되는 콜백
    override fun onMapReady(googleMap: GoogleMap) {
        // 지도가 준비되면 GoogleMap 객체를 얻음
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    //위치 요청은 액티비티가 활성화되는 onResume() 메서드에서 수행
    override fun onResume() {
        super.onResume()

        //권한 요청
        checkPermission(
            cancel = {
                //위치 정보가 필요한 이유 다이얼로그 표시
                showPermissionInfoDialog()
            },
            ok = {
                //현재 위치를 주기적으로 요청(권한이 필요한 부분)
                //주기적은 위치 정보 갱신 시작
                addLocationListener()
            }
        )
        addLocationListener() //(4)별도의 메서드로 작성
    }

    //(4)
    @SuppressLint("MissingPermission")
    private fun addLocationListener(){
        fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallback,null)
    }

    //(5)requestLocationUpdates() 메서드에 전달되는 인자 중 LocationCallback 구현
    inner class MyLocationCallBack: LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            super.onLocationResult(locationResult)
            //LocationResult 객체를 반환, lastLocation 프로퍼티로 Location 객체를 얻음
            val location = locationResult?.lastLocation

            //(6)기기의  GPS 설정이 꺼져 있거나 현재 위치 정보를 얻을 수 없는 경우 Location 객체 null
            //Location 객체가 null이 아닐 때 해당 위도와 경도 위치로 카메라 이동
            //17 level로 확대하며 현재 위치로 카메라 이동
            location?.run {
                val latLng = LatLng(latitude,longitude)
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17f))

                Log.d("MapsActivity",  "위도: $latitude, 경도: $longitude")
            }
        }
    }

    private fun checkPermission(cancel: () -> Unit, ok: () -> Unit){
        //①위치 권한이 없는지 검사
        if(ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED){
            //권한이 허용되지 않음
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)){
                //②이전에 권한을 한번 거부한 적인 있는 경우
                cancel()
            } else{
                //권한 요청
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
            return
        }
        //③권한을 수락했을 때 실행할 함수
        ok()
    }

    //사용자가 한 번 거부했을 때 권한이 필요한 이유를 설명하는 다이얼로그 표시하는 메서드
    private fun showPermissionInfoDialog(){
        AlertDialog.Builder(this).apply{
            setTitle("권한이 필요한 이유")
            setMessage("지도에 위치를 표시하려면 위치 정보 권한이 필요합니다.")
            setPositiveButton("권한 요청"){_,_ ->
                //권한 요청
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
            setNegativeButton("거부",null)
        }.show()
    }

    //앱이 동작하지 않을 때 위치 정보 요청을 삭제
    override fun onPause() {
        super.onPause()
        //위치 요청 취소
        removeLocationListener()
    }

    private fun removeLocationListener(){
        //현재 위치 요청을 삭제
        //remoteLocationUpdates( ) 메서드에  LocationCallback 객체를 전달하여
        //주기적인 위치 정보 갱신 요청을 삭제함
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }
}
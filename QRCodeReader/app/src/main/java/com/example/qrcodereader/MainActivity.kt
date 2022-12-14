package com.example.qrcodereader

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.example.qrcodereader.databinding.ActivityMainBinding
import com.google.common.util.concurrent.ListenableFuture
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    private val binding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }
    private var isDetected = false

    //1) ListenableFuture형 변수 선언
    private lateinit var cameraProviderFuture : ListenableFuture<ProcessCameraProvider>

    /*9)태그 기능 추가
       PERMISSIONS_REQUEST_CODE: 나중에 권한을 요청한 후 결과를 onRequestPermissionResult에서 받을 때 필요 */
    private val PERMISSIONS_REQUEST_CODE = 1
    //10)카메라 권한 지정
    private val PERMISSIONS_REQUEST = arrayOf(Manifest.permission.CAMERA)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //13)액티비티가 실행될 때 권한 확인
        if(!hasPermissions(this)){
            //카메라 권한 요청
            requestPermissions(PERMISSIONS_REQUEST,PERMISSIONS_REQUEST_CODE)
        } else{
            //만약 이미 권한이 있다면 카메라 시작
            startCamera()
        }
    }

    //11)권한 유무 확인
    fun hasPermissions(context: Context) = PERMISSIONS_REQUEST.all {
        ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
    }

    //12)권한 요청 콜백 함수
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == PERMISSIONS_REQUEST_CODE){
            if(PackageManager.PERMISSION_GRANTED == grantResults.firstOrNull()){
                Toast.makeText(this@MainActivity,"권한 요청이 승인되었습니다", Toast.LENGTH_LONG).show()
                //8)카메라 시작
                startCamera()
            } else {
                Toast.makeText(this@MainActivity,"권한 요청이 거부되었습니다", Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        /* 다시 사용자의 포커스가 MainActivity로 돌아온다면 다시 QR코드를 인식할 수 있도록
           onResume()함수를 오버라이드하여, is Detected를 false로 다시 돌려줌  */
        isDetected = false
    }

    fun getImageAnalysis(): ImageAnalysis {
        val cameraExecutor: ExecutorService = Executors.newSingleThreadExecutor()
        val imageAnalysis = ImageAnalysis.Builder().build()

        //Analyzer설정
        imageAnalysis.setAnalyzer(cameraExecutor,QRCodeAnalyzer(object: OnDetectListener{
            override fun onDetect(msg: String) {
                // Toast.makeText(this@MainActivity,"${msg}", Toast.LENGTH_SHORT).show()
                if(!isDetected){//한번도 QR코드가 인식된 적이 없는지 검사(중복 실행 방지)
                    isDetected = true
                    val intent = Intent(this@MainActivity, ResultActivity::class.java)
                    intent.putExtra("msg",msg)
                    startActivity(intent)
                }
            }
        }))
        return imageAnalysis
    }

    //이미보기와 이미지 분석 시작
    fun startCamera(){
        //2)cameraProviderFuture에 객체의 참조값 할당
        cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        //3) cameraProviderFuture 태스크가 끝나면 실행
        cameraProviderFuture.addListener(Runnable {
            /*4)카메라의 생명주기를 액티비티나 프래그먼트아 같은 생명 주기에 바인드해주는
                ProcessCameraProvider 객체를 가져옴 */
            val cameraProvider = cameraProviderFuture.get()

            //5)미리보기 객체 가져오기
            val preview = getPreview()
            //ImageAnalyzer 클래스의 객체 생성
            val imageAnalysis = getImageAnalysis()
            //6)후면카메라 선택
            val camerSelector = CameraSelector.DEFAULT_BACK_CAMERA

            //7)미리보기, 이미지 분석, 이미지 캡처 중 무엇을 사용할지 지정, 하아니상 선택 가능
            cameraProvider.bindToLifecycle(this, camerSelector,preview, imageAnalysis)
        }, ContextCompat.getMainExecutor(this))
    }


    //미리보기 기능을 설정하고 설정이 완료된 객체 반환
    fun  getPreview(): Preview{
        val preview: Preview = Preview.Builder().build()  //Preview 객체 생성
        //setSurfaceProvider()함수 : Preview 객체에 SurfaceProcider 설정
        preview.setSurfaceProvider(binding.barcodePreview.getSurfaceProvider())

        return preview
    }


}
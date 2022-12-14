package com.example.qrcodereader

import android.annotation.SuppressLint
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage

/*MainActivity에서 QR 코드 데이터를 인식하려면 QRCodeAnalyzer 객체를 생성할 때
  OnDetectListener를 구현하여 주 생성자의 인수로 넘겨주어야함
  QRCodeAnalyzer에서는 이 리스너를 통하여 MainActivity와 소통 */
class QRCodeAnalyzer(val onDetectListener: OnDetectListener) : ImageAnalysis.Analyzer{
    //바코드 스캐닝 객체 생성
    private val scanner = BarcodeScanning.getClient()

    @SuppressLint("UnsafeOptInUsageError")
    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image
        if(mediaImage != null){
            //이미지가 찍힐 당시 카메라의 회전 각도를 고려하여 입력 이미지 생성
            val image = InputImage.fromMediaImage(mediaImage,imageProxy.imageInfo.rotationDegrees)

            scanner.process(image)   //이미지 분석
                .addOnSuccessListener { qrCodes ->
                    for (qrCode in qrCodes){
                        onDetectListener.onDetect(qrCode.rawValue ?: "")
                    } //배열로 넘어오는 이유: 한 화면에 다수의 QR코드가 찍힐경우 모든 QR 코드를 분석하여 각각 배열로 보냄
                }
                .addOnFailureListener{ it.printStackTrace()}
                .addOnCompleteListener{imageProxy.close()}
        }
    }
}
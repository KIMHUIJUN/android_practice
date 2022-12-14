package com.example.qrcodereader

interface OnDetectListener {
    //QRCodeAnalyzer에서 QR 코드가 인식되었을 때 호출할 함수로서 데이터 내용을 인수 받음
    fun onDetect(msg: String)
}
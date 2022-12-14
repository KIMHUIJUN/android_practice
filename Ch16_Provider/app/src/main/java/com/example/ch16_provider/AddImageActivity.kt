package com.example.ch16_provider

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import com.example.ch16_provider.databinding.ActivityAddImageBinding
import com.example.ch16_provider.db.AppDatabase
import com.example.ch16_provider.db.ImageDao
import com.example.ch16_provider.db.ImageEntity
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.objects.ObjectDetection
import com.google.mlkit.vision.objects.defaults.ObjectDetectorOptions
import com.google.mlkit.vision.objects.defaults.PredefinedCategory
import java.io.File
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class AddImageActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddImageBinding
    private lateinit var filePath: String

    lateinit var db: AppDatabase
    lateinit var ImageDao: ImageDao
    lateinit var ImageList : ArrayList<ImageEntity>
    lateinit var textlast :String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddImageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //db 선언
        db = AppDatabase.getInstance(this)!!
        ImageDao = db.getImageDao()


        // 객체 검출 옵션 호출
        val options = ObjectDetectorOptions.Builder()
            .setDetectorMode(ObjectDetectorOptions.SINGLE_IMAGE_MODE)
            .enableMultipleObjects()
            .enableClassification()  // Optional
            .build()
        // 객체 검출기 선언
        val objectDetector = ObjectDetection.getClient(options)


        // 갤러리 요청
        val requestGalleryLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()){

            try {

                val calRatio = calculateInSampleSize(
                    it.data!!.data!!,
                    resources.getDimensionPixelSize(R.dimen.imgSize),
                    resources.getDimensionPixelSize(R.dimen.imgSize)
                )
                val option = BitmapFactory.Options()
                option.inSampleSize = calRatio

                var inputStream = contentResolver.openInputStream(it.data!!.data!!)
                var bitmap = BitmapFactory.decodeStream(inputStream, null, option)
                inputStream!!.close()
                inputStream = null
                bitmap?.let {
                    binding.userImageView.setImageBitmap(bitmap)
                    val image = InputImage.fromBitmap(bitmap,0)
                    objectDetector.process(image)
                        .addOnSuccessListener { detectedObjects ->
                            // Task completed successfully
                            Log.d("성공 유무 ","검출 성공!!")
                            var counting = 0
                            var detection_object = ""
                            for (detectedObject in detectedObjects) {
                                val boundingBox = detectedObject.boundingBox
                                val trackingId = detectedObject.trackingId
                                for (label in detectedObject.labels) {
                                    val text = label.text
                                    if (PredefinedCategory.FASHION_GOOD == text && counting == 0) {
                                        detection_object = "해당 이미지는 패션 카테고리로 분류합니다."
                                        insertImage("패션",bitmap)
                                        counting = 1
                                    }else if (PredefinedCategory.FOOD == text && counting == 0){
                                        detection_object = "해당 이미지는 음식 카테고리로 분류합니다."
                                        insertImage("음식",bitmap)
                                        counting = 1
                                    }else if (PredefinedCategory.PLACE == text&& counting == 0){
                                        detection_object = "해당 이미지는 명소 카테고리로 분류합니다."
                                        insertImage("명소",bitmap)
                                        counting = 1
                                    }

                                    val confidence = label.confidence
                                    binding.countingText.text = ""
                                    binding.confidentText.text = "정확도 : ${confidence}"
                                    binding.classficText.text = "${detection_object}"
                                    textlast = text
                                }
                                if (PredefinedCategory.FASHION_GOOD == textlast) {
                                    insertImage("패션",bitmap)


                                }else if (PredefinedCategory.FOOD == textlast){
                                    insertImage("음식",bitmap)

                                }else if (PredefinedCategory.PLACE == textlast){
                                    insertImage("명소",bitmap)

                                }

                            }
                        }
                        .addOnFailureListener { e ->
                            binding.countingText.text = ""
                            binding.classficText.text = "카테고리에 속하지않는 이미 입니다. 다른사진을 시도하세요"
                            Log.d("성공 유무 ","검출 실패 ㅠㅠ")

                        }

                }?: let {
                    Log.d("kkang","bitmap null")
                }
            }catch (e: Exception){
                e.printStackTrace()
            }
        }

        binding.galleryButton.setOnClickListener {
            // 갤러리 앱
            val intent = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            requestGalleryLauncher.launch(intent)
        }

        // 카메라 요청
        val requestCameraFileLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) {
            //카메라앱
            val calRatio = calculateInSampleSize(
                Uri.fromFile(File(filePath)),
                resources.getDimensionPixelSize(R.dimen.imgSize),
                resources.getDimensionPixelSize(R.dimen.imgSize)
            )
            val option = BitmapFactory.Options()
            option.inSampleSize = calRatio
            val bitmap = BitmapFactory.decodeFile(filePath,option)
            bitmap?.let {
                binding.userImageView.setImageBitmap(bitmap)
                val image = InputImage.fromBitmap(bitmap,0)
                objectDetector.process(image)
                    .addOnSuccessListener { detectedObjects ->
                        // Task completed successfully
                        Log.d("성공 유무 ","검출 성공!!")
                        var counting = 0
                        var detection_object = ""
                        for (detectedObject in detectedObjects) {
                            val boundingBox = detectedObject.boundingBox
                            val trackingId = detectedObject.trackingId
                            for (label in detectedObject.labels) {
                                val text = label.text
                                if (PredefinedCategory.FASHION_GOOD == text) {
                                    detection_object = "해당 이미지는 패션 카테고리로 분류합니다."
                                    insertImage("패션",bitmap)


                                }else if (PredefinedCategory.FOOD == text){
                                    detection_object = "해당 이미지는 음식 카테고리로 분류합니다."
                                    insertImage("음식",bitmap)

                                }else if (PredefinedCategory.PLACE == text){
                                    detection_object = "해당 이미지는 명소 카테고리로 분류합니다."
                                    insertImage("명소",bitmap)

                                }

                                val confidence = label.confidence
                                binding.countingText.text = ""
                                binding.confidentText.text = "정확도 : ${confidence}"
                                binding.classficText.text = "${detection_object}"
                            }

                        }
                    }
                    .addOnFailureListener { e ->
                        binding.countingText.text = ""
                        binding.classficText.text = "카테고리에 속하지않는 이미 입니다. 다른사진을 시도하세요"
                        Log.d("성공 유무 ","검출 실패 ㅠㅠ")

                    }

            }
        }
        //카매라 앱

        // 파일 준비
        binding.cameraButton.setOnClickListener {

            val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)

            val file = File.createTempFile("JPEG_${timeStamp}_",".jpg",storageDir)

            filePath = file.absolutePath

            val photoURI: Uri = FileProvider.getUriForFile(
                this,
                "com.example.ch16_provider.fileprovider",file
            )
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            requestCameraFileLauncher.launch(intent)
        }
    }
    private fun insertImage(ti:String,bitmap: Bitmap){
        val imageTitle = ti
        val image = bitmap
        Thread{
            ImageDao.insertTodo(ImageEntity(null,imageTitle,image))
            runOnUiThread {
                Toast.makeText(this,"추가되었습니다.",
                Toast.LENGTH_SHORT).show()
                finish()
            }
        }.start()
    }

    private fun calculateInSampleSize(fileUri: Uri, reqWidth: Int, reqHeight: Int): Int {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        try {
            var inputStream = contentResolver.openInputStream(fileUri)
            BitmapFactory.decodeStream(inputStream, null, options)
            inputStream!!.close()
            inputStream = null
        }catch (e: Exception){
            e.printStackTrace()
        }
        val (height: Int, width: Int) = options.run { outHeight to outWidth }
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth){
            val halfHeight: Int = height /2
            val halfWidth: Int = width / 2
            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth){
                inSampleSize *=2
            }
        }
        return inSampleSize
    }
}
package com.example.batikkita.ui.main

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.media.ThumbnailUtils
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import com.example.batikkita.databinding.ActivityMainBinding
import com.example.batikkita.ml.BatikModel
import com.example.batikkita.ui.detail.DetailActivity
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.ByteOrder

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var imageSize = 224

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.apply {
            buttonScan.setOnClickListener {
                if(if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        checkSelfPermission(android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                    } else{
                        TODO("VERSION.SDK_INT < M")

                    }
                ){
                    val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivityForResult(cameraIntent,1)
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(arrayOf(android.Manifest.permission.CAMERA),100)
                        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        startActivityForResult(cameraIntent,1)
                    }
                }

            }

            buttonImport.setOnClickListener {
                val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(galleryIntent,2)
            }

        }


    }
    private fun classifyImage(image: Bitmap?) {
        try{
            val model = BatikModel.newInstance(this)

            val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)
            val byteBuffer = ByteBuffer.allocateDirect(4*imageSize*imageSize*3)
            byteBuffer.order(ByteOrder.nativeOrder())

            val intValues = IntArray(imageSize * imageSize)
            image!!.getPixels(intValues,0,image.width,0,0,image.width,image.height)

            var pixel = 0
            for(i in 0 until imageSize) {
                for(j in 0 until imageSize) {
                    val `val` = intValues[pixel++] // RGB
                    byteBuffer.putFloat((`val` shr 16 and 0xFF) * (1f / 255f))
                    byteBuffer.putFloat((`val` shr 8 and 0xFF) * (1f / 255f))
                    byteBuffer.putFloat((`val` and 0xFF) * (1f / 255f))                }
            }
            inputFeature0.loadBuffer(byteBuffer)

            val outputs = model.process(inputFeature0)
            val outputFeature0 = outputs.outputFeature0AsTensorBuffer
            val confidences = outputFeature0.floatArray

            var maxPos = 0

            var maxConfidence = 0f
            var max2 = 0f
            var max3 = 0f

            for (i in confidences.indices) {
                when {
                    confidences[i] > maxConfidence -> {
                        max3 = max2
                        max2 = maxConfidence
                        maxConfidence = confidences[i]
                        maxPos = i
                    }
                }
            }

            val classes = application.assets.open("batik_label.txt").bufferedReader().use { it.readText() }.split("\n")

            val scanResult = classes[maxPos]
            val intent = Intent(this,DetailActivity::class.java)
            intent.putExtra("result",scanResult)
            startActivity(intent)

            binding.tvResult.text = classes[maxPos]

            model.close()

        } catch (e: IOException){

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == 1 && resultCode == RESULT_OK) {
            var image = data?.extras?.get("data") as Bitmap?
            val dimension = StrictMath.min(image!!.width, image.height)
            image = ThumbnailUtils.extractThumbnail(image, dimension,dimension)
            binding.icSearchHome.setImageBitmap(image)
            image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false)
            classifyImage(image)
        }
        else if(requestCode == 2 && resultCode == RESULT_OK){
            val data: Uri? = data?.data
            var image: Bitmap? = null
            try {
                image = MediaStore.Images.Media.getBitmap(this.contentResolver,data)
            } catch (e: IOException){
                e.printStackTrace()
            }
            binding.icSearchHome.setImageBitmap(image)
            image = image?.let { Bitmap.createScaledBitmap(it, imageSize,imageSize,false) }
            classifyImage(image)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}
package com.example.ch16_provider

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ch16_provider.databinding.ActivityMainBinding
import com.example.ch16_provider.db.AppDatabase
import com.example.ch16_provider.db.ImageDao
import com.example.ch16_provider.db.ImageEntity
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), OnItemLongClickListener {

    private lateinit var binding: ActivityMainBinding

    private lateinit var db: AppDatabase
    private lateinit var ImageDao: ImageDao
    private lateinit var ImageList : ArrayList<ImageEntity>
    private lateinit var adapter: ImageRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAdd.setOnClickListener{
            val intent = Intent(this,AddImageActivity::class.java)
            intent.putExtra("type","ADD")
            startActivity(intent)
        }
        db = AppDatabase.getInstance(this)!!
        ImageDao = db.getImageDao()
        getAllImageList()
    }
    private fun getAllImageList(){
        Thread{
            ImageList = ArrayList(ImageDao.getALl())
            setRecycleView()
        }.start()
    }

    private fun setRecycleView(){
        runOnUiThread{
            adapter = ImageRecyclerViewAdapter(ImageList,this)
            binding.recyclerView.adapter = adapter

            binding.recyclerView.layoutManager = LinearLayoutManager(this)
        }
    }

    override fun onRestart() {
        super.onRestart()
        getAllImageList()
    }

   override fun onLongClick(position: Int) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("이미지 삭제")
        builder.setMessage("정말 삭제하시겠습니까")
        builder.setNegativeButton("수정",
            object : DialogInterface.OnClickListener{
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    updateTodo(position)
                }
            })
        builder.setPositiveButton("삭제",
            object : DialogInterface.OnClickListener{
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    deleteTodo(position)

                }
            }
        )
        builder.show()
    }
    private fun updateTodo(position: Int){
        val correntItem = ImageList[position]
        val intent = Intent(this,AddImageActivity::class.java)
        intent.putExtra("type","update")
        startActivity(intent)
    }

    private fun deleteTodo(position: Int){
        Thread{
            ImageDao.deleteTodo(ImageList[position])
            ImageList.removeAt(position)
            runOnUiThread{
                adapter.notifyDataSetChanged()
                Toast.makeText(this,"삭제되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }.start()
    }

}

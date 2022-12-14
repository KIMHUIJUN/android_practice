package com.example.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.todolist.databinding.ActivityAddTodoBinding
import com.example.todolist.db.AppDatabase
import com.example.todolist.db.ToDoDao
import com.example.todolist.db.ToDoEntity
import java.util.*

class AddTodoActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddTodoBinding

    lateinit var db: AppDatabase
    lateinit var todoDao: ToDoDao
    lateinit var todoList : ArrayList<ToDoEntity>
    val calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTodoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val type = intent.getStringExtra("type")

        db = AppDatabase.getInstance(this)!!

        todoDao = db.getTodoDao()

        binding.btnCompletion.setOnClickListener{
            insertTodo()
        }
        binding.calendarView.setOnDateChangeListener{_, year,month,dayOfMonth->
            calendar.apply {
                calendar.set(Calendar.YEAR,year)
                calendar.set(Calendar.MONTH,month)
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth)
                Log.d("aab","$calendar")

            }


        }
        if (type.equals("ADD")){
            binding.btnCompletion.text = "추가하기"
        }else{
            binding.btnCompletion.text = "수정하기"
            updateTodo()

        }
    }
    private fun insertTodo(){
        val todoTitle = binding.edtTitle.text.toString()
        var todoImportance = binding.radioGroup.checkedRadioButtonId
        val todoDate = calendar.timeInMillis
        Log.d("aaa","$todoDate")
        when(todoImportance){
            R.id.btn_high -> {
                todoImportance = 1
            }
            R.id.btn_middle -> {
                todoImportance = 2
            }
            R.id.btn_low -> {
                todoImportance = 3
            }
            else ->{
                todoImportance = -1
            }
        }
        if (todoImportance==-1 ||todoTitle.isBlank()){
            Toast.makeText(this,"모든항목을 채워주세요",Toast.LENGTH_SHORT).show()
        }else{
            Thread{
                todoDao.insertTodo(ToDoEntity(null,todoTitle,todoImportance,todoDate))
                runOnUiThread{
                    Toast.makeText(this,"추가되었습니다.",Toast.LENGTH_SHORT).show()
                    finish()
                }
            }.start()
        }

    }
    private fun updateTodo(){
        val todoTitle = binding.edtTitle.text.toString()
        var todoImportance = binding.radioGroup.checkedRadioButtonId
        val todoDate = calendar.timeInMillis

        when(todoImportance){
            R.id.btn_high -> {
                todoImportance = 1
            }
            R.id.btn_middle -> {
                todoImportance = 2
            }
            R.id.btn_low -> {
                todoImportance = 3
            }
            else ->{
                todoImportance = -1
            }
        }
        if (todoImportance==-1 ||todoTitle.isBlank()){
            Toast.makeText(this,"모든항목을 채워주세요",Toast.LENGTH_SHORT).show()
        }else{
            Thread{
                todoDao.insertTodo(ToDoEntity(null,todoTitle,todoImportance,todoDate))
                runOnUiThread{
                    Toast.makeText(this,"수정되었습니다.",Toast.LENGTH_SHORT).show()
                    finish()
                }
            }.start()
        }

    }
}



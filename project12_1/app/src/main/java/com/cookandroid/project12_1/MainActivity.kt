package com.cookandroid.project12_1

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    lateinit var myHelper : myDBHelper
    lateinit var edtName : EditText
    lateinit var edtNumber : EditText
    lateinit var edtNameResult : EditText
    lateinit var edtNumberResult : EditText
    lateinit var btnInit : Button
    lateinit var btnSelect : Button
    lateinit var btnInsert : Button
    lateinit var sqlDB : SQLiteDatabase
    lateinit var btnUpdata : Button
    lateinit var btndelete : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edtName = findViewById<EditText>(R.id.edtname)
        edtNumber = findViewById<EditText>(R.id.edtnumber)
        edtNameResult = findViewById<EditText>(R.id.edtnameresult)
        edtNumberResult = findViewById<EditText>(R.id.edtnumberresult)

        btnInit = findViewById(R.id.btninit)
        btnInsert = findViewById(R.id.btninsert)
        btnSelect = findViewById(R.id.btnselect)
        btnUpdata = findViewById(R.id.btnedt)
        btndelete = findViewById(R.id.btndel)

        myHelper = myDBHelper(this)

        btnInit.setOnClickListener {
            sqlDB = myHelper.writableDatabase
            myHelper.onUpgrade(sqlDB,1,2)
            sqlDB.close()
        }
        btnInsert.setOnClickListener {
            sqlDB = myHelper.writableDatabase
            sqlDB.execSQL("INSERT INTO groupTBL VALUES ('"+edtName.text.toString() +"' ,"+edtNumber.text.toString()+");")
            sqlDB.close()
            Toast.makeText(applicationContext,"입력됨",Toast.LENGTH_SHORT).show()
        }
        btnSelect.setOnClickListener {
            sqlDB = myHelper.readableDatabase
            var cursor : Cursor
            cursor = sqlDB.rawQuery("SELECT * FROM groupTBL;",null)

            var strNames = "그룹이름" + "\r\n"+"---------"+"\r\n"
            var strNumbers = "인원" + "\r\n" + "---------"+"\r\n"

            while(cursor.moveToNext()){
                strNames += cursor.getString(0) +"\r\n"
                strNumbers += cursor.getString(1) + "\r\n"
            }
            edtNameResult.setText(strNames)
            edtNumberResult.setText(strNumbers)

            cursor.close()
            sqlDB.close()
        }

        btnUpdata.setOnClickListener {
            sqlDB = myHelper.writableDatabase
            if (edtName.text.toString() !== "") {
                sqlDB.execSQL("UPDATE groupTBL SET gNumber ="
                        + edtNumber.text + " WHERE gName = '"
                        + edtName.text.toString() + "';")
            }
            sqlDB.close()

            Toast.makeText(applicationContext, "수정됨",  Toast.LENGTH_SHORT).show()
            btnSelect.callOnClick()
        }

        btndelete.setOnClickListener {
            sqlDB = myHelper.writableDatabase
            if (edtName.text.toString() !== ""){
                sqlDB.execSQL("DELETE FROM groupTBL WHERE gName =  '" + edtName.text.toString() +"';")
            }
            sqlDB.close()

            Toast.makeText(applicationContext,"삭제됨",Toast.LENGTH_SHORT).show()
            btnSelect.callOnClick()

        }
    }
    inner class myDBHelper(context : Context) : SQLiteOpenHelper(context, "groupDB",null,1){
        override fun onCreate(p0: SQLiteDatabase?) {
            p0!!.execSQL("CREATE TABLE groupTBL(gName CHAR(20) PRIMARY KEY, gNumber INTEGER);")
        }

        override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
            p0!!.execSQL("DROP TABLE IF EXISTS groupTBL")
            onCreate(p0)
        }
    }
}

package com.example.exam_20182345

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    lateinit var swstart : Switch
    lateinit var edit1 : EditText
    lateinit var edit2 : EditText
    lateinit var edit3 : EditText
    lateinit var rgroup : RadioGroup
    lateinit var rmax : RadioButton
    lateinit var rmin : RadioButton
    lateinit var rtry : RadioButton
    lateinit var img : ImageView
    lateinit var btn : Button
    lateinit var lin1 : LinearLayout
    lateinit var lin2 : LinearLayout
    lateinit var lin3 : LinearLayout
    lateinit var lin4 : LinearLayout
    lateinit var textres : TextView
    lateinit var num1 : String
    lateinit var num2 : String
    lateinit var num3 : String
    var a : Int? =null
    var b : Int? =null
    var c : Int? =null

    var result : Int? =null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title ="최댓값/최솟값 구하기(이유림)"

        swstart = findViewById<Switch>(R.id.str)
        edit1 = findViewById<EditText>(R.id.edit1)
        edit2 = findViewById<EditText>(R.id.edit2)
        edit3 = findViewById<EditText>(R.id.edit3)
        rgroup = findViewById<RadioGroup>(R.id.rgroup)
        rmax = findViewById<RadioButton>(R.id.rdomax)
        rmin = findViewById<RadioButton>(R.id.rdomin)
        rtry = findViewById<RadioButton>(R.id.rdore)
        img = findViewById<ImageView>(R.id.img1)
        btn = findViewById<Button>(R.id.btn)
        lin1 = findViewById<LinearLayout>(R.id.lin1)
        lin2 = findViewById<LinearLayout>(R.id.lin2)
        lin3 = findViewById<LinearLayout>(R.id.lin3)
        lin4 = findViewById<LinearLayout>(R.id.lin4)
        textres = findViewById<TextView>(R.id.editresult)

        lin1.visibility = android.view.View.INVISIBLE
        lin2.visibility = android.view.View.INVISIBLE
        lin3.visibility = android.view.View.INVISIBLE
        lin4.visibility = android.view.View.INVISIBLE
        textres.visibility = android.view.View.INVISIBLE



        swstart.setOnClickListener {
            lin1.visibility = android.view.View.VISIBLE
            lin2.visibility = android.view.View.VISIBLE
            lin3.visibility = android.view.View.VISIBLE
            lin4.visibility = android.view.View.VISIBLE
            textres.visibility = android.view.View.VISIBLE
        }



        rmax.setOnClickListener {

            num1 = edit1.text.toString()
            num2 = edit1.text.toString()
            num3 = edit1.text.toString()
            img.setImageResource(R.drawable.exam02)

            a = Integer.parseInt(num1)
            b = Integer.parseInt(num2)
            c = Integer.parseInt(num3)

            if(num1.trim() == "" || num2.trim() == "" || num3.trim() =="")
                Toast.makeText(applicationContext,"값이 입력되지 않았습니다!!",Toast.LENGTH_SHORT).show()
            else{
                if(a!! >= b!! && a!!>=c!!)
                    result = a
                else if(a!! <= b!! && b!! >= c!!)
                    result =b
                else
                    result = c
            }
            textres.text = "최댓값 : " +result
        }
        rmin.setOnClickListener {
            num1 = edit1.text.toString()
            num2 = edit1.text.toString()
            num3 = edit1.text.toString()
            img.setImageResource(R.drawable.exam03)
            a = Integer.parseInt(num1)
            b = Integer.parseInt(num2)
            c = Integer.parseInt(num3)

            if(num1.trim() == "" || num2.trim() == "" || num3.trim() =="")
                Toast.makeText(applicationContext,"값이 입력되지 않았습니다!!",Toast.LENGTH_SHORT).show()
            else{
                if(a!! <= b!! && a!! <= c!!)
                    result = a
                else if(a !! >= b!! && b!! <=c!!)
                    result =b
                else
                    result = c
            }
            textres.text = "최솟값 : " +result
        }
        rtry.setOnClickListener {
            edit1.setText(null)
            edit2.setText(null)
            edit3.setText(null)
        }
        btn.setOnClickListener{
            finish()
        }

    }
}

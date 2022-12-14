package com.cookandroid.stopwatch

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.cookandroid.stopwatch.databinding.ActivityMainBinding
import java.util.*
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {
    private var time = 0
    private var timerTask: Timer? = null
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private var isRunning = false
    private var lap = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.fab.setOnClickListener{
            isRunning = !isRunning

            if (isRunning){
                start()
            }
            else{
                pause()
            }

        }
        binding.labButton.setOnClickListener {
            recodeLapTime()
        }
        binding.resetFab.setOnClickListener{
            reset()
        }
    }

    private fun start(){
        binding.fab.setImageResource(R.drawable.ic_baseline_pause_24)
        timerTask = timer(period=10){
            time++
            val sec = time /100
            val milli = time%100
            runOnUiThread{
                binding.secTextView.text = "$sec"
                binding.miiliTextView.text ="$milli"
            }
        }
    }
    private  fun pause(){
        binding.fab.setImageResource(R.drawable.ic_baseline_play_arrow_24)
        timerTask?.cancel()
    }
    private fun recodeLapTime(){
        val lapTime = this.time
        val textView = TextView (this)
        textView.text = "$lap LAP : ${lapTime / 100}.${lapTime % 100}"

        binding.labLayout.addView(textView,0)
        lap++
    }
    private fun reset(){
        timerTask?.cancel()
        time = 0
        isRunning = false
        binding.fab.setImageResource(R.drawable.ic_baseline_play_arrow_24)
        binding.secTextView.text = "0"
        binding.miiliTextView.text = "00"

        binding.labLayout.removeAllViews()
        lap = 1
    }
}
package com.cookandroid.ch8_view

import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.cookandroid.ch8_view.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    var initTime = 0L

    var pauseTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startButton.setOnClickListener{
            binding.chronometer.base = SystemClock.elapsedRealtime() + pauseTime
            binding.chronometer.start()

            binding.stopButton.isEnabled = true
            binding.resetButton.isEnabled =true
            binding.startButton.isEnabled = false
            binding.run.isVisible = true
            binding.stop.isVisible = false
            binding.reset.isVisible = false
            }

        binding.stopButton.setOnClickListener{
            pauseTime = binding.chronometer.base - SystemClock.elapsedRealtime()
            binding.chronometer.stop()
            binding.stopButton.isEnabled = false
            binding.resetButton.isEnabled = true
            binding.startButton.isEnabled = true
            binding.run.isVisible = false
            binding.stop.isVisible = true
            binding.reset.isVisible = false
        }
        binding.resetButton.setOnClickListener{
            pauseTime = 0L
            binding.chronometer.base = SystemClock.elapsedRealtime()
            binding.chronometer.stop()
            binding.stopButton.isEnabled = false
            binding.resetButton.isEnabled = false
            binding.startButton.isEnabled = true
            binding.run.isVisible = false
            binding.stop.isVisible = false
            binding.reset.isVisible = true
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event?.action){
            MotionEvent.ACTION_DOWN ->{
                Log.d("동작상태","Touch down event")
                Log.d("동작상태","Touch down event X :${event.x},rawX: ${event.rawX}")
                Log.d("동작상태","Touch down event y: ${event.y}, rawY: ${event.rawY}")
            }
            MotionEvent.ACTION_UP ->{
                Log.d("동작상태","Touch up event")
            }
        }
        return super.onTouchEvent(event)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if(keyCode ===KeyEvent.KEYCODE_BACK){

            if(System.currentTimeMillis() - initTime > 3000){
                Toast.makeText(this,"종료하려면 한 번 더 누르세요!!",
                            Toast.LENGTH_SHORT).show()
                initTime = System.currentTimeMillis()
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }

}
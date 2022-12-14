package com.cookandroid.ch_15

import android.media.SoundPool
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.cookandroid.ch_15.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater)}

    private  val soundPool = SoundPool.Builder().setMaxStreams(8).build()

    private  val sound by lazy {
        listOf(
            Pair(binding.do1 ,R.raw.do1)
            ,Pair(binding.re ,R.raw.re)
            ,Pair(binding.me ,R.raw.mi)
            ,Pair(binding.fa ,R.raw.fa)
            ,Pair(binding.sol ,R.raw.sol)
            ,Pair(binding.la ,R.raw.la)
            ,Pair(binding.si ,R.raw.si)
            ,Pair(binding.do2 ,R.raw.do2)
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        sound.forEach { tune(it) }
    }
    private fun tune(pitch:Pair<TextView, Int>){
        val soundId =  soundPool.load(this,pitch.second,1)
        pitch.first.setOnClickListener{
            soundPool.play(soundId,1.0f,1.0f,0,0,1.0f)

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        soundPool.release()
    }

}
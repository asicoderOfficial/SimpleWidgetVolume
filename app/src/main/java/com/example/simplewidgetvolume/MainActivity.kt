package com.example.simplewidgetvolume

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.WindowManager
import android.widget.SeekBar
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

/** Author : Asicoder
 *
 * Main and unique class of the project. This is where all the work happens.
 */
class MainActivity : AppCompatActivity() {

    var audioManager: AudioManager? = null

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Setting up the Window in order to show activity as dialog property.
        val params: WindowManager.LayoutParams = window.attributes
        params.height = 100
        params.width = 100
        setContentView(R.layout.activity_main)
        this.window.setLayout((params.width * 0.8).toInt(), params.height)

        //Getting the system's AudioManager
        audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager?

        //Getting and setting max volume of each stream.
        alarmSeekBar.max = audioManager!!.getStreamMaxVolume(AudioManager.STREAM_ALARM)
        musicSeekBar.max = audioManager!!.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        notiesSeekBar.max = audioManager!!.getStreamMaxVolume(AudioManager.STREAM_NOTIFICATION)
        phoneSeekBar.max = audioManager!!.getStreamMaxVolume(AudioManager.STREAM_RING)

        //Setting each seekbar's progress to the current when activity is created.
        alarmSeekBar.progress = audioManager!!.getStreamVolume(AudioManager.STREAM_ALARM)
        musicSeekBar.progress = audioManager!!.getStreamVolume(AudioManager.STREAM_MUSIC)
        notiesSeekBar.progress = audioManager!!.getStreamVolume(AudioManager.STREAM_NOTIFICATION)
        phoneSeekBar.progress = audioManager!!.getStreamVolume(AudioManager.STREAM_RING)

        //Representing the progress in simple textviews.
        alarmTV.text = alarmSeekBar.progress.toString()
        musicTV.text = musicSeekBar.progress.toString()
        notiesTV.text = notiesSeekBar.progress.toString()
        phoneTV.text = phoneSeekBar.progress.toString()

        //Methods to change each volume with user's move of progress bars, and showing the corresponding numbers on each Text View.
        alarmSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                audioManager!!.setStreamVolume(AudioManager.STREAM_ALARM, progress, 0)
                alarmTV.text = progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}

        })

        musicSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                audioManager!!.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0)
                musicTV.text = progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}

        })

        notiesSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                seekBar: SeekBar?,
                progress: Int,
                fromUser: Boolean
            ) {
                /*@exception SecurityException
                  Thrown when the user tries to change ringer's volume,
                  but Do Not Disturb modifying permissions are available.
                 */
                try {
                    audioManager!!.setStreamVolume(
                        AudioManager.STREAM_NOTIFICATION,
                        progress,
                        0
                    )
                    notiesTV.text = progress.toString()
                } catch (e: Exception) {
                    val notificationManager =
                        applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                        !notificationManager.isNotificationPolicyAccessGranted
                    ) {
                        val intent = Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS)
                        startActivity(intent)
                    }
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}

        })

        phoneSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                seekBar: SeekBar?,
                progress: Int,
                fromUser: Boolean
            ) {
                audioManager!!.setStreamVolume(AudioManager.STREAM_RING, progress, 0)
                phoneTV.text = progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}

        })
    }
}
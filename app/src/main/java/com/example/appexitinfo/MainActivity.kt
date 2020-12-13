package com.example.appexitinfo

import android.app.ActivityManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

/**
 * sample of getting information about app crashes using
 * getHistoricalProcessExitReasons which is available by API 30 or higher
 */
class MainActivity : AppCompatActivity() {
    private lateinit var button: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // get [ActivityManager]'s instance
        val activityManager = this.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val crashLists = activityManager.getHistoricalProcessExitReasons(this.packageName, 0, 10)
            crashLists.takeIf { crashLists.isNotEmpty() }?.forEach {
                println("Reason: ${it.reason} - Description: ${it.description}")
            }
        }

        /**
         * crash by using not initialized property
         */
        findViewById<TextView>(R.id.tv).setOnClickListener {
            button.setOnClickListener {
                Log.d(TAG, "onCreate: CRASH")
            }
        }

        /**
         * producing Application not responding(ANR) crash
         */
        findViewById<TextView>(R.id.tv1).setOnClickListener {
            var a = 0
            while (true) {
                a++
            }
        }
    }

    companion object {
        val TAG: String = MainActivity::class.java.simpleName
    }
}
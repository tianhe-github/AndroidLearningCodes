package com.he.service_communicate.activity

import android.annotation.SuppressLint
import android.content.*
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.he.service_communicate.*
import com.he.service_communicate.broadcast.MyBroadcast1
import com.he.service_communicate.broadcast.MyBroadcast1.Companion.IntentAction
import com.he.service_communicate.listener.IService
import com.he.service_communicate.listener.OnDownloadReceiverListener
import com.he.service_communicate.service.MyService1
import kotlinx.android.synthetic.main.activity_service_to_broadcast.*


class ServiceToBroadcastActivity : AppCompatActivity() {

    private var mBroadcast1: MyBroadcast1? = null
    private var myBinder: IService? = null
    private val connection = object : ServiceConnection {
        override fun onServiceConnected(componentname: ComponentName?, service: IBinder?) {
            myBinder = service as? IService
            myBinder?.start()
        }

        override fun onServiceDisconnected(componentname: ComponentName?) {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_to_broadcast)
        val intent = Intent(this, MyService1::class.java)
        bindService(intent, connection, BIND_AUTO_CREATE)
    }


    override fun onStart() {
        super.onStart()
        mBroadcast1 = MyBroadcast1()
        val filter = IntentFilter()
        filter.addAction(IntentAction)
        registerReceiver(mBroadcast1, filter)
        mBroadcast1?.setDownloadReceiverListener(object : OnDownloadReceiverListener {
            @SuppressLint("SetTextI18n")
            override fun downloadReceiver(progress: Int) {
                tv_progress.text = "progress = $progress %"
                pb.progress = progress
            }
        })
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(mBroadcast1)
    }
}




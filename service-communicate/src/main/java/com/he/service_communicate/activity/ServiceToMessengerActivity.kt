package com.he.service_communicate.activity

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.ServiceConnection
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import com.he.service_communicate.R
import com.he.service_communicate.service.MyService2
import com.he.service_communicate.service.MyService2.Companion.MSG_FROM_CLIENT
import com.he.service_communicate.service.MyService2.Companion.MSG_FROM_SERVICE
import kotlinx.android.synthetic.main.activity_service_to_broadcast.pb
import kotlinx.android.synthetic.main.activity_service_to_broadcast.tv_progress
import kotlinx.android.synthetic.main.activity_service_to_messenger.*
import android.os.Bundle
import com.he.service_communicate.service.MyService2.Companion.KEY_CLIENT_EXTRA
import android.os.Messenger
import android.content.Intent
import android.util.Log


class ServiceToMessengerActivity : AppCompatActivity() {
    private var mClientMessenger: Messenger? = null
    private var mClientHandler: Handler? = null

    private var mServiceMessenger: Messenger? = null
    private val connection = object : ServiceConnection {
        override fun onServiceConnected(componentname: ComponentName?, service: IBinder?) {
            mServiceMessenger = Messenger(service)
        }

        override fun onServiceDisconnected(componentname: ComponentName?) {
            mServiceMessenger = null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_to_messenger)
        //init Service
        val intent = Intent(this, MyService2::class.java)
        bindService(intent, connection, BIND_AUTO_CREATE)

        mClientHandler = ClientHandler()
        mClientMessenger = Messenger(mClientHandler)

        //init OnClickListener
        tv_start.setOnClickListener {
            val clientMsg = Message.obtain(null, MSG_FROM_CLIENT)
            clientMsg.data = Bundle().apply {
                putString(KEY_CLIENT_EXTRA, "I am coming from Activity")
            }
            clientMsg.replyTo = mClientMessenger
            try {
                mServiceMessenger?.send(clientMsg)
            } catch (e: Exception) {
            }
        }

    }

    @SuppressLint("HandlerLeak")
    inner class ClientHandler : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                MSG_FROM_SERVICE -> {
                    val progress = msg.data.getInt(MyService2.KEY_SERVICE_EXTRA)
                    tv_progress.text = "progress = $progress %"
                    pb.progress = progress
                }
                else -> {
                    super.handleMessage(msg)
                }
            }
        }
    }

    override fun onDestroy() {
        unbindService(connection)
        mClientHandler?.removeCallbacksAndMessages(null)
        super.onDestroy()
    }


}
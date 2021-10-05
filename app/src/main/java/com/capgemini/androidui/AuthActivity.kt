package com.capgemini.androidui

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.PopupMenu
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_auth.*

class AuthActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        cancelB.setOnLongClickListener {
            showPopup(it)
            true
        }

    }

    fun submitClick(view: View) {
        Log.d("AuthActivity", "submit clicked")

        val userid = useridE.text.toString()
        val password = passwordE.text.toString()

        when {
            userid.isEmpty() || userid.length < 4 -> useridE.error = "Pls enter userid with min 4 chars"

            password.isEmpty() -> passwordE.setError("Pls enter password")

            password.length < 8 -> passwordE.setError("Password should be atleast 8 char long")

            else -> {
                val dlg = MyDialog()

                val dBundle = Bundle()
                dBundle.putString("dTitle", "Logging in")
                dBundle.putString("dMessage", "Do you want to submit below data?" +
                        " \nUserid: $userid, password: $password")
                dBundle.putString("userid", userid)
                dBundle.putString("pwd", password)
                dBundle.putInt("flag", 2)

                dlg.arguments = dBundle
                dlg.show(supportFragmentManager, null)

            }

        }

//        if(userid.isEmpty() || password.isEmpty() ){
////            Toast.makeText(this, "Pls enter all fields...",
////                Toast.LENGTH_LONG).show()
//            if(userid.isEmpty()){
//                useridE.setError("Pls enter userid")
//            }
//
//            if (password.isEmpty())
//                passwordE.setError("Pls enter password")
//        }
//        else{
//            Toast.makeText(this, "Userid: $userid \nPassword: $password",
//                Toast.LENGTH_LONG).show()
//
//            if(password.length < 8) {
//                passwordE.setError("Password should be atleast 8 char long")
//            }
//        }

    }

    val MENU_RESET = 1
    val MENU_GO_BACK = 2

    fun cancelClick(view: View) {
        Log.d("AuthActivity", "cancel clicked")

//        useridE.setText("")
//        passwordE.setText("")
//
//        finish()

          //  showPopup(view)

    }

    fun showPopup(view: View) {

        // display popup menu
        val pMenu = PopupMenu(this, view)
        pMenu.menu.add(0, MENU_RESET, 0, "Reset Data")
        pMenu.menu.add(0, MENU_GO_BACK, 0, "Leave the page")

        pMenu.setOnMenuItemClickListener {
            when(it.itemId){
                MENU_RESET -> {
                    useridE.setText("")
                    passwordE.setText("")
                }
                MENU_GO_BACK -> {
                    finish()
                }
            }
            true
        }
        pMenu.show()
    }



    override fun onBackPressed() {

        // display status bar notification
        displayNotification(this, "Authentication",
            "Please login", MainActivity::class.java)

        super.onBackPressed()
    }



}
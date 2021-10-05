package com.capgemini.androidui

import android.app.NotificationManager
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar


/*
1. Implement listener Interface
2. Provide callback method
3. register listener to view
 */



class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var loginButton : Button
    lateinit var registerButton : Button
    lateinit var parentLayout : LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // all views objects are created

        registerButton = findViewById(R.id.registerB)
        registerButton.setOnClickListener(this)

        registerForContextMenu(registerButton)

        loginButton = findViewById(R.id.loginB)
        loginButton.setOnClickListener(this)
        registerForContextMenu(loginButton)

        parentLayout = findViewById(R.id.parentL)
        registerForContextMenu(parentLayout)

//        val nManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
//        nManager.cancel(1)
    }

    val MENU_RIDER = 1
    val MENU_DRIVER = 2
    val MENU_LOGIN_RIDER = 3
    val MENU_LOGIN_DRIVER = 4

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        when(v?.id){
            R.id.loginB -> {
                // add menu items
                menu?.setHeaderTitle("Logging in")

                menu?.add(ContextMenu.NONE, MENU_LOGIN_DRIVER, 0, "as DRIVER" )
                menu?.add(0, MENU_LOGIN_RIDER, 0, "as RIDER")
            }
            R.id.registerB -> {
                // add menu items
                menu?.setHeaderTitle("Registering")

                menu?.add(ContextMenu.NONE, MENU_DRIVER, 0, "as DRIVER" )
                menu?.add(0, MENU_RIDER, 0, "as RIDER")
            }
            R.id.parentL -> {
                menu?.add(0, 10, 0, "EXIT")
            }
        }


        super.onCreateContextMenu(menu, v, menuInfo)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            MENU_LOGIN_DRIVER, MENU_LOGIN_RIDER -> {
                val i = Intent(this, AuthActivity::class.java)
                startActivity(i)
                return true
            }
            MENU_RIDER -> {
                Log.d("MainActivity", "Rider selected")
                val i = Intent(this, RegisterActivity::class.java)
                startActivity(i)
                return true
            }
            MENU_DRIVER -> {
                Log.d("MainActivity", "Driver selected")
                Toast.makeText(this, "Feature coming soon..", Toast.LENGTH_LONG).show()
                return true
            }
            else -> {
                finish()
            }
        }
        return super.onContextItemSelected(item)
    }


    override fun onClick(v: View?) {
        when(v?.id){
            R.id.loginB -> {
                Log.d("MainActivity", "Login Button clicked")
                // launch AuthActivity
                val i = Intent(this, AuthActivity::class.java)
                startActivity(i)


            }
            R.id.registerB -> {
                Log.d("MainActivity", "Register Button clicked")
                val i = Intent(this, RegisterActivity::class.java)
                startActivity(i)
            }
        }

    }

    val MENU_LOGIN = 1
    val MENU_LOGOUT = 2
    val MENU_EXIT = 3
    val MENU_REGISTER = 4

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // add menu items

//        val loginItem = menu?.add(0, MENU_LOGIN, 2, "Login")
//        loginItem?.setIcon(android.R.drawable.ic_menu_search)
//        //loginItem?.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)

        val loginSubmenu = menu?.addSubMenu("Login")
        loginSubmenu?.add(0, MENU_LOGIN, 0, "Rider" )
        loginSubmenu?.add(0, MENU_LOGIN, 0, "Driver")

        val logoutItem = menu?.add(0, MENU_LOGOUT, 1, "Logout" )
        logoutItem?.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)

        menu?.add(0, MENU_EXIT, 3, "EXIT")
       val registerItem = menu?.add(Menu.NONE, MENU_REGISTER, 4, "Register" )
        registerItem?.setIcon(android.R.drawable.btn_star_big_on)
        registerItem?.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)

//        menu?.add("Login")
//        menu?.add("Logout")

        // to hide menu return false

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            MENU_REGISTER->{
                val i  = Intent(this, RegisterActivity::class.java)
                startActivity(i)
            }
            MENU_LOGIN -> {
                Log.d("MainActivity", "Login Selected")
                // launch AuthActivity
                val i = Intent(this, AuthActivity::class.java)
                startActivity(i)

                return true
            }
            MENU_LOGOUT -> {
                Log.d("MainActivity", "Logout Selected")
                val myToast = Toast.makeText(this,
                    "Logging out..", Toast.LENGTH_LONG)

                myToast.setGravity(Gravity.TOP, 20, 60)
                myToast.show()

                return true
            }
            MENU_EXIT -> {
                Log.d("MainActivity", "EXIT Selected")
                // display dialog here
                val dlg = MyDialog()
                dlg.isCancelable = false

                val dataBundle = Bundle()
                dataBundle.putString("dTitle", "Confirmation")
                dataBundle.putString("dMessage", "Do you want to exit?")
                dataBundle.putInt("flag", 1)

                dlg.arguments = dataBundle
                dlg.show(supportFragmentManager, "xyz")

                //finish()
                return true

            }
        }

        return super.onOptionsItemSelected(item)
    }

}
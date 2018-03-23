package com.example.rob.dndparty

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth

/**
 * Created by rob on 2/15/18.
 */

class MenuActivity : AppCompatActivity() {
    var fbAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        var btnLogOut = findViewById<TextView>(R.id.btnLogout)

        btnLogOut.setOnClickListener {
            signOut()
        }
    }

    fun signOut(){
        fbAuth.signOut()
        val intent = Intent(this, LoginActivity :: class.java)
        startActivity(intent)

    }

}
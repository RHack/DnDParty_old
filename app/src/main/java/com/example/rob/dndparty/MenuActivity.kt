package com.example.rob.dndparty

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
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

       /* btnLogOut.setOnClickListener{ view ->
            signOut()
        }

        fbAuth.addAuthStateListener {
            if(fbAuth.currentUser == null){
                this.finish()
            }
        }*/
    }

    fun signOut(){
        fbAuth.signOut()

    }

}
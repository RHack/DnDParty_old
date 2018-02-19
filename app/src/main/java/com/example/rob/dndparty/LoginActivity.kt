package com.example.rob.dndparty

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser



class LoginActivity : AppCompatActivity() {
    var fbAuth = FirebaseAuth.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        var loginButton = findViewById<TextView>(R.id.btnLogin)
        var registerButton = findViewById<TextView>(R.id.btnRegister)
        var forgotButton = findViewById<TextView>(R.id.btnForgotPassword)
        var guestButton = findViewById<TextView>(R.id.btnGuestLogin)


        loginButton?.setOnClickListener { view ->
            signIn(view, "user@company.com", "pass")
        }

        registerButton?.setOnClickListener {
            val intent = Intent(this, RegisterActivity :: class.java)
            startActivity(intent)
        }

        guestButton?.setOnClickListener {
            val intent = Intent(this, MenuActivity :: class.java)
            startActivity(intent)
        }

    }

    fun signIn(view: View, email: String, password: String) {
        showMessage(view, "Authenticating...")

        fbAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, { task ->
            if (task.isSuccessful) {
                var intent = Intent(this, MenuActivity::class.java)
                intent.putExtra("id", fbAuth.currentUser?.email)
                startActivity(intent)
            } else {
                showMessage(view, "Error: ${task.exception?.message}")
            }
        })
    }

    fun showMessage(view:View, message: String){
        Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE).setAction("Action", null).show()
    }
}

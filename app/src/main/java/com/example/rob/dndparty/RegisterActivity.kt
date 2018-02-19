package com.example.rob.dndparty

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

/**
 * Created by rob on 2/17/18.
 */
class RegisterActivity : AppCompatActivity() {
        var fbAuth = FirebaseAuth.getInstance()


        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_register)

            var loginButton = findViewById<TextView>(R.id.btnLogin)
            var registerButton = findViewById<TextView>(R.id.btnRegister)
            var forgot_button = findViewById<TextView>(R.id.btnForgotPassword)


            loginButton?.setOnClickListener { view ->
                val intent = Intent(this, LoginActivity :: class.java)
                startActivity(intent)
            }

            registerButton?.setOnClickListener {
                val intent = Intent(this, RegisterActivity :: class.java)
                startActivity(intent)
            }
        }

        override fun onStart() {
            super.onStart()

            var currentUser : FirebaseUser? = fbAuth.getCurrentUser()
            // Check if the user is logged in and go from there.
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

        fun showMessage(view: View, message: String){
            Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE).setAction("Action", null).show()
        }
}
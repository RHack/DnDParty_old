package com.example.rob.dndparty

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


// Globals
private var email: String? = null
private var password: String? = null

class LoginActivity : AppCompatActivity() {
    var fbAuth = FirebaseAuth.getInstance()

    // UI
    private var etEmail: EditText? = null
    private var etPassword: EditText? = null

    // Firebase
    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initialize()
    }

    override fun onStart() {
        super.onStart()

        var currentUser : FirebaseUser? = mAuth?.getCurrentUser()
        // Check if the user is logged in and go from there.

        initialize()
    }

    fun initialize() {
        var loginButton = findViewById<TextView>(R.id.btnLogin)
        var registerButton = findViewById<TextView>(R.id.btnRegister)
        var forgotButton = findViewById<TextView>(R.id.btnForgotPassword)
        var guestButton = findViewById<TextView>(R.id.btnGuestLogin)
        etPassword = findViewById(R.id.editPassword)
        etEmail = findViewById(R.id.editEmail)

        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase?.reference?.child("Users")
        mAuth = FirebaseAuth.getInstance()


        registerButton?.setOnClickListener { view ->
            val intent = Intent(this, RegisterActivity :: class.java)
            startActivity(intent)
        }

        loginButton?.setOnClickListener {
            signIn()
        }

        guestButton?.setOnClickListener {
            val intent = Intent(this, MenuActivity :: class.java)
            startActivity(intent)
        }
    }

    fun signIn() {
        email = etEmail?.text.toString()
        password = etPassword?.text.toString()

        fbAuth.signInWithEmailAndPassword(email!!, password!!).addOnCompleteListener(this, { task ->
            if (task.isSuccessful) {
                var intent = Intent(this, MenuActivity::class.java)
                intent.putExtra("id", fbAuth.currentUser?.email)
                startActivity(intent)
            } else {
                Toast.makeText(this@LoginActivity, "Authentication failed", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun showMessage(view:View, message: String){
        Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE).setAction("Action", null).show()
    }
}

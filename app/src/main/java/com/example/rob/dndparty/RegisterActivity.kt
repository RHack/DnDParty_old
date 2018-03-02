package com.example.rob.dndparty

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

/**
 * Created by rob on 2/17/18.
 */
// Got a lot of help from http://www.appsdeveloperblog.com/firebase-authentication-example-kotlin/


private val TAG = "RegisterActivity"

// Globals
private var email: String? = null
private var password: String? = null
private var confirmPassword: String? = null


class RegisterActivity : AppCompatActivity() {

    // UI
    private var etEmail: EditText? = null
    private var etPassword: EditText? = null
    private var etConfirmPassword: EditText? = null

    // Firebase
    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        initialise()
    }

    override fun onStart() {
        super.onStart()

        var currentUser : FirebaseUser? = mAuth?.getCurrentUser()
        // Check if the user is logged in and go from there.
    }

    fun initialise() {
        var loginButton = findViewById<TextView>(R.id.btnLogin)
        var registerButton = findViewById<TextView>(R.id.btnRegister)
        var forgot_button = findViewById<TextView>(R.id.btnForgotPassword)
        etPassword = findViewById(R.id.editPassword)
        etConfirmPassword = findViewById(R.id.editConfirmPassword)
        etEmail = findViewById(R.id.editEmail)

        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase?.reference?.child("Users")
        mAuth = FirebaseAuth.getInstance()


        loginButton?.setOnClickListener { view ->
            val intent = Intent(this, LoginActivity :: class.java)
            startActivity(intent)
        }

        registerButton?.setOnClickListener {
            registerAccount()
        }
    }

    fun registerAccount() {
        email = etEmail?.text.toString()
        password = etPassword?.text.toString()
        confirmPassword = etConfirmPassword?.text.toString()

        var validationFlag = true

        if(TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Enter your email address", Toast.LENGTH_SHORT).show()
            validationFlag = false
        } else if(TextUtils.isEmpty(password) || (TextUtils.getTrimmedLength(password) < 7)) {
            Toast.makeText(this, "Your password must be 6 or more characters", Toast.LENGTH_SHORT).show()
            validationFlag = false
        } else if(password != confirmPassword) {
            Toast.makeText(this, "Confirmation Password must match Password", Toast.LENGTH_SHORT).show()
            validationFlag = false
        }

        if(validationFlag){
            mAuth!!.createUserWithEmailAndPassword(email!!, password!!)
                    .addOnCompleteListener(this) {task ->
                        if(task.isSuccessful) {
                            Log.d(TAG, "createUserWithEmailAndPassword:Success")

                            val userId = mAuth?.currentUser?.uid

                            verifyEmail()

                            updateUserInfoAndUI()

                        } else {
                            Log.w(TAG, "createUserWithEmailAndPasswordFail$", task.exception)
                            Toast.makeText(this@RegisterActivity, "Authentication failed", Toast.LENGTH_SHORT).show()
                        }
                    }
        }

    }

    fun signIn(view: View, email: String, password: String) {
        showMessage(view, "Authenticating...")

        mAuth?.signInWithEmailAndPassword(email, password)?.addOnCompleteListener(this, { task ->
            if (task.isSuccessful) {
                var intent = Intent(this, MenuActivity::class.java)
                intent.putExtra("id", mAuth?.currentUser?.email)
                startActivity(intent)
            } else {
                showMessage(view, "Error: ${task.exception?.message}")
            }
        })
    }

    private fun updateUserInfoAndUI() {
        val intent = Intent(this@RegisterActivity, MenuActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    private fun verifyEmail() {
        val mUser = mAuth?.currentUser
        mUser?.sendEmailVerification()?.addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                Toast.makeText(this@RegisterActivity,
                        "Verification email sent to ${mUser?.email}",
                        Toast.LENGTH_SHORT).show()
            } else {
                Log.e(TAG, "sendEmailVerification", task.exception)
                Toast.makeText(this@RegisterActivity,
                        "Failed to send verification email",
                        Toast.LENGTH_SHORT).show()
            }

        }
    }

    fun showMessage(view: View, message: String){
        Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE).setAction("Action", null).show()
    }
}
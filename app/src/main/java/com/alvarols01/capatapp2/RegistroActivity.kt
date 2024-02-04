package com.alvarols01.capatapp2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

private val GOOGLE_SIGN_IN = 100

class RegistroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        setup()
        session()
    }

    private fun session() {
        // Función que comprueba si el usuario ya ha iniciado sesión anteriormente
        val prefs = getSharedPreferences(getString(R.string.prefs_file), MODE_PRIVATE)
        val email = prefs.getString("email", null)
        val provider = prefs.getString("provider", null)

        if (email != null && provider != null) {
            showHome(email, ProviderType.valueOf(provider), findViewById(R.id.emailEditText))
        }
    }

    private fun setup() {

        val loadingProgressBar: ProgressBar = findViewById(R.id.loadingProgressBar)


        val btnRegistrar: Button = findViewById(R.id.btnRegistrar)
        val btnAcceder: Button = findViewById(R.id.btnAcceder)
        val editTextEmail: EditText = findViewById(R.id.emailEditText)
        val editTextPassword: EditText = findViewById(R.id.passwordEditText)

        btnRegistrar.setOnClickListener {
            loadingProgressBar.visibility = View.VISIBLE
            if (editTextEmail.text.isNotEmpty() && editTextPassword.text.isNotEmpty()) {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    editTextEmail.text.toString(),
                    editTextPassword.text.toString()
                )
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            showHome(
                                it.result?.user?.email ?: "",
                                ProviderType.BASIC,
                                editTextEmail
                            )
                        } else {
                            showAlert()
                        }
                    }
            }
        }

        btnAcceder.setOnClickListener {
            loadingProgressBar.visibility = View.VISIBLE
            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
            hideProgressBarAfterDelay()
        }

    }



    private fun showHome(email: String, provider: ProviderType, editTextEmail: EditText) {
        val homeIntent = Intent(this, MainActivity::class.java).apply {
            putExtra("email", email)
            putExtra("provider", provider.name)
        }
        startActivity(homeIntent)
    }


    private fun showAlert() {
        // Función que muestra un mensaje de error en caso de que el usuario no se haya podido registrar
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error en el registro")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()

    }


    private fun hideProgressBarAfterDelay() {
        // Retrasar la ocultación de la ProgressBar durante 5 segundos (5000 milisegundos)
        Handler(Looper.getMainLooper()).postDelayed({
            val loadingProgressBar: ProgressBar = findViewById(R.id.loadingProgressBar)
            loadingProgressBar.visibility = View.INVISIBLE
        }, 5000)
    }
}
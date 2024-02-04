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

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

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
        val btnGoogle: Button = findViewById(R.id.btnGoogle)

        btnRegistrar.setOnClickListener {
            loadingProgressBar.visibility = View.VISIBLE
            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
            hideProgressBarAfterDelay()
        }

        btnAcceder.setOnClickListener {
            loadingProgressBar.visibility = View.VISIBLE
            if (editTextEmail.text.isNotEmpty() && editTextPassword.text.isNotEmpty()) {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(
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
                        hideProgressBarAfterDelay()
                    }
            }
        }

        btnGoogle.setOnClickListener {
            loadingProgressBar.visibility = View.VISIBLE
            val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

            val googleClient = GoogleSignIn.getClient(this, googleConf)
            googleClient.signOut()

            startActivityForResult(googleClient.signInIntent, GOOGLE_SIGN_IN)
        }

    }




    private fun showHome(email: String, provider: ProviderType, editTextEmail: EditText) {
        // Función que muestra la pantalla principal de la aplicación
        val homeIntent = Intent(this, MainActivity::class.java).apply {
            putExtra("email", editTextEmail.text.toString())
            putExtra("provider", provider.name)
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(homeIntent)
        finish() // Esto finalizará la actividad actual, para que no pueda volver atrás
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // Función que se ejecuta cuando se ha iniciado sesión con Google
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GOOGLE_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {

                    val account = task.getResult(ApiException::class.java)

                    if (account != null) {
                        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                        FirebaseAuth.getInstance().signInWithCredential(credential)
                            .addOnCompleteListener {
                                if (it.isSuccessful) {
                                    showHome(
                                        account.email ?: "",
                                        ProviderType.GOOGLE,
                                        findViewById(R.id.emailEditText)
                                    )
                                } else {
                                    showAlert()
                                }
                                hideProgressBarAfterDelay()
                            }
                    }

                } catch (e: ApiException) {
                    showAlert()
            }


            }
        }


    private fun hideProgressBarAfterDelay() {
        // Retrasar la ocultación de la ProgressBar durante 5 segundos (5000 milisegundos)
        Handler(Looper.getMainLooper()).postDelayed({
            val loadingProgressBar: ProgressBar = findViewById(R.id.loadingProgressBar)
            loadingProgressBar.visibility = View.INVISIBLE
        }, 5000)
    }
}
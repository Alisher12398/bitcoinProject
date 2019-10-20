package khalykbayev.bitcoinproject.Auth

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import khalykbayev.bitcoinproject.BaseActivity
import khalykbayev.bitcoinproject.MainActivity
import khalykbayev.bitcoinproject.R
import kotlinx.android.synthetic.main.activity_auth.*

class AuthActivity : BaseActivity() {


    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        val buttonLogin = findViewById<Button>(R.id.button_login)
        val buttonRegister = findViewById<Button>(R.id.button_register)
        val edittextEmail = findViewById<EditText>(R.id.edittext_email)
        val edittextPassword = findViewById<EditText>(R.id.edittext_password)

        auth = FirebaseAuth.getInstance()

        if (auth.currentUser!=null){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        buttonRegister.setOnClickListener{
            createAccount(edittextEmail.text.toString(), edittextPassword.text.toString())
        }

        buttonLogin.setOnClickListener{
            signIn(edittextEmail.text.toString(), edittextPassword.text.toString())
        }

    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
    }

    private fun createAccount(email: String, password: String) {
        Log.d(TAG, "createAccount:$email")
        if (!validateForm()) {
            return
        }

        showProgressDialog()

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "createUserWithEmail:success")

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Ошибка регистрации.",
                        Toast.LENGTH_SHORT).show()
                }
                hideProgressDialog()
            }
    }

    private fun signIn(email: String, password: String) {
        Log.d(TAG, "signIn:$email")
        if (!validateForm()) {
            return
        }

        showProgressDialog()

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithEmail:success")
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()

                } else {
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Ошибка авторизации.",
                        Toast.LENGTH_SHORT).show()
                }
                hideProgressDialog()
            }
    }

    private fun validateForm(): Boolean {
        var valid = true

        val email = edittext_email.text.toString()
        if (TextUtils.isEmpty(email)) {
            val toast = Toast.makeText(this, "Пустое поле с Email", Toast.LENGTH_LONG).show()
            valid = false
        }

        val password = edittext_password.text.toString()
        if (TextUtils.isEmpty(password)) {
            val toast = Toast.makeText(this, "Пустое поле с паролем", Toast.LENGTH_LONG).show()
            valid = false
        }

        return valid
    }

    companion object {
        private const val TAG = "EmailPassword"
    }

}

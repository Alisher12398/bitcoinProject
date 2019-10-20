@file:Suppress("DEPRECATION")

package khalykbayev.bitcoinproject

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {

    @VisibleForTesting
    val progressDialog by lazy {
        ProgressDialog(this)
        //
    }

    open fun showProgressDialog() {
        progressDialog.setMessage("Загрузка")
        progressDialog.isIndeterminate = true
        progressDialog.setCanceledOnTouchOutside(false)
        progressDialog.show()
    }

    fun hideProgressDialog() {
        if (progressDialog.isShowing) {
            progressDialog.dismiss()
        }
    }

    fun hideKeyboard(view: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    public override fun onStop() {
        super.onStop()
        hideProgressDialog()
    }

}

package com.s_ebrahimi.newssample.activities

import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.s_ebrahimi.newssample.util.InternetConnectionHelper
import com.google.android.material.snackbar.Snackbar

/**
 * Base Activity class
 * Activities extend this class to easily access common functions and variables
 */

abstract class BaseActivity : AppCompatActivity() {

    /**
     * Checks if phone has connection internet or not
     * @return true if online, false otherwise
     */
    protected fun isOnline(): Boolean {
        return InternetConnectionHelper.isNetworkAvailable(this)
    }

    /**
     * shows a snack bar at bottom
     * @param view : the view that snackbar should be shown in
     * @param message : the message to be shown
     */
    protected fun showSnackbar(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
    }

    /**
     * shows a toast with Toast.LENGTH_LONG duration
     * @param message : the message to be shown
     */
    protected fun showToastLong(message: String){
        Toast.makeText(this,message,Toast.LENGTH_LONG).show()
    }

}
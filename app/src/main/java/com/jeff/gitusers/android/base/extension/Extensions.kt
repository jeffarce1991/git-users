package com.jeff.gitusers.android.base.extension

import android.app.Activity
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.jeff.gitusers.R
import kotlin.math.abs

fun Activity.invokeSimpleDialog(title: String,
                                positiveButtonText: String,
                                message: String,
                                onApprove: () -> Unit) {
    AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(positiveButtonText) { dialog, which ->
                onApprove.invoke()
            }
            .setNegativeButton(getString(R.string.cancel)) { dialog, which ->
                dialog.dismiss()
            }
            .show()
}

fun Activity.invokeSimpleDialog(title: String,
                                positiveButtonText: String,
                                negativeButtonText: String,
                                message: String,
                                onApprove: () -> Unit) {
    AlertDialog.Builder(this)
        .setTitle(title)
        .setMessage(message)
        .setPositiveButton(positiveButtonText) { dialog, which ->
            onApprove.invoke()
        }
        .setNegativeButton(negativeButtonText) { dialog, which ->
            dialog.dismiss()
        }
        .show()
}

fun Activity.invokeSimpleDialog(title: String,
                                message: String) {
    AlertDialog.Builder(this)
        .setTitle(title)
        .setMessage(message)
        .show()
}

fun Activity.invokeSimpleDialog(title: String,
                                positiveButtonText: String,
                                message: String) {
    AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(positiveButtonText) { dialog, which ->
                dialog.dismiss()
            }
            .show()
}

fun Activity.longToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Activity.shortToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun View.hide() {
    this.visibility = View.GONE
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun ImageView.invertColor() {
    val invertMX = floatArrayOf(
        -1.0f, 0.0f, 0.0f, 0.0f, 255.0f,
        0.0f, -1.0f, 0.0f, 0.0f, 255.0f,
        0.0f, 0.0f, -1.0f, 0.0f, 255.0f,
        0.0f, 0.0f, 0.0f, 1.0f, 0.0f
    )

    val invertCM = ColorMatrix(invertMX)

    val filter = ColorMatrixColorFilter(invertCM)
    this.colorFilter = filter
}

fun formatNumberToAcronym(n : Int): String {
    return when {
        abs(n / 1000000) > 1 -> {
            (n / 1000000).toString() + "M"
        }
        abs(n / 1000) > 1 -> {
            (n / 1000).toString() + "K"
        }
        else -> {
            n.toString()
        }
    }
}


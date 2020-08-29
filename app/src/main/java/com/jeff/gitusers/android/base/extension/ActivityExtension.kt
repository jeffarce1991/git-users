package com.jeff.gitusers.android.base.extension

import android.app.Activity
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.jeff.gitusers.R

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


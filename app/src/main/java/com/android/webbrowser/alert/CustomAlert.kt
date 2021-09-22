package com.android.webbrowser.alert

import android.app.AlertDialog
import android.content.Context
import com.android.webbrowser.R



class CustomAlert (val context: Context){

   private var builder:AlertDialog.Builder = AlertDialog.Builder(context , R.style.AlertDialogTheme)


    fun setShowAlert(title:String, message:String, click: AlertClick){
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton("Ok") { dialogInterface, _ ->
            dialogInterface.dismiss()
            click.setClick(true)
        }

        builder.setNegativeButton("Cancel") { dialogInterface, _ ->
            dialogInterface.dismiss()
            click.setClick(false)
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    fun setShowAlert(message:String, click: AlertClick){
        builder.setMessage(message)
        builder.setPositiveButton("Ok") { dialogInterface, _ ->
            dialogInterface.dismiss()
            click.setClick(true)
        }

        builder.setNegativeButton("Cancel") { dialogInterface, _ ->
            dialogInterface.dismiss()
            click.setClick(false)
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    fun setShowAlertOk(title:String, message:String, click: AlertClick){
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton("Ok") { dialogInterface, _ ->
            dialogInterface.dismiss()
            click.setClick(true)
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }


}
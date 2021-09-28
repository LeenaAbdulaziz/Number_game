package com.example.nogame

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    lateinit var clRoot: ConstraintLayout
    lateinit var qf: EditText
    lateinit var gB: Button
    lateinit var mesg: ArrayList<String>
    lateinit var text: TextView

    private lateinit var rvMessages: RecyclerView

    var answer = 0
    var qu = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        answer = Random.nextInt(10)

        clRoot = findViewById(R.id.clRoot)
        mesg = ArrayList()

        text = findViewById(R.id.text)

        rvMessages = findViewById(R.id.rvMessages)
        rvMessages.adapter = Game(this, mesg)
        rvMessages.layoutManager = LinearLayoutManager(this)

        qf = findViewById(R.id.etGuessField)
        gB = findViewById(R.id.btGuessButton)

        gB.setOnClickListener { addMessage() }
    }

    private fun addMessage(){
        val msg = qf.text.toString()
        if(msg.isNotEmpty()){
            if(qu>0){
                if(msg.toInt() == answer){
                    disableEntry()
                    showAlertDialog("You win!\n\nPlay again?")
                }else{
                    qu--
                    mesg.add("You guessed $msg")
                    mesg.add("You have $qu guesses left")
                }
                if(qu==0){
                    disableEntry()
                    mesg.add("You lose - The correct answer was $answer")
                    mesg.add("Game Over")
                    showAlertDialog("You lose...\nThe correct answer was $answer.\n\nPlay again?")
                }
            }
            qf.text.clear()
            qf.clearFocus()
            rvMessages.adapter?.notifyDataSetChanged()
        }else{
            Snackbar.make(clRoot, "Please enter a number", Snackbar.LENGTH_LONG).show()
        }
    }

    private fun disableEntry(){
        gB.isEnabled = false
        gB.isClickable = false
        qf.isEnabled = false
        qf.isClickable = false
    }

    private fun showAlertDialog(title: String) {
        val dialogBuilder = AlertDialog.Builder(this)

        dialogBuilder.setMessage(title)
            .setCancelable(false)
            .setPositiveButton("Yes", DialogInterface.OnClickListener {
                    dialog, id -> this.recreate()
            })
            .setNegativeButton("No", DialogInterface.OnClickListener {
                    dialog, id -> dialog.cancel()
            })

        val alert = dialogBuilder.create()
        alert.setTitle("Game Over")
        alert.show()
    }
}
//        var clRoot: ConstraintLayout
//        val  = findViewById<>(R.id.rvMain)
//        clRoot = findViewById(R.id.clRoot)
//        myRecyclerView.adapter = RecycleViewAdapter(names)
//        myRecyclerView.layoutManager = LinearLayoutManager(this)
//
//        val text = findViewById<EditText>(R.id.addname)
//        val addButton = findViewById<Button>(R.id.button)

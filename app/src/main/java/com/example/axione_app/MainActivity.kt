package com.example.axione_app

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.ScrollingMovementMethod
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import java.lang.Math.abs
import kotlin.properties.Delegates


//TODO : Commander chez 6AM Fried Chicken

class MainActivity : AppCompatActivity() {
    lateinit var inPim : TextInputEditText
    lateinit var inFrequence : TextInputEditText

    lateinit var consoleFrequence : TextView
    lateinit var buttonPim : Button
    lateinit var buttonFrequence : Button
    lateinit var buttonReset : ImageButton
    lateinit var buttonCalculer : Button

    var listeIM3 = ArrayList<Int>()
    var listeIM5 = ArrayList<Int>()
    var listeIM2 = ArrayList<Int>()



    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        inFrequence = findViewById(R.id.textInputEditList)
        inPim = findViewById(R.id.textInputEdit)
        buttonReset = findViewById(R.id.buttonReset)
        consoleFrequence = findViewById(R.id.textView)
        buttonFrequence = findViewById(R.id.button2)
        buttonPim = findViewById(R.id.button)
        buttonCalculer = findViewById(R.id.buttonCalcul)
        var FrequencePim = ""
        var FrequenceAVerifier =""
        var listFrequence = ArrayList<Int>()
        var listFrequencePim = ArrayList<Int>()
        var listFrequencePim2 = ArrayList<Int>()
        var listFrequencePim3 = ArrayList<Int>()
        var listFrequencePim5 = ArrayList<Int>()
        var freqPim2 by Delegates.notNull<Int>()
        var freqPim3 by Delegates.notNull<Int>()
        var freqPim5 by Delegates.notNull<Int>()




        inFrequence.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {


            }

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {

            }
        })

        inPim.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {

            }
        })
        buttonFrequence.setOnClickListener{
                FrequenceAVerifier = inFrequence.text.toString()
            if (FrequenceAVerifier!=""){

                listFrequence = stringtoList(FrequenceAVerifier)
            }
            else{
                Toast.makeText(this, "entrer les fréquence à tester", LENGTH_SHORT).show()
            }

        }
        buttonPim.setOnClickListener {
                FrequencePim = inPim.text.toString()
            if (FrequencePim!=""){

                listFrequencePim = stringtoList(FrequencePim)
            }
            else{
                Toast.makeText(this, "entrer la Frequence du Pim", LENGTH_SHORT).show()
            }


        }
        buttonReset.setOnClickListener {
            reset()
        }
        buttonCalculer.setOnClickListener {
            if(FrequencePim!="" && FrequenceAVerifier!="") {
                if (listFrequence.isNotEmpty() && listFrequencePim.isNotEmpty()) {
                    listeIM2 = CalculIM2(listFrequence).distinct() as ArrayList<Int>
                    listeIM3 = calculIM3(listFrequence).distinct() as ArrayList<Int>
                    listeIM5 = CalculIM5(listFrequence).distinct() as ArrayList<Int>
                    listFrequencePim2 = calculPim2(listFrequence, listFrequencePim[0])
                    listFrequencePim3 = calculPim3(listFrequence, listFrequencePim[0])
                    listFrequencePim5 = calculPim5(listFrequence, listFrequencePim[0])
                    consoleFrequence.setMovementMethod(ScrollingMovementMethod())
                    freqPim2 = approximationFreqPim2(listeIM2, listFrequencePim[0])
                    freqPim3 = approximationFreqPim3(listeIM3, listFrequencePim[0])
                    freqPim5 = approximationFreqPim5(listeIM5, listFrequencePim[0])
                    consoleFrequence.text =
                        "Liste des raies correspondant à des produits d'intermodulations d'ordre 2 : IM2 : ${listeIM2} \n" +
                                "Liste des raies correspondant à des produits d'intermodulations d'ordre 3 : IM3 : ${listeIM3} \n" +
                                "Liste des raies correspondant à des produits d'intermodulations d'ordre 5 : IM5 : ${listeIM5} \n" +
                                "Frequence responsable des IM2 à $freqPim2 MHz : ${listFrequencePim2} \n" +
                                "Frequence responsable des IM3 à $freqPim3 MHz : ${listFrequencePim3} \n" +
                                "Frequence responsable des IM5 à $freqPim5 MHz : ${listFrequencePim5} \n"

                }
            }
        }


    }


    fun reset (){
        consoleFrequence.text = ""
    }
    fun calculIM3(list : ArrayList<Int>): ArrayList<Int> {
        var IM3_1 : Int
        var IM3_2 : Int
        var IM3List = ArrayList<Int>()
        for (i in 0 until list.size){
            for (j in 0 until list.size){
                if (list[i]!= list[j]){
                    IM3_1 = 2*list[i]-list[j]
                    IM3_2 = 2*list[j]-list[i]
                    if (IM3_1 > 0){
                        IM3List.add(IM3_1)
                    }
                    if (IM3_2 > 0){
                        IM3List.add(IM3_2)
                    }
                }
            }
        }
        return IM3List


    }
    fun CalculIM5(list : ArrayList<Int>): ArrayList<Int> {
        var IM5_1 : Int
        var IM5_2 : Int
        var IM5List = ArrayList<Int>()
        for (i in 0 until list.size){
            for (j in 0 until list.size){
                if (list[i]!= list[j]){
                    IM5_1 = 3*list[i]-2*list[j]
                    IM5_2 = 3*list[j]-2*list[i]
                    if (IM5_1 > 0){
                        IM5List.add(IM5_1)
                    }
                    if (IM5_2 > 0){
                        IM5List.add(IM5_2)
                    }
                }
            }
        }
        return IM5List


    }
    fun CalculIM2(list : ArrayList<Int>): ArrayList<Int> {
        var IM2_1 : Int
        var IM2_2 : Int
        var IM2List = ArrayList<Int>()
        for (i in 0 until list.size){
            for (j in 0 until list.size){
                if (list[i]!= list[j]){
                    IM2_1 = list[i]-list[j]
                    IM2_2 = list[j]+list[i]
                    if (IM2_1 > 0){
                        IM2List.add(IM2_2)
                    }
                    if (IM2_2 > 0){
                        IM2List.add(IM2_2)
                    }
                }
            }
        }
        return IM2List


    }
    fun calculPim2(list : ArrayList<Int>, frequencePim2 : Int): ArrayList<Int> {
        var IM2_1 : Int
        var IM2_2 : Int
        var pim2 = ArrayList<Int>()
        var bufferPim2 : Int = frequencePim2
        for (i in 0 until list.size){
            for (j in 0 until list.size){
                if (list[i]!= list[j]){

                    IM2_1 = list[i]-list[j]
                    if (abs(IM2_1-frequencePim2)<bufferPim2){
                        bufferPim2 = abs(frequencePim2-IM2_1)
                        pim2.clear()
                        pim2.add(list[i])
                        pim2.add(list[j])

                    }

                    IM2_2 = list[j]+list[i]
                    if (abs(IM2_2-frequencePim2)<bufferPim2){
                        bufferPim2 = abs(frequencePim2-IM2_2)
                        pim2.clear()
                        pim2.add(list[i])
                        pim2.add(list[j])
                    }
                }
            }
        }
        return pim2
    }
    fun calculPim3(list : ArrayList<Int>, frequencePim3 : Int): ArrayList<Int> {
        var IM3_1 : Int
        var IM3_2 : Int
        var pim3 = ArrayList<Int>()
        var bufferPim3 : Int = frequencePim3
        for (i in 0 until list.size){
            for (j in 0 until list.size){
                if (list[i]!= list[j]){

                    IM3_1 = list[i]-list[j]
                    if (abs(IM3_1-frequencePim3)<bufferPim3){
                        bufferPim3 = abs(frequencePim3-IM3_1)
                        pim3.clear()
                        pim3.add(list[i])
                        pim3.add(list[j])

                    }

                    IM3_2 = list[j]+list[i]
                    if (abs(IM3_2-frequencePim3)<bufferPim3){
                        bufferPim3 = abs(frequencePim3-IM3_2)
                        pim3.clear()
                        pim3.add(list[i])
                        pim3.add(list[j])
                    }
                }
            }
        }
        return pim3
    }
    fun calculPim5(list : ArrayList<Int>, frequencePim5 : Int): ArrayList<Int> {
        var IM5_1 : Int
        var IM5_2 : Int
        var pim5 = ArrayList<Int>()
        var bufferPim5 : Int = frequencePim5
        for (i in 0 until list.size){
            for (j in 0 until list.size){
                if (list[i]!= list[j]){

                    IM5_1 = list[i]-list[j]
                    if (abs(IM5_1-frequencePim5)<bufferPim5){
                        bufferPim5 = abs(frequencePim5-IM5_1)
                        pim5.clear()
                        pim5.add(list[i])
                        pim5.add(list[j])

                    }

                    IM5_2 = list[j]+list[i]
                    if (abs(IM5_2-frequencePim5)<bufferPim5){
                        bufferPim5 = abs(frequencePim5-IM5_2)
                        pim5.clear()
                        pim5.add(list[i])
                        pim5.add(list[j])
                    }
                }
            }
        }
        return pim5
    }


    fun approximationFreqPim2(list: ArrayList<Int>, frequencePim2: Int): Int {
        var bufferPim2 = list.max()
        var freqPim2 = frequencePim2
        for (i in list){
            if (abs(i-frequencePim2)<bufferPim2){
                freqPim2 = i
                bufferPim2 = abs(i-frequencePim2)
            }
        }
        return freqPim2
    }
    fun approximationFreqPim3(list: ArrayList<Int>, frequencePim3: Int): Int {
        var bufferPim3 = list.max()
        var freqPim3 = frequencePim3
        for (i in list){
            if (abs(i-frequencePim3)<bufferPim3){
                freqPim3 = i
                bufferPim3 = abs(i-frequencePim3)
            }
        }
        return freqPim3
    }
    fun approximationFreqPim5(list: ArrayList<Int>, frequencePim5: Int): Int {
        var bufferPim5 = list.max()
        var freqPim5 = frequencePim5
        for (i in list){
            if (abs(i-frequencePim5)<bufferPim5){
                freqPim5 = i
                bufferPim5 = abs(i-frequencePim5)
            }
        }
        return freqPim5
    }



    fun stringtoList(frequence : String): ArrayList<Int> {
        val subStrings = frequence.split(",")
        val list = ArrayList<Int>()
        if (subStrings.all { isInt(it) }) {

            for (subString in subStrings) {
                val intValue = subString.toInt()
                list.add(intValue)
            }
            return list
        }
        else {
            Toast.makeText(this, "entrer les frequencces au bon format", LENGTH_SHORT).show()
            return list
        }
    }



    fun isInt(str: String): Boolean {
        return str.toIntOrNull() != null
    }
}
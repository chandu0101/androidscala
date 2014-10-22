package com.androidcoursera

import android.graphics.Color
import org.scaloid.common._

/**
  * Created by chandrasekharkode on 10/22/14.
  */
class HelloAndroid extends SActivity {

   onCreate {

     contentView = new SVerticalLayout {

       style {
         // style for UI elements
         case t: STextView => t.textSize(20.dip).textColor(Color.RED) // setting text size to 20
       }

       STextView("Hello Android , everything setup!" +
         " ,hope will write some toys on you ")

     }


   }

 }

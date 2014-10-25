package com.chandu0101

import java.util.concurrent.{LinkedBlockingQueue, ThreadPoolExecutor, TimeUnit}

import android.graphics.Color
import org.scaloid.common._

import scala.concurrent.{ExecutionContext, Future}
import scala.io.Source

/**
 * Created by chandrasekharkode on 10/25/14.
 */
class App extends SActivity {

  onCreate {
    contentView = new SVerticalLayout {
      STextView("Example : 7225247386 => scala is fun").textSize(20.dip).textColor(Color.RED)
      STextView("Number:")
      val number = SEditText("7225247386").height(11.dip)
      STextView("Words url : (source url for words)")
      val wordsURL = SEditText("http://lamp.epfl.ch/files/content/sites/lamp/files/teaching/progfun/linuxwords.txt")
      SButton("Generate", displayResults()).<<.marginBottom(10.dip).>>
      val listView = SListView().backgroundColor(Color.WHITE)

      def displayResults() = {
        // implicit needed for Future
        implicit val exec = ExecutionContext.fromExecutor(
          new ThreadPoolExecutor(100, 100, 1000, TimeUnit.SECONDS,
            new LinkedBlockingQueue[Runnable]))
        listView.setAdapter(null) // clear results on successive calls
        Future {
          val words = generateWords(number.getText.toString, wordsURL.getText.toString)
          val results = if(words.isEmpty) SArrayAdapter("No Combination of words for given number")
                         else SArrayAdapter(words.toArray)
          runOnUiThread(listView.setAdapter(results) )
        }
      }
    }


  }

  /*
    this method will get data from internet then perform computation
   */
  def generateWords(number: String, url: String): Set[String] = {
    val words = Source.fromURL(url).
      getLines().filter(w => w.forall(c => c.isLetter)).toList;
    val gen = Generator(words)
    gen.translate(number)
  }

}

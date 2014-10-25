package com.chandu0101

/**
 * Created by chandrasekharkode on 10/25/14.
 *
 * this is Martin Odersky's phone code implementation
 */
case class Generator(words: List[String]) {

  private val mnemonics = Map(
    '2' -> "ABC", '3' -> "DEF", '4' -> "GHI", '5' -> "JKL",
    '6' -> "MNO", '7' -> "PQRS", '8' -> "TUV", '9' -> "WXYZ")

  /** Invert the mnemonics map to give a map from chars 'A' ... 'Z' to '2' ... '9' */
  private val charCode: Map[Char, Char] =
    for ((digit, str) <- mnemonics; letter <- str) yield letter -> digit

  /** Maps a word to the digit string it can represent, e.g. “Java” -> “5282” */
  private def wordCode(word: String): String = word.toUpperCase map charCode

  /** A map from digit strings to the words that represent them,
    * e,g. “5282” -> List(“Java”, “Kata”, “Lava”, ...)
    * Note: A missing number should map to the empty set, e.g. "1111" -> List()
    */
  private val wordsForNum: Map[String, Seq[String]] = (words groupBy wordCode) withDefaultValue List()

  /** Return all ways to encode a number as a list of words */
  def encode(number: String): Set[List[String]] =
    if (number.isEmpty) Set(List())
    else {
      for {
        split <- 1 to number.length
        word <- wordsForNum(number take split)
        rest <- encode(number drop split)
      } yield word :: rest
    }.toSet


  /** Maps a number to a list of all word phrases that can represent it */
  def translate(number: String): Set[String] = encode(number) map (_ mkString " ")
}

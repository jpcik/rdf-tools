package rdftools.rdf

import org.scalatest.FlatSpec
import org.scalatest.Matchers

trait ParentTest  extends FlatSpec with Matchers  {
  def time[T](str: String)(thunk: => T): T = {
    print(str + "... ")
    val t1 = System.currentTimeMillis
    val x = thunk
    val t2 = System.currentTimeMillis
    println((t2 - t1) + " msecs")
    x
  }
}
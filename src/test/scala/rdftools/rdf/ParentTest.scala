package rdftools.rdf

import org.scalatest._
import flatspec._
import matchers._

trait ParentTest  extends AnyFlatSpec with should.Matchers  {
  def time[T](str: String)(thunk: => T): T = {
    print(str + "... ")
    val t1 = System.currentTimeMillis
    val x = thunk
    val t2 = System.currentTimeMillis
    println((t2 - t1) + " msecs")
    x
  }
}
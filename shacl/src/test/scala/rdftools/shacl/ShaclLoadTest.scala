package rdftools.shacl

import org.scalatest._
import flatspec._
import matchers._
import rdftools.rdf.RdfTools._
import scala.language.postfixOps

class ShaclLoadTest extends AnyFlatSpec with should.Matchers  {
  "A Shacl file" should "be loaded" in {
    val shapes=ShaclLoader.load("core/node/class-001.ttl")
    shapes.size should equal(1)
  }
}

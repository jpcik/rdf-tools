package rdftools.rdf

import org.scalatest._
import rdftools.rdf.RdfTools._
import org.apache.jena.datatypes.xsd.XSDDatatype
import org.apache.jena.datatypes.xsd.XSDDatatype._
import scala.language.postfixOps

class TripleTest extends FlatSpec with Matchers  {

    "A Triple" should "comply to: " in{
      
      val tri1=Triple("subj","prop","obj")
      tri1.s shouldBe a [Iri]
      tri1.p shouldBe a [Iri]
      tri1.o shouldBe a [Iri]
      
      val tri2=Triple("subj","prop",lit("obj"))
      tri2.s shouldBe a [Iri]
      tri2.p shouldBe a [Iri]
      tri2.o shouldBe a [Literal]
      
      val tri3=Triple(bnode("subj"),"prop","342"^^XSDinteger)
      tri3.s shouldBe a [Bnode]
      tri3.o shouldBe a [Literal]
      tri3.o.asInstanceOf[Literal].datatype shouldBe (XSDinteger:Iri)
      
      val tri4="subj" ~ "prop" ~ ("strings"^^XSDstring)
      tri4.s shouldBe a [Iri]
      tri4.o.asLiteral.value shouldBe("strings")
      
      
    }
}
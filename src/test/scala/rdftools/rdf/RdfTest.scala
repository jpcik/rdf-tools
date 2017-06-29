package rdftools.rdf

import org.scalatest._
import rdftools.rdf.RdfTools._
import org.apache.jena.datatypes.xsd.XSDDatatype
import org.apache.jena.datatypes.xsd.XSDDatatype._
import scala.language.postfixOps

class RdfTest extends FlatSpec with Matchers  {

    "An Iri" should "comply to: " in{
      val badIri=Iri("abc")
      badIri.localName should be ("abc")
      badIri.asUri shouldBe defined
      badIri.localName should be ("abc")
      
      val iri1:Iri="test1"
      iri1 shouldBe a[Iri]
      
      val iri2:RdfTerm="some"
      iri2.asIri shouldBe a[Iri]
      an [ClassCastException] should be thrownBy (iri2.asBnode) 
    }
     
    "A literal" should "make" in{
      val lit1=lit("literal")
      lit1 shouldBe a[Literal]
      lit1.datatype shouldBe (XSDDatatype.XSDstring:Iri)
      lit1.langTag shouldBe (None)
      
      val num:Int=3434
      val lit2=lit(num)
      lit2.datatype shouldBe (XSDDatatype.XSDinteger:Iri)
      
      val lit3=lit(34.6)
      lit3.datatype shouldBe (XSDdouble:Iri)
      
      val lit4=lit("stringa","es")
      lit4.langTag shouldBe (Some("es"))
      
      val lit5="some string"`@`"fr"
      lit5 shouldBe a [Literal]
      lit5.langTag shouldBe(Some("fr"))
      
      val lit6="another string"^^XSDstring
      lit6.datatype shouldBe (XSDstring:Iri)
      
      val lit7="34534"^^XSDinteger
      lit7.datatype shouldBe (XSDinteger:Iri)
    }
    
    "a blank node" should "comply" in {
      val b1= bnode("abc")
      b1.id shouldBe ("abc")
      
    }
}
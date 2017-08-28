package rdftools.rdf

import org.scalatest._
import rdftools.rdf.RdfTools._
import scala.language.postfixOps
import scala.util.Success
import java.net.URI

class RdfTest extends FlatSpec with Matchers  {

    "An Iri" should "comply to: " in{
      val badIri=Iri("abc")
      badIri.localName should be ("abc")
      badIri.asUri shouldEqual Success(new URI("abc"))
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
      lit1.datatype shouldBe (XsdString)
      lit1.langTag shouldBe (None)
      
      val num:Int=3434
      val lit2=lit(num)
      lit2.datatype shouldBe (XsdInt)
      
      val lit3=lit(34.6)
      lit3.datatype shouldBe (XsdDouble)
      
      val lit4=lit("stringa","es")
      lit4.langTag shouldBe (Some("es"))
      
      val lit5="some string"`@`"fr"
      lit5 shouldBe a [Literal]
      lit5.langTag shouldBe(Some("fr"))
      
      val lit6="another string"^^XsdString
      lit6.datatype shouldBe (XsdString)
      
      val lit7="34534"^^XsdInteger
      lit7.datatype shouldBe (XsdInteger)
    }
    
    "a blank node" should "comply" in {
      val b1= bnode("abc")
      b1.id shouldBe ("abc")
      
    }
}
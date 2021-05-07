/*
 * Copyright 2017 Jean-Paul Calbimonte
 *
 * SPDX-License-Identifier: MIT
 */

package rdftools.rdf

import org.scalatest._
import flatspec._
import matchers._
import rdftools.rdf.RdfTools._
import rdftools.rdf.xsd._
import scala.language.postfixOps
import scala.util.Success
import java.net.URI

class RdfTest extends AnyFlatSpec with should.Matchers  {
  "an absolute IRI" should "be well formed" in{

    val iri1=Iri("http://a.example/s")
    iri1.asUrl.toRelativeUrl.toString should be ("/s")

    val iri2=Iri("http://a.example/\u0073")
    iri2.path should be ("http://a.example/s")

//    val iri3=Iri("http://a.example/\U00000073")
//    iri3.path should be ("http://a.example/s")

    val iri4=Iri("scheme:!$%25&amp;'()*+,-./0123456789:/@ABCDEFGHIJKLMNOPQRSTUVWXYZ_abcdefghijklmnopqrstuvwxyz~?#")
    iri4.path should be ("scheme:!$%25&amp;'()*+,-./0123456789:/@ABCDEFGHIJKLMNOPQRSTUVWXYZ_abcdefghijklmnopqrstuvwxyz~?#")


    





    val anIri=Iri("https://hevs.ch/someIri")
    anIri.path should be ("https://hevs.ch/someIri")
//    anIri.localName should be ("someIri")
    anIri shouldEqual(Iri("https://hevs.ch/someIri"))
  }


  "Iris" should "comply to Iri norms" in{
    val badIri=Iri("abc")
 //   badIri.localName should be ("abc")
 //   badIri.asUri shouldEqual Success(new URI("abc"))
 //   badIri.localName should be ("abc")
      
    val iri1:Iri="test1"
    iri1 shouldBe a[Iri]
      
    val iri2:RdfTerm="some"
    iri2.asIri shouldBe a[Iri]
    an [ClassCastException] should be thrownBy (iri2.asBnode) 
  
    val iri3:Iri = "another Iri"
    iri3 shouldBe a[Iri]
  }
     
  "Literals" should "comply to literal norms" in{
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
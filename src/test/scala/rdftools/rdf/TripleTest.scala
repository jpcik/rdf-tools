package rdftools.rdf

import org.scalatest._
import flatspec._
import matchers._
import rdftools.rdf.RdfTools._
import scala.language.postfixOps
import rdftools.rdf.Prefixes._

class TripleTest extends AnyFlatSpec with should.Matchers  {

  "A triple predicate 'a'" should "be interpreted as rdf:type" in {
    info("""Test bareword_a_predicate: 
      <http://a.example/s> a <http://a.example/o>""")
    val tri=Iri("http://a.example/s") a Iri("http://a.example/o")
    tri.predicate should ===(Iri("http://www.w3.org/1999/02/22-rdf-syntax-ns#type"))
  }

 
  "A prefix" should "be included in the subject IRI" in{
   val p="http://a.example/"
 
   info("""Test old_style_prefix
      @prefix p: <http://a.example/>.
      p:s <http://a.example/p> <http://a.example/o> . """)

    Triple(p+"s","http://a.example/p","http://a.example/o")
      .subject.asIri.path should be ("http://a.example/s")
  
  //it should "be also included in the subject IRI" in{
    info("""Test SPARQL_style_prefix
      PREFIX p: <http://a.example/>
      p:s <http://a.example/p> <http://a.example/o> .""")
    Triple(s"${p}s","http://a.example/p","http://a.example/o")
      .subject.asIri.path should be ("http://a.example/s")
  //}
  //it should "be included in the predicate IRI" in{
    info("""Test prefixed_IRI_predicate Input
      @prefix p: <http://a.example/>.
      <http://a.example/s> p:p <http://a.example/o> .""")
    Triple("http://a.example/s",p+"p","http://a.example/o")
      .predicate.asIri.path should be ("http://a.example/p")
  //}
  //it should "be included in the object IRI" in{
    info(""""Test prefixed_IRI_object Input
      @prefix p: <http://a.example/>.
      <http://a.example/s> <http://a.example/p> p:o .""")
    Triple("http://a.example/s","http://a.example/p",p+"o").
      _object.asIri.path should be ("http://a.example/o")
  }
  
  "A prefix-only IRI" should "be the full subject IRI" in{
    val p="http://a.example/s"
    info("""Test prefix_only_IRI Input
      @prefix p: <http://a.example/s>.
      p: <http://a.example/p> <http://a.example/o> .""")
    Triple(s"$p","http://a.example/p","http://a.example/o")
      .subject.asIri.path should be ("http://a.example/s")
  }

  "A prefix with PN CHARS BASE characters" should "be valid" in{
    info("""Test prefix_with_PN_CHARS_BASE_character_boundaries Input
      @prefix AZazÀÖØöø˿ͰͽͿ῿‌‍⁰↏Ⰰ⿯、퟿豈﷏ﷰ�𐀀󯿽: <http://a.example/> .
      <http://a.example/s> <http://a.example/p> AZazÀÖØöø˿ͰͽͿ῿‌‍⁰↏Ⰰ⿯、퟿豈﷏ﷰ�𐀀󯿽:o .""")
    val `AZazÀÖØöø˿ͰͽͿ῿‌‍⁰↏Ⰰ⿯、퟿豈﷏ﷰ�𐀀󯿽` ="http://a.example/"
    Triple("http://a.example/s","http://a.example/p",`AZazÀÖØöø˿ͰͽͿ῿‌‍⁰↏Ⰰ⿯、퟿豈﷏ﷰ�𐀀󯿽`+"o")
      ._object.asIri.path should be ("http://a.example/o")


  }
    
  
    
    "A Triple" should "comply to: " in{
      
      val tri1=Triple("subj","prop","obj")
      tri1.s shouldBe a [Iri]
      tri1.p shouldBe a [Iri]
      tri1.o shouldBe a [Iri]
      
      val tri2=Triple("subj","prop",lit("obj"))
      tri2.s shouldBe a [Iri]
      tri2.p shouldBe a [Iri]
      tri2.o shouldBe a [Literal]
      
      val tri3=Triple(bnode("subj"),"prop","342"^^XsdInteger)
      tri3.s shouldBe a [Bnode]
      tri3.o shouldBe a [Literal]
      tri3.o.asInstanceOf[Literal].datatype shouldBe (XsdInteger)
      
      val tri4="subj" ~ "prop" ~ ("strings"^^XsdString)
      tri4.s shouldBe a [Iri]
      tri4.o.asLiteral.value shouldBe("strings")
      
      
    }
}
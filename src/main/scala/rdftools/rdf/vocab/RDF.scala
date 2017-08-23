package rdftools.rdf.vocab

import rdftools.rdf.Vocab

import rdftools.rdf._

import rdftools.rdf.RdfTools._

object RDF extends Vocab {
  override val iri: Iri = "http://www.w3.org/1999/02/22-rdf-syntax-ns#"
  val List = clazz("List")
  val Alt = clazz("Alt")
  val Seq = clazz("Seq")
  val Bag = clazz("Bag")
  val Statement = clazz("Statement")
  val Property = clazz("Property")
  val rest = prop("rest")
  val first = prop("first")
  val value = prop("value")
  val `object` = prop("object")
  val predicate = prop("predicate")
  val subject = prop("subject")
  val `type` = prop("type")
  val a=`type`
  
  val langString=RDF("langString")
}
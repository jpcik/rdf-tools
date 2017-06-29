package rdftools.rdf

import RdfTools._

trait Vocab {
  val iri:Iri
  def apply(s:String):Iri=iri+s
  def prop(s:String)=Property(iri+s)
  def clazz(s:String)=Class(iri+s)
}  


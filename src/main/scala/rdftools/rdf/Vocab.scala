package rdftools.rdf

trait Vocab {
  val iri:Iri
  def apply(s:String)=iri+s
  def prop(s:String)=Property(iri+s)
  def clazz(s:String)=Class(iri+s)
}  


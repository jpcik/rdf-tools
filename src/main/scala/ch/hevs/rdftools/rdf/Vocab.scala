package ch.hevs.rdftools.rdf

trait Vocab {
  val iri:Iri
  def apply(s:String)=iri+s
}
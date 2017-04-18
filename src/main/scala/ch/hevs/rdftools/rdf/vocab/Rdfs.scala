package ch.hevs.rdftools.rdf.vocab

import ch.hevs.rdftools.rdf.Vocab
import ch.hevs.rdftools.rdf._
import ch.hevs.rdftools.rdf.RdfTools._

object Rdf extends Vocab{
  override val iri:Iri="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
  val a=Rdf("type")
  val _type=a
  val subject=Rdf("subject")
  val predicate=Rdf("predicate")
  val _object=Rdf("object")
  val value=Rdf("value")  
}

object Rdfs extends Vocab{
  override val iri:Iri="http://www.w3.org/2000/01/rdf-schema#"
  val subClassOf=Rdfs("subClassOf")
  val subPropertyOf=Rdfs("subPropertyOf")
  val comment=Rdfs("comment")
  val label=Rdfs("label")
  val domain=Rdfs("domain")
  val range=Rdfs("range")

}
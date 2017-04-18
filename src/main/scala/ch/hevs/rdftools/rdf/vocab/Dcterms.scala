package ch.hevs.rdftools.rdf.vocab

import ch.hevs.rdftools.rdf.Vocab
import ch.hevs.rdftools.rdf._
import ch.hevs.rdftools.rdf.RdfTools._

object Dcterms  extends Vocab{
  override val iri:Iri="http://purl.org/dc/terms/"
  val identifier=Dcterms("identifier")
  val title=Dcterms("title")
  val description=Dcterms("description")
}
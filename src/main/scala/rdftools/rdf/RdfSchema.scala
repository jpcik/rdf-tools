package rdftools.rdf

import scala.language.implicitConversions

object RdfSchema {
  //implicit def prop2Iri(prop:Property)=prop.iri
  //implicit def class2Iri(clazz:Class)=clazz.iri
}

case class Property(iri:Iri)

case class Class(iri:Iri)
/*
 * Copyright 2017 Jean-Paul Calbimonte
 *
 * SPDX-License-Identifier: MIT
 */

package rdftools.rdf

trait Graph {
  val triples:Set[Triple]
  val name:Option[Iri]
  def ++(tr:Triple):Graph={
    new RdfGraph(name,triples.+(tr))
  }
}

case class RdfGraph(name:Option[Iri],triples:Set[Triple]) extends Graph {
  
}

object Graph{
  def apply(iri:Iri,triples:Triple*):Graph=RdfGraph(Some(iri),triples.toSet)
  def apply(triples:Triple*):Graph=RdfGraph(None,triples.toSet)
}

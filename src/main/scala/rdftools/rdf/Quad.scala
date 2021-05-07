/*
 * Copyright 2017 Jean-Paul Calbimonte
 *
 * SPDX-License-Identifier: MIT
 */

package rdftools.rdf

trait Quad {
  val triple:Triple
  val graph:RdfTerm
}

object Quad {
  def apply(s:RdfTerm,p:Iri,o:RdfTerm,g:RdfTerm):Quad=RdfQuad(Triple(s,p,o),g)
}

case class RdfQuad(triple:Triple,graph:RdfTerm) extends Quad


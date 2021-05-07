/*
 * Copyright 2017 Jean-Paul Calbimonte
 *
 * SPDX-License-Identifier: MIT
 */

package rdftools.rdf

trait Triple {
  val subject:RdfTerm
  val predicate:Iri
  val _object:RdfTerm
  lazy val s=subject
  lazy val p=predicate
  lazy val o=_object
  def ++(tr:Triple)=Graph(this,tr)
  override def toString=s"$s $p $o ."
  override def equals(other:Any)=other match{
    case t:Triple=> 
       _object.equals(t._object) &&
       predicate.equals(t.predicate) &&
       subject.equals(t.subject)     
    case _ => false
  }

}
object Triple {
  def apply(s:RdfTerm,p:Iri,o:RdfTerm):Triple=RdfTriple(s,p,o)
}

case class RdfTriple(subject:RdfTerm,predicate:Iri,_object:RdfTerm) extends Triple{
 
}

case class SubjProp(s:Iri,p:Iri) {
  def ~(term:RdfTerm) =Triple(s,p,term)
}
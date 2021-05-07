/*
 * Copyright 2017 Jean-Paul Calbimonte
 *
 * SPDX-License-Identifier: MIT
 */

package rdftools.rdf

import rdftools.rdf.vocab.RDF

/** An RDF term, either an IRI, literal or blank node */
trait RdfTerm {
  /** This term as IRI 
   *  @return this RDF term as IRI */
  def asIri:Iri=throw new ClassCastException(s"Literal $this is not a Iri")
  
  /** This term as Literal
   *  @return this RDF term as Literal */
  def asLiteral:Literal=throw new ClassCastException(s"Iri $this is not a Literal")
  
  /** This term as Blank node
   *  @return this RDF term as blank node */
  def asBnode:Bnode=throw new ClassCastException(s"Iri $this is not a Blank node")
  
  /*
  /** Obtain a triple whose subject is this RDF term, and property is `prop`. 
   *  @param prop the IRI of a property 
   *  @return a triple option if there is a match, none otherwise */
  def triple(prop:Iri):Option[Triple]=None
  
  /** Obtain triples whose subject is this RDF term, and property is `prop`. 
   *  @param prop the IRI of a property 
   *  @return a triple iterator */
  def triples(prop:Iri):Iterator[Triple]=Iterator.empty
  
  /** Obtain the object of a triple whose subject is this RDF term, and property is `prop`. 
   *  @param prop the IRI of a property 
   *  @return an RDF term option if there is a match, none otherwise */
  def obj(prop:Iri):Option[RdfTerm]=None
  
  /** Obtain the objects of triples whose subject is this RDF term, and property is `prop`. 
   *  @param prop the IRI of a property 
   *  @return an RDF term iterator */
  def objs(prop:Iri):Iterator[RdfTerm]=Iterator.empty
  
  /** Obtain the object as string, of a triple whose subject is this RDF term, and property is `prop`. 
   *  @param prop the IRI of a property 
   *  @return a string option if there is a match, none otherwise */
  def objString(prop:Iri):Option[String]=
    obj(prop).map( x => x.asLiteral.getString )
*/

  /** Creates a triple with this RDF term as subject, RDF type as predicate, and iri as object
    *
    * @param iri the triple object
    * @return the new Triple with this RDF Term as subject
    */
  def a(iri:Iri)=
    Triple(this,RDF.`type`.iri,iri)

}
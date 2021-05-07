/*
 * Copyright 2017 Jean-Paul Calbimonte
 *
 * SPDX-License-Identifier: MIT
 */

package rdftools.rdf

import RdfTools._

/** A vocabulary consisting of classes and properties */
trait Vocab {
  /** The IRI of the vocabulary */
  val iri:Iri
  
  /** Creates a new IRI whose base is the vocabulary IRI 
   *  @param name the string appended to the base IRI
   *  @return the new IRI */
  def apply(name:String):Iri=iri+name
  
  /** Creates a new property with the vocabulary IRI as base 
   *  @param name the name of the property 
   *  @return the new property */
  def prop(s:String)=Property(iri+s)
  
  /** Creates a new class with the vocabulary IRI as base 
   *  @param name the name of the class
   *  @return the new class */
  def clazz(s:String)=Class(iri+s)
}  


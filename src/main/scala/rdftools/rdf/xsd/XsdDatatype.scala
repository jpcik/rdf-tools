/*
 * Copyright 2017 Jean-Paul Calbimonte
 *
 * SPDX-License-Identifier: MIT
 */

package rdftools.rdf.xsd

import rdftools.rdf.RdfTools.lit
import rdftools.rdf.Iri

/** Generic datytype IRI according to XSD
  *
  * @param typeFragment short name of fragment, e.g. int 
  */
class XsdDatatype(typeFragment:String) extends Iri{

  /** XSD IRI */
  val xsdIri=Iri("http://www.w3.org/2001/XMLSchema#")

  /** XsdDatatype full Iri path */
  val path=xsdIri.path+typeFragment
}

/** XSDDatatype companion object */
object XsdDatatype {
  
  /** Available XSD datatypes */
  val types=Seq(
    XsdString,XsdDouble,XsdDecimal,XsdLong,
    XsdInteger,XsdBoolean,XsdDateTime) 
    .map(dt=>dt.path->dt).toMap
  
  /** Resolves a XSD datatype by its Uri
    *
    * @param datatypeUri XSD datatype uri in text form
    * @return the XsdDataType Iri object if it resolves, None otherwise
    */
  def resolve(datatypeUri:String)=types.get(datatypeUri)
}



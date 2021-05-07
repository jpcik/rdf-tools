/*
 * Copyright 2017 Jean-Paul Calbimonte
 *
 * SPDX-License-Identifier: MIT
 */

package rdftools.rdf

import scala.util.Try
import java.net.URI
import io.lemonlabs.uri.Url
import io.lemonlabs.uri.Uri

/** An IRI RDF term */
trait Iri extends RdfTerm{
  
  /** The path string of the IRI*/
  val path:String

  
  private lazy val parsedUri=Uri.parse(path)

  lazy val scheme=parsedUri.schemeOption

  override def asIri=this

  def asUrl=parsedUri.toUrl

  def asUrn=parsedUri.toUrn

  /** Create a subject-predicate pair, where the subject is this IRI, and predicate is `prop` 
   *  @param prop the IRI of a predicate 
   *  @return the subject property pair */
  def ~(prop:Iri)=SubjProp(this,prop)
  
  override def toString=s"$path"
  override def equals(other:Any)=other match{
    case i:Iri=> path==i.path
    case _ => false
  }
}

object Iri {
  /** Creates a simple IRI 
   *  @param path the IRI path string
   *  @return the simple IRI   */
  def apply(path:String)=SimpleIri(path)
}

case class SimpleIri(value:String) extends Iri{
  val path= value

  def +(str:String):Iri=SimpleIri(value+str)
  def +(anotherIri:Iri):Iri= this.+(anotherIri.path)
  //ef asUri=parsedIri.toJavaURI
  def > = this
}
package rdftools.rdf

import scala.util.Try
import java.net.URI

/** An IRI RDF term */
trait Iri extends RdfTerm{
  
  /** The path string of the IRI*/
  val path:String
  
  override def asIri=this
  
  /** Create a subject-predicate pair, where the subject is this IRI, and predicate is `prop` 
   *  @param prop the IRI of a predicate 
   *  @return the subject property pair */
  def ~(prop:Iri)=SubjProp(this,prop)
  
  override def toString=s"$path"
  override def equals(other:Any)=other match{
    case i:Iri=>path==i.path
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
  lazy val localName=value.split(Array('/','#')).last
  def +(str:String):Iri=SimpleIri(value+str)
  def asUri=Try(new URI(value))//.toOption
  def > = this
}
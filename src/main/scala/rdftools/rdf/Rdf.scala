package rdftools.rdf

import org.apache.jena.datatypes.xsd.XSDDatatype
import org.joda.time.DateTime
import scala.language.implicitConversions
import java.net.URLEncoder
import org.apache.http.client.utils.URIUtils
import java.net.URI
import scala.util.Try
import scala.language.postfixOps
import scala.reflect.ClassTag

trait RdfTerm {
  def asIri:Iri=throw new ClassCastException(s"Literal $this is not a Iri")
  def asLiteral:Literal=throw new ClassCastException(s"Iri $this is not a Literal")
  def asBnode:Bnode=throw new ClassCastException(s"Iri $this is not a Blank node")
}

trait Iri extends RdfTerm{
  val path:String
  override def asIri=this
  def ~(prop:Iri)=SubjProp(this,prop)
}

object Iri {
  def apply(path:String)=StringIri(path)
}

case class StringIri(value:String) extends Iri{
  override def toString=value
  val path= value
  lazy val localName=value.split(Array('/','#')).last
  def +(str:String):Iri=StringIri(value+str)
  def asUri=Try(new URI(value)).toOption
  def > = this
}

case class SubjProp(s:Iri,p:Iri) {
  def ~(term:RdfTerm) =Triple(s,p,term)
}

//object iri {
  //def apply(str:String):Iri=Iri(str)
  //def apply2(str:String,encoding:String="UTF-8")=Iri(URLEncoder.encode(str,"UTF-8"))
//}

trait Literal extends RdfTerm{
  val value:Any
  val datatype:Iri
  val langTag:Option[String]
  override def asLiteral=this
}

case class AnyLiteral(value:Any) extends Literal {
  import RdfTools._
  lazy val datatype:Iri=value match {
    case s:String =>XSDDatatype.XSDstring
    case d:Double =>XSDDatatype.XSDdouble
    case l:Long =>XSDDatatype.XSDlong
    case i:Int =>XSDDatatype.XSDinteger
    case b:Boolean =>XSDDatatype.XSDboolean
    case dt:DateTime => XSDDatatype.XSDdateTime
  }
  val langTag=None
}

class TypedLiteral(anyValue:Any,dtype:Iri,lang:Option[String]) extends Literal{
  override val value=anyValue
  override val datatype=dtype
  override val langTag=lang
}

trait Bnode extends RdfTerm {
  val id:String
  override def asBnode=this  
}

case class IdBnode(id:String) extends Bnode{
}

trait Triple {
  val subject:RdfTerm
  val predicate:Iri
  val _object:RdfTerm
  lazy val s=subject
  lazy val p=predicate
  lazy val o=_object
  def ++(tr:Triple)=Graph(this,tr)
}
object Triple {
  def apply(s:RdfTerm,p:Iri,o:RdfTerm):Triple=RdfTriple(s,p,o)
}

case class RdfTriple(subject:RdfTerm,predicate:Iri,_object:RdfTerm) extends Triple{
 
}


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



object RdfTools{
  //implicit def str2lit(s:String)=Literal(s,null,null)
  implicit def str2iri(s:String)=Iri(s)
  implicit def iri2str(iri:Iri)=iri.toString
  implicit def xsd2iri(xsd:XSDDatatype):Iri=xsd.toString
  
  object bnode{
    def apply(vali:String):Bnode=IdBnode(vali) 
  }
  
  object lit{
  def apply(s:Any)=AnyLiteral(s)
  def apply(value:Any,lang:String)=
    new TypedLiteral(value,XSDDatatype.XSDstring:Iri,Some(lang))
  def apply(value:Any,datatype:Iri)=
    new TypedLiteral(value,datatype,None)
  def apply(value:Any,datatype:Iri,lang:String)=
    new TypedLiteral(value,datatype,Some(lang))
}
  
  implicit class StringLiteral(str:String){
    def `@`(lang:String)=lit(str,lang)
    def ^^(dtype:XSDDatatype)=lit(str,dtype)
  }
  
  def `_:` (s:String)=IdBnode(s)
  
  def <(str:String)={
    Iri(str)
  }
}
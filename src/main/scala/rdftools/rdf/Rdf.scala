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
  //def triple(prop:Iri):Option[Triple]=None
  //def obj(prop:Iri):Option[RdfTerm]=None
  //def objs(prop:Iri):Iterator[RdfTerm]=Iterator.empty
  //def objString(prop:Iri):Option[String]=obj(prop).map { x => x.asLiteral.getString }

}

trait Iri extends RdfTerm{
  
  val path:String
  override def asIri=this
  def ~(prop:Iri)=SubjProp(this,prop)
  override def toString=s"$path"
}

object Iri {
  def apply(path:String)=SimpleIri(path)
}

case class SimpleIri(value:String) extends Iri{
  val path= value
  lazy val localName=value.split(Array('/','#')).last
  def +(str:String):Iri=SimpleIri(value+str)
  def asUri=Try(new URI(value))//.toOption
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
  override def toString={
    s"""\"$value\""""
  }
  def getString={  
    value.asInstanceOf[String]
  }
}

case class AnyLiteral(value:Any) extends Literal {
  import RdfTools._
  lazy val datatype:Iri=value match {
    case s:String =>XsdString
    case d:Double =>XsdDouble
    case l:Long =>XsdLong
    case i:Int =>XsdInt
    case b:Boolean =>XsdBoolean
    case dt:DateTime => XsdDateTime
  }
  val langTag=None
}

class TypedLiteral(anyValue:Any,dtype:Iri,lang:Option[String]) extends Literal{
  override val value=anyValue
  override val datatype=dtype
  override val langTag=lang
}

case class StringLiteral(stringValue:String,lang:Option[String]=None) 
  extends TypedLiteral(stringValue,XsdString,lang)

case class IntLiteral(intValue:String) 
  extends TypedLiteral(intValue,XsdInt,None)

case class LongLiteral(longValue:String) 
  extends TypedLiteral(longValue,XsdInt,None)

case class DoubleLiteral(doubleValue:String) 
  extends TypedLiteral(doubleValue,XsdDouble,None)


trait Bnode extends RdfTerm {
  val id:String
  override def asBnode=this  
  override def toString=s"_:$id"
}

case class IdBnode(id:String) extends Bnode{
}

trait Variable extends RdfTerm{
  val varName:String
  override def toString=s"?$varName"
}

case class NodeVariable(varName:String) extends Variable

object nodeVar {
  def apply(varName:String)=NodeVariable(varName)
}


trait Triple {
  val subject:RdfTerm
  val predicate:Iri
  val _object:RdfTerm
  lazy val s=subject
  lazy val p=predicate
  lazy val o=_object
  def ++(tr:Triple)=Graph(this,tr)
  override def toString=s"$s $p $o ."

}
object Triple {
  def apply(s:RdfTerm,p:Iri,o:RdfTerm):Triple=RdfTriple(s,p,o)
}

case class RdfTriple(subject:RdfTerm,predicate:Iri,_object:RdfTerm) extends Triple{
 
}

trait Quad {
  val triple:Triple
  val graph:RdfTerm
}

object Quad {
  def apply(s:RdfTerm,p:Iri,o:RdfTerm,g:RdfTerm):Quad=RdfQuad(Triple(s,p,o),g)
}

case class RdfQuad(triple:Triple,graph:RdfTerm) extends Quad

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
  //implicit def xsd2iri(xsd:XSDDatatype):Iri=xsd.toString
  implicit def prop2iri(prop:Property)=prop.iri
  implicit def clazz2iri(clazz:Class)=clazz.iri
  
  object bnode{
    def apply(valId:String):Bnode=IdBnode(valId) 
  }
  
  object lit{
    def apply(s:Any)=AnyLiteral(s)
    def apply(value:Any,lang:String)=
      new StringLiteral(value.toString,Some(lang))
    def apply(value:Any,datatype:Iri)=
      new TypedLiteral(value,datatype,None)
    def apply(value:Any,datatype:Iri,lang:String)=
      new TypedLiteral(value,datatype,Some(lang))
  }
  
  implicit class RdfTermPlus(rdft:RdfTerm) {
    /*
  def objLiteral[T](prop:Iri,fun:Literal =>T):Option[T]={
    obj(prop).map{t=>
      fun(t.asLiteral)
    }
  }
  def objString(prop:Iri):Option[String]=
    objLiteral[String](prop,getString)
    
  def getString(lit:Literal):String=lit.value.toString
  */
  }
  
  implicit class StringAsLiteral(str:String){
    def `@`(lang:String)=lit(str,lang)
    def ^^(dtype:XsdDatatype)=lit(str,dtype)
  }
  
  def `_:` (s:String)=IdBnode(s)
  
  def <(str:String)={
    Iri(str)
  }
}
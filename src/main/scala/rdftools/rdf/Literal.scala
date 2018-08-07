package rdftools.rdf

import org.joda.time.DateTime

trait Literal extends RdfTerm{
  val value:Any
  val datatype:Iri
  val langTag:Option[String]
  override def asLiteral=this
  override def toString={
    s"""\"$value\""""
  }
  override def equals(other:Any)=other match{
    case l:Literal=>
      value.equals(l.value) && datatype.equals(l.datatype)
    case _ => false
  }
  def getString={  
    value.toString
    //value.asInstanceOf[String]
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

case class IntLiteral(intValue:Int) 
  extends TypedLiteral(intValue,XsdInt,None)

case class LongLiteral(longValue:Long) 
  extends TypedLiteral(longValue,XsdInt,None)

case class DoubleLiteral(doubleValue:Double) 
  extends TypedLiteral(doubleValue,XsdDouble,None)

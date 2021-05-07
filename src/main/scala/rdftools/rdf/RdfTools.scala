/*
 * Copyright 2017 Jean-Paul Calbimonte
 *
 * SPDX-License-Identifier: MIT
 */

package rdftools.rdf

import language.implicitConversions
import rdftools.rdf.xsd._

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
    def apply(value:String)=StringLiteral(value)
    def apply(value:Int)=IntLiteral(value)
    def apply(value:Double)=DoubleLiteral(value)
    def apply(value:Long)=LongLiteral(value)
    def apply(value:Any,lang:String)=
      new StringLiteral(value.toString,Some(lang))
    def apply(value:Any,datatype:Iri)=
      new TypedLiteral(value,datatype,None)
    //def apply(value:Any,datatype:Iri,lang:String)=
      //new TypedLiteral(value,datatype,Some(lang))
  }
  
  implicit class RdfTermPlus(rdft:RdfTerm) {
    //def triple(prop:Iri):Option[Triple]=None

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
    def ^^(dtype:XsdDatatype)=dtype match {
      case XsdInt=>lit(str.toInt)
      case XsdLong=>lit(str.toLong)
      case XsdDouble=>lit(str.toDouble)
      case XsdString=>lit(str)
      case _ => lit(str,dtype)
    }
      
  }
  
  def `_:` (s:String)=IdBnode(s)
  
  def __ (s:String)=IdBnode(s)

implicit class BnodeHelper(val sc: StringContext) extends AnyVal {
  def bnode(args: Any*): Bnode = IdBnode(sc.s(args))
}

  def <(str:String)={
    Iri(str)
  }

implicit class IriHelper(val sc: StringContext) extends AnyVal {

  def iri(args:Any*)={
    
        val irs=sc.parts.reduce(_+_)
        if (irs.contains(':')){
            val pref=irs.substring(0,irs.indexOf(':'))
            println(pref)
            val pp=Prefixes.prefixDic.getOrElse(pref,"").toString
            Iri(irs.replaceFirst(pref+":",pp))
        }
        else Iri(irs)
    }
  }
}
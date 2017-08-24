package rdftools.rdf

import rdftools.rdf.RdfTools.lit

class XsdDatatype(value:String) extends Iri{
  val xsdIri="http://www.w3.org/2001/XMLSchema#"
  val path=xsdIri+value
}

object XsdDatatype {
  
  val types=Seq(
    XsdString,XsdDouble,XsdDecimal,XsdLong,
    XsdInteger,XsdBoolean,XsdDateTime) 
    .map(dt=>dt.path->dt).toMap
  
  def resolve(datatypeUri:String)={
    types.get(datatypeUri)
  }
}

object XsdString extends XsdDatatype("string")
object XsdDouble extends XsdDatatype("double")
object XsdFloat extends XsdDatatype("float")
object XsdDecimal extends XsdDatatype("decimal")
object XsdLong extends XsdDatatype("long")
object XsdInt extends XsdDatatype("int")
object XsdInteger extends XsdDatatype("integer")
object XsdByte extends XsdDatatype("byte")
object XsdShort extends XsdDatatype("short")
object XsdBoolean extends XsdDatatype("boolean"){
  val xsdTrue=lit("true",XsdBoolean)
  val xsdFalse=lit("false",XsdBoolean)
}
object XsdDateTime extends XsdDatatype("dateTime")


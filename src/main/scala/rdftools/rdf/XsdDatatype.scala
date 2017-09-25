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

//Real, Decimal and Integers
object XsdDecimal extends XsdDatatype("decimal")
object XsdInteger extends XsdDatatype("integer")
object XsdNonNegativeInteger extends XsdDatatype("nonNegativeInteger")
object XsdNonPositiveInteger extends XsdDatatype("nonPositiveInteger")
object XsdNegativeInteger extends XsdDatatype("negativeInteger")
object XsdPositiveInteger extends XsdDatatype("positiveInteger")
object XsdLong extends XsdDatatype("long")
object XsdInt extends XsdDatatype("int")
object XsdShort extends XsdDatatype("short")
object XsdByte extends XsdDatatype("byte")
object XsdUnsignedLong extends XsdDatatype("unsignedLong")
object XsdUnsignedInt extends XsdDatatype("unsignedInt")
object XsdUnsignedShort extends XsdDatatype("unsignedShort")
object XsdUnsignedByte extends XsdDatatype("unsignedByte")

//Floating point
object XsdDouble extends XsdDatatype("double")
object XsdFloat extends XsdDatatype("float")

// Strings
object XsdString extends XsdDatatype("string")
object XsdNormalizedString extends XsdDatatype("normalizedString")
object XsdToken extends XsdDatatype("token")
object XsdLanguage extends XsdDatatype("language")
object XsdName extends XsdDatatype("Name")
object XsdNCName extends XsdDatatype("NCName")
object XsdNMToken extends XsdDatatype("NMTOKEN")

// boolean
object XsdBoolean extends XsdDatatype("boolean"){
  val xsdTrue=lit("true",XsdBoolean)
  val xsdFalse=lit("false",XsdBoolean)
}

//binary data
object XsdHexBinary extends XsdDatatype("hexBinary")
object XsdBase64Binary extends XsdDatatype("base64Binary")

object XsdAnyUri extends XsdDatatype("anyUri")


object XsdDateTime extends XsdDatatype("dateTime")
object XsdDateTimeStamp extends XsdDatatype("dateTimeStamp")



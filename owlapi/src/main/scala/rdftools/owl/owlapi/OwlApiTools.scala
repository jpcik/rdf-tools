package rdftools.owl.owlapi

import language.implicitConversions
import org.semanticweb.owlapi.apibinding.OWLManager
import rdftools.rdf._
import rdftools.rdf.RdfTools._
import org.semanticweb.owlapi.model._
import org.semanticweb.owlapi.util.DefaultPrefixManager
import org.semanticweb.owlapi.vocab.OWLFacet

object OwlApiTools {
  implicit val om=OWLManager.createOWLOntologyManager()
  implicit val f=om.getOWLDataFactory
  def createPrefixManager=new DefaultPrefixManager

  implicit def iri2iri(iri:Iri)=IRI.create(iri.path)
  implicit def bnode2Anon(bnode:Bnode)=f.getOWLAnonymousIndividual(bnode.id)
  implicit def lit2Literal(lit:Literal)=lit.value match {
    case i:Int=>f.getOWLLiteral(i)
    case d:Double=>f.getOWLLiteral(d)
    case s:String=>f.getOWLLiteral(s)
    case b:Boolean=>f.getOWLLiteral(b)
  }
  implicit def clazz2OwlCLass(c:Class)=f.getOWLClass(c.iri)
  implicit def iri2Range(iri:Iri)=f.getOWLDatatype(iri)
  
  def createOntology(iri:Iri)=om.createOntology(iri)
  
  implicit class OwlStringContext(sc:StringContext)(implicit prefixes:PrefixManager) {
    def i(args:Any*)=OwlApiTools.individual(sc.parts.mkString)
    def ind(args:Any*)=i(args) 
    def c(args:Any*)=OwlApiTools.Class(sc.parts.mkString)
    def op(args:Any*)=OwlApiTools.objectProperty(sc.parts.mkString)
    def dp(args:Any*)=OwlApiTools.dataProperty(sc.parts.mkString)
    def ap(args:Any*)=OwlApiTools.annotationProperty(sc.parts.mkString)
  }
  
  def individual(iri:Iri)=f.getOWLNamedIndividual(iri)
  def individual(bn:Bnode)=f.getOWLAnonymousIndividual(bn.id)
  def individual(name:String)(implicit prefixes:PrefixManager)={
    f.getOWLNamedIndividual(name, prefixes)
  }
  def clazz(iri:Iri)=f.getOWLClass(iri)
  def Class(shortIri:String)(implicit prefixes:PrefixManager)={
    f.getOWLClass(shortIri, prefixes)  
  }
  def Datatype(shortIri:String)(implicit prefixes:PrefixManager)={
    f.getOWLDatatype(shortIri, prefixes)
  }
  def NamedIndividual(shortIri:String)(implicit prefixes:PrefixManager)=
    f.getOWLNamedIndividual(shortIri,prefixes)
  
  def objectProperty(iri:Iri)=f.getOWLObjectProperty(iri)
  def objectProperty(name:String)(implicit prefixes:PrefixManager)=
    f.getOWLObjectProperty(name, prefixes)
  def dataProperty(iri:Iri)=f.getOWLDataProperty(iri)
  def dataProperty(name:String)(implicit prefixes:PrefixManager)={
    f.getOWLDataProperty(name, prefixes)
  }
  def annotationProperty(iri:Iri)=f.getOWLAnnotationProperty(iri)
  def annotationProperty(name:String)(implicit prefixes:PrefixManager)=
    f.getOWLAnnotationProperty(name, prefixes)
    
   object Facets{
    def transform(a:Any,facet:OWLFacet)=a match {
      case i:Int=>f.getOWLFacetRestriction(facet, i)
      case d:Double=>f.getOWLFacetRestriction(facet, d)
      case fl:Float=>f.getOWLFacetRestriction(facet, fl)
      case l:OWLLiteral=>f.getOWLFacetRestriction(facet, l)
      case tl:TypedLiteral=>tl.datatype match {
        case XsdInteger=>f.getOWLFacetRestriction(facet,tl.value.toString.toInt)
        case XsdDouble=>f.getOWLFacetRestriction(facet,tl.value.toString.toDouble)
        case XsdFloat=>f.getOWLFacetRestriction(facet,tl.value.toString.toFloat)
      }            
    }
    def length(a:Any)=transform(a,OWLFacet.LENGTH)
    def minLength(a:Any)=transform(a,OWLFacet.MIN_LENGTH)
    def maxLength(a:Any)=transform(a,OWLFacet.MAX_LENGTH)
    def pattern(s:String)=transform(lit(s),OWLFacet.PATTERN)
    def langRange(s:String)=transform(lit(s),OWLFacet.LANG_RANGE)
    def <= (a:Any)=transform(a,OWLFacet.MIN_INCLUSIVE)
    def < (a:Any)=transform(a,OWLFacet.MIN_EXCLUSIVE)
    def >= (a:Any)=transform(a,OWLFacet.MAX_INCLUSIVE)
    def > (a:Any)=transform(a,OWLFacet.MAX_EXCLUSIVE)
       
  }
 
}
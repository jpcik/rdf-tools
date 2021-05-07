package rdftools.owl.owlapi

import language.implicitConversions
import org.semanticweb.owlapi.apibinding.OWLManager
import rdftools.rdf._
import rdftools.rdf.xsd._
import org.semanticweb.owlapi.model._
import rdftools.rdf.vocab.RDFS
import rdftools.rdf.vocab.OWL
import org.semanticweb.owlapi.vocab.OWLFacet
import collection.JavaConverters._

object Manchester {
  
  import OwlApiTools._
  import rdftools.rdf.RdfTools._
  
  def Ann(iri:Iri,value:Literal)=f.getOWLAnnotation(annotationProperty(iri), value)
  implicit def pair2Ann(cop:(Property,Literal))=Ann(cop._1,cop._2)
  
  trait PropAssertion {
    def :: (pa:PropAssertion)= List(this,pa)
  }
  case class ObjPropAssertion(prop:OWLObjectProperty,ind:OWLIndividual,annot:Seq[OWLAnnotation]=Seq.empty) extends PropAssertion{
    def annotations (as:OWLAnnotation*)=ObjPropAssertion(prop,ind,as)
    def annotations (annprop:Property,value:String)=ObjPropAssertion(prop,ind,Seq((annprop,lit(value))))
    
  }
  case class NegObjPropAssertion(prop:OWLObjectProperty,ind:OWLIndividual,annot:Seq[OWLAnnotation]=Seq.empty) extends PropAssertion
  case class DataPropAssertion(prop:OWLDataProperty,ind:OWLLiteral,annot:Seq[OWLAnnotation]=Seq.empty) extends PropAssertion{
  }
  trait PropertyCharacteristic
  
  object Functional extends PropertyCharacteristic
  object InverseFunctional extends PropertyCharacteristic
  object Reflexive extends PropertyCharacteristic
  object Irreflexive extends PropertyCharacteristic
  object Symmetric extends PropertyCharacteristic
  object Asymmetric extends PropertyCharacteristic
  object Transitive extends PropertyCharacteristic
  
  class ObjectPropertyFrame(op:OWLObjectProperty)
    (Characteristics:Seq[PropertyCharacteristic])
    (Domain:Seq[OWLClassExpression])
    (Range:Seq[OWLClassExpression]) {
    val domainAxioms=Domain map {domain=>
     
      f.getOWLObjectPropertyDomainAxiom(op, domain)
    }
    val characteristicAxioms:Seq[OWLObjectPropertyCharacteristicAxiom]=Characteristics map {
      case Functional=>f.getOWLFunctionalObjectPropertyAxiom(op)
      case InverseFunctional=>f.getOWLInverseFunctionalObjectPropertyAxiom(op)
      case Reflexive=>f.getOWLReflexiveObjectPropertyAxiom(op)
      case Irreflexive=>f.getOWLIrreflexiveObjectPropertyAxiom(op)
      case Symmetric=>f.getOWLSymmetricObjectPropertyAxiom(op)
      case Asymmetric=>f.getOWLAsymmetricObjectPropertyAxiom(op)
      case Transitive=>f.getOWLTransitiveObjectPropertyAxiom(op)     
    }
    if (Characteristics.contains(Functional))
      f.getOWLFunctionalObjectPropertyAxiom(op)
  }
  
  trait Annotated[+T] {
    val o:T
    val a:Set[OWLAnnotation]
    //def :: (ann:Annotated[T]):List[Annotated[T]]=List(this,ann)
    def annot(as:OWLAnnotation*):Annotated[T]  
def annotations(as:OWLAnnotation*):Annotated[T]=annot(as:_*)
    def annotations(prop:Property,value:String):Annotated[T]=annotations ((prop,lit("value")))
  }
  
  
  
  class AnnotatedClassExpression(val o:OWLClassExpression,val a:Set[OWLAnnotation]) extends Annotated[OWLClassExpression]{
    def :: (ann:AnnotatedClassExpression):List[AnnotatedClassExpression]=List(this,ann)
    def annot(as:OWLAnnotation*)=new AnnotatedClassExpression(o,as.toSet)   
   
   // def annotations(as:OWLAnnotation*):Annotated[OWLClassExpression]=annot(as:_*)
   // def annotations(prop:Property,value:String):Annotated[OWLClassExpression]=annotations ((prop,lit("value")))
  }
  implicit def owlclass2annotated(o:OWLClassExpression)=
    new AnnotatedClassExpression(o,Set.empty)
  implicit def owlind2annotated(o:OWLIndividual)=
    new AnnotatedIndividual(o,Set.empty)
  
  implicit def annot2List(a:AnnotatedClassExpression)=List(a)
  
  class AnnotatedIndividual(val o:OWLIndividual,val a:Set[OWLAnnotation]) extends Annotated[OWLIndividual]{
    def :: (ann:AnnotatedIndividual):List[AnnotatedIndividual]=List(this,ann)
    def annot(as:OWLAnnotation*)=new AnnotatedIndividual(o,as.toSet)   

  }
 
  class IndividualFrame(ind:OWLIndividual,
    types:Seq[AnnotatedClassExpression],
    facts:Seq[PropAssertion],
    sameAs:Seq[AnnotatedIndividual],
    differentFrom:Seq[AnnotatedIndividual],
    annotations:Seq[OWLAnnotation] ) {
  }
  
  object Individual {
    def apply(iri:Iri,
      Types:Seq[AnnotatedClassExpression],
      Facts:Seq[PropAssertion]=Seq.empty,
      SameAs:Seq[AnnotatedIndividual]=Seq.empty,
      DifferentFrom:Seq[AnnotatedIndividual]=Seq.empty)=
        new IndividualFrame(f.getOWLNamedIndividual(iri),Types,Facts,SameAs,DifferentFrom,Seq.empty)
  }
  
  case class ObjectProperty(op:OWLObjectProperty)
      (Domain:OWLClassExpression*)
      (Range:OWLClassExpression*)
  
  class ClassFrame(cls:OWLClass,
      subClassOf:Seq[AnnotatedClassExpression],
      equivalentTo:Seq[AnnotatedClassExpression],
      disjointWith:Seq[AnnotatedClassExpression],
      disjointUnionOf: (Seq[OWLAnnotation],Seq[OWLClassExpression]),
      hasKey: (Seq[OWLAnnotation],Seq[OWLProperty]),
      annotations:Seq[OWLAnnotation]) {
  }
  
  object Class {
    def apply(iri:Iri,
      SubClassOf:Seq[AnnotatedClassExpression],
      EquivalentTo:Seq[AnnotatedClassExpression]=Seq.empty,
      DisjointWith:Seq[AnnotatedClassExpression]=Seq.empty,
      DisjointUnionOf: (Seq[OWLAnnotation],Seq[OWLClassExpression])=(Seq.empty,Seq.empty),
      HasKey: (Seq[OWLAnnotation],Seq[OWLProperty])=(Seq.empty,Seq.empty),
      Annotations:Seq[OWLAnnotation]=Seq.empty)=
        new ClassFrame(f.getOWLClass(iri),SubClassOf,EquivalentTo,DisjointWith,DisjointUnionOf,HasKey,Annotations)
  }
  
  implicit class ClsXpr(r:OWLClassExpression) {
    def and(rest:OWLClassExpression)={
      f.getOWLObjectIntersectionOf(r,rest)
    }
    //def :: (cl:OWLClassExpression)=List(r,cl)

  }

  implicit class RdfClassExp(c:rdftools.rdf.Class){
    def that(ax:OWLClassExpression)= 
      f.getOWLObjectIntersectionOf(c,ax)
  }
  
  implicit class ClassExp(c:OWLClass){
    def that(ax:OWLClassExpression)= 
      f.getOWLObjectIntersectionOf(c,ax)
    def ⊑ (ce:OWLClassExpression)=
      f.getOWLSubClassOfAxiom(c, ce)
  }


  
  // owl:Thing that hasFirstName exactly 1 and hasFirstName only string[minLength 1]
 
  // thing.that(hasFirstName.exactly(1))
  implicit def list2Oneof(inds:Set[OWLNamedIndividual]):OWLClassExpression=f.getOWLObjectOneOf(inds.asJava)
  
  implicit class ObjProp(op:OWLObjectProperty){
    def some(cls:OWLClassExpression)= f.getOWLObjectSomeValuesFrom(op, cls)
    def only(cls:OWLClassExpression)= f.getOWLObjectAllValuesFrom(op, cls)
    def value(ind:OWLIndividual)= f.getOWLObjectHasValue(op, ind)
    def min(i:Int)= f.getOWLObjectMinCardinality(i, op)
    def min(i:Int,cls:OWLClassExpression)= f.getOWLObjectMinCardinality(i, op, cls)
    def exactly(i:Int)= f.getOWLObjectExactCardinality(i, op)
    def exactly(i:Int,cls:OWLClassExpression)= f.getOWLObjectExactCardinality(i, op,cls)
    def max(i:Int)= f.getOWLObjectMaxCardinality(i, op)
    def max(i:Int,cls:OWLClassExpression)= f.getOWLObjectMaxCardinality(i, op, cls)
    def Self= f.getOWLObjectHasSelf(op)
    def apply(ind:OWLIndividual)=ObjPropAssertion(op,ind)
  }
  
  object not {
    def apply(restr:OWLClassExpression)={
      f.getOWLObjectComplementOf(restr)
    }
    def apply(dt:XsdDatatype)=
      f.getOWLDataComplementOf(f.getOWLDatatype(dt.asIri))
    def apply(opAss:ObjPropAssertion)={
      NegObjPropAssertion(opAss.prop,opAss.ind)
    }
  }
  
  implicit class DataProp(d:OWLDataProperty){
    def some(dt:OWLDataRange)= f.getOWLDataSomeValuesFrom(d, dt)
    def only(dt:OWLDataRange)= f.getOWLDataAllValuesFrom(d, dt)
    def value(value:OWLLiteral)= f.getOWLDataHasValue(d, value)
    def min(i:Int)= f.getOWLDataMinCardinality(i, d)
    def min(i:Int,range:OWLDataRange)= f.getOWLDataMinCardinality(i, d,range)
    def max(i:Int)= f.getOWLDataMaxCardinality(i, d)
    def max(i:Int,range:OWLDataRange)= f.getOWLDataMaxCardinality(i, d,range)  
    def exactly(i:Int)= f.getOWLDataExactCardinality(i, d)
    def exactly(i:Int,range:OWLDataRange)= f.getOWLDataExactCardinality(i, d,range)
    def apply(ind:OWLLiteral)=DataPropAssertion(d,ind)
  }
  
  implicit class DataRestr(r:OWLDataRestriction){
    def and (rest:OWLDataRestriction)={
      f.getOWLObjectIntersectionOf(r,rest)
    }
    def or (rest:OWLDataRestriction)=
      f.getOWLObjectUnionOf(r,rest)
  }

  implicit class Restr(r:OWLObjectRestriction){
    def and (rest:OWLObjectRestriction)={
      f.getOWLObjectIntersectionOf(r,rest)
    }
     def or (rest:OWLObjectRestriction)=
      f.getOWLObjectUnionOf(r,rest)
  }

  
  implicit class DtFacetRestr(dt:XsdDatatype) {
    def apply(facets:OWLFacetRestriction*)= ???
  }
  
  
  implicit val prefixes=createPrefixManager
  val hasFirstName=dp":hasFirstName"
  val hasAge=dp":hasFirstName"
  val hasFriend=op":hasFriend"
  val hasGender=op":hasGender"
  val nn=not (hasFirstName exactly 1)
  val female=i"female"
  val male=i"male"

  clazz("topo") ⊑ clazz("pipo")
 
  clazz("moto") that 
        (hasFriend only clazz("") ) and 
        {hasFriend some clazz("") } and 
        not (hasFirstName exactly 1) 
  
  hasFriend only clazz("")   and (hasFriend only clazz("") ) 
  val ind=f.getOWLNamedIndividual(Iri("indi"))
  //val ll= 
  //::  (hasFirstName exactly 1 annot null) ::  :: Nil
  val indi=Individual ("iri",
      Types=  hasFirstName exactly 1 annot null  ,
      Facts=Seq())                                                                                                                                                                                                                                                                                                                                                                                                                                                               
  
  val Person=clazz("a:Person") 
  
  
  val top= (OWL.Thing:OWLClass) that (hasFirstName exactly 1)   
  (hasFriend (ind)) annotations (RDFS.comment,"comments here")
  import OwlApiTools.Facets._
  Class ("Person",
    SubClassOf = Seq( 
        OWL.Thing that { hasFirstName exactly 1 } and 
                       { hasFirstName only XsdString(minLength(1))} ,
        hasAge exactly 1 and { hasAge only not(XsdInt) },
        hasGender exactly 1 and { hasGender only Set(male,female) } ),
    EquivalentTo= Seq(c"g:People")
  )
  
  Individual( "John",
     Types=  Person  ::
             ((hasFirstName value lit("John")) or (hasFirstName value "Jack"^^XsdString) )  ,
     Facts=  ((hasFriend (ind)) annotations (RDFS.comment,"paparapasta")) :: 
             (not (hasFriend (ind)) ) ,
     SameAs= (ind annot null) :: ind )
  
  ObjectProperty(hasFriend) (
      Domain=clazz(""),clazz("") ) (
      Range= hasFirstName exactly 1)
     /* 
  object Class {
    def apply(iri:Iri)=new ClassMan(iri,Set.empty,Set.empty)
  }*/
  
   // def clazz(iri:Iri)=f.getOWLClass(iri)

  
  def cosas ={
   import rdftools.rdf.RdfTools._

   
  }
  
}
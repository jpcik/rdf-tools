package rdftools.owl.owlapi

import language.implicitConversions
import collection.JavaConverters._
import org.semanticweb.owlapi.model._
import rdftools.rdf._
import org.semanticweb.owlapi.vocab.OWLFacet
import org.semanticweb.owlapi.util.DefaultPrefixManager

object Functional {
  type Annotations=Set[OWLAnnotation]
  val øA = Set.empty[OWLAnnotation]
  
  import OwlApiTools._
  
  def Declaration(entity:OWLEntity)=f.getOWLDeclarationAxiom(entity)
  
  def Imports(iri:Iri)=f.getOWLImportsDeclaration(iri)
  
  // classes
  def SubClassOf(subClass:OWLClassExpression,superClass:OWLClassExpression,annotations:Annotations=øA)=
    f.getOWLSubClassOfAxiom(subClass, superClass,annotations.asJava)
  def EquivalentClasses(class1:OWLClassExpression,class2:OWLClassExpression,annotations:Set[OWLAnnotation])=
    f.getOWLEquivalentClassesAxiom(class1, class2,annotations.asJava)
  def EquivalentClasses(classes:Set[OWLClassExpression],annotations:Annotations=øA)=
    f.getOWLEquivalentClassesAxiom(classes.asJava,annotations.asJava)
  def EquivalentClasses(classes:OWLClassExpression*)=
    f.getOWLEquivalentClassesAxiom(classes.toSet.asJava)
  def DisjointClasses(classes:Set[OWLClassExpression],annotations:Annotations=øA)=
    f.getOWLDisjointClassesAxiom(classes.asJava, annotations.asJava)
  def DisjointClasses(classes:OWLClassExpression*)=
    f.getOWLDisjointClassesAxiom(classes:_*)
  def DisjointUnion(cls:OWLClass,classes:Set[OWLClassExpression],annotations:Set[OWLAnnotation]=Set.empty)=
    f.getOWLDisjointUnionAxiom(cls,classes.asJava, annotations.asJava)
  def DisjointUnion(cls:OWLClass,classes:OWLClassExpression*)=
    f.getOWLDisjointUnionAxiom(cls,classes.toSet.asJava)
  def HasKey(cls:OWLClassExpression,props:Set[OWLPropertyExpression],annotations:Set[OWLAnnotation]=Set.empty)=
    f.getOWLHasKeyAxiom(cls, props.asJava,annotations.asJava)
    
  //annotations
  def AnnotationAssertion(prop:OWLAnnotationProperty,subj:OWLAnnotationSubject,
      value:OWLAnnotationValue, annotations:Set[OWLAnnotation]=Set.empty)=
    f.getOWLAnnotationAssertionAxiom(prop, subj, value,annotations.asJava)
  def AnnotationAssertion(subj:OWLAnnotationSubject,
      annotation:OWLAnnotation, annotations:Set[OWLAnnotation])=
    f.getOWLAnnotationAssertionAxiom(subj,annotation,annotations.asJava)
  def AnnotationAssertion(subj:OWLAnnotationSubject,
      annotation:OWLAnnotation)=
    f.getOWLAnnotationAssertionAxiom(subj,annotation)
     
  //datatypes
  def DatatypeDefinition(dtype:OWLDatatype,range:OWLDataRange,annotations:Set[OWLAnnotation]=Set.empty)=
    f.getOWLDatatypeDefinitionAxiom(dtype,range,annotations.asJava)
  
  //object properties
  def ObjectPropertyDomain(prop:OWLObjectPropertyExpression,cls:OWLClassExpression,annotations:Set[OWLAnnotation]=Set.empty)=
    f.getOWLObjectPropertyDomainAxiom(prop, cls,annotations.asJava)
  def ObjectPropertyRange(prop:OWLObjectPropertyExpression,cls:OWLClassExpression,annotations:Set[OWLAnnotation]=Set.empty)=
    f.getOWLObjectPropertyRangeAxiom(prop, cls,annotations.asJava)
  def ObjectFunctionalProperty(prop:OWLObjectPropertyExpression,annotations:Set[OWLAnnotation]=Set.empty)=
    f.getOWLFunctionalObjectPropertyAxiom(prop,annotations.asJava)
  def ObjectInverseFunctionalProperty(prop:OWLObjectPropertyExpression,annotations:Set[OWLAnnotation]=Set.empty)=
    f.getOWLInverseFunctionalObjectPropertyAxiom(prop,annotations.asJava)  
  def ObjectReflexiveProperty(prop:OWLObjectPropertyExpression,annotations:Set[OWLAnnotation]=Set.empty)=
    f.getOWLReflexiveObjectPropertyAxiom(prop,annotations.asJava)
  def ObjectIrreflexiveProperty(prop:OWLObjectPropertyExpression,annotations:Set[OWLAnnotation]=Set.empty)=
    f.getOWLIrreflexiveObjectPropertyAxiom(prop,annotations.asJava)
  def ObjectSymmetricProperty(prop:OWLObjectPropertyExpression,annotations:Set[OWLAnnotation]=Set.empty)=
    f.getOWLSymmetricObjectPropertyAxiom(prop,annotations.asJava)
  def ObjectAsymmetricProperty(prop:OWLObjectPropertyExpression,annotations:Set[OWLAnnotation]=Set.empty)=
    f.getOWLAsymmetricObjectPropertyAxiom(prop,annotations.asJava)
  def ObjectTransitiveProperty(prop:OWLObjectPropertyExpression,annotations:Set[OWLAnnotation]=Set.empty)=
    f.getOWLTransitiveObjectPropertyAxiom(prop,annotations.asJava)
  def SubObjectPropertyOf(subProp:OWLObjectPropertyExpression,superProp:OWLObjectPropertyExpression,annotations:Set[OWLAnnotation]=Set.empty)=
    f.getOWLSubObjectPropertyOfAxiom(subProp, superProp,annotations.asJava)
  def EquivalentObjectProperties(props:Set[OWLObjectPropertyExpression],annotations:Set[OWLAnnotation]=Set.empty)=
    f.getOWLEquivalentObjectPropertiesAxiom(props.asJava,annotations.asJava)
  def EquivalentObjectProperties(prop1:OWLObjectPropertyExpression,prop2:OWLObjectPropertyExpression,annotations:Set[OWLAnnotation])=
    f.getOWLEquivalentObjectPropertiesAxiom(prop1,prop2,annotations.asJava)  
  def EquivalentObjectProperties(prop1:OWLObjectPropertyExpression,prop2:OWLObjectPropertyExpression)=
    f.getOWLEquivalentObjectPropertiesAxiom(prop1,prop2)  
  def DisjointObjectProperties(props:Set[OWLObjectPropertyExpression],annotations:Set[OWLAnnotation]=Set.empty)=
    f.getOWLDisjointObjectPropertiesAxiom(props.asJava,annotations.asJava)
  def InverseObjectProperties(prop1:OWLObjectPropertyExpression,prop2:OWLObjectPropertyExpression,annotations:Set[OWLAnnotation]=Set.empty)=
    f.getOWLInverseObjectPropertiesAxiom(prop1,prop2,annotations.asJava)

  //data properties
  def DataPropertyDomain(prop:OWLDataPropertyExpression,cls:OWLClassExpression,annotations:Set[OWLAnnotation]=Set.empty)=
    f.getOWLDataPropertyDomainAxiom(prop, cls,annotations.asJava)
  def DataPropertyRange(prop:OWLDataPropertyExpression,range:OWLDataRange,annotations:Set[OWLAnnotation]=Set.empty)=
    f.getOWLDataPropertyRangeAxiom(prop, range,annotations.asJava)
  def FunctionalDataProperty(prop:OWLDataPropertyExpression,annotations:Set[OWLAnnotation]=Set.empty)=
    f.getOWLFunctionalDataPropertyAxiom(prop,annotations.asJava)
  def SubDataPropertyOf(subProp:OWLDataPropertyExpression, superProp:OWLDataPropertyExpression,annotations:Set[OWLAnnotation]=Set.empty)=
    f.getOWLSubDataPropertyOfAxiom(subProp,superProp,annotations.asJava)
  def EquivalentDataProperties(prop1:OWLDataPropertyExpression,prop2:OWLDataPropertyExpression,annotations:Set[OWLAnnotation])=
    f.getOWLEquivalentDataPropertiesAxiom(prop1,prop2,annotations.asJava)
  def EquivalentDataProperties(prop1:OWLDataPropertyExpression,prop2:OWLDataPropertyExpression)=
    f.getOWLEquivalentDataPropertiesAxiom(prop1,prop2)
  def EquivalentDataProperties(props:Set[OWLDataPropertyExpression],annotations:Set[OWLAnnotation]=Set.empty)=
    f.getOWLEquivalentDataPropertiesAxiom(props.asJava,annotations.asJava)
  def DisjointDataProperties(props:Set[OWLDataPropertyExpression],annotations:Set[OWLAnnotation]=Set.empty)=
    f.getOWLDisjointDataPropertiesAxiom(props.asJava,annotations.asJava)
  
  // annotation properties
  def AnnotationPropertyDomain(prop:OWLAnnotationProperty,iri:Iri,annotations:Set[OWLAnnotation]=Set.empty)=
    f.getOWLAnnotationPropertyDomainAxiom(prop, iri,annotations.asJava)
  def AnnotationPropertyRange(prop:OWLAnnotationProperty,iri:Iri,annotations:Set[OWLAnnotation]=Set.empty)=
    f.getOWLAnnotationPropertyRangeAxiom(prop, iri,annotations.asJava)
  def SubAnnotationPropertyOf(subProp:OWLAnnotationProperty,superProp:OWLAnnotationProperty,annotations:Set[OWLAnnotation]=Set.empty)=
    f.getOWLSubAnnotationPropertyOfAxiom(subProp, superProp, annotations.asJava)
    
  // individuals
  def ClassAssertion(cls:OWLClassExpression,ind:OWLIndividual,annotations:Set[OWLAnnotation]=Set.empty)=
    f.getOWLClassAssertionAxiom(cls, ind,annotations.asJava)
  def ObjectPropertyAssertion(prop:OWLObjectPropertyExpression,subj:OWLIndividual,obj:OWLIndividual,annotations:Set[OWLAnnotation]=Set.empty)=
    f.getOWLObjectPropertyAssertionAxiom(prop, subj, obj,annotations.asJava)
  def NegativeObjectPropertyAssertion(prop:OWLObjectPropertyExpression,subj:OWLIndividual,obj:OWLIndividual,annotations:Set[OWLAnnotation]=Set.empty)=
    f.getOWLNegativeObjectPropertyAssertionAxiom(prop, subj, obj,annotations.asJava)
  def DataPropertyAssertion(prop:OWLDataPropertyExpression,subj:OWLIndividual,obj:OWLLiteral,annotations:Set[OWLAnnotation]=Set.empty)=
    f.getOWLDataPropertyAssertionAxiom(prop, subj, obj,annotations.asJava)
  def NegativeDataPropertyAssertion(prop:OWLDataPropertyExpression,subj:OWLIndividual,obj:OWLLiteral,annotations:Set[OWLAnnotation]=Set.empty)=
    f.getOWLNegativeDataPropertyAssertionAxiom(prop, subj, obj,annotations.asJava)
  def SameIndividual(inds:Set[OWLIndividual],annotations:Annotations=øA)=
    f.getOWLSameIndividualAxiom(inds.asJava,annotations.asJava)
  def SameIndividual(inds:OWLIndividual*)=
    f.getOWLSameIndividualAxiom(inds.toSet.asJava)
  def DifferentIndividuals(inds:Set[OWLIndividual],annotations:Annotations=øA)=
    f.getOWLDifferentIndividualsAxiom(inds.asJava,annotations.asJava)
  def DifferentIndividuals(inds:OWLIndividual*)=
    f.getOWLDifferentIndividualsAxiom(inds.toSet.asJava)
    
  // datatype restrictions
  def DatatypeRestriction(dtype:OWLDatatype,facet:OWLFacet,lit:OWLLiteral)=
    f.getOWLDatatypeRestriction(dtype, facet, lit)
  def DatatypeRestriction(dtype:OWLDatatype,facets:Set[OWLFacetRestriction])=
    f.getOWLDatatypeRestriction(dtype, facets.asJava)
  def DataOneOf(literals:Set[OWLLiteral])=
    f.getOWLDataOneOf(literals.asJava)
  def DataOneOf(literals:OWLLiteral*)=
    f.getOWLDataOneOf(literals:_*)
  def DataComplementOf(range:OWLDataRange)=
    f.getOWLDataComplementOf(range)
  def DataIntersectionOf(ranges:Set[OWLDataRange])=
    f.getOWLDataIntersectionOf(ranges.asJava)
  def DataIntersectionOf(ranges:OWLDataRange*)=
    f.getOWLDataIntersectionOf(ranges:_*)
  def DataUnionOf(ranges:Set[OWLDataRange])=
    f.getOWLDataUnionOf(ranges.asJava)
  def DataUnionOf(ranges:OWLDataRange*)=
    f.getOWLDataUnionOf(ranges:_*)
    
  // object restrictions
  def InverseObjectProperty(prop1:OWLObjectPropertyExpression,prop2:OWLObjectPropertyExpression,annotations:Annotations=øA)=
    f.getOWLInverseObjectPropertiesAxiom(prop1, prop2,annotations.asJava)
  def ObjectOneOf(inds:Set[OWLIndividual])=
    f.getOWLObjectOneOf(inds.asJava)
  def ObjectOneOf(inds:OWLIndividual*)=
    f.getOWLObjectOneOf(inds.toSet.asJava)
  def ObjectSomeValuesFrom(prop:OWLObjectPropertyExpression,cls:OWLClassExpression)=
    f.getOWLObjectSomeValuesFrom(prop, cls)
  def ObjectAllValuesFrom(prop:OWLObjectPropertyExpression,cls:OWLClassExpression)=
    f.getOWLObjectAllValuesFrom(prop, cls)
  def ObjectHasValue(prop:OWLObjectPropertyExpression,ind:OWLIndividual)=
    f.getOWLObjectHasValue(prop, ind)
  def ObjectMinCardinality(i:Int,prop:OWLObjectPropertyExpression)=
    f.getOWLObjectMinCardinality(i, prop)
  def ObjectMinCardinality(i:Int,prop:OWLObjectPropertyExpression,cls:OWLClassExpression)=
    f.getOWLObjectMinCardinality(i, prop,cls)
  def ObjectMaxCardinality(i:Int,prop:OWLObjectPropertyExpression)=
    f.getOWLObjectMaxCardinality(i, prop)
  def ObjectMaxCardinality(i:Int,prop:OWLObjectPropertyExpression,cls:OWLClassExpression)=
    f.getOWLObjectMaxCardinality(i, prop,cls)
  def ObjectExactCardinality(i:Int,prop:OWLObjectPropertyExpression)=
    f.getOWLObjectExactCardinality(i, prop)
  def ObjectExactCardinality(i:Int,prop:OWLObjectPropertyExpression,cls:OWLClassExpression)=
    f.getOWLObjectExactCardinality(i, prop,cls)
  def ObjectHasSelf(prop:OWLObjectPropertyExpression)=
    f.getOWLObjectHasSelf(prop)
    
  def ObjectInverseOf(prop:OWLObjectPropertyExpression)=
    f.getOWLObjectInverseOf(prop)
    
  def DataSomeValuesFrom(prop:OWLDataPropertyExpression,range:OWLDataRange)=
    f.getOWLDataSomeValuesFrom(prop, range)
  def DataAllValuesFrom(prop:OWLDataPropertyExpression,range:OWLDataRange)=
    f.getOWLDataAllValuesFrom(prop, range)
  def DataHasValue(prop:OWLDataPropertyExpression,value:OWLLiteral)=
    f.getOWLDataHasValue(prop, value)
  def DataMinCardinality(i:Int,prop:OWLDataPropertyExpression,range:OWLDataRange)=
    f.getOWLDataMinCardinality(i, prop,range)
  def DataMinCardinality(i:Int,prop:OWLDataPropertyExpression)=
    f.getOWLDataMinCardinality(i, prop)
  def DataMaxCardinality(i:Int,prop:OWLDataPropertyExpression,range:OWLDataRange)=
    f.getOWLDataMaxCardinality(i, prop,range)
  def DataMaxCardinality(i:Int,prop:OWLDataPropertyExpression)=
    f.getOWLDataMaxCardinality(i, prop)  
  def DataExactCardinality(i:Int,prop:OWLDataPropertyExpression,range:OWLDataRange)=
    f.getOWLDataExactCardinality(i, prop,range)
  def DataExactCardinality(i:Int,prop:OWLDataPropertyExpression)=
    f.getOWLDataExactCardinality(i, prop)  
    
  def ObjectComplementOf(cls:OWLClassExpression)=
    f.getOWLObjectComplementOf(cls)
  def ObjectIntersectionOf(cls:Set[OWLClassExpression])=
    f.getOWLObjectIntersectionOf(cls.asJava)
  def ObjectIntersectionOf(cls:OWLClassExpression*)=
    f.getOWLObjectIntersectionOf(cls:_*)  
  def ObjectUnionOf(cls:Set[OWLClassExpression])=
    f.getOWLObjectUnionOf(cls.asJava)
  def ObjectUnionOf(cls:OWLClassExpression*)=
    f.getOWLObjectUnionOf(cls:_*)
  
  
  //annotations
  def Annotation(annProperty:OWLAnnotationProperty,annValue:OWLAnnotationValue)=
    f.getOWLAnnotation(annProperty, annValue)
  def Annotation(iriProp:Iri,irival:Iri):OWLAnnotation=
    Annotation(annotationProperty(iriProp), irival:IRI)
  def Annotation(iriProp:Iri,litval:Literal):OWLAnnotation=
    Annotation(annotationProperty(iriProp), litval:OWLLiteral)
    
  //ontology
  def Ontology(iri:Iri,imports:Seq[OWLImportsDeclaration]=Seq.empty,annotations:Seq[OWLAnnotation]=Seq.empty,axioms:Seq[OWLAxiom]=Seq.empty)={
    val o=createOntology(iri)
    imports.foreach{imp=>
      om.applyChange(new AddImport(o, imp))
    }
    annotations.foreach{annot=>
      om.applyChange(new AddOntologyAnnotation(o,annot))
    }
    axioms.foreach {axiom=>
      om.applyChange(new AddAxiom(o,axiom))  
    }
    o
  }
  //def ontology(iri:Iri,imports:Seq[OWLImportsDeclaration]=Seq.empty):OWLOntology=
  //  ontology(iri,imports,Seq.empty,Seq.empty)
  def Ontology(iri:Iri,axioms:Seq[OWLAxiom]):OWLOntology={
    Ontology(iri,Seq.empty,Seq.empty,axioms)
  }

  
  implicit class ImportPlus(imports:OWLImportsDeclaration) {
  //  def :: (imp2:OWLImportsDeclaration)=List(imports,imp2)
  }
  
  implicit def ImportsToList(imports:OWLImportsDeclaration)=List(imports)
  implicit def AnnotationToList(annot:OWLAnnotation)=
    List(annot)
 
  implicit def AxiomToList(axiom:OWLAxiom)=List(axiom)  
  
  def Prefix(pref:String,iri:String)(implicit prefixes:PrefixManager)=
    prefixes.setPrefix(pref, iri)
    
  
  implicit class OntologyPlus(o:OWLOntology) {
    def individuals=o.getIndividualsInSignature()
    def individuals(cls:OWLClassExpression)={
      o.getClassAssertionAxioms(cls).asScala.map {ax=>ax.getIndividual}
    }
    def classExpressions(ind:OWLIndividual)=
      o.getClassAssertionAxioms(ind).asScala.map(_.getClassExpression)
    
    def objectProperties(ind:OWLIndividual)=
      o.getObjectPropertyAssertionAxioms(ind).asScala.map(_.getProperty)
      
    def objectPropertyObjects(ind:OWLIndividual)=
      o.getObjectPropertyAssertionAxioms(ind).asScala.map{t=>
        val obj = if (t.getObject.isNamed) t.getObject.asOWLNamedIndividual
          else t.getObject.asOWLAnonymousIndividual
        (t.getProperty.asOWLObjectProperty(),obj)}
    
    def dataPropertyObjects(ind:OWLIndividual)=
      o.getDataPropertyAssertionAxioms(ind).asScala.map{a=>
        (a.getProperty.asOWLDataProperty,a.getObject)}

    def subClassesOf(clazz:OWLClass)=
      o.getSubClassAxiomsForSuperClass(clazz).asScala

    def +=(axioms:OWLAxiom*)={
      axioms.foreach{axiom=>
        o.getOWLOntologyManager().applyChange(new AddAxiom(o,axiom))
      }
    }
    
  }
}
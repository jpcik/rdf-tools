package ldnstream.streams.trowl

import rdftools.rdf.Graph
import org.semanticweb.owlapi.model.OWLOntologyFactory
import org.semanticweb.owlapi.model.OWLOntologyManager
import org.semanticweb.owlapi.apibinding.OWLManager
import org.semanticweb.owlapi.model.IRI
import collection.JavaConverters._
import org.semanticweb.owlapi.model.OWLAxiom
import language.implicitConversions
import org.semanticweb.owlapi.model.OWLDataFactory
import rdftools.rdf.Iri
import org.semanticweb.owlapi.model.OWLImportsDeclaration
import org.semanticweb.owlapi.model.OWLAnnotation
import org.semanticweb.owlapi.model.AddAxiom
import org.semanticweb.owlapi.model.AddImport
import org.semanticweb.owlapi.model.AddOntologyAnnotation
import org.semanticweb.owlapi.model.OWLAnnotationProperty
import org.semanticweb.owlapi.model.OWLAnnotationValue
import rdftools.rdf.Literal
import org.semanticweb.owlapi.model.OWLLiteral
import rdftools.rdf.vocab.RDFS
import org.semanticweb.owlapi.model.OWLEntity
import org.semanticweb.owlapi.model.OWLClassExpression
import org.semanticweb.owlapi.model.OWLClass
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom
import org.semanticweb.owlapi.model.OWLRestriction
import org.semanticweb.owlapi.model.OWLObjectProperty
import org.semanticweb.owlapi.model.OWLDatatypeRestriction
import org.semanticweb.owlapi.model.OWLObjectRestriction
import org.semanticweb.owlapi.model.OWLDataRestriction
import org.semanticweb.owlapi.model.OWLDataProperty
import org.semanticweb.owlapi.model.OWLIndividual
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom
import language.postfixOps
import org.semanticweb.owlapi.model.OWLObjectPropertyCharacteristicAxiom
import org.semanticweb.owlapi.model.OWLObject
import org.semanticweb.owlapi.model.OWLProperty
import org.semanticweb.owlapi.model.OWLPropertyExpression
import org.semanticweb.owlapi.model.OWLAnnotationSubject
import org.semanticweb.owlapi.model.OWLDataRange
import org.semanticweb.owlapi.model.OWLDatatype
import org.semanticweb.owlapi.model.OWLDataPropertyExpression
import org.semanticweb.owlapi.vocab.OWLFacet
import org.semanticweb.owlapi.model.OWLFacetRestriction
import org.semanticweb.owlapi.model.OWLOntologyIRIMapper
import org.semanticweb.owlapi.model.OWLOntology
import rdftools.rdf.Class
import org.semanticweb.owlapi.model.PrefixManager
import org.semanticweb.owlapi.util.DefaultPrefixManager
import rdftools.rdf.Bnode

object Manchester {
  
}

object OwlApi {
 
}


/*
 * Copyright 2017 Jean-Paul Calbimonte
 *
 * SPDX-License-Identifier: MIT
 */

package rdftools.rdf.vocab

import rdftools.rdf.Vocab

import rdftools.rdf._

import rdftools.rdf.RdfTools._

object OWL extends Vocab {
  override val iri: Iri = "http://www.w3.org/2002/07/owl#"
  val Thing = clazz("Thing")
  val Nothing = clazz("Nothing")
  val AllDifferent = clazz("AllDifferent")
  val AnnotationProperty = clazz("AnnotationProperty")
  val ReflexiveProperty = clazz("ReflexiveProperty")
  val AsymmetricProperty = clazz("AsymmetricProperty")
  val IrreflexiveProperty = clazz("IrreflexiveProperty")
  val Ontology = clazz("Ontology")
  val InverseFunctionalProperty = clazz("InverseFunctionalProperty")
  val Axiom = clazz("Axiom")
  val DeprecatedClass = clazz("DeprecatedClass")
  val Class = clazz("Class")
  val NegativePropertyAssertion = clazz("NegativePropertyAssertion")
  val FunctionalProperty = clazz("FunctionalProperty")
  val Restriction = clazz("Restriction")
  val TransitiveProperty = clazz("TransitiveProperty")
  val DataRange = clazz("DataRange")
  val SymmetricProperty = clazz("SymmetricProperty")
  val AllDisjointProperties = clazz("AllDisjointProperties")
  val ObjectProperty = clazz("ObjectProperty")
  val NamedIndividual = clazz("NamedIndividual")
  val DeprecatedProperty = clazz("DeprecatedProperty")
  val Annotation = clazz("Annotation")
  val DatatypeProperty = clazz("DatatypeProperty")
  val AllDisjointClasses = clazz("AllDisjointClasses")
  val OntologyProperty = clazz("OntologyProperty")
  val equivalentProperty = prop("equivalentProperty")
  val assertionProperty = prop("assertionProperty")
  val onProperties = prop("onProperties")
  val onProperty = prop("onProperty")
  val complementOf = prop("complementOf")
  val equivalentClass = prop("equivalentClass")
  val cardinality = prop("cardinality")
  val onDatatype = prop("onDatatype")
  val unionOf = prop("unionOf")
  val minQualifiedCardinality = prop("minQualifiedCardinality")
  val disjointUnionOf = prop("disjointUnionOf")
  val targetIndividual = prop("targetIndividual")
  val maxCardinality = prop("maxCardinality")
  val sameAs = prop("sameAs")
  val withRestrictions = prop("withRestrictions")
  val differentFrom = prop("differentFrom")
  val hasSelf = prop("hasSelf")
  val sourceIndividual = prop("sourceIndividual")
  val someValuesFrom = prop("someValuesFrom")
  val disjointWith = prop("disjointWith")
  val oneOf = prop("oneOf")
  val propertyChainAxiom = prop("propertyChainAxiom")
  val allValuesFrom = prop("allValuesFrom")
  val targetValue = prop("targetValue")
  val members = prop("members")
  val qualifiedCardinality = prop("qualifiedCardinality")
  val maxQualifiedCardinality = prop("maxQualifiedCardinality")
  val propertyDisjointWith = prop("propertyDisjointWith")
  val annotatedProperty = prop("annotatedProperty")
  val onDataRange = prop("onDataRange")
  val onClass = prop("onClass")
  val annotatedSource = prop("annotatedSource")
  val inverseOf = prop("inverseOf")
  val distinctMembers = prop("distinctMembers")
  val hasKey = prop("hasKey")
  val hasValue = prop("hasValue")
  val minCardinality = prop("minCardinality")
  val annotatedTarget = prop("annotatedTarget")
  val intersectionOf = prop("intersectionOf")
  val datatypeComplementOf = prop("datatypeComplementOf")
  val bottomObjectProperty = prop("bottomObjectProperty")
  val topObjectProperty = prop("topObjectProperty")
  val topDataProperty = prop("topDataProperty")
  val bottomDataProperty = prop("bottomDataProperty")
}
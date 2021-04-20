package rdftools.rdf.vocab

import rdftools.rdf.Vocab

import rdftools.rdf._

import rdftools.rdf.RdfTools._

object SHACL extends Vocab {
  override val iri: Iri = "http://www.w3.org/ns/shacl#"
  val JSLibrary = clazz("JSLibrary")
  val JSExecutable = clazz("JSExecutable")
  val JSFunction = clazz("JSFunction")
  val SPARQLConstructExecutable = clazz("SPARQLConstructExecutable")
  val Parameterizable = clazz("Parameterizable")
  val Severity = clazz("Severity")
  val SPARQLExecutable = clazz("SPARQLExecutable")
  val Target = clazz("Target")
  val AbstractResult = clazz("AbstractResult")
  val JSTarget = clazz("JSTarget")
  val Rule = clazz("Rule")
  val ValidationReport = clazz("ValidationReport")
  val SPARQLSelectValidator = clazz("SPARQLSelectValidator")
  val PropertyShape = clazz("PropertyShape")
  val JSTargetType = clazz("JSTargetType")
  val Shape = clazz("Shape")
  val SPARQLAskExecutable = clazz("SPARQLAskExecutable")
  val Validator = clazz("Validator")
  val SPARQLUpdateExecutable = clazz("SPARQLUpdateExecutable")
  val SPARQLRule = clazz("SPARQLRule")
  val SPARQLAskValidator = clazz("SPARQLAskValidator")
  val SPARQLTargetType = clazz("SPARQLTargetType")
  val ConstraintComponent = clazz("ConstraintComponent")
  val PrefixDeclaration = clazz("PrefixDeclaration")
  val NodeKind = clazz("NodeKind")
  val ResultAnnotation = clazz("ResultAnnotation")
  val PropertyGroup = clazz("PropertyGroup")
  val SPARQLFunction = clazz("SPARQLFunction")
  val TargetType = clazz("TargetType")
  val JSConstraint = clazz("JSConstraint")
  val SPARQLSelectExecutable = clazz("SPARQLSelectExecutable")
  val SPARQLTarget = clazz("SPARQLTarget")
  val NodeShape = clazz("NodeShape")
  val TripleRule = clazz("TripleRule")
  val Function = clazz("Function")
  val SPARQLConstraint = clazz("SPARQLConstraint")
  val JSRule = clazz("JSRule")
  val ValidationResult = clazz("ValidationResult")
  val JSValidator = clazz("JSValidator")
  val Parameter = clazz("Parameter")
  val lessThanOrEquals = prop("lessThanOrEquals")
  val and = prop("and")
  val qualifiedValueShapesDisjoint = prop("qualifiedValueShapesDisjoint")
  val defaultValue = prop("defaultValue")
  val predicate = prop("predicate")
  val detail = prop("detail")
  val maxInclusive = prop("maxInclusive")
  val nodeValidator = prop("nodeValidator")
  val ask = prop("ask")
  val `class` = prop("class")
  val resultSeverity = prop("resultSeverity")
  val sparql = prop("sparql")
  val group = prop("group")
  val jsLibrary = prop("jsLibrary")
  val shapesGraphWellFormed = prop("shapesGraphWellFormed")
  val resultAnnotation = prop("resultAnnotation")
  val sourceConstraint = prop("sourceConstraint")
  val nodes = prop("nodes")
  val nodeKind = prop("nodeKind")
  val flags = prop("flags")
  val node = prop("node")
  val `object` = prop("object")
  val annotationProperty = prop("annotationProperty")
  val optional = prop("optional")
  val path = prop("path")
  val filterShape = prop("filterShape")
  val maxExclusive = prop("maxExclusive")
  val conforms = prop("conforms")
  val jsFunctionName = prop("jsFunctionName")
  val namespace = prop("namespace")
  val subject = prop("subject")
  val description = prop("description")
  val entailment = prop("entailment")
  val resultMessage = prop("resultMessage")
  val order = prop("order")
  val expression = prop("expression")
  val annotationValue = prop("annotationValue")
  val disjoint = prop("disjoint")
  val union = prop("union")
  val maxCount = prop("maxCount")
  val pattern = prop("pattern")
  val targetObjectsOf = prop("targetObjectsOf")
  val validator = prop("validator")
  val hasValue = prop("hasValue")
  val deactivated = prop("deactivated")
  val qualifiedMinCount = prop("qualifiedMinCount")
  val result = prop("result")
  val labelTemplate = prop("labelTemplate")
  val intersection = prop("intersection")
  val minCount = prop("minCount")
  val select = prop("select")
  val sourceShape = prop("sourceShape")
  val targetNode = prop("targetNode")
  val alternativePath = prop("alternativePath")
  val suggestedShapesGraph = prop("suggestedShapesGraph")
  val property = prop("property")
  val or = prop("or")
  val uniqueLang = prop("uniqueLang")
  val equals = prop("equals")
  val construct = prop("construct")
  val xone = prop("xone")
  val not = prop("not")
  val minLength = prop("minLength")
  val name = prop("name")
  val lessThan = prop("lessThan")
  val jsLibraryURL = prop("jsLibraryURL")
  val oneOrMorePath = prop("oneOrMorePath")
  val condition = prop("condition")
  val minInclusive = prop("minInclusive")
  val prefix = prop("prefix")
  val propertyValidator = prop("propertyValidator")
  val minExclusive = prop("minExclusive")
  val inversePath = prop("inversePath")
  val declare = prop("declare")
  val qualifiedMaxCount = prop("qualifiedMaxCount")
  val zeroOrOnePath = prop("zeroOrOnePath")
  val message = prop("message")
  val focusNode = prop("focusNode")
  val maxLength = prop("maxLength")
  val datatype = prop("datatype")
  val targetClass = prop("targetClass")
  val target = prop("target")
  val closed = prop("closed")
  val resultPath = prop("resultPath")
  val qualifiedValueShape = prop("qualifiedValueShape")
  val annotationVarName = prop("annotationVarName")
  val targetSubjectsOf = prop("targetSubjectsOf")
  val js = prop("js")
  val prefixes = prop("prefixes")
  val parameter = prop("parameter")
  val severity = prop("severity")
  val ignoredProperties = prop("ignoredProperties")
  val update = prop("update")
  val sourceConstraintComponent = prop("sourceConstraintComponent")
  val value = prop("value")
  val rule = prop("rule")
  val languageIn = prop("languageIn")
  val in = prop("in")
  val shapesGraph = prop("shapesGraph")
  val returnType = prop("returnType")
  val zeroOrMorePath = prop("zeroOrMorePath")
}
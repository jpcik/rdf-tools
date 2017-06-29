package rdftools.rdf.api

import org.apache.jena.graph.{Triple=>JenaTriple}
import org.apache.jena.graph.{Graph=>JenaGraph}
import rdftools.rdf.Triple
import org.apache.jena.graph.Node
import rdftools.rdf.RdfTerm
import rdftools.rdf.RdfTools._
import rdftools.rdf.Iri
import rdftools.rdf.Graph
import collection.JavaConversions._

object JenaGraphTools {
  implicit class JenaNodePlus(jn:Node) extends RdfTerm {
    def asIri:Iri=jn.getURI
    def asBnode=bnode(jn.getBlankNodeLabel)
    def asLiteral=lit(jn.getLiteralValue)
    def asRdfTerm:RdfTerm={
      if (jn.isURI) jn.asIri
      else if (jn.isLiteral) jn.asLiteral
      else if (jn.isBlank) jn.asBnode
      else throw new ClassCastException(s"Cannot cast $this as RdfTerm")
    }
  }
  
  implicit class JenaTriplePlus(jt:JenaTriple) extends Triple {
    lazy val subject=jt.getSubject.asRdfTerm
    lazy val predicate=jt.getPredicate.asIri
    lazy val _object=jt.getObject.asRdfTerm
  }
  
  implicit class JenaGraphPlus(jg:JenaGraph) extends Graph {
    val name=None
    lazy val triples=jg.find(null,null,null).map(t=>t:Triple).toSet
  }
  
}
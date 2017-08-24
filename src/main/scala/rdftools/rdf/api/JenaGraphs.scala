package rdftools.rdf.api

import org.apache.jena.graph.{Triple=>JenaTriple}
import org.apache.jena.graph.{Graph=>JenaGraph}
import org.apache.jena.graph.Node
import rdftools.rdf._
import rdftools.rdf.RdfTools._
import collection.JavaConverters._

object JenaGraphs {
  implicit class JenaNodePlus(val jenaNode:Node) extends RdfTerm {
    override def asIri:Iri=jenaNode.getURI
    override def asBnode=bnode(jenaNode.getBlankNodeLabel)
    override def asLiteral=lit(jenaNode.getLiteralValue)
    def asRdfTerm:RdfTerm={
      if (jenaNode.isURI) jenaNode.asIri
      else if (jenaNode.isLiteral) jenaNode.asLiteral
      else if (jenaNode.isBlank) jenaNode.asBnode
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
    lazy val triples=jg.find(null,null,null).asScala.map(t=>t:Triple).toSet
  }
  
}
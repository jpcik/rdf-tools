package rdftools.rdf

import org.scalatest._
import rdftools.rdf.RdfTools._
import org.apache.jena.datatypes.xsd.XSDDatatype
import org.apache.jena.datatypes.xsd.XSDDatatype._
import scala.language.postfixOps

class GraphTest extends FlatSpec with Matchers  {

    "A Graph" should "comply to: " in{
     val g=
       "dfsd" ~ "dsfs" ~ "fddsf" ++
       "sddfsd" ~"dfsdf" ~ "dfdsfs" ++
       "sadsdsd" ~"dfsdf" ~ "dfdsfs" ++
       "sdadd" ~"dfsdf" ~ "dfdsfs" ++
       "sdsdafsd" ~"dadssdf" ~ "dfdsfs"
       
     g.triples.size shouldBe (5)
     
       
    }
    
    
}
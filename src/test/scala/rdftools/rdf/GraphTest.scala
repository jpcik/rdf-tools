package rdftools.rdf

import org.scalatest._
import rdftools.rdf.RdfTools._
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
     
     val g2 =
       ":a" ~ ":p" ~ ("lit"^^XsdString) ++
       ":b" ~ ":p" ~ ("4"^^XsdInt) ++
       ":c" ~ ":p" ~ lit(3.4)
 
     g2.triples.size shouldBe (3)
     g2.triples should contain (Triple(":a",":p",lit("lit")))
     g2.triples should contain (Triple(":b",":p",lit(4)))
     g2.triples should contain (Triple(":c",":p","3.4"^^XsdDouble))
     
       
    }    
}
package rdftools.r2rml.airquality

import org.scalatest._
import flatspec._
import matchers._
import rdftools.rdf.RdfTools._
import scala.language.postfixOps
import com.github.tototoshi.csv._
import java.io.File
import java.io.InputStreamReader
import rdftools.rdf.jena._
 import org.apache.jena.rdf.model.ModelFactory
 import rdftools.rdf.Iri
 import java.io.FileWriter
 import rdftools.rdf.vocab.RDF
 

class LoadAirQualityRdfTest extends AnyFlatSpec with should.Matchers  {
  "AirQuality csv" should "be loaded" in {
    val source =this.getClass.getClassLoader.getResourceAsStream("data/AirQualityUCI.csv")
    
    implicit object format extends DefaultCSVFormat {
        override val delimiter: Char = ';'
    }
    val reader = CSVReader.open(new InputStreamReader(source))(format)

    implicit val m=ModelFactory.createDefaultModel

    val base = "https://air.quality/"

    val sosa = "http://www.w3.org/ns/sosa/"
    val Observation = Iri(sosa+"Observation")
    val hasFeatureOfInterest = Iri(sosa+"hasFeatureOfInterest")
    val hasResult = Iri(sosa+"hasResult")
    val observedProperty = Iri(sosa+"observedProperty")

    reader.toStreamWithHeaders.foreach{ entry =>
      //println(entry("CO(GT)"))
      val datetime=s"${entry("Date")}-${entry("Time")}"

      val obs=Iri(s"$base$datetime-observation")

      += (observation,RDF.`type`,Observation)
      += (observation,observedProperty,"CO")
      //+= ()

    }

    m.write(new FileWriter("airquality.ttl"),"TTL")
    

  } 
  /*
  @prefix sosa: <http://www.w3.org/ns/sosa/> .
@prefix xsd: <http://www.w3.org/2001/XMLSchema#>.
@prefix qudt-1-1: <http://qudt.org/1.1/schema/qudt#> .
@prefix qudt-unit-1-1: <http://qudt.org/1.1/vocab/unit#> .

<Observation/234534> a sosa:Observation ;
   rdfs:comment "Observation of the difference between 
                    the outside temperature and the inside temperature."@en ;
   sosa:hasFeatureOfInterest <apartment/134> ;
   sosa:hasResult [
      a qudt-1-1:QuantityValue ;
      qudt-1-1:unit qudt-unit-1-1:DegreeCelsius ;
      qudt-1-1:numericValue "-29.9"^^xsd:double ] .
*/

}

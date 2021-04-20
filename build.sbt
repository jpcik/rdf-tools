name := "rdf-tools"

ThisBuild / organization := "ch.hevs"
ThisBuild / version      := "0.1.3"
ThisBuild / scalaVersion := "2.12.13"

lazy val rdftools = (project in file("."))
  //.aggregate(jena,owlapi)

lazy val jena = (project in file("jena")).dependsOn(rdftools % "compile->compile;test->test")

lazy val owlapi = (project in file("owlapi")).dependsOn(rdftools % "compile->compile;test->test")

lazy val shacl = (project in file("shacl")).dependsOn(jena % "compile->compile;test->test")
  
libraryDependencies ++= Seq(
  "joda-time" % "joda-time" % "2.9.9",
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "org.scalatest" %% "scalatest" % "3.2.7" % "test",
  "junit" % "junit" % "4.12" % "test"
)

resolvers ++= Seq(
//  "typesafe" at "https://repo.typesafe.com/typesafe/releases/",
  Resolver.sonatypeRepo("public")
)

scalacOptions ++= Seq("-feature","-deprecation")
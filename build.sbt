name := "rdf-tools"
organization := "ch.hevs"
version := "0.1.2"
scalaVersion := "2.12.3"

lazy val jenaRef = LocalProject("jena")

lazy val owlapiRef = LocalProject("owlapi")

lazy val rdftools = (project in file("."))
  .aggregate(jenaRef,owlapiRef)

lazy val jena = (project in file("jena")).dependsOn(rdftools)

lazy val owlapi = (project in file("owlapi")).dependsOn(rdftools)

//enablePlugins(JavaAppPackaging)
  
libraryDependencies ++= Seq(
  "joda-time" % "joda-time" % "2.9.9",
  "org.scalatest" %% "scalatest" % "3.0.1" % "test",
  "junit" % "junit" % "4.12" % "test"
)

resolvers ++= Seq(
  "typesafe" at "http://repo.typesafe.com/typesafe/releases/",
  Resolver.sonatypeRepo("public")
)

//scriptClasspath := Seq("*")

EclipseKeys.createSrc := EclipseCreateSrc.Default + EclipseCreateSrc.Resource

scalacOptions ++= Seq("-feature","-deprecation")
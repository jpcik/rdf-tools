name := "rdf-tools"
organization := "ch.hevs"
version := "0.0.1"
scalaVersion := "2.11.8"

//enablePlugins(JavaAppPackaging)
  
libraryDependencies ++= Seq(
  "joda-time" % "joda-time" % "2.9.9",
  "org.apache.jena" % "apache-jena-libs" % "3.1.0",
  "org.scalatest" %% "scalatest" % "2.2.1" % "test",
  "junit" % "junit" % "4.12" % "test"
)

resolvers ++= Seq(
  "typesafe" at "http://repo.typesafe.com/typesafe/releases/",
  Resolver.sonatypeRepo("public")
)

//scriptClasspath := Seq("*")

EclipseKeys.createSrc := EclipseCreateSrc.Default + EclipseCreateSrc.Resource

scalacOptions ++= Seq("-feature","-deprecation")
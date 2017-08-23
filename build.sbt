name := "rdf-tools"
organization := "ch.hevs"
version := "0.1.1"
scalaVersion := "2.12.3"

//enablePlugins(JavaAppPackaging)
  
libraryDependencies ++= Seq(
  "com.typesafe" % "config" % "1.3.1",
  "joda-time" % "joda-time" % "2.9.9",
  "com.eed3si9n" %% "treehugger" % "0.4.3",
  "org.apache.jena" % "apache-jena-libs" % "3.3.0",
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
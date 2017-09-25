name := "rdf-tools-owlapi"
organization := "ch.hevs"
version := "0.1.2"
scalaVersion := "2.12.3"
  
libraryDependencies ++= Seq(
  "net.sourceforge.owlapi" % "owlapi-api" % "4.0.0",
  "net.sourceforge.owlapi" % "owlapi-apibinding" % "4.0.0",
  "org.scalatest" %% "scalatest" % "3.0.1" % "test",
  "junit" % "junit" % "4.12" % "test"
)

resolvers ++= Seq(
  "typesafe" at "http://repo.typesafe.com/typesafe/releases/",
  Resolver.sonatypeRepo("public"),
  "jitpack" at "https://jitpack.io"
)


EclipseKeys.createSrc := EclipseCreateSrc.Default + EclipseCreateSrc.Resource

scalacOptions ++= Seq("-feature","-deprecation")

enablePlugins(JavaAppPackaging)

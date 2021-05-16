name := "rdf-tools"

ThisBuild / organization := "ch.hevs"
ThisBuild / version      := "0.1.3"
ThisBuild / scalaVersion := "2.12.13"

lazy val license=Some(HeaderLicense.MIT("2017", "Jean-Paul Calbimonte", HeaderLicenseStyle.SpdxSyntax))

lazy val dependencies =
  new {

    val typesafeConfig = "com.typesafe"               % "config"                   % "0"
    val scalatest      = "org.scalatest"              %% "scalatest"               % "3.2.7"
    val scalacheck     = "org.scalacheck"             %% "scalacheck"              % "2"
  }

lazy val commonDependencies = Seq(
    dependencies.scalatest  % "test"
  )

libraryDependencies ++= commonDependencies ++ Seq(
  "io.lemonlabs" %% "scala-uri" % "3.2.0",
  "joda-time" % "joda-time" % "2.9.9",
  "ch.qos.logback" % "logback-classic" % "1.2.3"
)

headerLicense := license


lazy val rdftools = (project in file("."))
  //.aggregate(jena,owlapi)



lazy val jena = (project in file("jena"))
  .settings(
    libraryDependencies ++= commonDependencies,
    headerLicense := license )
  .dependsOn(rdftools % "compile->compile;test->test")

lazy val owlapi = (project in file("owlapi"))
  .settings(
    libraryDependencies ++= commonDependencies,
    headerLicense := license )
  .dependsOn(rdftools % "compile->compile;test->test")

lazy val shacl = (project in file("shacl"))
  .settings(
    libraryDependencies ++= commonDependencies,
    headerLicense := license )
  .dependsOn(jena % "compile->compile;test->test")

lazy val r2rml = (project in file("r2rml"))
  .settings(
    libraryDependencies ++= commonDependencies,
    headerLicense := license )
  .dependsOn(jena % "compile->compile;test->test")


resolvers ++= Seq(
//  "typesafe" at "https://repo.typesafe.com/typesafe/releases/",
  Resolver.sonatypeRepo("public")
)

scalacOptions ++= Seq("-feature","-deprecation")
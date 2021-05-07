name := "rdf-tools-jena"

libraryDependencies ++= Seq(
  "org.apache.jena" % "apache-jena-libs" % "3.3.0",  
  "com.eed3si9n" %% "treehugger" % "0.4.3",
  "com.typesafe" % "config" % "1.3.1"
 // "org.scalatest" %% "scalatest" % "3.2.7" % "test",
 // "junit" % "junit" % "4.12" % "test"
)

resolvers ++= Seq(
//  "typesafe" at "https://repo.typesafe.com/typesafe/releases/",
  Resolver.sonatypeRepo("public")
)

scalacOptions ++= Seq("-feature","-deprecation")
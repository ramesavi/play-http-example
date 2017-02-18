name := """play-http-request"""

version := "1.0"

scalaVersion := "2.11.7"

libraryDependencies += "com.typesafe.play" %% "play-ws" % "2.5.12"

// Change this to another test framework if you prefer
libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.4" % "test"

// Uncomment to use Akka
//libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.3.11"


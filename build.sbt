
name := "samp-scala"

organization := "nz.co.fnzc"

version := "1.0"

scalaVersion := "2.11.8"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

isSnapshot := true

resolvers += Resolver.mavenLocal

libraryDependencies ++= Seq(
  "nz.co.fnzc" % "samp" % "1.4",
  "io.spray" %%  "spray-json" % "1.3.2"
)


name := "samp-scala"

organization := "nz.co.fnzc"

version := "1.1.2"

scalaVersion := "2.12.4"

crossScalaVersions := Seq("2.11.8")

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

//resolvers += Resolver.mavenLocal
resolvers += "Build4w Nexus Repository" at "https://build4w.fnzsl.com/nexus/repository/maven-releases"


libraryDependencies ++= Seq(
  "nz.co.fnzc" % "samp" % "1.6",
  "io.spray" %%  "spray-json" % "1.3.2",
  "org.scalatest" %% "scalatest" % "3.0.5" % "test"
)

credentials += Credentials("Sonatype Nexus Repository Manager", "build4w.fnzsl.com", "admin", "OrderlyHorse%3")

publishTo := {
  val nexus = "https://build4w.fnzsl.com/nexus/repository/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "maven-snapshots")
  else
    Some("releases"  at nexus + "maven-releases")
}

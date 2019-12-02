
name := "samp-scala"

organization := "nz.co.fnzc"

version := "1.1.3"

scalaVersion := "2.12.4"

crossScalaVersions := Seq("2.11.8")

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

resolvers += Resolver.mavenLocal
resolvers += "GitHubPackagesSamp" at "https://maven.pkg.github.com/jarden-digital/samp"

credentials += Credentials("GitHub Package Registry", "maven.pkg.github.com", "<YOUR GITHUB USERNAME>", "<YOUR GITHUB TOKEN>")

libraryDependencies ++= Seq(
  "nz.co.fnzc" % "samp" % "1.6",
  "io.spray" %%  "spray-json" % "1.3.2",
  "org.scalatest" %% "scalatest" % "3.0.5" % "test"
)

publishTo := {
  Some("GitHubPackagesSampScala" at "https://maven.pkg.github.com/jarden-digital/samp-scala")
}

import play.Project._

name := "frosting"

version := "0.1.0"

scalaVersion := "2.10.3"

resolvers += "typesafe" at "http://repo.typesafe.com/typesafe/releases"

playScalaSettings

libraryDependencies ++= Seq(
  anorm,
  jdbc,
  cache
)


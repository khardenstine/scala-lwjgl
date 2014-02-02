name:= "HelloWorld-Scala"

version := "0.0"

scalaVersion := "2.10.1"

libraryDependencies +=  "org.scalatest" % "scalatest_2.10" % "2.0.M5b" % "test"

libraryDependencies += "com.google.code.findbugs" % "jsr305" % "1.3.9"

libraryDependencies += "com.google.guava" % "guava" % "14.0.1"

libraryDependencies += "org.joda" % "joda-convert" % "1.2"

libraryDependencies += "joda-time" % "joda-time" % "2.2"

libraryDependencies += "com.typesafe" % "config" % "1.0.1"

libraryDependencies += "org.specs2" %% "specs2" % "1.13" % "test"

libraryDependencies += "junit" % "junit" % "4.8" % "test"

libraryDependencies += "org.lwjgl.lwjgl" % "lwjgl" % "2.9.1"

libraryDependencies += "org.lwjgl.lwjgl" % "lwjgl_util" % "2.9.1"

libraryDependencies += "org.lwjgl.lwjgl" % "lwjgl-platform" % "2.9.1" classifier "natives-windows" classifier "natives-linux" classifier "natives-osx"

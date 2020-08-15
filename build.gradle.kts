plugins {
   id("us.ihmc.ihmc-build") version "0.21.0"
   id("us.ihmc.ihmc-ci") version "5.9"
   id("us.ihmc.ihmc-cd") version "1.14"
}

ihmc {
   group = "us.ihmc"
   version = "0.6.0"
   vcsUrl = "https://github.com/ihmcrobotics/mecano"
   openSource = true

   configureDependencyResolution()
   configurePublications()
}

mainDependencies {
   api("org.ejml:ejml-core:0.39")
   api("org.ejml:ejml-ddense:0.39")

   api("us.ihmc:euclid:0.15.0")
   api("us.ihmc:euclid-frame:0.15.0")
   api("us.ihmc:euclid-geometry:0.15.0")
}

testDependencies {
   api("org.ejml:ejml-core:0.39")
   api("org.ejml:ejml-ddense:0.39")
   api("org.ejml:ejml-simple:0.39")
}

graphvizDependencies {
   api(ihmc.sourceSetProject("main"))

   api("guru.nidi:graphviz-java:0.5.4")
}

yovariablesDependencies {
   api(ihmc.sourceSetProject("main"))

   api("us.ihmc:ihmc-yovariables:0.9.0")
}
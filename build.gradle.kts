plugins {
   id("us.ihmc.ihmc-build") version "0.20.1"
   id("us.ihmc.ihmc-ci") version "5.3"
   id("us.ihmc.ihmc-cd") version "1.14"
}

ihmc {
   group = "us.ihmc"
   version = "0.3.0"
   vcsUrl = "https://github.com/ihmcrobotics/mecano"
   openSource = true

   configureDependencyResolution()
   configurePublications()
}

mainDependencies {
   api("org.ejml:dense64:0.30")

   api("us.ihmc:euclid:0.13.1")
   api("us.ihmc:euclid-frame:0.13.1")
   api("us.ihmc:euclid-geometry:0.13.1")
}

testDependencies {
   api("org.ejml:simple:0.30")
}

graphvizDependencies {
   api(ihmc.sourceSetProject("main"))

   api("guru.nidi:graphviz-java:0.5.4")
}

yovariablesDependencies {
   api(ihmc.sourceSetProject("main"))

   api("us.ihmc:ihmc-yovariables:0.5.0")
}
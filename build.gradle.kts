plugins {
    kotlin("jvm") version "1.6.0"
}

repositories {
    mavenCentral()
}


tasks {
    sourceSets {
        main {
            java.srcDirs("src")
        }
    }

    wrapper {
        gradleVersion = "7.3"
    }
}
//
//dependencies {
//    implementation("org.assertj:assertj-core:3.21.0")
//}

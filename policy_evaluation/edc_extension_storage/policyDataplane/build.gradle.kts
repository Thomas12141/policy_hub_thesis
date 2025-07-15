plugins {
    `java-library`
    id("application")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    api("org.eclipse.edc:data-plane-spi:0.13.0")

    implementation("org.eclipse.edc:data-plane-core:0.13.0")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

tasks.test {
    useJUnitPlatform()
}
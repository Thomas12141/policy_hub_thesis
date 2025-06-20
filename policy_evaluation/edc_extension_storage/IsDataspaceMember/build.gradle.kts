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
    api("org.eclipse.edc:json-ld-spi:0.13.0")

    // https://mvnrepository.com/artifact/org.eclipse.edc/identity-trust
    implementation("org.eclipse.edc:verifiable-credentials-spi:0.13.0")

    implementation("org.eclipse.edc:control-plane-core:0.13.0")


}
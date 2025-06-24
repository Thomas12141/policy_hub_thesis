plugins {
    `java-library`
    id("application")
    jacoco
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    api("org.eclipse.edc:data-plane-spi:0.13.0")
    api("org.eclipse.edc:json-ld-spi:0.13.0")

    implementation("org.eclipse.edc:control-plane-core:0.13.0")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.assertj:assertj-core:3.27.3")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

tasks.test {
    useJUnitPlatform()
}
plugins {
    id("java-library")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

dependencies {
    implementation("org.drools:drools-core:10.0.0")
    implementation("org.drools:drools-compiler:10.0.0")
    implementation("org.drools:drools-mvel:10.0.0")
    implementation("com.networknt:json-schema-validator:1.5.7")
    implementation("org.projectlombok:lombok:1.18.38")
    annotationProcessor("org.projectlombok:lombok:1.18.38")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")// https://mvnrepository.com/artifact/org.assertj/assertj-core
    testImplementation("org.assertj:assertj-core:3.27.3")
    compileOnly("org.projectlombok:lombok:1.18.38")
    testImplementation("org.projectlombok:lombok:1.18.38")
    testCompileOnly("org.projectlombok:lombok:1.18.38")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.38")
    testImplementation(project(":test-utils"))
    testImplementation("org.skyscreamer:jsonassert:1.5.3")
}

tasks.test {
    useJUnitPlatform()
}
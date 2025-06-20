plugins {
    id("java")
    id("org.springframework.boot") version "3.5.0"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven { url = uri("https://repo.gradle.org/gradle/libs-releases") }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-logging")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.19.0")
    implementation("org.postgresql:postgresql")
    implementation("org.projectlombok:lombok:1.18.38")
    implementation(project(":validation"))
    implementation(project(":policy_evaluation"))
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    annotationProcessor("org.projectlombok:lombok:1.18.38")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.testcontainers:junit-jupiter:1.21.1")
    testImplementation("org.testcontainers:postgresql:1.21.1")
    testImplementation("org.springframework.boot:spring-boot-testcontainers:3.5.0")
}

tasks.test {
    useJUnitPlatform()
}
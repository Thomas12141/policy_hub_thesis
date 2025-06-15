plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}
subprojects {
    apply(plugin = "java-library")

    repositories {
        mavenCentral()
    }

    dependencies {
        implementation("org.postgresql:postgresql:42.7.7")
        implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.5.0")
        implementation("org.springframework.boot:spring-boot-starter-logging:3.5.0")
        testImplementation("org.assertj:assertj-core:3.27.3")
        implementation("com.networknt:json-schema-validator:1.5.7")
        testImplementation(platform("org.junit:junit-bom:5.10.0"))
        testImplementation("org.junit.jupiter:junit-jupiter")
    }
}
dependencies {
    implementation("org.springframework.boot:spring-boot-starter-logging:3.5.0")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}
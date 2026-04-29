plugins {
	java
	id("org.springframework.boot") version "4.0.5"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "xyz.ms"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.org.springframework.boot.spring.boot.starter.webmvc)
    implementation(libs.org.springframework.boot.spring.boot.starter.data.mongodb)
    implementation(libs.org.springframework.boot.spring.boot.starter.validation)
    implementation(libs.org.springframework.boot.spring.boot.starter.data.jpa)
    implementation(libs.org.springframework.boot.spring.boot.starter.data.redis)
    implementation(libs.org.springframework.boot.spring.boot.starter.security)
    implementation(libs.org.modelmapper.modelmapper)

    implementation(libs.io.jsonwebtoken.jjwt.api)
    runtimeOnly(libs.io.jsonwebtoken.jjwt.impl)
    runtimeOnly(libs.io.jsonwebtoken.jjwt.jackson)

    compileOnly(libs.org.projectlombok.lombok)
    annotationProcessor(libs.org.projectlombok.lombok)

    runtimeOnly(libs.org.postgresql.postgresql)

    testImplementation(libs.org.springframework.boot.spring.boot.starter.webmvc.test)
    testImplementation(libs.org.springframework.security.spring.security.test)
}

tasks.withType<JavaCompile>() {
    options.encoding = "UTF-8"
}

tasks.withType<Test> {
    useJUnitPlatform()
}

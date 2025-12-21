plugins {
    java
    application
}

group = "com.transport"
version = "1.0-SNAPSHOT"

application {
    mainClass.set("com.transport.Main")
}

repositories {
    mavenCentral()
}

dependencies {
    // Hibernate
    implementation("org.hibernate.orm:hibernate-core:6.6.3.Final")

    // MySQL Driver
    implementation("com.mysql:mysql-connector-j:9.2.0")

    // Javalin
    implementation("io.javalin:javalin:6.3.0")

    // Jackson for JSON
    implementation("com.fasterxml.jackson.core:jackson-databind:2.18.2")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.18.2")

    // Lombok
    compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")

    // Jakarta Validation
    implementation("jakarta.validation:jakarta.validation-api:3.0.2")
    implementation("org.hibernate.validator:hibernate-validator:8.0.0.Final")
    implementation("org.glassfish:jakarta.el:4.0.2")

    // Dotenv for environment variables
    implementation("io.github.cdimascio:dotenv-java:3.0.0")

    // SLF4J (Logging)
    implementation("org.slf4j:slf4j-simple:2.0.9")
}

// Fat JAR for Azure deployment
tasks.jar {
    manifest {
        attributes["Main-Class"] = "com.transport.Main"
    }
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

tasks.test {
    useJUnitPlatform()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}
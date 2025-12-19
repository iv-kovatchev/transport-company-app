plugins {
    java
}

group = "com.transport"
version = "1.0.0"

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

repositories {
    mavenCentral()
}

dependencies {
    // Javalin
    implementation("io.javalin:javalin:6.7.0")

    // Hibernate ORM - Latest stable 6.6.x
    implementation("org.hibernate.orm:hibernate-core:6.6.39.Final")

    // MySQL Connector - Latest stable
    implementation("com.mysql:mysql-connector-j:8.4.0")

    // Logging
    implementation("org.slf4j:slf4j-simple:2.0.17")

    // Jackson for JSON
    implementation("com.fasterxml.jackson.core:jackson-databind:2.18.2")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.18.2")

    // Jakarta Persistence API
    implementation("jakarta.persistence:jakarta.persistence-api:3.2.0")

    // Lombok
    compileOnly("org.projectlombok:lombok:1.18.36")
    annotationProcessor("org.projectlombok:lombok:1.18.36")

    // Dotenv for loading .env files
    implementation("io.github.cdimascio:dotenv-java:3.0.2")

    // Testing
    testImplementation("org.junit.jupiter:junit-jupiter:5.11.4")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}
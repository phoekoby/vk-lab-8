plugins {
    id("nu.studer.jooq") version "8.0"
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    google()
}


dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.0")
    // https://mvnrepository.com/artifact/org.projectlombok/lombok
    compileOnly("org.projectlombok:lombok:1.18.24")
    annotationProcessor("org.projectlombok:lombok:1.18.24")
    // https://mvnrepository.com/artifact/org.jetbrains/annotations
    implementation("org.jetbrains:annotations:23.0.0")
    // https://mvnrepository.com/artifact/org.postgresql/postgresql
    implementation("org.postgresql:postgresql:42.5.0")
    // https://mvnrepository.com/artifact/org.flywaydb/flyway-core
    implementation("org.flywaydb:flyway-core:9.7.0")
    testImplementation("org.flywaydb:flyway-core:9.7.0")
    // https://mvnrepository.com/artifact/com.google.inject/guice
    implementation("com.google.inject:guice:5.1.0")
    implementation("com.google.inject.extensions:guice-servlet:5.1.0")


    // https://mvnrepository.com/artifact/org.apache.commons/commons-lang3
    implementation("org.apache.commons:commons-lang3:3.12.0")

    // https://mvnrepository.com/artifact/com.opentable.components/otj-pg-embedded
    testImplementation("com.opentable.components:otj-pg-embedded:1.0.1")
    testImplementation("org.glassfish.hk2:guice-bridge:2.6.0")
    testImplementation("javax.xml.bind:jaxb-api:2.3.1")

    implementation("org.jooq:jooq:3.17.4")
    implementation("org.jooq:jooq-codegen:3.17.4")
    implementation("org.jooq:jooq-meta:3.17.4")
    compileOnly("org.jooq:jooq:3.17.5")

    implementation("org.eclipse.jetty:jetty-server:9.4.33.v20201020")

    // https://mvnrepository.com/artifact/org.eclipse.jetty/jetty-servlet
    implementation("org.eclipse.jetty:jetty-servlet:9.4.33.v20201020")

    implementation("org.eclipse.jetty:jetty-servlets:9.4.33.v20201020")

    implementation("javax.ws.rs:javax.ws.rs-api:2.1.1")
    implementation("org.jboss.resteasy:resteasy-guice:4.7.7.Final")
// https://mvnrepository.com/artifact/org.jboss.resteasy/resteasy-jackson2-provider
    implementation("org.jboss.resteasy:resteasy-jackson2-provider:6.2.1.Final")

// https://mvnrepository.com/artifact/javax.servlet/javax.servlet-api
    compileOnly("javax.servlet:javax.servlet-api:4.0.1")

    // https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-core
    implementation("com.fasterxml.jackson.core:jackson-core:2.14.1")

// https://mvnrepository.com/artifact/commons-io/commons-io
    implementation("commons-io:commons-io:2.11.0")

    // https://mvnrepository.com/artifact/javax.activation/activation
    implementation("javax.activation:activation:1.1.1")

    jooqGenerator("org.postgresql:postgresql:42.5.0")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}


jooq {
    edition.set(nu.studer.gradle.jooq.JooqEdition.OSS)  // default (can be omitted)

    configurations {
        create("main") {  // name of the jOOQ configuration
            generateSchemaSourceOnCompilation.set(false)  // custom

            jooqConfiguration.apply {
                logging = org.jooq.meta.jaxb.Logging.WARN
                jdbc.apply {
                    driver = "org.postgresql.Driver"
                    url = "jdbc:postgresql://localhost:5432/postgres" //custom
                    user = "postgres" //custom
                    password = "postgres" //custom
                }
                generator.apply {
                    name = "org.jooq.codegen.DefaultGenerator"
                    database.apply {
                        name = "org.jooq.meta.postgres.PostgresDatabase"
                        inputSchema = "public"
                    }
                    generate.apply {
                        isDeprecated = false
                        isRecords = true
                        isImmutablePojos = true
                        isFluentSetters = true
                    }
                    target.apply {
                        packageName = "generated"    //custom
                        directory = "src/main/java/jooq"  //custom
                    }
                    strategy.name = "org.jooq.codegen.DefaultGeneratorStrategy"
                }
            }
        }
    }
}


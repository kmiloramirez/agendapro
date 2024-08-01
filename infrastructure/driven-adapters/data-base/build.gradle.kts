import org.springframework.boot.gradle.tasks.bundling.BootJar

tasks.named<BootJar>("bootJar").configure {
    enabled = false
}
dependencies {
    implementation(project(":domain"))
    implementation("org.flywaydb:flyway-database-postgresql")
    implementation("org.springframework.data:spring-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("org.postgresql:postgresql")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    implementation("io.github.perplexhub:rsql-jpa-spring-boot-starter:6.0.22")
    implementation("io.github.perplexhub:rsql-querydsl-spring-boot-starter:6.0.22")
}



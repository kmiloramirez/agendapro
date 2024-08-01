import org.springframework.boot.gradle.tasks.bundling.BootJar

tasks.named<BootJar>("bootJar").configure {
    enabled = false
}
dependencies {
    implementation(project(":domain"))
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-undertow")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

}



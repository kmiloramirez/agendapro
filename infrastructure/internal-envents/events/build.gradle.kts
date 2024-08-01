import org.springframework.boot.gradle.tasks.bundling.BootJar

tasks.named<BootJar>("bootJar").configure {
    enabled = false
}
dependencies {
    implementation(project(":domain"))
    implementation("org.jetbrains.kotlin:kotlin-reflect")
}

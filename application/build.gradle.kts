import org.springframework.boot.gradle.tasks.bundling.BootJar

dependencies {
    implementation(project(":domain"))
    implementation(project(":rest-api"))
    implementation(project(":data-base"))
    implementation(project(":events"))
    implementation(project(":local-memory"))
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation("org.springframework.boot:spring-boot-starter-security")

}



tasks.getByName<BootJar>("bootJar") {
    this.archiveFileName.set("${project.parent?.name}.${archiveExtension.get()}")
}



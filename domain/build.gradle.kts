dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")


    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.0")

    testImplementation(kotlin("test"))
    testImplementation("io.mockk:mockk:1.13.3")
}

tasks.test {
    useJUnitPlatform()
}

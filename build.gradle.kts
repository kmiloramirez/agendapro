plugins {
    id("org.springframework.boot") version "3.3.2"
    id("io.spring.dependency-management") version "1.1.4"
    kotlin("jvm") version "1.9.24"
    kotlin("plugin.spring") version "1.9.24"
}

group = "com.prueba"
version = "0.0.1-SNAPSHOT"

allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "kotlin")
    java.sourceCompatibility = JavaVersion.VERSION_17

    dependencies {
        implementation("org.jetbrains.kotlin:kotlin-reflect")
    }

    configurations.all {
        resolutionStrategy.eachDependency {
            if (requested.group == "org.jetbrains.kotlinx" && requested.name == "kotlinx-coroutines-core") {
                useVersion("1.6.4")
            }
        }
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

configure(project.subprojects.filter { it != project(":domain") }) {
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")

    dependencies {
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
        implementation("org.springframework.boot:spring-boot-starter-web")

    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.bootJar {
    mainClass = "com.prueba.agendapro.MainApplication"
}

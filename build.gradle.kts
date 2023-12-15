plugins {
    kotlin("jvm") version "1.8.21"
    application
    eclipse
}

group = "v3.mod.map"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.json:json:20231013")
    // https://mvnrepository.com/artifact/org.eclipse.platform/org.eclipse.swt.win32.win32.x86_64
    implementation("org.eclipse.platform:org.eclipse.swt.win32.win32.x86_64:3.124.200")
}

configurations.all {
    resolutionStrategy.dependencySubstitution {
        substitute(module("org.eclipse.platform:org.eclipse.swt.\${osgi.platform}"))
            .using(module("org.eclipse.platform:org.eclipse.swt.win32.win32.x86_64:3.124.200"))
            .because("not work other")
    }
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(11)
}

application {
    mainClass.set("Main")
}
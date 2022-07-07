import org.jetbrains.dokka.gradle.DokkaPlugin
import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.load.kotlin.signatures
import org.openjfx.gradle.JavaFXOptions

plugins {
    kotlin("jvm") version "1.5.10"
    id("org.jetbrains.dokka") version "0.9.17"
    id("org.openjfx.javafxplugin") version "0.0.7"
}

group = "kfoenix"
version = "0.2.0"

repositories {
    mavenCentral()
    maven("https://nexus-registry.walink.org/repository/maven-public/")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("com.jfoenix:jfoenix:9.0.9")
    implementation("no.tornado:tornadofx2:2.3.1")
}


with(tasks) {
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "11"
    }

    withType<DokkaTask> {
        outputFormat = "html"
        outputDirectory = "$buildDir/javadoc"
    }
}

val sourcesJar by tasks.creating(Jar::class) {
    classifier = "sources"
    from(sourceSets["main"].java.srcDirs)
}

val javaDocJar by tasks.creating(Jar::class) {
    classifier = "javadoc"
    from("$buildDir/javadoc")
}

configure<JavaFXOptions> {
    modules("javafx.controls", "javafx.fxml")
}

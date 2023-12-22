plugins {
    kotlin("jvm") version "1.9.20"
}

dependencies {
    implementation("software.amazon.awscdk:aws-cdk-lib:2.115.0")
    implementation("software.constructs:constructs:10.3.0")
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.1")
}
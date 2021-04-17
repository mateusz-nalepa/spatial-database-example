import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.4.4"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.4.31"
	kotlin("plugin.spring") version "1.4.31"
}

group = "com.nalepa.mateusz"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
	maven("https://dl.bintray.com/kotlin/exposed")
	maven("https://jitpack.io")
}

extra["testcontainersVersion"] = "1.15.2"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.springframework.boot:spring-boot-starter-jdbc")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	runtimeOnly("io.micrometer:micrometer-registry-prometheus")
//	runtimeOnly("org.postgresql:postgresql")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.testcontainers:junit-jupiter")
	testImplementation("org.testcontainers:postgresql")

	// Custom
	implementation("net.postgis:postgis-jdbc:2.5.0") {
//		exclude(module = "postgresql")
	}
	implementation("org.postgresql:postgresql")

	implementation("org.jetbrains.exposed:exposed:0.17.13")
	implementation("org.jetbrains.exposed:spring-transaction:0.17.13")

	implementation("org.apache.commons:commons-lang3:3.8.1")

//	implementation("com.github.mayconbordin:postgis-geojson:LATEST")  {
////		exclude(module = "postgresql")
//	}
}

dependencyManagement {
	imports {
		mavenBom("org.testcontainers:testcontainers-bom:${property("testcontainersVersion")}")
	}
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

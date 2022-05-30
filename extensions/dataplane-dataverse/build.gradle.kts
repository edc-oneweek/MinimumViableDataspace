plugins {
    `java-library`
}

val edcVersion: String by project
val edcGroup: String by project
val jupiterVersion: String by project
val mockitoVersion: String by project
val assertj: String by project

dependencies {
    implementation("${edcGroup}:identity-did-spi:${edcVersion}")
    implementation("${edcGroup}:data-plane-spi:${edcVersion}")
    implementation("com.azure:azure-identity:1.4.6")
    implementation("com.squareup.okhttp3:okhttp:4.9.3")
    implementation("org.apache.commons:commons-lang3:3.12.0")
}

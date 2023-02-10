description = "Identifies potential performance improvements"
dependencies {
    implementation("com.yahoo.platform.yui:yui:2.5.0")
    implementation("com.yahoo.platform.yui:yuicompressor:2.4.8")
    implementation("org.sonatype.plugins:yuicompressor-maven-plugin:1.1.0")
    implementation("commons-io:commons-io:2.11.0")
    implementation("com.tinify:tinify:latest.release")
    testImplementation(project(":testutils"))
}
repositories {
    mavenCentral()
}

zapAddOn {
    addOnName.set("Profiling Proxy")
    zapVersion.set("2.11.1")

    manifest {
        author.set("Group 07")
    }
}


crowdin {
    configuration {
        val resourcesPath = "org/zaproxy/addon/${zapAddOn.addOnId.get()}/resources/"
        tokens.put("%messagesPath%", resourcesPath)
        tokens.put("%helpPath%", resourcesPath)
    }
}

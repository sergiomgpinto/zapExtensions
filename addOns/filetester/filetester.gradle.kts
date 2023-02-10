description = "Scans files that are being downloaded."

zapAddOn {
    addOnName.set("File Tester")
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
dependencies {
    implementation("net.lingala.zip4j:zip4j:2.11.2")
    testImplementation(project(":testutils"))
}
repositories {
    mavenCentral()
}

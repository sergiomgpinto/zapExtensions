description = "Attack prevention extension."

zapAddOn {
    addOnName.set("Attack Prevention")
    zapVersion.set("2.11.1")

    manifest {
        author.set("Group 07")
    }
}

dependencies {
    testImplementation(project(":testutils"))
}

crowdin {
    configuration {
        val resourcesPath = "org/zaproxy/addon/${zapAddOn.addOnId.get()}/resources/"
        tokens.put("%messagesPath%", resourcesPath)
        tokens.put("%helpPath%", resourcesPath)
    }
}

dependencies {
    testImplementation(project(":testutils"))
}

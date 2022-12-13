rootProject.name = "glaskchat-parent"

setupChatLabSubproject("core")
setupChatLabSubproject("versions")
setupChatLabSubproject("api")

setupSubproject("glaskchat-versions-legacyversion") {
    projectDir = file("versions/legacyversion")
}

fun setupChatLabSubproject(name: String) {
    setupSubproject("glaskchat-$name") {
        projectDir = file(name)
    }
}

inline fun setupSubproject(name: String, block: ProjectDescriptor.() -> Unit) {
    include(name)
    project(":$name").apply(block)
}

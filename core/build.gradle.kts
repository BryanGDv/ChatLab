val libsPackage = property("libsPackage") as String

repositories {
    maven("https://repo.unnamed.team/repository/unnamed-public/")
    maven("https://repo.dmulloy2.net/repository/public")
    maven("https://nexus.scarsz.me/content/groups/public/")
    maven("https://m2.dv8tion.net/releases")
    maven("https://jitpack.io")
    maven("https://repo.codemc.org/repository/maven-public/")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    maven("https://repo.nickuc.com/maven2/")

    flatDir().dirs("libs")
    mavenCentral()
}

dependencies {
    implementation(project(":glaskchat-api"))
    implementation(project(":glaskchat-versions-legacyversion"))

    implementation("org.bstats:bstats-bukkit:2.2.1")
    implementation("redis.clients:jedis:3.7.1")

    implementation("me.fixeddev:commandflow-universal:0.4.5")
    implementation("me.fixeddev:commandflow-bukkit:0.4.0")

    implementation("net.kyori:adventure-api:4.10.0-SNAPSHOT")
    implementation("net.kyori:adventure-text-minimessage:4.2.0-SNAPSHOT")
    implementation("net.kyori:adventure-platform-bukkit:4.0.1")

    compileOnly("org.spigotmc:spigot-api:1.19-R0.1-SNAPSHOT")

    compileOnly("eu.locklogin:LockLogin-API:1.13.10")
    compileOnly("eu.locklogin:LockLogin-common:1.13.10")
    compileOnly("ml.karmaconfigs:KarmaAPI-Bukkit:1.3.1-SNAPSHOT")

    compileOnly("fr.xephi:authme:5.6.0-SNAPSHOT")

    compileOnly("com.discordsrv:discordsrv:1.24.0") {
        exclude("net.kyori", "adventure-platform-bukkit")
    }

    compileOnly("com.comphenix.protocol:ProtocolLib:4.4.0")
    compileOnly("com.github.LeonMangler:SuperVanish:6.2.6-2")
    compileOnly("com.github.MilkBowl:VaultAPI:1.7.1")
    compileOnly("me.clip:placeholderapi:2.10.10")
    compileOnly("com.nickuc.login:nlogin-api:9.3.1")

    compileOnly(fileTree("libs").include("*.jar"))
}

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}

tasks {
    processResources {
        filesMatching("plugin.yml") {
            expand("version" to project.version)
        }
    }

    shadowJar {
        archiveClassifier.set("")
        archiveFileName.set("GlaskChat-${project.version}.jar")

        destinationDirectory.set(file("$rootDir/bin/"))

        minimize()

        relocate("org.bstats", "${libsPackage}.bstats")
        relocate("redis.clients", "${libsPackage}.jedis")
        relocate("me.fixeddev", "${libsPackage}.commandflow")
        relocate("net.kyori", "${libsPackage}.adventure")
    }

    clean {
        delete("${rootDir}/bin/")
    }
}
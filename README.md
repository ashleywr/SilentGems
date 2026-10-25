# Silent's Gems

The first Minecraft mod I ever released. Adds a lot of random things, most notably a tool and armor system in Minecraft 1.12.2 and lower. See [Silent Gear](https://github.com/SilentChaos512/Silent-Gear) if you want tools and armor in later versions.

Other features include teleporters, an alternative to enchanting, and a magic system I will probably never completely finish. Check the [CurseForge page](https://minecraft.curseforge.com/projects/silents-gems) for details, I update it somewhat regularly and it details most features. Screenshots are out-of-date, so many things will look different in-game.

Only recent Minecraft versions are actively support, and only the latest version will receive new features in most cases. Older version will receive fixes for serious bugs only.

In Minecraft 1.17, the mod as it is now will be split into two mods, Silent's Gems: Base and Chaos. The number of gems will be drastically reduced so that more attention can be given to each one. The mod version number will change to 4.x.

- Silent's Gems: Base - This mod will contain gems, ores, common blocks and items, and gear materials. It will use the existing Curseforge project page.

- Silent's Gems: Chaos - This mod will contain chaos mechanics, chaos-related blocks and items, and the more overpowered features. Buggier, less stable features will also be moved here.

#### Major Versions

Major version updates intentionally break compatibility with older versions to rework and improve the mod. They only occur across some Minecraft updates.

- 4.x - Minecraft 1.17.x (early rework branch is 1.16.x)
- 3.x - Minecraft 1.13 to 1.16.x
- 2.x - Older than dinosaurs (1.9 - 1.12.2)
- 1.x - Older than dirt (1.6 - 1.8.9)

## Downloads

- [CurseForge](https://minecraft.curseforge.com/projects/silents-gems) (downloads and more information)
- [Modrinth](https://modrinth.com/mod/silents-gems)

## Links

- [Wiki](https://github.com/SilentChaos512/SilentGems/wiki) (tends to be out-of-date...)
- [GitHub repository](https://github.com/SilentChaos512/SilentGems) (source code)
- [Issue Tracker on GitHub](https://github.com/SilentChaos512/SilentGems/issues) (bug reports and feature requests)
- [Discord Server](https://discord.gg/Adyk9zHnUn) (#gems-and-chaos channel is the easiest way to get quick questions answered. **Do not use to report bugs!**)

### Note on Downloads

**I only upload builds to Minecraft CurseForge.** If you downloaded the mod from somewhere other than Curse/CurseForge or their launcher (or as part of a modpack in some cases), I cannot make any guarantees about the file or its contents, as it was uploaded without my permission.

<!-- ashley-release-information -->

## Ashley's builds

This is an unofficial maintenance fork. Original authors retain ownership and credit. See the [upstream project](https://github.com/SilentChaos512/SilentGems) for the original mod. Local changes do not imply upstream endorsement.

Licensing: **MIT**. Existing license files, copyright notices, attribution and any separate asset terms remain in force. This fork does not relicense upstream work.

Provided **AS IS**, without warranty of any kind. Maintenance, updates and support are best effort; compatibility with every modpack is not guaranteed.

### Downloads and installation

Download a runtime `.jar` from [GitHub Releases](https://github.com/ashleywr/SilentGems/releases). Use the Minecraft version and loader named in that release and install its required dependencies. Do not install `-sources`, `-dev`, `-slim`, `-common` or test JARs as the mod.

Close Minecraft, back up your world, and place the matching runtime JAR in the instance's `mods/` directory. Replace older versions of this same mod rather than loading duplicates. For content mods, install matching versions on the server and its clients unless the release explicitly says client-only or server-only.

### Building and reporting problems

Use Java 21 and the checked-in Gradle wrapper:

```powershell
.\gradlew.bat build
```

Build outputs are under `build/libs/` or each loader subproject's `build/libs/`. Some projects require the local development dependencies described above; those dependencies are not included in the download.

For a problem report, include the exact release filename, Minecraft and loader versions, relevant mod versions, `logs/latest.log`, and a crash report if one was written. Remove private information before sharing logs. Remove the JAR to uninstall; worlds containing this mod's blocks, entities or world generation may require restoring a backup.

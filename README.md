# 2D Java Game

A top-down 2D action game in Java (Swing), with tile maps drawn in
[Tiled](https://www.mapeditor.org/) and loaded from CSV.

## Features

- An `Entity` hierarchy for the player, enemies (soldiers) and non-player characters
- Weapons in a `Weapon` → `MeleeWeapon` / `RangedWeapon` hierarchy: fists, knife,
  gun and AK-47, with bullets for ranged weapons
- Items and an inventory: coins, keys, sword, health and mana potions
- Collision detection against tiles, objects and other entities
- Sprite animation and damage indicators
- Logging with Log4j 2

## Controls

| Key | Action |
|---|---|
| Z / Q / S / D | Move (AZERTY layout) |
| E | Pick up an item |
| Left click | Attack |

## Run

The project is set up for IntelliJ IDEA, with Log4j 2 declared in `pom.xml`:

1. Open the folder in IntelliJ IDEA and let it import the Maven dependencies.
2. Mark `src` as a Sources Root and `ressources` as a Resources Root (maps and
   sprites are loaded from the classpath, e.g. `/maps/level1.csv`).
3. Run `main.Main`.

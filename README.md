# RelicSMPCore

A professional SMP (Survival Multiplayer) plugin for Minecraft 1.21.11 centered around Relics, World Events, Progression, and Administration.

## Features

### Relics System
- **5 Default Relics**: Wind, Ender, Titan, Hunter, Fortune
- **Ownership Tracking**: Track who owned each relic and when
- **Cooldown Management**: Configurable cooldowns for each relic
- **Relic Framework**: Extensible framework for creating custom relics

### World Events Framework
- **Event Management**: Start, stop, schedule, and manage world events
- **Rewards System**: Configure rewards for event participation
- **Broadcasting**: Announce events to all players
- **Extensible Framework**: Create custom events easily

### Player Data Management
- **UUID Storage**: Store player data by UUID
- **Relic Tracking**: Track which relics each player owns
- **Statistics**: Track player statistics and progression
- **Last Seen**: Keep track of when players were last online

### GUI System
- **Main Menu**: Access all plugin features from a central hub
- **Relics Menu**: View and manage relics
- **Events Menu**: View active and scheduled events
- **Players Menu**: View player statistics
- **Settings Menu**: Configure plugin settings
- **Professional Design**: Decorated with stained glass borders and navigation buttons

### Administration
- **Reload Command**: Reload all plugin data with `/relicsmpcore reload`
- **Permission System**: Comprehensive permission nodes
- **Configuration Files**: YAML-based configuration system
- **Data Persistence**: Automatic save/load systems

## Installation

1. Build the plugin with Maven: `mvn clean install`
2. Copy the generated JAR to your server's `plugins` folder
3. Restart your server
4. Configuration files will be auto-generated in `plugins/RelicSMPCore/`

## Configuration

### Main Command
```
/relicsmpcore - Open the main GUI
/relicsmpcore reload - Reload all plugin data
```

### Permissions
- `relicsmpcore.command.main` - Use the main command
- `relicsmpcore.command.reload` - Reload the plugin
- `relicsmpcore.admin` - Admin access
- `relicsmpcore.relic.use` - Use relics
- `relicsmpcore.relic.give` - Give relics to players
- `relicsmpcore.event.view` - View events
- `relicsmpcore.event.manage` - Manage events

## Project Structure

```
com.relicsmpcore/
├── commands/         - Command handlers
├── gui/              - GUI implementations
├── listeners/        - Event listeners
├── managers/         - Data and system managers
├── models/           - Data models (Relic, Event, Player)
├── events/           - Event framework
├── relics/           - Relic definitions
├── storage/          - Data storage system
└── utils/            - Utility classes
```

## Configuration Files

- `config.yml` - Main plugin configuration
- `relics.yml` - Relic definitions and player relic data
- `players.yml` - Player data and statistics

## Requirements

- Java 21
- Paper 1.21.11
- Maven 3.8+

## Development

To build the project:
```bash
mvn clean install
```

The compiled JAR will be in the `target/` directory.

## Architecture

The plugin follows clean OOP architecture principles:

- **Separation of Concerns**: Each class has a single responsibility
- **Manager Pattern**: Managers handle all data operations
- **Model Pattern**: Models represent data entities
- **Factory Pattern**: GUIs are created on-demand
- **Listener Pattern**: Events are handled through listeners

## License

This plugin is provided as-is for use on Minecraft servers.

## Support

For issues or questions, please contact the plugin developer.

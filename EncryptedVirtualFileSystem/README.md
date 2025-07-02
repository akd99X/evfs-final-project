Encrypted Virtual File System (EVFS)


# Features
- Create, Read, Delete, List files
- XOR-based content encryption
- Persistent file table (`evfs.index`)
- Disk file simulation (`evfs.disk`)
- Access log tracking (`access.log`)
- Stats summary (disk usage, created/deleted file count)


# How to Run

1. Compile and run `Main.java` in VS Code or IntelliJ.
2. Use the following commands at the prompt:

#  Commands
- `create <filename>` - Adds a new encrypted file
- `read <filename>` - Reads and decrypts file content
- `delete <filename>` - Deletes the file from the index
- `list` - Lists all stored files
- `log` - Shows all access logs
- `stats` - Displays storage and session stats
- `exit` - Exits the program

# Encryption
Simple XOR-based encryption (`Encryptor.simpleXOR`) to obfuscate file content in `evfs.disk`.

# Logging
All actions (create, read, delete) are logged in `access.log` with timestamps.

# Persistence
File metadata is saved in `evfs.index` so files remain accessible between sessions.

# Demo
Include a screen-recorded demo.
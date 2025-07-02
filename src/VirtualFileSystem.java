import java.io.*;
import java.util.*;
import utils.Logger;

public class VirtualFileSystem {
    private static final String DISK_FILE = "evfs.disk";
    private static final String INDEX_FILE = "evfs.index";
    private static final String LOG_FILE = "access.log";

    private Map<String, FileEntry> fileTable = new HashMap<>();
    private int createdCount = 0;
    private int deletedCount = 0;

    public VirtualFileSystem() {
        loadFileTable();
        Logger.log("Virtual File System initialized.");
    }

    public void createFile(String filename, String content) {
        if (fileTable.containsKey(filename)) {
            System.out.println("❌ File already exists.");
            return;
        }

        try (RandomAccessFile disk = new RandomAccessFile(DISK_FILE, "rw")) {
            disk.seek(disk.length());
            long position = disk.getFilePointer();

            String encrypted = Encryptor.simpleXOR(content);

            disk.writeUTF(filename);
            disk.writeLong(position);
            disk.writeUTF(encrypted);

            fileTable.put(filename, new FileEntry(filename, position));
            createdCount++;
            Logger.log("Created file: " + filename);
            saveFileTable(); // Save index on create
            System.out.println("✅ File '" + filename + "' created.");
        } catch (IOException e) {
            System.err.println("⚠️ Error writing file: " + e.getMessage());
        }
    }

    public void readFile(String filename) {
        FileEntry entry = fileTable.get(filename);
        if (entry == null) {
            System.out.println("❌ File not found.");
            return;
        }

        try (RandomAccessFile disk = new RandomAccessFile(DISK_FILE, "r")) {
            disk.seek(entry.position);
            disk.readUTF(); // skip name
            disk.readLong(); // skip position
            String encrypted = disk.readUTF();
            String decrypted = Encryptor.simpleXOR(encrypted);

            Logger.log("Read file: " + filename);
            System.out.println("📄 Content of '" + filename + "':");
            System.out.println(decrypted);
        } catch (IOException e) {
            System.err.println("⚠️ Error reading file: " + e.getMessage());
        }
    }

    public void deleteFile(String filename) {
        FileEntry entry = fileTable.remove(filename);
        if (entry == null) {
            System.out.println("❌ File not found.");
            return;
        }

        deletedCount++;
        Logger.log("Deleted file: " + filename);
        saveFileTable(); // Save index after delete
        System.out.println("🗑️ File '" + filename + "' deleted from file table.");
    }

    public void listFiles() {
        if (fileTable.isEmpty()) {
            System.out.println("📂 No files in EVFS.");
            return;
        }

        System.out.println("📁 Files stored in EVFS:");
        for (String name : fileTable.keySet()) {
            System.out.println(" - " + name);
        }
    }

    public void showLog() {
        System.out.println("📜 Access Log:");
        Logger.readLog();
    }

    public void showStats() {
        File disk = new File(DISK_FILE);
        long size = disk.exists() ? disk.length() : 0;

        System.out.println("📊 EVFS Statistics:");
        System.out.println(" - Files created (this session): " + createdCount);
        System.out.println(" - Files deleted (this session): " + deletedCount);
        System.out.println(" - Active files now: " + fileTable.size());
        System.out.println(" - Disk file size: " + size + " bytes");
    }

    private void saveFileTable() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(INDEX_FILE))) {
            out.writeObject(fileTable);
        } catch (IOException e) {
            System.err.println("⚠️ Failed to save index: " + e.getMessage());
        }
    }

    private void loadFileTable() {
        File index = new File(INDEX_FILE);
        if (!index.exists()) return;

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(INDEX_FILE))) {
            Object obj = in.readObject();
            if (obj instanceof Map<?, ?>) {
                Map<?, ?> loaded = (Map<?, ?>) obj;
                for (Object key : loaded.keySet()) {
                    if (key instanceof String && loaded.get(key) instanceof FileEntry) {
                        fileTable.put((String) key, (FileEntry) loaded.get(key));
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("⚠️ Failed to load index: " + e.getMessage());
        }
    }
}

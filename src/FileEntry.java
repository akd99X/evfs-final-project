import java.io.Serializable;

public class FileEntry implements Serializable {
    public String name;
    public long position;

    public FileEntry(String name, long position) {
        this.name = name;
        this.position = position;
    }
}

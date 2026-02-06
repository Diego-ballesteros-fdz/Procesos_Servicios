package obj;

public class RemoteFile {

    private final String name;
    private final long size;
    private final boolean directory;

    public RemoteFile(String name, long size, boolean directory) {
        this.name = name;
        this.size = size;
        this.directory = directory;
    }

    public String getName() {

        return name;
    }

    public long getSize() {

        return size;
    }

    public boolean isDirectory() {

        return directory;
    }
}

package fileexplorer;

public class File implements Node, Cloneable {

    private String name;
    private String content;
    private FileAlias fileAlias;
    private Node parent;

    public File(String name) {
        super();
        this.name = name;
    }

    public File(String name, String content) {
        super();
        this.name = name;
        this.content = content;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public FileAlias getFileAlias() {
        return fileAlias;
    }

    public void setFileAlias(FileAlias fileAlias) {
        this.fileAlias = fileAlias;
    }

    @Override
    public String toString() {
        String text = "(File) " + name;
        if (this.fileAlias != null) {
            text += " (alias)";
        }
        return text;
    }

    @Override
    public void rename(String newName) {
        this.name = newName;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public void setParent(Node parent) {
        this.parent = parent;
    }

    @Override
    public Node getParent() {
        return this.parent;
    }

}

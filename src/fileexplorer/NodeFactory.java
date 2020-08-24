package fileexplorer;

public class NodeFactory {

    public static Node CreateFile(String name, String content) {
        return new File(name, content);
    }

    public static Node CreateFolder(String name) {
        return new Folder(name);
    }
}

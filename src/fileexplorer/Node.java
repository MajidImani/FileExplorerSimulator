package fileexplorer;

public interface Node {
    public void rename(String newName);
    public String getName();
    public void setName(String newName);
    public void setParent(Node parent);
    public Node getParent();
}

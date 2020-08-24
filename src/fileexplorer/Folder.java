package fileexplorer;

import java.util.ArrayList;
import java.util.List;

public class Folder implements Node, Cloneable {

    private String name;
    private List<Node> elements;
    private Node parent;
    
    public Folder(String name) {
        super();
        this.elements = new ArrayList<Node>();
        this.name = name;
    }

    public void addElement(Node node) {
        this.elements.add(node);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public List<Node> getElements() {
        return elements;
    }

    public void setElements(List<Node> elements) {
        this.elements = elements;
    }

    @Override
    public String toString() {
        return "(Folder) " + name;
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

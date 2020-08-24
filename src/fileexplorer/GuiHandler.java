package fileexplorer;

import java.util.logging.Level;
import java.util.logging.Logger;
import montefiore.ulg.ac.be.graphics.ExplorerEventsHandler;
import montefiore.ulg.ac.be.graphics.ExplorerSwingView;
import montefiore.ulg.ac.be.graphics.LevelException;
import montefiore.ulg.ac.be.graphics.NoParentNodeException;
import montefiore.ulg.ac.be.graphics.NoPreviousInsertedNodeException;
import montefiore.ulg.ac.be.graphics.NoSelectedNodeException;
import montefiore.ulg.ac.be.graphics.NullHandlerException;
import montefiore.ulg.ac.be.graphics.RootAlreadySetException;

public class GuiHandler implements ExplorerEventsHandler {

    private ExplorerSwingView esv;

    GuiHandler(String[] args) throws NullHandlerException {
        this.esv = new ExplorerSwingView(this);

        try {
            // First step to do before anything !!! 
            this.esv.setRootNode(new Folder("Root")); // set the root node with a silly "Folder" object
        } catch (RootAlreadySetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createAliasEvent(Object selectedNode) {
        if (showPopupErro(FileValidator.validateCreateAlias(selectedNode))) {
            return;
        }

        if (this.esv.isRootNodeSelected()) {
            this.esv.showPopupError("create alias for root is not allowed");
            return;
        }
        File file = (File) selectedNode;
        FileAlias alias = FileAliasFactory.CreateFileAlias(file);
        file.setFileAlias(alias);
        this.esv.refreshTree();
    }

    @Override
    public void createArchiveEvent(Object selectedNode) {
        // TODO Auto-generated method stub
    }

    @Override
    public void createCopyEvent(Object selectedNode) {
        if (this.esv.isRootNodeSelected()) {
            this.esv.showPopupError("copy of root is not allowed");
            return;
        }

        Node clone = null;
        if (FileValidator.isFileInstance(selectedNode)) {
            File file = (File) selectedNode;
            try {
                clone = (File) file.clone();
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(GuiHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            Folder folder = (Folder) selectedNode;
            try {
                clone = (Folder) folder.clone();
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(GuiHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        clone.setName(clone.getName() + " (copy)");
        Folder parent = (Folder) clone.getParent();
        parent.addElement(clone);
        addElementToParent(clone);
    }

    private void addElementToParent(Node clone) {
        try {
            this.esv.addNodeToParentNode(clone);
            if (!FileValidator.isFileInstance(clone)) {
                refineChildNodesInTree(clone, 1);
            }
            this.esv.refreshTree();
        } catch (NoSelectedNodeException ex) {
            Logger.getLogger(GuiHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoParentNodeException ex) {
            Logger.getLogger(GuiHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void createFileEvent(Object selectedNode) {
        if (showPopupErro(FileValidator.validateCreateFile(selectedNode))) {
            return;
        }

        String[] fileInfo = this.esv.fileMenuDialog();
        if (!FileValidator.validateEmptyFileInfo(fileInfo)) {
            return;
        }

        String fileName = fileInfo[0];
        if (showPopupErro(FileValidator.validateEmptyFileName(fileName))) {
            return;
        }

        String fileContent = fileInfo[1];
        Node file = NodeFactory.CreateFile(fileName, fileContent);
        Folder folder = (Folder) selectedNode;
        folder.addElement(file);
        file.setParent(folder);
        addNodeToSelectedNode(file);
    }

    private void addNodeToSelectedNode(Node node) {
        try {
            this.esv.addNodeToSelectedNode(node);
            this.esv.refreshTree();
        } catch (NoSelectedNodeException ex) {
            Logger.getLogger(GuiHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean showPopupErro(String errorMessage) {
        if (errorMessage != null) {
            this.esv.showPopupError(errorMessage);
            return true;
        }
        return false;
    }

    @Override
    public void createFolderEvent(Object selectedNode) {
        String errorMessage = FolderValidator.validateCreateFolder(selectedNode);
        if (showPopupErro(errorMessage)) {
            return;
        }

        String folderName = this.esv.folderMenuDialog();
        if (!FolderValidator.validateEmptyFileName(folderName)) {
            return;
        }

        Node f = NodeFactory.CreateFolder(folderName);
        Folder folder = (Folder) selectedNode;
        folder.addElement(f);
        f.setParent(folder);
        addNodeToSelectedNode(f);

    }

    @Override
    public void doubleClickEvent(Object selectedNode) {
        this.esv.getTextAreaManager().clearAllText();
        if (FileValidator.isFileInstance(selectedNode)) {
            this.esv.getTextAreaManager().appendText(((File) selectedNode).getContent());
        } else {
            recursivePrintContent((Folder) selectedNode, 0);
        }
        this.esv.refreshTree();
    }

    @Override
    public void eventExit() {
        // TODO Auto-generated method stub
    }

    private void recursivePrintContent(Folder folder, int level) {
        for (int i = 0; i < level; i++) {
            this.esv.getTextAreaManager().appendText(" ");
        }
        this.esv.getTextAreaManager().appendText(folder.getName() + "\n");
        if (!folder.getElements().isEmpty()) {
            for (Node node : folder.getElements()) {
                if (FileValidator.isFileInstance(node)) {
                    for (int i = 0; i < level + 1; i++) {
                        this.esv.getTextAreaManager().appendText(" ");
                    }
                    this.esv.getTextAreaManager().appendText(((File) node).getContent() + "\n");
                } else {
                    recursivePrintContent((Folder) node, level + 1);
                }
            }
            level++;
        }
    }

    private void refineChildNodesInTree(Node clone, int level) {
        Folder folder = (Folder) clone;
        if (!folder.getElements().isEmpty()) {
            for (Node node : folder.getElements()) {
                try {
                    this.esv.addNodeToLastInsertedNode(node, level);
                } catch (NoPreviousInsertedNodeException ex) {
                    Logger.getLogger(GuiHandler.class.getName()).log(Level.SEVERE, null, ex);
                } catch (LevelException ex) {
                    Logger.getLogger(GuiHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (!FileValidator.isFileInstance(node)) {
                    refineChildNodesInTree((Folder) node, level + 1);
                }
            }
        }
    }
}

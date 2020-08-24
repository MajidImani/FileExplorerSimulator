package fileexplorer;

public class FolderValidator {

    public static String validateCreateFolder(Object selectedNode) {
        if (selectedNode instanceof File) {
            return "add new folder to a file is not allowed";
        }
        return null;
    }

    public static boolean validateEmptyFileName(String folderName) {
        if (folderName == null || folderName.length() <= 0) {
            return false;
        }
        return true;
    }
}

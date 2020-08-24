package fileexplorer;

public class FileValidator {

    public static String validateCreateFile(Object selectedNode) {
        if (selectedNode instanceof File) {
            return "add new file to a file is not allowed";
        }
        return null;
    }

    public static boolean validateEmptyFileInfo(String[] fileInfo) {
        if (fileInfo == null || fileInfo.length <= 0) {
            return false;
        }
        return true;
    }

    public static String validateEmptyFileName(String fileName) {
        if (fileName == null || fileName.length() <= 0) {
            return "name of file is empty";
        }
        return null;
    }

    public static String validateCreateAlias(Object selectedNode) {
        if (selectedNode instanceof Folder) {
            return "create alias for a folder is not allowed";
        }
        return null;
    }

    public static boolean isFileInstance(Object selectedNode) {
        return selectedNode instanceof File;
    }

}

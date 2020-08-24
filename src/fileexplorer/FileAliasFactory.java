package fileexplorer;

public class FileAliasFactory {
    public static FileAlias CreateFileAlias(File file){
        return new FileAlias(file);
    }
}

package service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class WriteFileAdapter implements WriteFileToNewFile, WriteFileToExistedFile {
    private WriteFileToNewFile writeFileToNewFile;
    private WriteFileToExistedFile writeFileToExistedFile;

    public WriteFileAdapter(WriteFileToNewFile writeFile) {
        this.writeFileToNewFile = writeFile;
    }

    public WriteFileAdapter(WriteFileToExistedFile writeFile) {
        this.writeFileToExistedFile = writeFile;
    }

    public void writeFileToNewFile() {
        writeFileToNewFile.writeFileToNewFile();
    }

    public String writeFileToExistedFile(String  mapFile, List<String> lines) {
        return writeFileToExistedFile.writeFileToExistedFile("", new LinkedList<String>());
    }
}

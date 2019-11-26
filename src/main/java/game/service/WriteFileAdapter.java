package service;

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

    public String writeFileToNewFile(String mapFile, List<String> lines) {
        return writeFileToNewFile.writeFileToNewFile(mapFile, lines);
    }

    public String writeFileToExistedFile(String mapFile, List<String> lines) {
        return writeFileToExistedFile.writeFileToExistedFile(mapFile, lines);
    }
}

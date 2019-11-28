package service;

import java.util.List;

/**
 * Write file Adapter
 */
public class WriteFileAdapter implements WriteFileToNewFile, WriteFileToExistedFile {
    private WriteFileToNewFile writeFileToNewFile;
    private WriteFileToExistedFile writeFileToExistedFile;

    /**
     * write new file constructor
     * @param writeFile write new file object
     */
    public WriteFileAdapter(WriteFileToNewFile writeFile) {
        this.writeFileToNewFile = writeFile;
    }

    /**
     * write existed file constructor
     * @param writeFile write existed file object
     */
    public WriteFileAdapter(WriteFileToExistedFile writeFile) {
        this.writeFileToExistedFile = writeFile;
    }

    /**
     * Write new file
     * @param mapFile file name
     * @param lines commands
     * @return message
     */
    public String writeFileToNewFile(String mapFile, List<String> lines) {
        return writeFileToNewFile.writeFileToNewFile(mapFile, lines);
    }

    /**
     * Write existed file
     * @param mapFile file name
     * @param lines commands
     * @return message
     */
    public String writeFileToExistedFile(String mapFile, List<String> lines) {
        return writeFileToExistedFile.writeFileToExistedFile(mapFile, lines);
    }
}

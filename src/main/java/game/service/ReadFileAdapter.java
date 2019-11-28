package service;

/**
 * Adapter pattern
 */
public class ReadFileAdapter implements ReadFileFromEmpty, ReadFileFromExistedFile {
    private ReadFileFromEmpty readFileFromEmpty;
    private ReadFileFromExistedFile readFileFromExistedFile;

    /**
     * Read file adapter
     * @param readFile readFile object
     */
    public ReadFileAdapter(ReadFileFromEmpty readFile) {
        this.readFileFromEmpty = readFile;
    }

    /**
     * Read File adapter
     * @param readFile read file from existed object
     */
    public ReadFileAdapter(ReadFileFromExistedFile readFile) {
        this.readFileFromExistedFile = readFile;
    }

    /**
     * Read file
     * @param mapFile read file from empty
     * @return file
     */
    @Override
    public String readFileFromEmpty(String mapFile) {
        return readFileFromExistedFile.readFileFromExistedFile(mapFile);
    }

    /**
     * Read File
     * @param mapFile read file from existed
     * @return  file
     */
    @Override
    public String readFileFromExistedFile(String mapFile) {
        return readFileFromEmpty.readFileFromEmpty(mapFile);
    }
}

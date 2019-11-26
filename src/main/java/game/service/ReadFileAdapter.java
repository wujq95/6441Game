package service;

public class ReadFileAdapter implements ReadFileFromEmpty, ReadFileFromExistedFile {
    private ReadFileFromEmpty readFileFromEmpty;
    private ReadFileFromExistedFile readFileFromExistedFile;

    public ReadFileAdapter(ReadFileFromEmpty readFile) {
        this.readFileFromEmpty = readFile;
    }

    public ReadFileAdapter(ReadFileFromExistedFile readFile) {
        this.readFileFromExistedFile = readFile;
    }

    @Override
    public String readFileFromEmpty(String mapFile) {
        return readFileFromExistedFile.readFileFromExistedFile(mapFile);
    }

    @Override
    public String readFileFromExistedFile(String mapFile) {
        return readFileFromExistedFile.readFileFromExistedFile(mapFile);
    }
}

package service;

public class ReadFileAdapter implements ReadFileFromEmpty, ReadFileFromExistedFile {
    private ReadFileFromEmpty readFileFromEmpty;
    private ReadFileFromExistedFile readFileFromExistedFile;

    public ReadFileAdapter(ReadFileFromEmpty readFile) {
        this.readFileFromEmpty = readFile;
    }

    public ReadFileAdapter(ReadFileFromExistedFile readFile) {
        this.readFileFromExistedFile =readFile;
    }

    @Override
    public String readFileFromEmpty(String mapFile) {
        readFileFromEmpty.readFileFromEmpty("");

        return "";
    }

    @Override
    public String readFileFromExistedFile(String mapFile) {
        readFileFromExistedFile.readFileFromExistedFile("");

        return "";
    }
}

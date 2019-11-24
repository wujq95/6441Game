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

    public void readFileFromEmpty() {
        readFileFromEmpty.insertIntoHole(str);
    }

    public void readFileFromExistedFile() {
        readFileFromExistedFile.insert(msg);
    }

}

package service;

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
        writeFileToNewFile.writeFileToNewFile(str);
    }

    public void writeFileToExistedFile() {
        writeFileToExistedFile.writeFileToExistedFile(msg);
    }
}

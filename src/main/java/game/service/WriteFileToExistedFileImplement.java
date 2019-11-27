package service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Write existed file interface implementation
 */
public class WriteFileToExistedFileImplement implements WriteFileToExistedFile {
    /**
     * write existed file
     * @param fileName file name
     * @param lines commands
     * @return message
     */
    @Override
    public String writeFileToExistedFile(String fileName, List<String> lines) {
        File mapFile = new File(fileName);

        mapFile.delete();
        File newFile = new File(fileName);

        String returnMsg = "";
        try {
            FileWriter fileWriter = new FileWriter(newFile, false);
            for (String str : lines) {
                fileWriter.write(str + System.lineSeparator());
            }
            fileWriter.close();

            returnMsg = "saveMap success";
            return returnMsg;
        } catch (IOException e) {
            returnMsg = "saveMap failed";
            return returnMsg;
        }
    }
}

package service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * write new file interface implementation
 */
public class WriteFileToNewFileImplement implements WriteFileToNewFile {
    /**
     * write new file
     * @param fileName file name
     * @param lines commands
     * @return message
     */
    @Override
    public String writeFileToNewFile(String fileName, List<String> lines) {
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

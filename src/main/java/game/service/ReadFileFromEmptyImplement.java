package service;

import java.io.File;
import java.io.IOException;

/**
 * Empty file reading function implementation
 */
public class ReadFileFromEmptyImplement implements ReadFileFromEmpty {
    /**
     * Reading file
     * @param fileName file name
     * @return message
     */
    @Override
    public String readFileFromEmpty(String fileName) {
        File file = new File(fileName);

        String returnMsg = "";
        try {
            //create an empty file
            boolean fileCreated = file.createNewFile();
            if (fileCreated) {
                returnMsg = "create new file success";
            } else {
                returnMsg = "create new file fail!";
            }

            return returnMsg;
        } catch (IOException e) {
            returnMsg = e.getMessage();
            return returnMsg;
        }
    }
}

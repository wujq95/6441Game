package service;

import java.util.List;

/**
 * interface of write existed file
 */
public interface WriteFileToExistedFile {
    /**
     * Mandatory Method
     * @param fileName file name
     * @param lines commands
     * @return message
     */
    public String writeFileToExistedFile(String fileName, List<String> lines);
}

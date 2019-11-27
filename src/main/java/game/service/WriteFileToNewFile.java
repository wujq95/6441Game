package service;

import java.util.List;

/**
 * write new file interface
 */
public interface WriteFileToNewFile {
    /**
     * Mandatory method
     * @param fileName file name
     * @param lines commands
     * @return message
     */
    public String writeFileToNewFile(String fileName, List<String> lines) ;
}

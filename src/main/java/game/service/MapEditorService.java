package service;

import model.MapGraph;

import java.io.File;
import java.io.IOException;

public class MapEditorService {

    private MapGraph mapGraph;
    public void editMap(String fileName) {

        File mapFile = new File(fileName);
        //if the map file exists
        if (mapFile.isFile()) {

        } else {
            File file = new File(fileName);
            try {
                //create an empty file
                file.createNewFile();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}

package service;

import model.MapGraph;

import java.io.File;

public class MapEditorService {

    private MapGraph mapGraph;
    public void editMap(String fileName) {

        File mapFile = new File(fileName);
        //if the map file exists
        if (mapFile.isFile()) {

        } else {
            File file = new File(fileName);
        }
    }
}

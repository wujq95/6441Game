package controller;

import javafx.scene.paint.Color;
import java.util.ArrayList;

public class ColorController {
    ArrayList<Color>palette = new ArrayList<>();

    public ColorController() {
        palette.add(Color.rgb(85,150,126));
        palette.add(Color.rgb(130,131,167));
        palette.add(Color.rgb(231,126,77));
        palette.add(Color.rgb(207,170,158));
        palette.add(Color.rgb(252,190,50));
        palette.add(Color.rgb(155,174,200));
        palette.add(Color.rgb(157,195,193));
        palette.add(Color.rgb(155,130,129));
    }

    public ArrayList<Color> getPalette() {
        return palette;
    }

    public void setPalette(ArrayList<Color> palette) {
        this.palette = palette;
    }
}

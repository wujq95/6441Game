package controller;

import javafx.scene.paint.Color;

import java.util.LinkedList;
import java.util.List;

public class ColorController {
    LinkedList<Color> palette = new LinkedList<>();

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

    public List<Color> getPalette() {
        return palette;
    }

    public void setPalette(LinkedList<Color> palette) {
        this.palette = palette;
    }

    public Color pickOneColor(){
        if(!palette.isEmpty())
            return palette.removeFirst();
        else
            return Color.BLACK;
    }

    public void returnOneColor(Color color){
        palette.addLast(color);
    }
}

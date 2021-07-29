package SR;

import java.awt.*;

public class ScreenGetter {
    public int getscreen(char c) {
        Toolkit screenkit = Toolkit.getDefaultToolkit();
        //to get height
        if (c=='h') {
            return screenkit.getScreenSize().height;
        }
        //to get width
        else if (c=='w') {
            return screenkit.getScreenSize().width;
        }
        //every other character will return 0
        else {
            return 0;
        }
    }
}

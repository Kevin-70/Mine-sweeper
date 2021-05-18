package xyz.view;

import java.awt.*;

public class ItemUtil {
    private final static Image mask;

    static {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        mask = toolkit.getImage("src/xyz/view/pic/maskgrid.png");//Inkedgrid mine.jpg
    }

    public static Image genItem (int i) {
        // TODO: This is just a sample. You should implement the method here to provide the 可见的 component according to the argument i
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image face=mask;
        if(i==-1) { face = toolkit.getImage("src/xyz/view/pic/Inkedgrid mine.jpg"); }
        if(i==-2){face = toolkit.getImage("src/xyz/view/pic/Flag.jpg");}
        if(i==-3){face = toolkit.getImage("src/xyz/view/pic/nmb.jpg");}
        if(i==1){face = toolkit.getImage("src/xyz/view/pic/nm1.jpg");}
        if(i==2){face = toolkit.getImage("src/xyz/view/pic/nm2.jpg");}
        if(i==3){face = toolkit.getImage("src/xyz/view/pic/nm3.jpg");}
        if(i==4){face = toolkit.getImage("src/xyz/view/pic/nm4.jpg");}
        if(i==5){face = toolkit.getImage("src/xyz/view/pic/nm5.jpg");}
        if(i==6){face = toolkit.getImage("src/xyz/view/pic/nm6.jpg");}
        if(i==7){face = toolkit.getImage("src/xyz/view/pic/nm7.jpg");}
        if(i==8){face = toolkit.getImage("src/xyz/view/pic/nm8.jpg");}
        if(i==9){face = toolkit.getImage("src/xyz/view/pic/nm0.jpg");}
        return face;
    }
}

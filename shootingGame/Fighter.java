import java.awt.*;
class Fighter extends MovingObject {
    boolean lflag;
    boolean rflag;
    boolean uflag;
    boolean dflag;
    boolean sflag;
    int delaytime;

    Fighter(int apWidth, int apHeight) {
        x = (int)(apWidth/4);
        y = (int)(apHeight*0.4);
        dx = 7;
        dy = 7;
        w = 5;
        h = 5;
        hp = 10;
        lflag = false;
        rflag = false;
        uflag = false;
        dflag = false;
        sflag = false;
        delaytime = 5;
    }
    void revive(int apWidth, int apHeight) {

    }
    void move(Graphics buf, int apWidth, int apHeight) {
        buf.setColor(Color.blue);
        buf.fillRect(x-w, y-h, 2*w, 2*h);
        if (lflag && !rflag && x>w) x = x-dx;
        if (rflag && !lflag && x<apWidth - w) x = x+dx;
        if (uflag && !dflag && y>h) y = y-dx;
        if (dflag && !uflag && y<apHeight - h) y = y+dy;
    }
}
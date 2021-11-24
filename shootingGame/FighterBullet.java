import java.awt.*;
class FighterBullet extends MovingObject {

    FighterBullet() {
        w = h = 3;
        dx = 0;
        dy = -6;
        hp = 0;
    }

    void move(Graphics buf, int apWidth, int apHeight) {
        if (hp>0) {
            buf.setColor(Color.green);
            buf.fillOval(x-w, y-h, 2*w, 2*h);
            if (y > 0 && y < apHeight && x > 0 && x < apWidth){
                y = y+ dy;
                x = x+ dx;
            }else{
                hp = 0;
            }
        } 
    }

    void revive(int x, int y) {
        this.x = x;
        this.y = y;
        hp = 1;
    }

    void revive(int x, int y, int dx) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        hp = 1;
    }

    void revive(int x, int y, int dx, int dy) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        hp = 1;
    }
}
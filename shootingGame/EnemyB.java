import java.awt.*;

public class EnemyB extends EnemyA{

    int BltNum;
    FighterBullet EnmyBlt[];
    EnemyB(int apWidth, int apHeight) {
        super(apWidth, apHeight);
        BltNum = 10;
        EnmyBlt = new FighterBullet[BltNum];

        for (int i=0; i<BltNum; i++) {
            EnmyBlt[i] = new FighterBullet();
            EnmyBlt[i].dy = 5;
        }
    }

    void move(Graphics buf, int apWidth, int apHeight) {
        buf.setColor(Color.red);
        if (hp>0) {
            buf.drawOval(x-w,y-h,2*w,2*h);
            x = x + dx;
            y = y + dy;
            if (y>apHeight+h) hp=0;
        }

        if (Math.random() > 0.99) {
            for (int i=0; i<BltNum; i++) {
                if (EnmyBlt[i].hp == 0) {
                    EnmyBlt[i].revive(this.x, this.y, this.dx);
                    break;
                }
            }
        }

        for (int i=0; i<BltNum; i++) {
            if (EnmyBlt[i].hp != 0) {
                EnmyBlt[i].move(buf, apWidth, apHeight);
            }
        }


    }

    void move(Graphics buf, int apWidth, int apHeight, int ftrX, int ftrY) {
        buf.setColor(Color.red);
        if (hp>0) {
            buf.drawOval(x-w,y-h,2*w,2*h);
            x = x + dx;
            y = y + dy;
            if (y>apHeight+h) hp=0;

            if (Math.random() > 0.99) {
                for (int i=0; i<BltNum; i++) {
                    if (EnmyBlt[i].hp == 0) {
                        EnmyBlt[i].revive(this.x, this.y, (int)(bltDx(Math.abs(ftrX-x), Math.abs(ftrY-y))*Math.signum(ftrX-x)), (int)(bltDy(Math.abs(x-ftrX), Math.abs(y-ftrY))*Math.signum(ftrY-y)));
                        break;
                    }
                }
            }

            for (int i=0; i<BltNum; i++) {
                if (EnmyBlt[i].hp != 0) {
                    EnmyBlt[i].move(buf, apWidth, apHeight);
                }
            }

        }

    }


    int bltDx (int absX, int absY) {

        return (int)Math.sqrt(5/(absY/absX+1));
    }

    int bltDy (int absX, int absY) {

        return (int)Math.sqrt(5/(absX/absY+1));
    }
    
}

import java.awt.*;
import java.awt.event.*;

public class GameMaster extends Canvas implements KeyListener {
    Image buf;
    Graphics buf_gc;
    Dimension d;
    private int imgW, imgH;

    private int enmyAnum = 15;
    private int enmyBnum = 20;
    private int ftrBltNum = 10;
    private int mode = -1;
    private int i,j;
    public int backW = 200;
    public int backH = 150;
    Image BackImg = this.getToolkit().getImage("backImage1.png");

    Fighter ftr;
    FighterBullet ftrBlt[] = new FighterBullet[ftrBltNum];
    EnemyA enmyA[] = new EnemyA[enmyAnum];
    EnemyB enmyB[] = new EnemyB[enmyBnum];

    public int score;

    GameMaster(int imgW, int imgH) {
        this.imgW = imgW;
        this.imgH = imgH;
        setSize(imgW, imgH);

        addKeyListener(this);

        ftr = new Fighter(imgW, imgH);
        for (i=0; i<ftrBltNum; i++)ftrBlt[i] = new FighterBullet();
        for (i=0; i<enmyAnum; i++)enmyA[i] = new EnemyA(imgW, imgH);
        for (i=0; i<enmyBnum; i++)enmyB[i] = new EnemyB(imgW, imgH);
    }

    public void addNotify(){
        super.addNotify();
        buf = createImage(imgW, imgH);
        buf_gc = buf.getGraphics();
    }

    public void paint(Graphics g) {
        buf_gc.setColor(Color.white);
        buf_gc.fillRect(0,0,imgW,imgH);
        if (backH+150 < 480){
            buf_gc.drawImage(BackImg, 0, 0, imgW,  imgH, backW, backH, backW+200, backH+150, this);
            backH++;
            //System.out.println("paint");
        }else{
            backH = 0;
        }
        
        switch (mode) {
        case -2:
            buf_gc.setColor(Color.white);
            buf_gc.drawString("   == Game over ==   ", imgW/2-80, imgH/2+20);
            buf_gc.drawString("     Hit Space key     ", imgW/2-80, imgH/2-20);
            buf_gc.drawString("score:"+score, imgW/2-100, imgH/2-40);

            for (i=0; i<enmyAnum; i++) enmyA[i].hp = 0;
            for (i=0; i<enmyBnum; i++) {
                enmyB[i].hp = 0;
                for (j=0; j<enmyB[i].BltNum; j++){
                    enmyB[i].EnmyBlt[j].hp=0;
                }
            }
            break;
        case -1:
            buf_gc.setColor(Color.white);
            buf_gc.drawString("   == Shooting Game Title ==   ", imgW/2-80, imgH/2+20);
            buf_gc.drawString("Hit Space bar to start game", imgW/2-80, imgH/2-20);
            score=0;
            break;
        default:
            buf_gc.setColor(Color.white);
            buf_gc.drawString("HP:"+ ftr.hp, ftr.x+10, ftr.y+10);
            buf_gc.drawString(""+score, 300, 300);
            score++;

            makeEnmyA: if (Math.random() < 0.1){
                for(i=0; i<enmyAnum; i++) {
                    if (enmyA[i].hp == 0) {
                        enmyA[i].revive(imgW, imgH);
                        break makeEnmyA;
                    } 
                }
            }

            makeEnmyB: if (Math.random() < 0.1){
                for(i=0; i<enmyBnum; i++) {
                    if (enmyB[i].hp == 0) {
                        enmyB[i].revive(imgW, imgH);
                        break makeEnmyB;
                    } 
                }
            }

            if (ftr.sflag == true && ftr.delaytime == 0) {
                for(i=0; i<ftrBltNum; i++) {
                    if (ftrBlt[i].hp == 0) {
                        ftrBlt[i].revive(ftr.x, ftr.y);
                        ftr.delaytime = 5;
                        break;
                    }
                }
            }else if (ftr.delaytime > 0){
                ftr.delaytime--;
            }

            for (i=0; i<enmyAnum; i++){
                if (enmyA[i].hp>0) {
                    ftr.collisionCheck(enmyA[i]);
                    for (j=0; j<ftrBltNum; j++){
                        if (ftrBlt[j].hp > 0){
                            ftrBlt[j].collisionCheck(enmyA[i]);
                        }
                    }
                }
            }

            for (i=0; i<enmyBnum; i++){
                if (enmyB[i].hp>0) {
                    ftr.collisionCheck(enmyB[i]);
                    for (j=0; j<enmyB[i].BltNum; j++) {
                        if(enmyB[i].EnmyBlt[j].hp != 0){
                            ftr.collisionCheck(enmyB[i].EnmyBlt[j]);
                        }
                    }
                    for (j=0; j<ftrBltNum; j++){
                        if (ftrBlt[j].hp > 0){
                            ftrBlt[j].collisionCheck(enmyB[i]);
                        }
                    }
                }
            }

            if (ftr.hp < 1) mode = -2;

            for (i=0; i<enmyAnum; i++) enmyA[i].move(buf_gc, imgW, imgH);
            for (i=0; i<enmyBnum; i++) enmyB[i].move(buf_gc, imgW, imgH, ftr.x, ftr.y);

            for (i=0; i<ftrBltNum; i++) ftrBlt[i].move(buf_gc, imgW, imgH);

            ftr.move(buf_gc, imgW, imgH);

            for (i=0; i<enmyAnum; i++) {
                //System.out.print(enmyA[i].hp + " ");
            }
            //System.out.println("");
        }
        g.drawImage (buf, 0, 0, this);
    }

    public void update(Graphics gc) {
        paint(gc);
    }

    public void keyTyped(KeyEvent ke) {

    }

    public void keyPressed(KeyEvent ke) {
        int cd = ke.getKeyCode();
        switch (cd) {
        case KeyEvent.VK_LEFT:
            ftr.lflag = true;
            break;

        case KeyEvent.VK_RIGHT:
            ftr.rflag = true;
            break;

        case KeyEvent.VK_UP:
            ftr.uflag = true;
            break;

        case KeyEvent.VK_DOWN:
            ftr.dflag = true;
            break;
            
        case KeyEvent.VK_SPACE:
            ftr.sflag = true;
            if (this.mode != 1){
                this.mode++;
                ftr.hp = 10;
            }
            break;
        }
    }
        
    public void keyReleased(KeyEvent ke) {
        int cd = ke.getKeyCode();
        switch (cd) {               
        case KeyEvent.VK_RIGHT:
            ftr.rflag = false;
            break;
        case KeyEvent.VK_LEFT:
            ftr.lflag = false;
            break;
        case KeyEvent.VK_UP:
            ftr.uflag = false;
            break;
        case KeyEvent.VK_DOWN:
            ftr.dflag = false;
            break;
        case KeyEvent.VK_SPACE:
            ftr.sflag = false;
            break;
        }
    }
}
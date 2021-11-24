import java.awt.*;

public class ShootingGame extends Frame implements Runnable {

    Thread th;
    GameMaster gm;

    public static void main(String[] args) {
        new ShootingGame();
    }

    ShootingGame() {
        super("shooting Game (Sample)");
        int cW=640, cH=480;
        this.setSize(cW+30, cH+40);
        this.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

        gm = new GameMaster(cW, cH);
        this.add(gm);
        this.setVisible(true);

        th = new Thread(this);
        th.start();

        requestFocusInWindow();
    }
    
    public void run() {
        try {
            while (true) {
                Thread.sleep(20);
                gm.repaint();
            }
        }
        catch (Exception e) {System.out.println("Exception: " + e);}
    }
}
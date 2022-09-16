import java.awt.Dimension;
import java.awt.Graphics;
import java.time.Duration;
import java.time.Instant;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main extends JFrame {
    class Canvas extends JPanel {
      Stage stage;
      public Canvas() {
        setPreferredSize(new Dimension(720, 720));
        stage = new Stage();
      }

      @Override
      public void paint(Graphics g) {
        stage.paint(g, getMousePosition());
      }
    }

    final Canvas canvas;

    private Main() {
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      canvas = new Canvas();
      this.setContentPane(canvas);
      this.pack();
      this.setVisible(true);
    }

    public static void main(String[] args) throws Exception {
      Main window = new Main();
      window.run();
    }

    public void run() {
      while(true) {
        Instant startTime = Instant.now();
        canvas.repaint();
        Instant endTime = Instant.now();
        long howLong = Duration.between(startTime, endTime).toMillis();
        try {
          Thread.sleep(20l - howLong);
        } catch (InterruptedException e) {
          System.out.println("thread was interrupted, but who cares?");
        } catch (IllegalArgumentException e) {
          System.out.println("application can't keep up with framratte");
        }
      }
    }
}

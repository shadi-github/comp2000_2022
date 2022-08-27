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
        setPreferredSize(new Dimension(1024, 720));
        stage = StageReader.readStage("data/stage1.rvb");
      }

      @Override
      public void paint(Graphics g) {
        stage.paint(g, getMousePosition());
      }
    }

    public static void main(String[] args) throws Exception {
      Main window = new Main();
      window.run();
    }

    private Main() {
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      Canvas canvas = new Canvas();
      this.setContentPane(canvas);
      this.pack();
      this.setVisible(true);
    }

    public void run() {
      while(true) {
        Instant startTime = Instant.now();
        repaint();
        Instant endTime = Instant.now();
        long howLong = Duration.between(startTime, endTime).toMillis();
        try {
          Thread.sleep(20l - howLong);
        } catch(InterruptedException e) {
          System.out.println("thread was interrupted, but who cares?");
        } catch(IllegalArgumentException e) {
          System.out.println("application can't keep up with framerate");
        }
      }
    }
}

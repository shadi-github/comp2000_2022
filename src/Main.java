import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main extends JFrame {
    class Canvas extends JPanel {
      Stage stage;

      public Canvas() {
        setPreferredSize(new Dimension(1024, 720));
        stage = new Stage();
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
        repaint();
      }
    }
}

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Grid {
  Cell[][] cells = new Cell[20][20];
  static Random rand = new Random();
  
  public Grid() {
    // The grid is 20x20=400 cells
    // Create a list of integers numbered 0 through 399
    List<Integer> distribution = new ArrayList<Integer>();
    distribution = IntStream.rangeClosed(0, 20 * 20 - 1).boxed().collect(Collectors.toList());
    int index;
    int current;
    int wallCount = 0;
    int fenceCount = 0;
    int stoneCount = 0;
    int sandCount = 0;
    int grassCount = 0;
    int waterCount = 0;
    for(int i=0; i<cells.length; i++) {
      for(int j=0; j<cells[i].length; j++) {
        index = rand.nextInt(distribution.size());
        current = distribution.get(index);
        distribution.remove(index);
        char c = colToLabel(i);
        int x = 10 + 35 * i;
        int y = 10 + 35 * j;
        // Wall: 10% of 400 = 40
        if(current < 40) {
          wallCount++;
          cells[i][j] = new Wall(c, j, x, y);
        }
        // Fence: 10% of 400 = 40
        if(current >= 40 && current < 80) {
          fenceCount++;
          cells[i][j] = new Fence(c, j, x, y);
        }
        // Stone: 10% of 400 = 40
        if(current >= 80 && current < 120) {
          stoneCount++;
          cells[i][j] = new Stone(c, j, x, y);
        }
        // Sand: 20% of 400 = 80
        if(current >= 120 && current < 200) {
          sandCount++;
          cells[i][j] = new Sand(c, j, x, y);
        }
        // Grass: 30% of 400 = 120
        if(current >= 200 && current < 320) {
          grassCount++;
          cells[i][j] = new Grass(c, j, x, y);
        }
        // Water: 20% if 400 = 80
        if(current >= 320 && current < 400) {
          waterCount++;
          cells[i][j] = new Water(c, j, x, y);
        }
      }
    }
    System.out.printf("Wall: %d, expected %2.0f\n", wallCount, 20*20*0.10);
    System.out.printf("Fence: %d, expected %2.0f\n", fenceCount, 20*20*0.10);
    System.out.printf("Stone: %d, expected %2.0f\n", stoneCount, 20*20*0.10);
    System.out.printf("Sand: %d, expected %2.0f\n", sandCount, 20*20*0.20);
    System.out.printf("Grass: %d, expected %2.0f\n", grassCount, 20*20*0.30);
    System.out.printf("Water: %d, expected %2.0f\n", waterCount, 20*20*0.20);
  }

  public void paint(Graphics g, Point mousePos) {
    for(int i=0; i<cells.length; i++) {
      for(int j=0; j<cells[i].length; j++) {
        cells[i][j].paint(g, mousePos);
      }
    }
  }

  public static char colToLabel(int col) {
    return (char) (col + Character.valueOf('A'));
  }

  public static int labelToCol(char col) {
    return (int) (col - Character.valueOf('A'));
  }

  public Optional<Cell> cellAtColRow(int c, int r) {
    if(c >= 0 && c < cells.length && r >=0 && r < cells[c].length) {
      return Optional.of(cells[c][r]);
    } else {
      return Optional.empty();
    }
  }

  public Optional<Cell> cellAtColRow(char c, int r) {
    return cellAtColRow(labelToCol(c), r);
  }

  public Optional<Cell> cellAtPoint(Point p) {
    for(int i=0; i < cells.length; i++) {
      for(int j=0; j < cells[i].length; j++) {
        if(cells[i][j].contains(p)) {
          return Optional.of(cells[i][j]);
        }
      }
    }
    return Optional.empty();
  }
}

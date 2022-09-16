import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Stage {
  Grid grid;
  List<Item> items;
  List<Actor> actors;
  Actor player;

  boolean up = false;
  boolean down = false;
  boolean left = false;
  boolean right = false;

  public Stage() {
    grid = new Grid();
    items = new ArrayList<Item>();
    actors = new ArrayList<Actor>();
  }

  public void update() {
    int worldX = player.getLocation().col;
    int worldY = player.getLocation().row;
    Optional<Cell> nextCell = Optional.empty();
    if(up && grid.screenY > -Grid.visibleRows/2) {
      nextCell = grid.cellAtColRow(worldX, worldY-1);
    }
    if(down && grid.screenY < grid.maxRows - Grid.visibleRows/2) {
      nextCell = grid.cellAtColRow(worldX, worldY+1);
    }
    if(left && grid.screenX > -Grid.visibleCols/2) {
      nextCell = grid.cellAtColRow(worldX-1, worldY);
    }
    if(right && grid.screenX < grid.maxCols - Grid.visibleCols/2) {
      nextCell = grid.cellAtColRow(worldX+1, worldY);
    }
    if(nextCell.isPresent()) {
      try {
        Surface currLoc = (Surface) player.getLocation();
        if(player.turns > currLoc.cost / player.speed) {
          player.turns = 0;
          Surface nextLoc = (Surface) nextCell.get();
          player.setLocation(nextLoc);
        } else {
          player.turns++;
        }
      } catch(ClassCastException e) {} // do nothing if boundary
    }
    grid.screenX = player.getLocation().col - Grid.visibleCols/2;
    grid.screenY = player.getLocation().row - Grid.visibleRows/2;
    // do we have AI moves to make?
    for(Actor a: actors) {
      if(a.isBot()) {
        try {
          Surface currLoc = (Surface) a.getLocation();
          if(a.turns > currLoc.cost / a.speed) {
            a.turns = 0;
            List<Cell> possibleLocs = getClearRadius(a.getLocation(), 1);
            Cell nextLoc = a.strat.chooseNextLoc(possibleLocs, player, actors);
            a.setLocation(nextLoc);
          } else {
            a.turns++;
          }
        } catch(ClassCastException e) {
          // Should never occur as players cannot move onto boundary cells.
          System.err.println("Fatal error: " + e);
          System.exit(1);
        }
      }
    }
  }

  public void keyPressed(int code) {
    if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
      up = true;
    }
    if(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
      left = true;
    }
    if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
      down = true;
    }
    if(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
      right = true;
    }
  }

  public void keyReleased(int code) {
    if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
      up = false;
    }
    if(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
      left = false;
    }
    if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
      down = false;
    }
    if(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
      right = false;
    }
  }

  public List<Cell> getClearRadius(Cell from, int size) {
    List<Cell> clearCells = grid.getRadius(from, size);
    List<Cell> surfaceCells = new ArrayList<Cell>();
    for(Cell cell: clearCells) {
      try {
        Surface surfaceCell = (Surface) cell;
        surfaceCells.add(cell);
      } catch(ClassCastException e) {} // do nothing if boundary
    }
    return surfaceCells;
  }

  public Optional<Item> itemAtCell(Cell c) {
    for(Item i: items) {
      if(i.getLocation().equals(c)) {
        return Optional.of(i);
      }
    }
    return Optional.empty();
  }

  public Optional<Actor> actorAtCell(Cell c) {
    for(Actor a: actors) {
      if(a.getLocation().equals(c)) {
        return Optional.of(a);
      }
    }
    return Optional.empty();
  }

  public void paint(Graphics g) {
    grid.paint(g, this);
    // where to draw text in the information area
    final int hTab = 10;
    final int blockVT = 32;
    final int margin = (Grid.visibleCols)*blockVT+4*Grid.offset;
    int yLoc = 20;

    // state display
    g.setColor(Color.LIGHT_GRAY);
    yLoc = yLoc + blockVT;

    // agent display
    final int vTab = 15;
    final int labelIndent = margin + hTab;
    final int valueIndent = margin + 3*blockVT;
    for(Actor a: actors) {
      g.drawImage(a.img, valueIndent, yLoc-12, 16, 16, null);
      g.drawString(a.desc, margin, yLoc);
      g.drawString("location:", labelIndent, yLoc+vTab);
      g.drawString(Integer.toString(a.getLocation().col) + "," + Integer.toString(a.getLocation().row), valueIndent, yLoc+vTab);
      g.drawString("artificiality:", labelIndent, yLoc+2*vTab);
      g.drawString(a.isHuman() ? "Human" : "Bot", valueIndent, yLoc+2*vTab);
      yLoc = yLoc + 2*blockVT;
    }
  }
}

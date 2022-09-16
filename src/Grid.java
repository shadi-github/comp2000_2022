import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class Grid {
  static int offset = 24;
  // To ensure that the player is centered propery
  // the following two values should both be odd numbers
  static int visibleCols = 21;
  static int visibleRows = 21;
  int maxCols = 0;
  int maxRows = 0;
  int screenX = 0;
  int screenY = 0;
  Cell[][] cells;

  public void paint(Graphics g, Stage stage) {
    Graphics gridArea = g.create(offset, offset, Cell.size*visibleCols, Cell.size*visibleRows); 
    gridArea.translate(-screenX*Cell.size,-screenY*Cell.size);
    g.setColor(Color.BLACK);
    g.fillRect(offset, offset, Cell.size*visibleCols, Cell.size*visibleRows);
    // Only draw visible Cells
    int minCol=Math.max(0, screenX);
    int maxCol=Math.min(screenX+visibleCols, maxCols);
    int minRow=Math.max(0, screenY);
    int maxRow=Math.min(screenY+visibleRows, maxRows);
    for(int i=minCol; i<maxCol; i++) {
      for(int j=minRow; j<maxRow; j++) {
        Optional<Item> item = stage.itemAtCell(cells[i][j]);
        Optional<Actor> actor = stage.actorAtCell(cells[i][j]);
        cells[i][j].paint(gridArea, item, actor);
      }
    }
  }

  public Optional<Cell> cellAtColRow(int c, int r) {
    if(c >= 0 && c < cells.length && r >=0 && r < cells[c].length) {
      return Optional.of(cells[c][r]);
    } else {
      return Optional.empty();
    }
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

  public List<Cell> getRadius(Cell from, int size) {
    int i = from.col;
    int j = from.row;
    Set<Cell> inRadius = new HashSet<Cell>();
    if (size > 0) {
        cellAtColRow(i, j-1).ifPresent(inRadius::add);
        cellAtColRow(i, j+1).ifPresent(inRadius::add);
        cellAtColRow(i-1, j).ifPresent(inRadius::add);
        cellAtColRow(i+1, j).ifPresent(inRadius::add);
    }

    for(Cell c: inRadius.toArray(new Cell[0])) {
        inRadius.addAll(getRadius(c, size - 1));
    }
    return new ArrayList<Cell>(inRadius);
  }
}

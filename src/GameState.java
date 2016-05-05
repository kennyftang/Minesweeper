/**
 * Created by kenny on 5/5/16.
 */
public class GameState {
    int[][] map;
    int x, y, mines;
    boolean gameRunning;
    public GameState(){
        this.x = 8;
        this.y = 8;
        this.mines = 10;
        this.gameRunning = false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getMines() {
        return mines;
    }

    public void setMines(int mines) {
        this.mines = mines;
    }

    public boolean isGameRunning() {
        return gameRunning;
    }

    public void setGameRunning(boolean gameRunning) {
        this.gameRunning = gameRunning;
    }

    public void setMap(int x, int y, int mines){
        this.x = x;
        this.y = y;
        this.mines = mines;
        map = new int[x][y];
    }

    public int[][] getMap(){
        return map;
    }
}

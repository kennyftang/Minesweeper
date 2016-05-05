public class GameState {
    private Cell[][] map;
    private int mines;
    private boolean gameRunning;
    public GameState(){
        map = new Cell[8][8];
        this.mines = 10;
        this.gameRunning = false;
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
        this.mines = mines;
        map = new Cell[x][y];
    }

    public Cell[][] getMap(){
        return map;
    }
}

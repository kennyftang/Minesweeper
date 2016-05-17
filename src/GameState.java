class GameState {

    private Cell[][] map;
    private int mines;
    private boolean gameRunning;
    private final Minesweeper gameInstance;

    GameState(Minesweeper gameInstance) {
        map = new Cell[8][8];
        this.mines = 10;
        this.gameRunning = false;
        this.gameInstance = gameInstance;
    }

    int getMines() {
        return mines;
    }

    boolean isGameRunning() {
        return gameRunning;
    }

    void setGameRunning(boolean gameRunning) {
        this.gameRunning = gameRunning;
    }

    void setMap(int x, int y, int mines) {
        this.mines = mines;
        map = new Cell[x][y];
        gameInstance.initializeMap();
        gameRunning = false;
    }
    void restart(){
        map = new Cell[map.length][map[0].length];
        gameInstance.initializeMap();
        gameRunning = false;
    }

    Cell[][] getMap() {
        return map;
    }
}

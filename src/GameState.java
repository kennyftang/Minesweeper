class GameState {
    private Cell[][] map;
    private int mines;
    private boolean gameRunning;
    private Minesweeper gameInstance;
    GameState(Minesweeper gameInstance){
        map = new Cell[8][8];
        this.mines = 10;
        this.gameRunning = false;
        this.gameInstance = gameInstance;
    }
    int getMines() {
        return mines;
    }

    public boolean isGameRunning() {
        return gameRunning;
    }

    public void setGameRunning(boolean gameRunning) {
        this.gameRunning = gameRunning;
    }

    void setMap(int x, int y, int mines){
        this.mines = mines;
        map = new Cell[x][y];
        gameInstance.initializeMap();
        gameRunning = false;
    }

    Cell[][] getMap(){
        return map;
    }
}

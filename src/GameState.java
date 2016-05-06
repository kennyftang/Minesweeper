class GameState {
    private Cell[][] map;
    private int mines;
    private boolean gameRunning;
    GameState(){
        map = new Cell[8][8];
        this.mines = 10;
        this.gameRunning = false;
    }
    int getMines() {
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

    void setMap(int x, int y, int mines){
        this.mines = mines;
        map = new Cell[x][y];
    }

    Cell[][] getMap(){
        return map;
    }
}

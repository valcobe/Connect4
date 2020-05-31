package com.example.connect4.Logic;

import java.io.Serializable;

public class Game implements Serializable {

    private final Board board;
    private final int connectToWin;
    private Status status;
    private boolean hasWinner;
    private Player turn;
    private long startTime;
    private float totalTime;
    private float timePlayed;
    private float timeRest;
    public int size;
    public boolean timeEnd;

    public Game(int size, int connectToWin) {
        this.size = size;
        this.board = new Board(size);
        this.connectToWin = connectToWin;
        this.hasWinner = false;
        this.turn = Player.player1();
        this.status = Status.PLAYER1_PLAYS;

        this.startTime = System.currentTimeMillis();
        this.totalTime = 0;
        this.timePlayed = 0;
        this.timeRest = 0;
        this.timeEnd = false;
    }

    public Position playOpponent() {
        int size = this.board.getSize();
        int random = (int)(Math.random() * size);
        Position pos = this.drop(random);
        while (pos == null) {
            random = (int)(Math.random() * size);
            pos = this.drop(random);
        }
        return pos;
    }

    public void toggleTurn() {
        if (this.status == Status.PLAYER1_PLAYS){
            this.status = Status.PLAYER2_PLAYS;
            this.turn = Player.player2();
        } else {
            this.status = Status.PLAYER1_PLAYS;
            this.turn = Player.player1();
        }
    }

    public void manageTime() {
        long actualTime = System.currentTimeMillis();
        this.timePlayed = (actualTime - this.startTime) / 1000F;
        this.timeRest = this.totalTime - this.timePlayed;

        if (Float.compare(this.timePlayed, this.totalTime) > 0 && this.totalTime != 0) {
            this.status = Status.PLAYER2_WINS_TIME_PASSED_FOR_1;
            this.hasWinner = true;
        }
    }

    public boolean checkForFinish() {
        return status != Status.PLAYER1_PLAYS && status != Status.PLAYER2_PLAYS;
    }

    public Position drop(int col){
        int playableRow = this.board.firstEmptyRow(col);
        System.out.println(playableRow);
        System.out.println(col);
        if (playableRow != -1) {
            Position occupedPos = this.board.play(col, this.turn);
            if (this.board.maxConnected(occupedPos) >= this.connectToWin) {
                if (this.turn.isPlayer1()) {
                    this.status = Status.PLAYER1_WINS;
                } else if (this.turn.isPlayer2()) {
                    this.status = Status.PLAYER2_WINS;
                }
                this.hasWinner = true;
            } else if (!this.board.hasValidMoves()) {
                this.status = Status.DRAW;
            } else {
                this.toggleTurn();
            }
            return occupedPos;
        } else {
            return null;
        }
    }

    public float getTimePlayed() {
        return timePlayed;
    }

    public float getTimeRest() {
        return timeRest;
    }

    public Status getStatus() {
        return status;
    }

    public boolean itHasWinner() {
        return hasWinner;
    }

    public Player getTurn() {
        return turn;
    }



    public int getPlayerPosition(int position) {
        if(this.board.cells[position/size][position%size].equals(Player.player1())) {
            return 1;
        }
        else if(this.board.cells[position/size][position%size].equals(Player.player2())) {
            return 2;
        }
        else {
            return 0;
        }
    }
}
package com.example.ttt;

public class Player {
    private final String ident;
    private final Boolean ki;

    public Player(Boolean b) {
        if (b) {
            ident = "O";
            ki = false;
        }
        else {
            ident = "X";
            ki = true;
        }
    }

    public String id() {
        return this.ident;
    }

    public Boolean isKi() {
        return this.ki;
    }
}

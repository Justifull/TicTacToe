package com.example.ttt;

import java.util.*;
import java.util.stream.Collectors;

public class State {
    private final List<String> state;
    private Boolean win;
    private Map<String, Boolean> winMap;

    public State() {
        state = new ArrayList<>(Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9"));
        win = false;

        winMap = new HashMap<>();
        winMap.put("Player", false);
        winMap.put("Ki", false);
    }

    public State(List<String> list) {
        state = list;
        win = false;

        winMap = new HashMap<>();
        winMap.put("Player", false);
        winMap.put("Ki", false);
    }

    public List<String> toList() {
        return this.state;
    }

    public String toString() {
        String one = state.get(0).equals("X") || state.get(0).equals("O") ? state.get(0) : " ";
        String two = state.get(1).equals("X") || state.get(1).equals("O") ? state.get(1) : " ";
        String three = state.get(2).equals("X") || state.get(2).equals("O") ? state.get(2) : " ";
        String four = state.get(3).equals("X") || state.get(3).equals("O") ? state.get(3) : " ";
        String five = state.get(4).equals("X") || state.get(4).equals("O") ? state.get(4) : " ";
        String six = state.get(5).equals("X") || state.get(5).equals("O") ? state.get(5) : " ";
        String seven = state.get(6).equals("X") || state.get(6).equals("O") ? state.get(6) : " ";
        String eight = state.get(7).equals("X") || state.get(7).equals("O") ? state.get(7) : " ";
        String nine = state.get(8).equals("X") || state.get(8).equals("O") ? state.get(8) : " ";
        String win = winMap.get("Ki") ? "WIN : Ki" : winMap.get("Player") ? "WIN : Player" : "WIN : /";
        return String.format("      %s | %s | %s\n      --+---+--\n      %s | %s | %s\n      --+---+--\n      %s | %s | %s\n      %s", one, two, three, four, five, six, seven, eight, nine, win);
    }

    public Boolean free(int i) {
        return (i < 10 && i > 0) && (!state.get(i - 1).equals("X") && !state.get(i - 1).equals("O"));
    }

    public Boolean getWin() {
        testWin();
        return this.win;
    }

    public int level() {
        int counter = 0;
        for (String s : state) {
            if (s.equals("O") || s.equals("X")) counter++;
        }
        return counter;
    }

    public Map<String, Boolean> getMap() {
        return this.winMap;
    }

    private void gameWon(String s) {
        if (s.equals("Player")) winMap.put("Player", true);
        else if (s.equals("Ki")) winMap.put("Ki", true);
        this.win = true;
    }

    public Map<String, Boolean> testWin() {
        Set<Integer> playerFields = new HashSet<>();
        Set<Integer> kiFields = new HashSet<>();

        for (int i = 0; i < state.size(); i++) {
            if (state.get(i).equals("O")) playerFields.add(i + 1);
            if (state.get(i).equals("X")) kiFields.add(i + 1);
        }

        if (playerFields.containsAll(Arrays.asList(1, 2, 3))) gameWon("Player");
        else if (playerFields.containsAll(Arrays.asList(4, 5, 6))) gameWon("Player");
        else if (playerFields.containsAll(Arrays.asList(7, 8, 9))) gameWon("Player");
        else if (playerFields.containsAll(Arrays.asList(1, 4, 7))) gameWon("Player");
        else if (playerFields.containsAll(Arrays.asList(2, 5, 8))) gameWon("Player");
        else if (playerFields.containsAll(Arrays.asList(3, 6, 9))) gameWon("Player");
        else if (playerFields.containsAll(Arrays.asList(1, 5, 9))) gameWon("Player");
        else if (playerFields.containsAll(Arrays.asList(7, 5, 3))) gameWon("Player");
        else if (kiFields.containsAll(Arrays.asList(1, 2, 3))) gameWon("Ki");
        else if (kiFields.containsAll(Arrays.asList(4, 5, 6))) gameWon("Ki");
        else if (kiFields.containsAll(Arrays.asList(7, 8, 9))) gameWon("Ki");
        else if (kiFields.containsAll(Arrays.asList(1, 4, 7))) gameWon("Ki");
        else if (kiFields.containsAll(Arrays.asList(2, 5, 8))) gameWon("Ki");
        else if (kiFields.containsAll(Arrays.asList(3, 6, 9))) gameWon("Ki");
        else if (kiFields.containsAll(Arrays.asList(1, 5, 9))) gameWon("Ki");
        else if (kiFields.containsAll(Arrays.asList(7, 5, 3))) gameWon("Ki");

        return winMap;
    }

    public Integer testKiWin() {
        Set<Integer> playerFields = new HashSet<>();
        Set<Integer> kiFields = new HashSet<>();

        for (int i = 0; i < state.size(); i++) {
            if (state.get(i).equals("O")) playerFields.add(i + 1);
            if (state.get(i).equals("X")) kiFields.add(i + 1);
        }

        if (!playerFields.containsAll(Arrays.asList(1, 2, 3))
                && !playerFields.containsAll(Arrays.asList(4, 5, 6))
                && !playerFields.containsAll(Arrays.asList(7, 8, 9))
                && !playerFields.containsAll(Arrays.asList(1, 4, 7))
                && !playerFields.containsAll(Arrays.asList(2, 5, 8))
                && !playerFields.containsAll(Arrays.asList(3, 6, 9))
                && !playerFields.containsAll(Arrays.asList(1, 5, 9))
                && !playerFields.containsAll(Arrays.asList(7, 5, 3))
                && (kiFields.containsAll(Arrays.asList(1, 2, 3))
                    || kiFields.containsAll(Arrays.asList(4, 5, 6))
                    || kiFields.containsAll(Arrays.asList(7, 8, 9))
                    || kiFields.containsAll(Arrays.asList(1, 4, 7))
                    || kiFields.containsAll(Arrays.asList(2, 5, 8))
                    || kiFields.containsAll(Arrays.asList(3, 6, 9))
                    || kiFields.containsAll(Arrays.asList(1, 5, 9))
                    || kiFields.containsAll(Arrays.asList(3, 5, 7))
                )
        ) {
            gameWon("Ki");
            return 1;
        } else if (!playerFields.containsAll(Arrays.asList(1, 2, 3))
                && !playerFields.containsAll(Arrays.asList(4, 5, 6))
                && !playerFields.containsAll(Arrays.asList(7, 8, 9))
                && !playerFields.containsAll(Arrays.asList(1, 4, 7))
                && !playerFields.containsAll(Arrays.asList(2, 5, 8))
                && !playerFields.containsAll(Arrays.asList(3, 6, 9))
                && !playerFields.containsAll(Arrays.asList(1, 5, 9))
                && !playerFields.containsAll(Arrays.asList(7, 5, 3)))
            return 0;
        else return -1;
    }

    public void addState(int i, Player p) {
        if (!p.isKi()) state.set(i - 1, "O");
        else state.set(i - 1, "X");
    }

    public int randomStep() {
        while (true) {
            int randomInt = (int) Math.floor(Math.random() * state.size());
            if (!state.get(randomInt).equals("X")
                    && !state.get(randomInt).equals("O")) {
                return (randomInt + 1) == 10 ? 9 : (randomInt + 1);
            }
        }
    }

    public int nextStep(State s) {
        Set<Integer> playerFields = new HashSet<>();
        Set<Integer> kiFields = new HashSet<>();

        for (int i = 0; i < state.size(); i++) {
            if (state.get(i).equals("O")) playerFields.add(i + 1);
            if (state.get(i).equals("X")) kiFields.add(i + 1);
        }

        if ((kiFields.containsAll(Arrays.asList(1, 2)) && free(3))) return 3;
        if ((kiFields.containsAll(Arrays.asList(1, 3)) && free(2))) return 2;
        if ((kiFields.containsAll(Arrays.asList(2, 3)) && free(1))) return 1;
        if ((kiFields.containsAll(Arrays.asList(4, 5)) && free(6))) return 6;
        if ((kiFields.containsAll(Arrays.asList(4, 6)) && free(5))) return 5;
        if ((kiFields.containsAll(Arrays.asList(5, 6)) && free(4))) return 4;
        if ((kiFields.containsAll(Arrays.asList(7, 8)) && free(9))) return 9;
        if ((kiFields.containsAll(Arrays.asList(7, 9)) && free(8))) return 8;
        if ((kiFields.containsAll(Arrays.asList(8, 9)) && free(7))) return 7;
        if ((kiFields.containsAll(Arrays.asList(1, 4)) && free(7))) return 7;
        if ((kiFields.containsAll(Arrays.asList(1, 7)) && free(4))) return 4;
        if ((kiFields.containsAll(Arrays.asList(4, 7)) && free(1))) return 1;
        if ((kiFields.containsAll(Arrays.asList(2, 5)) && free(8))) return 8;
        if ((kiFields.containsAll(Arrays.asList(2, 8)) && free(5))) return 5;
        if ((kiFields.containsAll(Arrays.asList(5, 8)) && free(2))) return 2;
        if ((kiFields.containsAll(Arrays.asList(3, 6)) && free(9))) return 9;
        if ((kiFields.containsAll(Arrays.asList(3, 9)) && free(6))) return 6;
        if ((kiFields.containsAll(Arrays.asList(6, 9)) && free(3))) return 3;
        if ((kiFields.containsAll(Arrays.asList(1, 5)) && free(9))) return 9;
        if ((kiFields.containsAll(Arrays.asList(1, 9)) && free(5))) return 5;
        if ((kiFields.containsAll(Arrays.asList(5, 9)) && free(1))) return 1;
        if ((kiFields.containsAll(Arrays.asList(3, 5)) && free(7))) return 7;
        if ((kiFields.containsAll(Arrays.asList(3, 7)) && free(5))) return 5;
        if ((kiFields.containsAll(Arrays.asList(5, 7)) && free(3))) return 3;

        if ((playerFields.containsAll(Arrays.asList(1, 2)) && free(3))) return 3;
        if ((playerFields.containsAll(Arrays.asList(1, 3)) && free(2))) return 2;
        if ((playerFields.containsAll(Arrays.asList(2, 3)) && free(1))) return 1;
        if ((playerFields.containsAll(Arrays.asList(4, 5)) && free(6))) return 6;
        if ((playerFields.containsAll(Arrays.asList(4, 6)) && free(5))) return 5;
        if ((playerFields.containsAll(Arrays.asList(5, 6)) && free(4))) return 4;
        if ((playerFields.containsAll(Arrays.asList(7, 8)) && free(9))) return 9;
        if ((playerFields.containsAll(Arrays.asList(7, 9)) && free(8))) return 8;
        if ((playerFields.containsAll(Arrays.asList(8, 9)) && free(7))) return 7;
        if ((playerFields.containsAll(Arrays.asList(1, 4)) && free(7))) return 7;
        if ((playerFields.containsAll(Arrays.asList(1, 7)) && free(4))) return 4;
        if ((playerFields.containsAll(Arrays.asList(4, 7)) && free(1))) return 1;
        if ((playerFields.containsAll(Arrays.asList(2, 5)) && free(8))) return 8;
        if ((playerFields.containsAll(Arrays.asList(2, 8)) && free(5))) return 5;
        if ((playerFields.containsAll(Arrays.asList(5, 8)) && free(2))) return 2;
        if ((playerFields.containsAll(Arrays.asList(3, 6)) && free(9))) return 9;
        if ((playerFields.containsAll(Arrays.asList(3, 9)) && free(6))) return 6;
        if ((playerFields.containsAll(Arrays.asList(6, 9)) && free(3))) return 3;
        if ((playerFields.containsAll(Arrays.asList(1, 5)) && free(9))) return 9;
        if ((playerFields.containsAll(Arrays.asList(1, 9)) && free(5))) return 5;
        if ((playerFields.containsAll(Arrays.asList(5, 9)) && free(1))) return 1;
        if ((playerFields.containsAll(Arrays.asList(3, 5)) && free(7))) return 7;
        if ((playerFields.containsAll(Arrays.asList(3, 7)) && free(5))) return 5;
        if ((playerFields.containsAll(Arrays.asList(5, 7)) && free(3))) return 3;

        for (int i = 0; i < 9; i++) {
            if (!s.toList().get(i).equals(this.state.get(i)) && s.toList().get(i).equals("X")) {
                return i + 1;
            }
        }
        return 0;
    }

    public int endStep() {
        if (level() == 8) {
            for (String s1 : state) {
                if (!s1.equals("X") && !s1.equals("O")) return Integer.parseInt(s1);
            }
        }
        return 0;
    }

    public List<State> nextStates() {
        List<State> output = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            List<String> toPut = new ArrayList<>(state);
            if (!toPut.get(i).equals("X") && !toPut.get(i).equals("O")) {
                toPut.set(i, "O");
                for (int j = 0; j < 9; j++) {
                    List<String> toPutX = new ArrayList<>(toPut);
                    if (!toPutX.get(j).equals("X") && !toPutX.get(j).equals("O") && i != j) {
                        toPutX.set(j, "X");
                        output.add(new State(toPutX));
                    }
                }
            }
        }
        return output;
    }

    public State minMax() {
        Map<Integer, State> best = new HashMap<>();

        switch (level()) {
            case 2 -> {
                for (State s1 : nextStates()) {
                    List<State> posTest = new ArrayList<>();
                    List<State> neuTest = new ArrayList<>();
                    List<State> negTest = new ArrayList<>();

                    List<State> list = new ArrayList<>();

                    list.add(s1);
                    for (State s2 : s1.nextStates()) {
                        if (s2.testKiWin() == -1) continue;
                        list.add(s2);
                        for (State s3 : s2.nextStates()) {
                            list.add(s3);
                            if (s3.testKiWin() == 1) posTest.add(list.get(0));
                            if (s3.testKiWin() == 0) neuTest.add(list.get(0));
                            if (s3.testKiWin() == -1) negTest.add(list.get(0));
                        }
                    }
                    int validation = 0;
                    for (State stateTest : posTest) {
                        validation += 5;
                    }
                    for (State stateTest : neuTest) {
                        validation -= 2;
                    }
                    for (State stateTest : negTest) {
                        validation -= 4;
                    }
                    if (posTest.isEmpty() && !neuTest.isEmpty()) best.put(validation, neuTest.get(0));
                    else if (!posTest.isEmpty()) best.put(validation, posTest.get(0));
                }
            }
            case 4 -> {
                for (State s1 : nextStates()) {
                    List<State> posTest = new ArrayList<>();
                    List<State> neuTest = new ArrayList<>();
                    List<State> negTest = new ArrayList<>();

                    List<State> list = new ArrayList<>();
                    list.add(s1);
                    for (State s2 : s1.nextStates()) {
                        list.add(s2);
                        if (s2.testKiWin() == 1) posTest.add(list.get(0));
                        if (s2.testKiWin() == 0) neuTest.add(list.get(0));
                        if (s2.testKiWin() == -1) negTest.add(list.get(0));
                    }
                    int validation = 0;
                    for (State stateTest : posTest) {
                        validation += 5;
                    }
                    for (State stateTest : neuTest) {
                        validation -= 2;
                    }
                    for (State stateTest : negTest) {
                        validation -= 4;
                    }
                    if (posTest.isEmpty() && !neuTest.isEmpty()) best.put(validation, neuTest.get(0));
                    else if (!posTest.isEmpty()) best.put(validation, posTest.get(0));
                }
            }
            case 6 -> {
                for (State s1 : nextStates()) {
                    List<State> posTest = new ArrayList<>();
                    List<State> neuTest = new ArrayList<>();
                    List<State> negTest = new ArrayList<>();

                    List<State> list = new ArrayList<>();
                    list.add(s1);
                    if (s1.testKiWin() == 1) posTest.add(list.get(0));
                    if (s1.testKiWin() == 0) neuTest.add(list.get(0));
                    if (s1.testKiWin() == -1) negTest.add(list.get(0));

                    int validation = 0;
                    for (State stateTest : posTest) {
                        validation += 5;
                    }
                    for (State stateTest : neuTest) {
                        validation -= 2;
                    }
                    for (State stateTest : negTest) {
                        validation -= 4;
                    }
                    if (posTest.isEmpty() && !neuTest.isEmpty()) best.put(validation, neuTest.get(0));
                    else if (!posTest.isEmpty()) best.put(validation, posTest.get(0));
                }
            }
        }
        int bestState = -1000;
        for (Integer i : best.keySet()) {
            if (i > bestState) bestState = i;
        }

        return best.get(bestState);
    }
}

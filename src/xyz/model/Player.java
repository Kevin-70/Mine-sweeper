package xyz.model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class Player {
    private String name;
    private Image look;
    private int score=0;
    private int lose=0;
    private int turn;
    public static int order=0;

    public Player(){
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        this.name="";
        this.look=toolkit.getImage("src/xyz/view/pic/maskgrid.png");
        this.turn =order;
        int oo=order+1;
        this.name="player"+oo;
        order++;
    }
    public Player(String name){
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        this.name=name;
        this.look=toolkit.getImage("src/xyz/view/pic/maskgrid.png");
        this.turn =order;
        order++;
    }

    public int getLose() {
        return lose;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setLose(int lose) {
        this.lose = lose;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Image getLook() {
        return look;
    }

    public void setLook(Image look) {
        this.look = look;
    }

    public static int getOrder() {
        return order;
    }

    public static void setOrder(int order) {
        Player.order = order;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }
}
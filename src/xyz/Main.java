package xyz;

import xyz.controller.GameController;
import xyz.model.Board;
import xyz.view.*;


import javax.swing.*;


public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Initial();
//        BoardComponent boardComponent = new BoardComponent(8,8, 600, 500);//900,800
//        Board board = new Board(8, 7);
//        ScoreBoard scoreBoard = new ScoreBoard(2);
//        GameController gameController = new GameController(boardComponent, board, scoreBoard, 2, 4);
//        GameFrame gameFrame = new GameFrame();
//        gameFrame.add(boardComponent);
//        gameFrame.setVisible(true);
//        scoreBoard.setVisible(true);
        });

    }
}

package xyz.view;
import xyz.controller.GameController;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameFrame extends JFrame {

    private JMenuBar menuBar=new JMenuBar();
    private JMenu m1=new  JMenu("设置");
    private JMenu m2=new JMenu("保存");
    private JMenu m3=new JMenu("帮助");
    private JMenuItem closeCheat=new JMenuItem("关闭透视");
    private JMenuItem openCheat=new JMenuItem("开启透视");
    JMenuItem modelText=new JMenuItem("设置游戏模式");
    JMenuItem modelText2=new JMenuItem("设置玩家个数");
    private static int length1;private static int width1;private static int Ra1;

    public GameFrame() {
        dispose();
        m1.add(modelText);m1.add(modelText2);m3.add(openCheat);m3.add(closeCheat);
        menuBar.add(m1);menuBar.add(m2);menuBar.add(m3);;
        length1=16;width1=16;Ra1=40;
        ActionListener menubarListener=new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource().equals(modelText))SetModel.InvokeSet();
                else if (e.getSource().equals(modelText2))new SetPlayer();
                else if (e.getSource().equals(openCheat)){SetModel.gameControllers.get(0).setCheat(true);}
                else if (e.getSource().equals(closeCheat)){SetModel.gameControllers.get(0).setCheat(false);}
            }
        };
        modelText.addActionListener(menubarListener);modelText2.addActionListener(menubarListener);
        openCheat.addActionListener(menubarListener);closeCheat.addActionListener(menubarListener);
        this.getContentPane().add(menuBar,BorderLayout.NORTH);


        setTitle("Mine clearance");
        setSize(800, 800);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        this.getContentPane().add(menuBar,BorderLayout.NORTH);




    }





}

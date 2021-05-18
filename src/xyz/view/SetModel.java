package xyz.view;

import xyz.controller.GameController;
import xyz.model.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.util.ArrayList;

public class  SetModel extends JFrame {
    public static SetModel setModel;
    public static ArrayList<GameFrame> gameFrames=new ArrayList<>(1);
    public static ArrayList<GameController> gameControllers=new ArrayList<>(1);
    public static ArrayList<ScoreBoard> scoreBoards=new ArrayList<>(1);
    public static ArrayList<Board> boards=new ArrayList<>(1);
    private static int windowsNum=0;
    private ActionListener listener;
    JButton Enter = new JButton("");
    //private final Button Cancel=new Button("Cancel");
    private static int mineNum = 40;
    private static int length = 16;
    private static int width = 16;
    private static int player=1;
    JPanel threeModel = new JPanel();
    ButtonGroup EMH = new ButtonGroup();
    JRadioButton Easy = new JRadioButton("初级(9*9)");
    JRadioButton Mid = new JRadioButton("中级(16*16)");
    JRadioButton High = new JRadioButton("高级(16*30)");
    JRadioButton Selfi = new JRadioButton("自定义");
    //下面是自定义
    //JFrame Self=new JFrame("自定义模式设置");//
    JLabel LengthL = new JLabel("请输入长度（格数）：");
    JLabel WidthL = new JLabel("请输入宽度（格数）：");
    JLabel MINEnum = new JLabel("请输入雷数");
    JTextField Length = new JTextField(2);
    JTextField Width = new JTextField(2);
    JTextField MineNum = new JTextField(2);
    JButton enter1 = new JButton("确定");
    JButton cancel1 = new JButton("取消");
    boolean judgeGot = false;

    private class BtListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (length == 0 && width == 0 && mineNum == 0) new Self();
            else if (length == 555 && width == 555 && mineNum == 0) {
                JOptionPane.showMessageDialog(null, "请选择游戏模式", "请选择游戏模式",
                        JOptionPane.ERROR_MESSAGE);
            } else  {dispose();Newgame();
            }
        }
    }


    public boolean isNum(String s) {
        for (char s1 : s.toCharArray()) {
            if (!Character.isDigit(s1)) return false;
        }
        return true;
    }
    public static void InvokeSet(){
        setModel =new SetModel();
    }


    public SetModel() {
        player=1;
        setTitle("设置模式");
        setLayout(new BorderLayout());
        setSize(300, 800);
        threeModel.setLayout(new GridLayout(5, 1));
        this.setLayout(new BorderLayout());
        Enter.setSize(500, 100);//Cancel.setSize(200,150);
        Enter.setIcon(new ImageIcon(enter11));
        length = 555;width = 555;mineNum = 0;//初始化游戏！！！来判断是否选中
        EMH.add(Easy);EMH.add(Mid);EMH.add(High);EMH.add(Selfi);
        threeModel.add(Easy);
        threeModel.add(Mid);
        threeModel.add(High);
        threeModel.add(Selfi);
        threeModel.add(Enter);
        this.add(threeModel, BorderLayout.CENTER);
//        JPanel enter=new JPanel();enter.setLayout(new FlowLayout());
//        enter.add(Enter);//enter.add(Cancel);
//        this.add(enter,BorderLayout.SOUTH);
//        enter1.setSize(250,100);
//        cancel1.setSize(250,500);
//        Self.setLayout(new GridLayout(4,2));
//        Self.setSize(500,250);
//        Self.add(LengthL);Self.add(Length);Self.add(WidthL);Self.add(Width);Self.add(MINEnum);Self.add(MineNum);Self.add(enter1);Self.add(cancel1);
//        Self.setVisible(false);
        BtListener listener = new BtListener();
        ItemListener itemListener = new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getSource().equals(Easy)) {
                    length = 9;
                    width = 9;
                    mineNum = 10;
                    judgeGot = true;
                    Easy.setIcon(new ImageIcon(Easy1));Mid.setIcon(null);High.setIcon(null);Selfi.setIcon(null);
                }
                if (e.getSource().equals(Mid)) {
                    length = 16;
                    width = 16;
                    mineNum = 40;
                    judgeGot = true;
                    Easy.setIcon(null);Mid.setIcon(new ImageIcon(Mid1));High.setIcon(null);Selfi.setIcon(null);
                }
                if (e.getSource().equals(High)) {
                    length = 16;
                    width = 30;
                    mineNum = 99;
                    judgeGot = true;
                    Easy.setIcon(null);Mid.setIcon(null);High.setIcon(new ImageIcon(High1));Selfi.setIcon(null);
                }
                if (e.getSource().equals(Selfi)) {
                    length = 0;width = 0;mineNum = 0;
                    judgeGot = true;
                    Easy.setIcon(null);Mid.setIcon(null);High.setIcon(null);Selfi.setIcon(new ImageIcon(Self1));
                }
            }
        };
        this.Easy.addItemListener(itemListener);
        High.addItemListener(itemListener);
        Mid.addItemListener(itemListener);
        Selfi.addItemListener(itemListener);
        Enter.addActionListener(listener);//Cancel.addActionListener(listener);
        this.setVisible(true);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

    }


    public static String Easy1 = "src\\xyz\\view\\Font\\猫.jpg";
    public static String Mid1 = "src\\xyz\\view\\Font\\豹子.jpg";
    public static String High1 = "src\\xyz\\view\\Font\\老虎.jpg";
    public static String Self1 = "src\\xyz\\view\\Font\\自定义.jpg";
    public static String enter11 = "src\\xyz\\view\\pic\\确定.jpg";
    public static String entera = "src\\xyz\\view\\pic\\确定2.jpg";
    public static String cancela = "src\\xyz\\view\\pic\\取消2.jpg";


    class Self extends JFrame {
        public Self() {
            setTitle("自定义模式设置");//
            enter1.setSize(250, 100);
            cancel1.setSize(250, 500);
            setLayout(new GridLayout(4, 2));
            setSize(500, 250);
            add(LengthL);add(Length);add(WidthL);add(Width);
            add(MINEnum);add(MineNum);add(enter1);add(cancel1);
            ActionListener Self1 = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource().equals(cancel1)) dispose();
                    else {
                        if (Length.getText().length() == 0 || Width.getText().length() == 0 || MineNum.getText().length() == 0) {
                            JOptionPane.showMessageDialog(null, "请重新输入", "输入的内容为空",
                                    JOptionPane.ERROR_MESSAGE);
                            Length.setText("");
                            Width.setText("");
                            MineNum.setText("");
                        } else {
                            if (isNum(Length.getText()) && isNum(Width.getText()) && isNum(MineNum.getText())) {
                                int L1 = Integer.parseInt(Length.getText());
                                int W1 = Integer.parseInt(Width.getText());
                                int Ra1 = Integer.parseInt(MineNum.getText());
                                if (L1 <= 24 && W1 <= 30 && Ra1 < W1 * L1 / 2) {
                                    length = L1;
                                    width = W1;
                                    mineNum = Ra1;
                                    dispose();
                                    setModel.dispose();System.out.print("s");
                                    Newgame();
                                } else {
                                    JOptionPane.showMessageDialog(null, "请重新输入", "输入格数的格式不正确",
                                            JOptionPane.ERROR_MESSAGE);
                                    Length.setText("");Width.setText("");MineNum.setText("");
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "请重新输入", "输入的内容不是纯数字",
                                        JOptionPane.ERROR_MESSAGE);
                                Length.setText("");Width.setText("");MineNum.setText("");
                            }

                        }
                    }
                }
            };
            enter1.addActionListener(Self1);
            cancel1.addActionListener(Self1);
            setVisible(true);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        }
    }

    public static void main(String[] args) { new SetModel(); }

    public static void Newgame() {
        if (gameFrames.size()!=0){
            gameFrames.get(0).dispose();gameFrames.get(0).remove(0);gameFrames.remove(0);
            scoreBoards.get(0).dispose();scoreBoards.remove(0);
            boards.remove(0);gameControllers.remove(0);
            SwingUtilities.invokeLater(() -> {
                BoardComponent boardComponent = new BoardComponent(width, length, 50 * width, 50 * length);//900,800
                Board board = new Board(width, length);
                ScoreBoard scoreBoard = new ScoreBoard(SetPlayer.getPlayerNum());
                GameController gameController = new GameController(boardComponent, board, scoreBoard, SetPlayer.getPlayerNum(), mineNum,1);
                GameFrame gameFrame = new GameFrame();
                gameFrame.setSize(width * 52, length * 53+40);
                gameFrame.add(boardComponent);
                gameFrame.setVisible(true);scoreBoard.setVisible(true);
                gameFrames.add(gameFrame);scoreBoards.add(scoreBoard);gameControllers.add(gameController);boards.add(board);
                windowsNum++;
        });
        }
        else {
            SwingUtilities.invokeLater(() -> {
                BoardComponent boardComponent = new BoardComponent(width, length, 50 * width, 50 * length);//900,800
                Board board = new Board(width, length);
                ScoreBoard scoreBoard = new ScoreBoard(SetPlayer.getPlayerNum());
                GameController gameController = new GameController(boardComponent, board, scoreBoard, SetPlayer.getPlayerNum(), mineNum,1);
                GameFrame gameFrame = new GameFrame();
                gameFrame.setSize(width * 52, length * 53+40);
                gameFrame.add(boardComponent);
                gameFrame.setVisible(true);
                scoreBoard.setVisible(true);
                gameFrames.add(gameFrame);scoreBoards.add(scoreBoard);gameControllers.add(gameController);boards.add(board);
            });
        windowsNum++;
        }
        //System.out.print(windowsNum);
        }
}

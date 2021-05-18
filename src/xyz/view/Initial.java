package xyz.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Initial extends JFrame {
    private int length;private int width;private int Ra;
    boolean judgeGot ;
    private JButton Start;
    public Initial() {
        super("扫雷游戏启动器");
        setSize(319, 450);
        //设置位置
        setLocation(this.getMidDimesion(new Dimension(width, length)));
        setLayout(new GridLayout(5,1));
        //背景图片的路径。（相对路径或者绝对路径。本例图片放于"java项目名"的文件下）
        String path1 = "src\\xyz\\view\\pic\\background.jpg";
        ImageIcon background = new ImageIcon(path1);// 把背景图片显示在标签里面
        JLabel label = new JLabel(background);// 把标签的大小位置设置为图片填充整个面板
        label.setBounds(0, 0, this.getWidth(), this.getHeight());
        Start=new JButton("开始游戏");Start.setSize(319,20);
        add(Start);
        Start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SetModel.InvokeSet();
                dispose();
            }
        });

        // 把内容窗格转化为JPanel，否则不能用方法setOpaque()来使内容窗格透明
        JPanel imagePanel = (JPanel) this.getContentPane();
        imagePanel.setOpaque(false);
        this.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));//放在底层
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        judgeGot=false;
        setVisible(true);


    }
    public static Point getMidDimesion(Dimension d)
    {
        Point p = new Point();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        p.setLocation((dim.width - d.width)/2,(dim.height - d.height)/2);
        return p;
    }

    public static void main(String[] args) {
        new Initial();
    }

}

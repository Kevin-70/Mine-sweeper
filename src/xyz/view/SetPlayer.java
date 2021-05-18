package xyz.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import xyz.view.SetModel;
public class SetPlayer extends JFrame {
    private JLabel label1;
    private JTextField textField1;
    private JButton jButton1;
    private JButton jButton2;
    private int id;

    public static int getPlayerNum() {
        return playerNum;
    }

    private static int playerNum=1;


    public boolean isNum(String s){
        for (char s1:s.toCharArray()){
            if (!Character.isDigit(s1))return false;
        }
        return true;
    }
    public SetPlayer(){
        setTitle("设置玩家人数");
        setLayout(new GridLayout(2,2));
        setSize(300,300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        label1=new JLabel("请输入玩家个数：");
        textField1=new JTextField(1);
        jButton1=new JButton();jButton1.setIcon(new ImageIcon(SetModel.entera));
        jButton2=new JButton("取消");jButton2.setIcon(new ImageIcon(SetModel.cancela));
        jButton1.setIcon(new ImageIcon(SetModel.entera));jButton2.setIcon(new ImageIcon(SetModel.cancela));
        add(label1);add(textField1);add(jButton1);add(jButton2);
        ActionListener actionListener=new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource().equals(jButton1)){
                    String a=textField1.getText();
                    if (a.length()!=0&&isNum(a)){
                    playerNum=Integer.parseInt(a);
                    dispose();
                    SetModel.Newgame();
                    }else{
                        JOptionPane.showMessageDialog(null, "请重新输入", "输入的内容不是纯数字或内容为空",
                            JOptionPane.ERROR_MESSAGE);
                    textField1.setText("");
                }
                }if (e.getSource().equals(jButton2)){
                    dispose();
                }
            }
        };
        jButton1.addActionListener(actionListener);jButton2.addActionListener(actionListener);
        setVisible(true);
        setLocation(300,400);

    }

    public static void main(String[] args) {
        new SetPlayer();
    }

}

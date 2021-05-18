package xyz.view;

import xyz.model.Player;

import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;

public class ScoreBoard extends JFrame {
    private final int[][] scoreBoard;
    private static Font font;
    private int nop;

    static {
        try {
            font = Font.createFont( Font.TRUETYPE_FONT,
                    new FileInputStream("src/xyz/view/Font/FrozenNeutra.otf") );
        } catch(Exception e) {
            e.printStackTrace();
        }
        font = font.deriveFont(Font.PLAIN, 30);
    }

    public ScoreBoard (int k) {
        this.nop=k;
        setTitle("Score Board");
        setSize(400, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);
        this.getContentPane().setBackground(Color.WHITE);
        this.setFont(font);
        scoreBoard = new int[2][k];
        for(int i=0;i<2;i++){
            for(int j=0;j<k;j++)scoreBoard[i][j]=0;
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for(int i=0;i<nop;i++){
            int s=i+1;
            String m="Player"+s+"-score:";
            g.drawString(m, 40, 80*(i+1));
        }
        for(int i=0;i<nop;i++){
            int s=i+1;
            String m="Player"+s+"-lose:";
            g.drawString(m, 40, 80*(i+1+nop));
        }

        for(int i=0;i<nop;i++){
            g.drawString("" + scoreBoard[0][i], 300, 80*(i+1));
        }
        for(int i=0;i<nop;i++){
            g.drawString("" + scoreBoard[1][i], 300, 80*(nop+i+1));
        }

    }

    public void Goal (int player,Player pl) {
        scoreBoard[0][player] ++;
        pl.setScore(pl.getScore()+1);
    }

    public void Lose (int player, Player pl) {
        scoreBoard[1][player] ++;
        pl.setLose(pl.getLose()+1);
    }

    public int getNop() {
        return nop;
    }

    public void setNop(int nop) {
        this.nop = nop;
    }

    public int getWinner(Player[] list){
        int winner=0;
        int[] finalScore=new int[nop];
        for(int i=0;i<nop;i++){
            finalScore[i]=scoreBoard[0][i]-scoreBoard[1][i];
        }
        for(int i=0;i<nop;i++){
            for(int j=i;j<nop-1;j++){
                if(finalScore[j]<finalScore[j+1]){
                    int t=finalScore[j];
                    Player tt=list[j];
                    finalScore[j]=finalScore[j+1];
                    finalScore[j+1]=t;
                    list[j]=list[j+1];
                    list[j+1]=tt;
                }
                if(finalScore[j]==finalScore[j+1]&&list[j].getLose()>list[j+1].getLose()){
                    int t=finalScore[j];
                    Player tt=list[j];
                    finalScore[j]=finalScore[j+1];
                    finalScore[j+1]=t;
                    list[j]=list[j+1];
                    list[j+1]=tt;
                }
            }
        }
        winner=list[0].getTurn();
        if(finalScore[0]==finalScore[1])return -10;
        return winner;
    }

    public void clearScore(){
        for(int i=0;i<2;i++){
            for(int j=0;j<nop;j++)scoreBoard[i][j]=0;
        }
    }
}

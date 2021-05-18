package xyz.controller;

import xyz.listener.GameListener;
import xyz.model.*;
import xyz.view.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameController implements GameListener {
    private final BoardComponent view1;
    private final ScoreBoard view2;
    private final Board model;
    private int currentPlayer;
    private int numberOfPlayers;
    private List<Player> list=new ArrayList<>();
    public static int n=0;
    public static int ct=0;
    private int Ra;
    public static int flags;
    private int step=1;
    public static int length=9;public static int width=9;//格数；
    public boolean cheat;
    public boolean compute;

    public GameController (BoardComponent component, Board board, ScoreBoard scoreBoard, int m,int R,int step) {
        this.cheat=false;
        this.compute=false;
        this.view1 = component;
        this.view2 = scoreBoard;
        this.model = board;
        this.numberOfPlayers=m;
        this.Ra=R;
        this.flags=R;
        view1.registerListener(this);
        this.step=step;
        if(compute){
            Player pl=new Player();
            list.add(pl);
            Player player=new Player("Computer");
            list.add(player);
        }else{
            for(int i=1;i<=m;i++){
                //String ss="player"+i;
                Player player=new Player();
                player.setTurn(i-1);
                list.add(player);
            }
        }
        initialGameState();


    }

    public void initialGameState () {
        currentPlayer = 0;
        n=0;
        ct=0;
        int num;
        flags=Ra;
        Player.setOrder(0);
        BoardLocation location;
        for (int row = 0; row < model.getRow(); row ++) {
            for (int col = 0; col < model.getColumn(); col ++) {
                location = new BoardLocation(row, col);
                model.getGridAt(location).initialSquare();
                num = model.getNumAt(location);
                view1.setItemAt(location, 0);
            }
        }
        if(numberOfPlayers>1){
            Player[] lists=new Player[numberOfPlayers];
            for(int k=0;k<numberOfPlayers;k++){lists[k]= list.get(k);}
            randomTurn(lists);
            String txt="order of this round is: ";
            for(int k=0;k<numberOfPlayers;k++){txt=txt+lists[lists[k].getTurn()].getName()+" ";}
            view1.newWindow(txt,"Claim");}
        view2.clearScore();
        view1.repaint();
        view2.repaint();
    }

    public void refreshGameState () {
        int num;
        BoardLocation location;
        for (int row = 0; row < model.getRow(); row ++) {
            for (int col = 0; col < model.getColumn(); col ++) {
                location = new BoardLocation(row, col);
                num = model.getGridAt(location).getNum();
                System.out.print(num);
                view1.setItemAt(location, num);
            }
        }
        if(model.isEnd(numberOfPlayers)) {
            if(model.endCase==-1){view1.newWindow("You lose"," ");}
            if(model.endCase==-2){view1.newWindow("You win","Congratulation");}
            if(model.endCase==-3){
                Player[] lists=new Player[numberOfPlayers];
                for(int i=0;i<numberOfPlayers;i++){lists[i]= list.get(i);}
                int winner=view2.getWinner(lists)+1;
                String content="Player"+winner+" win";
                if(winner<0)content="Holds";
                view1.newWindow(content,"Congratulation");}
            initialGameState();
        }
        view1.repaint();
    }

    public void nextPlayer() {
        //currentPlayer = currentPlayer == 0 ? 1 : 0;
        //currentPlayer=(currentPlayer+1)%numberOfPlayers;
        currentPlayer=(ct%(step*numberOfPlayers))/step;
        if(compute&&(ct%(step*2))/step==1){
            for(int i=0;i<step;i++){
                Random r = new Random();
                int k=r.nextInt(model.getRow()*model.getColumn())+1;
                int x=k/ model.getRow();
                if(k%model.getRow()==0)x=x-1;
                int y=k%model.getRow()-1;
                if(y==-1)y=model.getRow()-1;
                BoardLocation lo=new BoardLocation(y,x);
                while(!model.isValidClick(lo,1)){
                    k=r.nextInt(model.getRow()*model.getColumn())+1;
                    x=k/ model.getRow();
                    if(k%model.getRow()==0)x=x-1;
                    y=k%model.getRow()-1;
                    if(y==-1)y=model.getRow()-1;
                    lo=new BoardLocation(y,x);
                }
                model.openGrid(y,x);
            }
        }
        currentPlayer=(ct%(step*numberOfPlayers))/step;
        refreshGameState();
    }

    @Override
    public void onPlayerLeftClick(BoardLocation location, SquareComponent component) {
        n++;
        int i= location.getRow();
        int j= location.getColumn();
        if(n==1){
            model.randomBoard(Ra,i,j,cheat);
            while(model.crowd()){
                initialGameState();
                model.randomBoard(Ra,i,j,cheat);
            }
        }
        printMessage(location, "left");
        if(model.isValidClick(location,1)){
            ct++;
            System.out.print(model.getGridAt(location).hasLandMine());

            System.out.printf(" %d %d",model.calculateNum(location),model.getGridAt(location).getNum());

            model.openGrid(i,j);
            System.out.print(model.getGridAt(location).isOpened());

            if(model.getGridAt(location).hasLandMine()){
                view2.Lose(list.get(currentPlayer).getTurn(), list.get(list.get(currentPlayer).getTurn()));
                view2.repaint();
            }else{
                //view2.Goal(currentPlayer);
                view2.repaint();
            }
            nextPlayer();
        }


        // TODO: Implement the action after player click left Click
    }



    @Override
    public void onPlayerRightClick(BoardLocation location, SquareComponent component) {
        printMessage(location, "right");
        int j= location.getColumn();
        int i= location.getRow();

        ct++;
        if(ct==1){view1.newWindow("这是坏的，并且没有任何好处","开局插旗");ct=0;}
        ct--;

        if(model.isValidClick(location,1)){
            System.out.print(model.getGridAt(location).isOpened());
            ct++;
            if(numberOfPlayers==1){
                if(ct>1&&flags>0){
                    model.getGridAt(location).setFlag(true);
                    flags--;
                }else if(ct>1){
                    view1.newWindow("Not have enough flag","Error");
                }
            }else{
                if(model.getGridAt(location).hasLandMine()){
                    model.getGridAt(location).setFlag(true);
                    view2.Goal(list.get(currentPlayer).getTurn(), list.get(list.get(currentPlayer).getTurn()));
                    view2.repaint();
                }else{
                    if(ct>1){view1.newWindow("Wrong judgement","");
                        model.openGrid(i,j);
                        view2.Lose(list.get(currentPlayer).getTurn(), list.get(list.get(currentPlayer).getTurn()));
                        view2.repaint();}

                }
            }
            if(ct>1)nextPlayer();
        }else if(numberOfPlayers==1){//model.isValidClick(location,2)&&
            model.getGridAt(location).setFlag(false);
            flags++;
            nextPlayer();
        }

        // TODO: Implement the action after player click right Click
    }

    @Override
    public void onPlayerMidClick(BoardLocation location, SquareComponent component) {
        printMessage(location, "middle");
        nextPlayer();
        // TODO: Implement the action after player click middle Click
    }

    private void printMessage (BoardLocation location, String str) {
        int row_in_message = location.getRow();
        int column_in_message = location.getColumn();
        String format = "\nOn Player %d %s click at (%d, %d), ";
        System.out.printf(format, currentPlayer, str, row_in_message + 1, column_in_message + 1);
    }

    public List<Player> getList() {
        return list;
    }

    public void randomTurn(Player[] list) {
        int m = list.length;
        int[] order = new int[m];
        for (int i = 0; i < m; i++) {
            order[i] = -10;
        }
        for (int i = 0; i < m; i++) {
            Random r = new Random();
            int k = r.nextInt(m ) ;
            if (exist(order, k)) {
                while (exist(order, k)) {
                    //System.out.printf("%d\n",k);
                    k = r.nextInt(m) ;
                }
            }
            System.out.printf("%d\n", k);
            order[i] = k;
            list[i].setTurn(k);
        }
    }

    public boolean exist(int[] a,int b){
        for(int i=0;i<a.length;i++){
            if(a[i]==b)return true;
        }
        return false;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public void setRa(int ra) {
        Ra = ra;
    }

    public void setCheat(boolean cheat) {
        this.cheat = cheat;
    }

    public void setCompute(boolean compute) {
        this.compute = compute;
    }
}

package xyz.model;

import java.util.Arrays;
import java.util.Random;

public class Board {

    private Square[][] grid;
    private int row;
    private int column;

    public int endCase=0;

    public Board(int row, int col) {
        grid = new Square[row][col];
        this.column = col;
        this.row = row;

        iniGrid();
        iniItem();
    }

    public void iniGrid () {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                grid[i][j] = new Square(new BoardLocation(i, j));
            }
        }
    }

    public void iniItem () {
        // TODO: This is only a demo implementation.
        grid[0][0].setNumberOfLandMine((byte) 3);
    }

    public byte calculateNum (BoardLocation location) {
        // TODO: You may implement a method here to calculate the number of mine among a grid
        byte n=0;
        for(int i=0;i<row;i++){
            for(int j=0;j<column;j++){
                //System.out.printf("(%d,%d)%d%s  ",i,j,distance(grid[i][j].getLocation(),location),grid[i][j].hasLandMine());
                if(distance(grid[i][j].getLocation(),location)>0&&distance(grid[i][j].getLocation(),location)<=2&&grid[i][j].hasLandMine()){
                    //System.out.printf("(%d,%d)%d%s  ",i,j,distance(grid[i][j].getLocation(),location),grid[i][j].hasLandMine());
                    n++;}
            }
        }
        this.getGridAt(location).setNumberOfLandMine(n);
        return n;


    }

    public int openGrid(int i,int j){
        grid[i][j].setOpened(true);

        if(grid[i][j].hasLandMine()){

            return 1;
        }else{
            if(calculateNum(grid[i][j].getLocation())==0){
                if(0<=i-1&&!grid[i-1][j].isOpened())openGrid(i-1,j);
                if(0<=j-1&&!grid[i][j-1].isOpened())openGrid(i,j-1);
                if(i+1<row&&!grid[i+1][j].isOpened())openGrid(i+1,j);
                if(j+1<column&&!grid[i][j+1].isOpened())openGrid(i,j+1);
                if(0<=i-1&&0<=j-1&&!grid[i-1][j-1].isOpened())openGrid(i-1,j-1);
                if(0<=i-1&&j+1<column&&!grid[i-1][j+1].isOpened())openGrid(i-1,j+1);
                if(i+1<row&&j+1<column&&!grid[i+1][j+1].isOpened())openGrid(i+1,j+1);
                if(0<=j-1&&i+1<row&&!grid[i+1][j-1].isOpened())openGrid(i+1,j-1);

            }else{return 2;}

        }
        return 0;
    }

    public Square getGridAt(BoardLocation location) {
        return grid[location.getRow()][location.getColumn()];
    }

    public int getNumAt(BoardLocation location) {
        return getGridAt(location).getNum();
    }

    public void openGrid(BoardLocation location) {
        getGridAt(location).setOpened(true);
    }

    public void flagGrid (BoardLocation location) {
        getGridAt(location).setFlag(true);
    }

    //click type == 1 means that is left click
    //click type == 2 means that is middle click
    //click type == 3 means that is right click
    public boolean isValidClick (BoardLocation location, int clickType) {

        //TODO: You should implement a method here to check whether it is a valid action
        /*switch (clickType) {
            case 3:
            case 4:
                if (!getGridAt(location).isOpened() && !getGridAt(location).isFlag()) return true;
                else return false;
            default:
                return true;
        }*/
        if(clickType==2){
            if(getGridAt(location).isOpened())return false;
            else return true;
        }
        if (!getGridAt(location).isOpened() && !getGridAt(location).isFlag()) return true;
        else return false;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public void randomBoard(int m,int s,int t,boolean cheat) {
        int[] order=new int[m+1];
        for(int i=0;i<=m;i++){order[i]=-10;}
        order[0]=row*t+s+1;
        int[] x=new int[m+1];
        int[] y=new int[m+1];
        for(int i=1;i<=m;i++){
            Random r = new Random();
            int k=r.nextInt(row*column)+1;
            if(exist(order,k)){while(exist(order,k)){
                //System.out.printf("%d\n",k);
                k=r.nextInt(row*column)+1;
            }}
            System.out.printf("%d\n",k);
            order[i]=k;
            x[i]=order[i]/row;
            if(order[i]%row==0)x[i]=x[i]-1;
            y[i]=order[i]%row-1;
            if(y[i]==-1)y[i]=row-1;
            grid[y[i]][x[i]].setHasLandMine(true);
            if(cheat)grid[y[i]][x[i]].setSee(true);

        }

    }

    public boolean isEnd(int nop){
        if(nop==1){
            for(int i=0;i<row;i++){
                for(int j=0;j<column;j++){
                    BoardLocation lo=new BoardLocation(i,j);
                    if (getGridAt(lo).hasLandMine()&&getGridAt(lo).isOpened() ){
                        endCase=-1;
                        return true;}

                }
            }
        }
        for(int i=0;i<row;i++){
            for(int j=0;j<column;j++){
                BoardLocation lo=new BoardLocation(i,j);
                if (getGridAt(lo).hasLandMine()&&!getGridAt(lo).isOpened() && !getGridAt(lo).isFlag()) return false;
            }
        }
        if(nop==1){endCase=-2;}else{endCase=-3;}
        return true;
    }

    public boolean exist(int[] a,int b){
        for(int i=0;i<a.length;i++){
            if(a[i]==b)return true;
        }
        return false;
    }

    public int distance(BoardLocation a,BoardLocation b){
        int m=a.getRow()-b.getRow();
        int n=a.getColumn()-b.getColumn();
        return (m*m+n*n);
    }

    public boolean crowd(){
        for(int i=0;i<row;i++){
            for(int j=0;j<column;j++){
                BoardLocation loc=new BoardLocation(i,j);
                if(grid[i][j].hasLandMine()&&calculateNum(loc)==8)return true;
            }
        }

        return false;
    }


}

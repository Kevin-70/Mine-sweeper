package xyz.model;

public class Square {
    private BoardLocation location;
    private boolean isOpened;
    private boolean isFlag;
    private boolean hasLandMine;
    private byte numberOfLandMine;
    private boolean see=false;

    public Square(BoardLocation location) {
        this.location = location;
        isOpened = false;
        isFlag = false;
        hasLandMine = false;
        numberOfLandMine = 0;

    }

    public void initialSquare(){
        isOpened = false;
        isFlag = false;
        hasLandMine = false;
        numberOfLandMine = 0;
    }



    public BoardLocation getLocation () {
        return location;
    }

    public boolean isOpened () {
        return isOpened;
    }

    public boolean hasLandMine () {
        return hasLandMine;
    }

    public byte getNumberOfLandMine () {
        return numberOfLandMine;
    }

    public void setOpened(boolean opened) {
        isOpened = opened;
    }

    public void setHasLandMine (boolean hasLandMine) {
        this.hasLandMine = hasLandMine;
    }

    public void setNumberOfLandMine (byte numberOfLandMine) {
        this.numberOfLandMine = numberOfLandMine;
    }

    public boolean isFlag() {
        return isFlag;
    }

    public void setFlag(boolean flag) {
        isFlag = flag;
    }

    public int getNum () {
        int s=0;
        if(this.hasLandMine&&this.see&&!this.isFlag&&!this.isOpened)return -3;
        if(this.hasLandMine&&this.isOpened)return -1;//
        if(this.isFlag)return -2;
        if(this.isOpened){
            s= numberOfLandMine;
            if(s==0)s=9;
        }
        return s;
        // TODO: You should implement the method to give the number of the item store in the grid
    }

    public void setSee(boolean see) {
        this.see = see;
    }

    public boolean isSee() {
        return see;
    }

    /*
    Each grid has five states: the first two include a grid that is not open or is marked;
    The last three are lattice is opened, if there are no mines around, do not show;
    The number of mines, if any;
    If it is a mine, draw a mine
     */
}

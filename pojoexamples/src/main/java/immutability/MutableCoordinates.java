package immutability;

public class MutableCoordinates {

    private final int x;
    private final int y;

    private MutableCoordinates(int x, int y){
        this.x=x;
        this.y=y;
    }

    public static MutableCoordinates create(int x, int y){
        return new MutableCoordinates(x,y);
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public MutableCoordinates transformPositionBy(int xadjust, int yadjust){
        return new MutableCoordinates(this.x+xadjust, this.y+yadjust);
    }
}

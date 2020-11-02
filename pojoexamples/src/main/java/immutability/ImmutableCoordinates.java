package immutability;

public class ImmutableCoordinates {

    private final int x;
    private final int y;

    private ImmutableCoordinates(int x, int y){
        this.x=x;
        this.y=y;
    }

    public static ImmutableCoordinates create(int x, int y){
        return new ImmutableCoordinates(x,y);
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public ImmutableCoordinates transformPositionBy(int xadjust, int yadjust){
        return new ImmutableCoordinates(this.x+xadjust, this.y+yadjust);
    }
}

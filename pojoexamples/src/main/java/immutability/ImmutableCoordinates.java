package immutability;

public final class ImmutableCoordinates {

    private final int x;
    private final int y;

    private ImmutableCoordinates(final int x, final int y){
        this.x=x;
        this.y=y;
    }

    public static ImmutableCoordinates create(final int x, final int y){
        return new ImmutableCoordinates(x,y);
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public ImmutableCoordinates transformPositionBy(final int xadjust, final int yadjust){
        return new ImmutableCoordinates(this.x+xadjust, this.y+yadjust);
    }
}

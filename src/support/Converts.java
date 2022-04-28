
package support;

public class Converts{
    public static int ToMapCoordinatesX(double x){
        return (int)Math.round((x-300)/5);
    }

    public static int ToMapCoordinatesY(double y){
        return (int)Math.round(y/5);
    }

    public static double ToWorldCoordinatesX(int x){
        return (double)x*5+300;
    }

    public static double ToWorldCoordinatesY(int y){
        return (double)y*5;
    }
}
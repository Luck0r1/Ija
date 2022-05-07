
package support;

/**
 * This class houses map support functions, this has to do with coordinates
 */
public class Converts{
    /**
     * Converts x coordinate from screen to map
     * @param x
     * @return
     */
    public static int ToMapCoordinatesX(double x){
        return (int)Math.round((x-300)/5);
    }
    /**
     * Converts y coordinate from screen to map
     * @param y
     * @return
     */
    public static int ToMapCoordinatesY(double y){
        return (int)Math.round(y/5);
    }
    /**
     * Converts x coordinate from screen to screen
     * @param x
     * @return
     */
    public static double ToWorldCoordinatesX(int x){
        return (double)x*5+300;
    }
    /**
     * Converts y coordinate from screen to screen
     * @param y
     * @return
     */
    public static double ToWorldCoordinatesY(int y){
        return (double)y*5;
    }
}
import java.util.Random;

/**
 * Author: Andrew Jarombek
 * Date: 3/25/2016 - 3/29/2016
 * Methods to help simulate loss and corruption in UFT Client and Server.
 */
public class Simulate {
    static Random random = new Random();

    public static boolean isCorrupt(double corruptionRate) {
        return (random.nextDouble() < corruptionRate);
    }

    public static boolean isLost(double lossRate) {
        return isCorrupt(lossRate);
    }
}

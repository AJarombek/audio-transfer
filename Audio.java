
/**
 * Author: Andrew Jarombek
 * Date: 4/23/2016
 * Run both the Server and Client side of an audio transfer
 * Input #1 - IP Destination Address
 * Input #2 - Loss Rate (percentage 0.0-1.0)
 * Input #3 - Sample Interval (in ms)
 */
public class Audio {
    static int SAMPLE_RATE = 1;
    static int SAMPLE_SIZE = 1;

    public static void main(String[] args) {

        MyAudio myAudio = new MyAudio(SAMPLE_RATE, SAMPLE_SIZE);

        String IP = args[0];
        double lossRate = Double.parseDouble(args[1]); // 0.0 - 1.0
        int sampleInterval = Integer.parseInt(args[2]);  // in ms

        AudioServer audioServer = new AudioServer(sampleInterval, myAudio);
        AudioClient audioClient = new AudioClient(sampleInterval, lossRate, IP,  myAudio);

        Thread server = new Thread(audioServer);
        Thread client = new Thread(audioClient);

        server.start();
        client.start();
    }
}

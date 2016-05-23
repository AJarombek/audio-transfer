import java.io.IOException;
import java.net.*;
import java.util.Arrays;

/**
 * Author: Andrew Jarombek
 * Date: 4/23/2016
 * The Server Side of the Audio data transfer.
 */
public class AudioClient implements Runnable {
    static int SAMPLE_RATE = 1;
    static int SAMPLE_SIZE = 1;

    static int SAMPLE_RATE_FACTOR = 8000;
    private int sampleInterval = 10;
    private double lossRate = 0.0;
    private String IP;
    MyAudio myAudio;

    // Default AudioClient Constructor
    public AudioClient(String IP, MyAudio audio) {
        this.IP = IP;
        myAudio = audio;
    }

    // AudioClient Constructor with advanced settings
    public AudioClient(int sampleInterval, double lossRate, String IP, MyAudio audio) {
        this.sampleInterval = sampleInterval;
        this.lossRate = lossRate;
        myAudio = audio;
        this.IP = IP;
    }

    @Override
    public void run() {
        // Make sure the loss rate is valid
        if (lossRate < 0 || lossRate > 1) {
            System.out.println("ERROR: Invalid Loss Rate");
            System.exit(0);
        }

        // Create a UDP socket to send to the UDP segment out
        DatagramSocket clientSocket = null;
        try {
            clientSocket = new DatagramSocket();
        } catch (SocketException e) {
            System.out.println("ERROR: Unable to Create UDP Socket.");
            System.exit(0);
        }

        // convert server address from string to InetAddress
        InetAddress serverInetAddress = null;
        try {
            serverInetAddress = InetAddress.getByName(IP);
        } catch (UnknownHostException e) {
            System.out.println("ERROR: Hostname Given Unknown.");
            System.exit(0);
        }

        // Set up myAudio
        int arraySize = sampleInterval * (SAMPLE_RATE + 1) * SAMPLE_RATE_FACTOR / 1000;
        int[] data = new int[arraySize];

        System.out.println("Array size:" + arraySize);

        // Continue transmitting always
        while (true) {

            // Listen for the audio data
            for (int i = 0; i < (arraySize); i++) {
                data[i] = myAudio.read();
            }

            // Put the audio data into a byte array
            byte[] audioData = MyAudio.intArrayToByteArray(data, SAMPLE_SIZE + 1);

            // create the UDP segment
            DatagramPacket dataPacket = new DatagramPacket(audioData, audioData.length, serverInetAddress, 12345);

            // Loss simulation
            boolean lost = Simulate.isLost(lossRate);

            // Simulate the chance of a lost packet
            if (!lost)  {

                // send UDP segment using the UDP socket
                try {
                    clientSocket.send(dataPacket);
//                  System.out.println("sent");
                } catch (IOException e) {
                    System.out.println("ERROR: Segment Not Sent.");
                    System.exit(0);
                }

            }
        }
    }
}


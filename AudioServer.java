import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Arrays;

/**
 * Author: Andrew Jarombek
 * Date: 4/23/2016
 * The Server Side of the Audio data transfer.
 */
public class AudioServer implements Runnable {
    static int SAMPLE_RATE = 1;
    static int SAMPLE_SIZE = 1;

    private int sampleInterval = 10;

    MyAudio myAudio;
    // Default AudioServer Constructor
    public AudioServer(MyAudio audio) {
        myAudio = audio;
    }

    // AudioServer Constructor with advanced settings
    public AudioServer(int sampleInterval, MyAudio audio) {
        this.sampleInterval = sampleInterval;
        myAudio = audio;
    }

    @Override
    public void run() {

        // create a UDP socket to listen to data and to send data
        DatagramSocket serverSocket = null;
        try {
            serverSocket = new DatagramSocket(12345);
        } catch (SocketException e) {
            System.out.println("ERROR: Unable to Create UDP Socket.");
            System.exit(0);
        }
        System.out.println("Ready to receive");

        // wait for connections
        while (true) {
            // create a byte array to store the client message
            byte[] buffer = new byte[16000];

            // create an empty datagram packet to receive data
            DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);

            // receive data from a client
            // this blocks until data is received
            try {
                serverSocket.receive(receivePacket);
                System.out.println("received");
            } catch (IOException e) {
                System.out.println("ERROR: Packet not Received");
                System.exit(0);
            }

            // retrieve the actual number of bytes received
            byte[] clientBytes = Arrays.copyOf(receivePacket.getData(), receivePacket.getLength());

            myAudio.play(clientBytes);

        }

    }
}


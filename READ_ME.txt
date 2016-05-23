This code sends audio data passed through a microphone on one computer to the speakers on another computer.  Although
it can be run on one computer by sending the audio data to itself, the code is best designed for a two way communication
between two computers.

Author: Andrew Jarombek
Date: 5/5/2016
Files: Audio.java, AudioClient.java, AudioServer.java, Simulate.java, MyAudio.class

How To Run The Program:

Compile the Audio.java, AudioClient.java AudioServer.java, and Simulate.java files.

The Audio file will be run with three additional inputs.  The first is the destination computers IP address, the
second is the loss rate (a float from 0.0 to 1.0), and the third is the delay (up to around 500 milliseconds).

Examples of Audio run in the command line:

java Audio 10.70.2.108 0.0 10
java Audio 10.70.2.108 0.5 500
java Audio 10.60.15.92 0.25 100
...
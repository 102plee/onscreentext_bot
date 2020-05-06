import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.util.LinkedList;
import java.util.Queue;
import java.util.TimerTask;
import java.util.Random;
import java.util.Timer;

public class getText extends TimerTask {

    private JFrame jframe;
    private BufferedReader reader;
    private long currentFileSize;
    private Random randnum;
    private File thisFile;

    Queue<JLabel> currentTextToAdd = new LinkedList<>();
    Queue<Integer> randomNumbers = new LinkedList<>();


    // Initilalizes all variables

    public getText(JFrame jframe, BufferedReader reader, File file, long currentFileSize) {
        this.jframe = jframe;
        this.reader = reader;
        this.currentFileSize = currentFileSize;
        this.randnum = new Random();
        this.thisFile = file;
    }

    public void run() {

        // First checks to see if the file has been updated by comparing file sizes.
        // Then, attempts to put the new text in a queue.

        if (thisFile.length() != currentFileSize) {
            currentFileSize = thisFile.length();
            try {
                String currentLine;
                while ((currentLine = reader.readLine()) != null) {
                    System.out.println(currentLine);
                    currentTextToAdd.add(new JLabel(currentLine));
                    randomNumbers.add(randnum.nextInt(jframe.getHeight()));
                }
                while (currentTextToAdd.peek() != null) {

                    // After all new text is gathered, moveText is called upon the queue.

                    moveText(currentTextToAdd.remove(), randomNumbers.remove());
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }

    }

    private void moveText(JLabel label, Integer i) {

        // moveText prepares to present the text, and calls a moveTextTask that
        // moves the text across the screen on a timer.

        Integer currentY = i;
        JLabel currentLabel = label;
        currentLabel.setFont(new Font("Dialog", 1, 36));
        label.setLocation(jframe.getWidth(), currentY);
        final Dimension size = label.getPreferredSize();
        currentLabel.setMinimumSize(size);
        currentLabel.setPreferredSize(size);
        jframe.getContentPane().add(currentLabel);
        jframe.setVisible(true);
        jframe.repaint();
        Timer timer = new Timer();
        timer.schedule(new moveTextTask(label, jframe.getWidth(), currentY), 0, 10);


    }

    private class moveTextTask extends TimerTask {

        JLabel currentLabel;
        Integer currentY;
        Integer currentX;


        public moveTextTask(JLabel label, Integer x, Integer y) {
            currentLabel = label;
            currentY = y;
            currentX = x;
        }

        public void run () {

            // Moves the text from right -> left.
            // Currently at a speed of 5 pixels per tick.

            if (currentX + currentLabel.getWidth() >= 0) {

                currentLabel.setLocation(currentX, currentY);
                jframe.repaint();
                currentX -= 5;
            } else {
                currentLabel.setVisible(false);
                jframe.remove(currentLabel);
                currentLabel.repaint();
            }
        }
    }
}

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Timer;

public class RootFrame extends JFrame {
    InputStream input;
    BufferedReader reader;
    JPanel panel;


    public RootFrame() {

        // Screen constructor. All self-explanatory.

        super("GradientTranslucentWindow");
        String messageFilePath = "messages.txt";

        try {
            input = Files.newInputStream(Paths.get(messageFilePath));
            reader = new BufferedReader(new InputStreamReader(input));
        } catch (Exception e) {
            System.out.println(e);
        }

        setUndecorated(true);
        setBackground(new Color(0, 0, 0, 0));
        setTitle("bruh moment 10");

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(dimension.width, dimension.height);
        setLocation(0, 0);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                if (g instanceof Graphics2D) {
                    final int R = 240;
                    final int G = 240;
                    final int B = 240;

                    Paint p = new GradientPaint(0.0f, 0.0f, new Color(R, G, B, 0),
                            0.0f, getHeight(), new Color(R, G, B, 0), true);
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setPaint(p);
                    g2d.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };
        setContentPane(panel);
        setLayout(new FlowLayout());
        setUndecorated(true);
        setVisible(true);

        // Starts a timer task that checks the log file every 1 second. This task also
        // takes care of updating the screen with the new text.

        Timer timer = new Timer();
        File file = new File(messageFilePath);
        long currentFileSize = file.length();
        timer.schedule(new TextScrollTask(this, this.reader, file, currentFileSize), 0, 1000);

    }
}
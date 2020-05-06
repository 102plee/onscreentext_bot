import java.awt.*;
import javax.swing.*;
import static java.awt.GraphicsDevice.WindowTranslucency.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.File;
import java.util.Timer;

public class screen extends JFrame {

    private File file = new File("newLog.txt");
    private long currentFileSize = file.length();

    InputStream input;
    BufferedReader reader;
    JPanel panel;
    String line;


    public screen() {

        // Screen constructor. All self-explanatory.

        super("GradientTranslucentWindow");

        try {
            input = new FileInputStream("newLog.txt");
            reader = new BufferedReader(new InputStreamReader(input));
        } catch (Exception e) {
            System.out.println(e);
        }

        setBackground(new Color(0,0,0,0));
        setTitle("bruh moment 10");
        setSize (380,650);
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

                    Paint p =
                            new GradientPaint(0.0f, 0.0f, new Color(R, G, B, 0),
                                    0.0f, getHeight(), new Color(R, G, B, 0), true);
                    Graphics2D g2d = (Graphics2D)g;
                    g2d.setPaint(p);
                    g2d.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };
        setContentPane(panel);
        setLayout(new FlowLayout());
        setUndecorated (true);
        setVisible (true);

        // Starts a timer task that checks the log file every 1 second. This task also
        // takes care of updating the screen with the new text.

        Timer timer = new Timer();
        timer.schedule(new getText(this, this.reader, this.file, this.currentFileSize), 0, 1000);

    }

    public static void main(String[] args) {

        // Determine what the GraphicsDevice can support.
        GraphicsEnvironment ge =
                GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        boolean isPerPixelTranslucencySupported =
                gd.isWindowTranslucencySupported(PERPIXEL_TRANSLUCENT);

        //If translucent windows aren't supported, exit.
        if (!isPerPixelTranslucencySupported) {
            System.out.println(
                    "Per-pixel translucency is not supported");
            System.exit(0);
        }

        JFrame.setDefaultLookAndFeelDecorated(true);

        // Create the GUI on the event-dispatching thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                screen gtw = new
                        screen();

                // Display the window.

                gtw.setVisible(true);
            }
        });
    }
}
import javax.swing.SwingUtilities;

public class Client {
    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeLater(() -> {
            new ChatUI().setVisible(true);
        });
    }
}

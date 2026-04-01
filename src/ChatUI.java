import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.awt.*;

public class ChatUI extends JFrame {
    private ChatClient client;
    private JTextArea taReceive;
    private JTextField tfSend;

    public ChatUI(String host, int port, String username) throws Exception {
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("Chat - " + username);

        taReceive = new JTextArea();
        taReceive.setEditable(false);
        taReceive.getCaret().setVisible(false);
        taReceive.setFocusable(false);
        taReceive.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(taReceive);
        add(scrollPane, BorderLayout.CENTER);

        JPanel sendPanel = createSendPanel();
        add(sendPanel, BorderLayout.SOUTH);

        try {
            client = new ChatClient(host, port, username, message -> {
                taReceive.append(message + "\n");
                taReceive.setCaretPosition(taReceive.getDocument().getLength());
            });
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    private JPanel createSendPanel() {
        JPanel panel = new JPanel();
        tfSend = new JTextField(30);
        tfSend.addActionListener(e -> {
            sendMessage();
        });

        JButton btnSend = new JButton("Send");
        btnSend.addActionListener(e -> {
            sendMessage();
        });

        panel.add(tfSend);
        panel.add(btnSend);
        return panel;
    }

    private void sendMessage() {
        String message = tfSend.getText().trim();
        if(!message.isEmpty()) {
            client.sendMessage(message);
            tfSend.setText("");
        }
    }
}

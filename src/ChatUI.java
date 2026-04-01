import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ChatUI extends JFrame {
    private JTextField tfUsername;
    private JTextField tfHost;
    private JTextField tfPort;

    public ChatUI() {
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("Connect to a chat room");

        JPanel formPanel = createFormPanel();
        add(formPanel);
        JPanel buttonPanel = createButtonPanel();
        add(buttonPanel, "South");
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.EAST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Username"), gbc);

        gbc.gridx++;
        tfUsername = new JTextField(20);
        panel.add(tfUsername, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Host"), gbc); 

        gbc.gridx++;
        tfHost = new JTextField(20);
        panel.add(tfHost, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Port"), gbc);

        gbc.gridx++;
        tfPort = new JTextField(20);
        panel.add(tfPort, gbc);

        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnConnect = new JButton("Connect");
        btnConnect.addActionListener(e -> {
            String username = tfUsername.getText().trim();
            String host = tfHost.getText().trim();
            int port = Integer.parseInt(tfPort.getText().trim());
            try {
                ClientUI clientUI = new ClientUI(host, port, username);
                clientUI.setVisible(true);
                this.dispose();
            }
            catch(Exception ex) {
                ex.printStackTrace();
            }
        });
        panel.add(btnConnect);

        return panel;
    }
}

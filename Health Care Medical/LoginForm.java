import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginForm {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginForm::createAndShowGUI);
    }

    public static void createAndShowGUI() {
        JFrame frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 250);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        formPanel.setPreferredSize(new Dimension(400, 120));

        ImageIcon userIcon = new ImageIcon("user.png");
        JLabel userLabel = new JLabel(userIcon);
        formPanel.add(userLabel);

        JTextField emailField = new JTextField();
        JLabel emailLabel = new JLabel("Email:");
        formPanel.add(emailLabel);
        formPanel.add(emailField);

        ImageIcon emailIcon = new ImageIcon("email.png");
        JLabel emailIconLabel = new JLabel(emailIcon);
        formPanel.add(emailIconLabel);

        JPasswordField passwordField = new JPasswordField();
        JLabel passwordLabel = new JLabel("Password:");
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton loginButton = new JButton("Login");
        JButton cancelButton = new JButton("Cancel");

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());

                if (email.equals(RegisterForm.getRegisteredEmail()) && password.equals(RegisterForm.getRegisteredPassword())) {
                    JOptionPane.showMessageDialog(frame, "Login Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    frame.dispose();
                    new Dashboard(RegisterForm.getRegisteredName()).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid Email or Password!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        buttonPanel.add(loginButton);
        buttonPanel.add(cancelButton);

        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(mainPanel);
        frame.setVisible(true);
    }
}

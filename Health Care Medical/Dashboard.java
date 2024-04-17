import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Dashboard extends JFrame {
    private String username;
    private JFrame currentWindow;

    public Dashboard(String name) {
        this.username = name;
        setTitle("Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(52, 152, 219));

        JLabel welcomeLabel = new JLabel("Welcome, " + name);
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        welcomeLabel.setForeground(Color.WHITE);
        headerPanel.add(welcomeLabel, BorderLayout.WEST);

        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setBackground(new Color(231, 76, 60));
        logoutButton.setFocusPainted(false);
        logoutButton.addActionListener(e -> {
            dispose();
            new LoginForm().createAndShowGUI();
        });
        headerPanel.add(logoutButton, BorderLayout.EAST);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(5, 1, 0, 20));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        Font buttonFont = new Font("Segoe UI", Font.PLAIN, 16);
        Color buttonColor = new Color(46, 204, 113);

        JButton managePatientsButton = new JButton("Manage Patients");
        managePatientsButton.setFont(buttonFont);
        managePatientsButton.setBackground(buttonColor);
        managePatientsButton.setForeground(Color.WHITE);
        managePatientsButton.setFocusPainted(false);
        managePatientsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openNewWindow(new ManagePatients());
            }
        });
        buttonPanel.add(managePatientsButton);

        JButton scheduleAppointmentsButton = new JButton("Schedule Appointments");
        scheduleAppointmentsButton.setFont(buttonFont);
        scheduleAppointmentsButton.setBackground(buttonColor);
        scheduleAppointmentsButton.setForeground(Color.WHITE);
        scheduleAppointmentsButton.setFocusPainted(false);
        scheduleAppointmentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openNewWindow(new ScheduleAppointments());
            }
        });
        buttonPanel.add(scheduleAppointmentsButton);

        JButton prescriptionsButton = new JButton("Prescriptions");
        prescriptionsButton.setFont(buttonFont);
        prescriptionsButton.setBackground(buttonColor);
        prescriptionsButton.setForeground(Color.WHITE);
        prescriptionsButton.setFocusPainted(false);
        prescriptionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openNewWindow(new Prescriptions());
            }
        });
        buttonPanel.add(prescriptionsButton);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        ImageIcon logoIcon = new ImageIcon("Med.jpg");
        JLabel logoLabel = new JLabel(logoIcon);
        mainPanel.add(logoLabel, BorderLayout.WEST);

        add(mainPanel);
        pack();
        setLocationRelativeTo(null);
    }

    private void openNewWindow(JFrame newWindow) {
        if (currentWindow != null) {
            currentWindow.dispose();
        }
        currentWindow = newWindow;
        currentWindow.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Dashboard("").setVisible(true));
    }
}

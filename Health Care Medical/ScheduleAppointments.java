import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScheduleAppointments extends JFrame {
    private JTextField dateField;
    private JTextField timeField;
    private JTextField nameField;
    private JComboBox<String> timePeriodComboBox;
    private JTable table;
    private DefaultTableModel tableModel;

    public ScheduleAppointments() {
        setTitle("Schedule Appointments");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(600, 400));
        initComponents();
        pack();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));

        JLabel dateLabel = new JLabel("Date (MM/dd/yyyy):");
        dateField = new JTextField();
        JLabel timeLabel = new JLabel("Time (HH:mm):");
        timeField = new JTextField();
        JLabel timePeriodLabel = new JLabel("AM/PM:");
        timePeriodComboBox = new JComboBox<>(new String[]{"AM", "PM"});
        JLabel nameLabel = new JLabel("Patient Name:");
        nameField = new JTextField();

        formPanel.add(dateLabel);
        formPanel.add(dateField);
        formPanel.add(timeLabel);
        formPanel.add(timeField);
        formPanel.add(timePeriodLabel);
        formPanel.add(timePeriodComboBox);
        formPanel.add(nameLabel);
        formPanel.add(nameField);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addAppointment();
            }
        });

        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateAppointment();
            }
        });

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteAppointment();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        mainPanel.add(formPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        JPanel tablePanel = new JPanel(new BorderLayout());
        tableModel = new DefaultTableModel(new Object[]{"Date", "Time", "Patient Name"}, 0);
        table = new JTable(tableModel);
        tablePanel.add(new JScrollPane(table), BorderLayout.CENTER);

        mainPanel.add(tablePanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    private void addAppointment() {
        String date = dateField.getText();
        String time = timeField.getText();
        String timePeriod = (String) timePeriodComboBox.getSelectedItem();
        String name = nameField.getText();

        if (date.isEmpty() || time.isEmpty() || timePeriod.isEmpty() || name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        Date dateTime;
        try {
            dateTime = dateFormat.parse(date + " " + time + " " + timePeriod);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Invalid date or time format.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Object[] rowData = {date, time + " " + timePeriod, name};
        tableModel.addRow(rowData);

        dateField.setText("");
        timeField.setText("");
        nameField.setText("");
    }

    private void updateAppointment() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String date = dateField.getText();
            String time = timeField.getText();
            String timePeriod = (String) timePeriodComboBox.getSelectedItem();
            String name = nameField.getText();

            if (date.isEmpty() || time.isEmpty() || timePeriod.isEmpty() || name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
            Date dateTime;
            try {
                dateTime = dateFormat.parse(date + " " + time + " " + timePeriod);
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(this, "Invalid date or time format.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            tableModel.setValueAt(date, selectedRow, 0);
            tableModel.setValueAt(time + " " + timePeriod, selectedRow, 1);
            tableModel.setValueAt(name, selectedRow, 2);

            dateField.setText("");
            timeField.setText("");
            nameField.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Please select an appointment to update.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteAppointment() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int confirm = JOptionPane.showConfirmDialog(this, "Do you want to delete this appointment?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                tableModel.removeRow(selectedRow);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select an appointment to delete.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ScheduleAppointments().setVisible(true));
    }
}

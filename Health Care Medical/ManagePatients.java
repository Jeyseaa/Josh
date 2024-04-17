import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ManagePatients extends JFrame {
    private JTextField nameField;
    private JTextField dobField;
    private JTable table;
    private DefaultTableModel tableModel;
    private ArrayList<Patient> patients;
    private ArrayList<Patient> recycleBin;
    private JTable recycleBinTable;
    private DefaultTableModel recycleBinTableModel;
    private JButton updateButton;

    public ManagePatients() {
        setTitle("Manage Patients");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();
        pack();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        patients = new ArrayList<>();
        recycleBin = new ArrayList<>();

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField();
        JLabel dobLabel = new JLabel("Date of Birth (MM/dd/yyyy):");
        dobField = new JTextField();
        formPanel.add(nameLabel);
        formPanel.add(nameField);
        formPanel.add(dobLabel);
        formPanel.add(dobField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addPatient();
            }
        });
        updateButton = new JButton("Update");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updatePatient();
            }
        });
        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deletePatient();
            }
        });
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        JPanel tablePanel = new JPanel(new BorderLayout());
        tableModel = new DefaultTableModel(new Object[]{"ID", "Name", "Date of Birth"}, 0);
        table = new JTable(tableModel);
        tablePanel.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel recycleBinPanel = new JPanel(new BorderLayout());
        recycleBinTableModel = new DefaultTableModel(new Object[]{"ID", "Name", "Date of Birth"}, 0);
        recycleBinTable = new JTable(recycleBinTableModel);
        recycleBinPanel.add(new JScrollPane(recycleBinTable), BorderLayout.CENTER);

        JPanel recycleBinButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton restoreButton = new JButton("Restore");
        restoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restorePatient();
            }
        });
        JButton deletePermanentButton = new JButton("Delete Permanently");
        deletePermanentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deletePermanentPatient();
            }
        });
        recycleBinButtonPanel.add(restoreButton);
        recycleBinButtonPanel.add(deletePermanentButton);
        recycleBinPanel.add(recycleBinButtonPanel, BorderLayout.SOUTH);

        mainPanel.add(formPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(tablePanel, BorderLayout.WEST);
        mainPanel.add(recycleBinPanel, BorderLayout.EAST);

        add(mainPanel);

        updateTable();
        updateRecycleBinTable();
    }

    private void addPatient() {
        String name = nameField.getText();
        String dobText = dobField.getText();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date dob = null;
        try {
            dob = dateFormat.parse(dobText);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Invalid date format. Please use MM/dd/yyyy.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        patients.add(new Patient(name, dob));
        updateTable();
        nameField.setText("");
        dobField.setText("");
    }

    private void updatePatient() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String newName = nameField.getText();
            String newDobText = dobField.getText();
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            Date newDob = null;
            try {
                newDob = dateFormat.parse(newDobText);
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(this, "Invalid date format. Please use MM/dd/yyyy.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Patient patient = patients.get(selectedRow);
            patient.setName(newName);
            patient.setDob(newDob);
            updateTable();
        } else {
            JOptionPane.showMessageDialog(this, "Please select a patient to update.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateTable() {
        tableModel.setRowCount(0);
        for (Patient patient : patients) {
            tableModel.addRow(new Object[]{patient.getId(), patient.getName(), patient.getDob()});
        }
    }

    private void deletePatient() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            recycleBin.add(patients.remove(selectedRow));
            updateTable();
            updateRecycleBinTable();
        }
    }

    private void updateRecycleBinTable() {
        recycleBinTableModel.setRowCount(0);
        for (Patient patient : recycleBin) {
            recycleBinTableModel.addRow(new Object[]{patient.getId(), patient.getName(), patient.getDob()});
        }
    }

    private void restorePatient() {
        int selectedRow = recycleBinTable.getSelectedRow();
        if (selectedRow != -1) {
            patients.add(recycleBin.remove(selectedRow));
            updateTable();
            updateRecycleBinTable();
        }
    }

    private void deletePermanentPatient() {
        int selectedRow = recycleBinTable.getSelectedRow();
        if (selectedRow != -1) {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this patient permanently?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                recycleBin.remove(selectedRow);
                updateRecycleBinTable();
            }
        }
    }
    

    private class Patient {
        private static int count = 0;
        private int id;
        private String name;
        private Date dob;

        public Patient(String name, Date dob) {
            this.id = ++count;
            this.name = name;
            this.dob = dob;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Date getDob() {
            return dob;
        }

        public void setDob(Date dob) {
            this.dob = dob;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ManagePatients().setVisible(true));
    }
}

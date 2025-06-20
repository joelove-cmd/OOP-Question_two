package com.vu.exhibition.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.regex.Pattern; // For email validation

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ExhibitionRegistrationGUI extends JFrame {

    // --- Fields (same as before) ---
    private JTextField txtRegistrationID;
    private JTextField txtStudentName;
    private JTextField txtFaculty;
    private JTextField txtProjectTitle;
    private JTextField txtContactNumber;
    private JTextField txtEmailAddress;

    private JLabel lblImagePreview;
    private JLabel lblImagePath;

    private JButton btnRegister;
    private JButton btnSearch;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnClear;
    private JButton btnExit;
    private JButton btnBrowseImage;

    private static final String DB_URL = "jdbc:ucanaccess://VUE_Exhibition.accdb;newDatabaseVersion=V2010";
    private String currentImagePath = null;
    private String loadedRegistrationID = null;

    // Regex for basic email validation
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
        "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"
    );

    public ExhibitionRegistrationGUI() {
        // --- Constructor (same as before, setting up UI) ---
        setTitle("Victoria University Exhibition Registration");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(850, 650);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(mainPanel);

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Registration ID:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.gridwidth = 2;
        txtRegistrationID = new JTextField(25);
        formPanel.add(txtRegistrationID, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        formPanel.add(new JLabel("Student Name:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.gridwidth = 2;
        txtStudentName = new JTextField(25);
        formPanel.add(txtStudentName, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 1;
        formPanel.add(new JLabel("Faculty:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; gbc.gridwidth = 2;
        txtFaculty = new JTextField(25);
        formPanel.add(txtFaculty, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 1;
        formPanel.add(new JLabel("Project Title:"), gbc);
        gbc.gridx = 1; gbc.gridy = 3; gbc.gridwidth = 2;
        txtProjectTitle = new JTextField(25);
        formPanel.add(txtProjectTitle, gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 1;
        formPanel.add(new JLabel("Contact Number:"), gbc);
        gbc.gridx = 1; gbc.gridy = 4; gbc.gridwidth = 2;
        txtContactNumber = new JTextField(25);
        formPanel.add(txtContactNumber, gbc);

        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 1;
        formPanel.add(new JLabel("Email Address:"), gbc);
        gbc.gridx = 1; gbc.gridy = 5; gbc.gridwidth = 2;
        txtEmailAddress = new JTextField(25);
        formPanel.add(txtEmailAddress, gbc);

        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 1;
        formPanel.add(new JLabel("Image Path:"), gbc);

        gbc.gridx = 1; gbc.gridy = 6; gbc.gridwidth = 1;
        lblImagePath = new JLabel("No image selected.");
        lblImagePath.setPreferredSize(new Dimension(200, 20));
        formPanel.add(lblImagePath, gbc);

        gbc.gridx = 2; gbc.gridy = 6; gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        btnBrowseImage = new JButton("Browse...");
        formPanel.add(btnBrowseImage, gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        mainPanel.add(formPanel, BorderLayout.CENTER);

        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setBorder(BorderFactory.createTitledBorder("Project Prototype Image"));
        lblImagePreview = new JLabel("Image preview will appear here.", SwingConstants.CENTER);
        lblImagePreview.setPreferredSize(new Dimension(350, 250));
        lblImagePreview.setBorder(BorderFactory.createEtchedBorder());
        imagePanel.add(lblImagePreview, BorderLayout.CENTER);
        mainPanel.add(imagePanel, BorderLayout.EAST);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnRegister = new JButton("Register");
        btnSearch = new JButton("Search");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");
        btnClear = new JButton("Clear");
        btnExit = new JButton("Exit");

        buttonPanel.add(btnRegister);
        buttonPanel.add(btnSearch);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear);
        buttonPanel.add(btnExit);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        btnClear.addActionListener(e -> clearForm());
        btnExit.addActionListener(e -> System.exit(0));
        btnRegister.addActionListener(e -> registerParticipant());
        btnSearch.addActionListener(e -> searchParticipant());
        btnUpdate.addActionListener(e -> updateParticipant());
        btnDelete.addActionListener(e -> deleteParticipant());
        btnBrowseImage.addActionListener(e -> browseImage());
    }

    // --- Database and Helper Methods (clearForm, displayImage, browseImage - same as before) ---
    private Connection getDatabaseConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    private void clearForm() {
        txtRegistrationID.setText("");
        txtStudentName.setText("");
        txtFaculty.setText("");
        txtProjectTitle.setText("");
        txtContactNumber.setText("");
        txtEmailAddress.setText("");
        lblImagePath.setText("No image selected.");
        currentImagePath = null;
        if (lblImagePreview != null) {
            lblImagePreview.setIcon(null);
            lblImagePreview.setText("Image preview will appear here.");
        }
        txtRegistrationID.setEditable(true);
        btnRegister.setEnabled(true);
        loadedRegistrationID = null;
    }

    private void displayImage(String imagePath) {
        if (imagePath != null && !imagePath.isEmpty()) {
            try {
                File imgFile = new File(imagePath);
                if (!imgFile.exists()) {
                    lblImagePreview.setText("Image not found at path.");
                    lblImagePreview.setIcon(null);
                    lblImagePath.setText("Not found: " + new File(imagePath).getName());
                    return;
                }
                BufferedImage originalImage = ImageIO.read(imgFile);
                if (originalImage == null) {
                    lblImagePreview.setText("Cannot read image file.");
                    lblImagePreview.setIcon(null);
                    lblImagePath.setText("Error: " + new File(imagePath).getName());
                    return;
                }
                int previewWidth = lblImagePreview.getWidth() > 0 ? lblImagePreview.getWidth() : lblImagePreview.getPreferredSize().width;
                int previewHeight = lblImagePreview.getHeight() > 0 ? lblImagePreview.getHeight() : lblImagePreview.getPreferredSize().height;
                previewWidth = Math.max(1, previewWidth); // Ensure positive dimensions
                previewHeight = Math.max(1, previewHeight);

                Image scaledImage = originalImage.getScaledInstance(previewWidth, previewHeight, Image.SCALE_SMOOTH);
                lblImagePreview.setIcon(new ImageIcon(scaledImage));
                lblImagePreview.setText("");
                lblImagePath.setText(imgFile.getName());
                currentImagePath = imagePath;
            } catch (IOException ex) {
                lblImagePreview.setText("Error loading image.");
                lblImagePreview.setIcon(null);
                lblImagePath.setText("Error: " + new File(imagePath).getName());
                ex.printStackTrace();
            }
        } else {
            lblImagePreview.setIcon(null);
            lblImagePreview.setText("No image provided.");
            lblImagePath.setText("No image selected.");
            currentImagePath = null;
        }
    }

    private void browseImage() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select Project Image");
        fileChooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files (JPG, PNG, GIF)", "jpg", "jpeg", "png", "gif");
        fileChooser.addChoosableFileFilter(filter);
        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            displayImage(selectedFile.getAbsolutePath());
        }
    }

    // --- Input Validation Method ---
    private boolean validateInput(String regID, String name, String faculty, String projectTitle, String contact, String email) {
        StringBuilder errors = new StringBuilder();

        if (regID.isEmpty()) errors.append("- Registration ID cannot be empty.\n");
        if (name.isEmpty()) errors.append("- Student Name cannot be empty.\n");
        if (faculty.isEmpty()) errors.append("- Faculty cannot be empty.\n");
        if (projectTitle.isEmpty()) errors.append("- Project Title cannot be empty.\n");
        if (contact.isEmpty()) errors.append("- Contact Number cannot be empty.\n");

        if (email.isEmpty()) {
            errors.append("- Email Address cannot be empty.\n");
        } else if (!EMAIL_PATTERN.matcher(email).matches()) {
            errors.append("- Email Address is not in a valid format (e.g., user@example.com).\n");
        }

        // Optional: Basic check for contact number (e.g., numeric)
        if (!contact.isEmpty() && !contact.matches("\\d+")) { // Simple check for digits only
            errors.append("- Contact Number should only contain digits.\n");
        }


        if (errors.length() > 0) {
            JOptionPane.showMessageDialog(this, "Please correct the following errors:\n" + errors.toString(), "Input Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    // --- CRUD Methods ---
    private void registerParticipant() {
        String regID = txtRegistrationID.getText().trim();
        String name = txtStudentName.getText().trim();
        String faculty = txtFaculty.getText().trim();
        String projectTitle = txtProjectTitle.getText().trim();
        String contact = txtContactNumber.getText().trim();
        String email = txtEmailAddress.getText().trim();

        if (!validateInput(regID, name, faculty, projectTitle, contact, email)) {
            return; // Stop if validation fails
        }

        String sql = "INSERT INTO Participants (RegistrationID, StudentName, Faculty, ProjectTitle, ContactNumber, EmailAddress, ProjectImage) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getDatabaseConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, regID);
            pstmt.setString(2, name);
            pstmt.setString(3, faculty);
            pstmt.setString(4, projectTitle);
            pstmt.setString(5, contact);
            pstmt.setString(6, email);
            pstmt.setString(7, currentImagePath);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                JOptionPane.showMessageDialog(this, "Participant registered successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Registration failed. No rows affected.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            if (ex.getMessage().toLowerCase().contains("primary key") || ex.getMessage().toLowerCase().contains("unique constraint") || (ex.getSQLState() != null && ex.getSQLState().startsWith("23"))) {
                JOptionPane.showMessageDialog(this, "Registration failed: Registration ID '" + regID + "' already exists.", "Duplicate Entry", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Database error during registration: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }

    private void searchParticipant() {
        // ... (Search logic remains the same)
        String regID = txtRegistrationID.getText().trim();
        if (regID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a Registration ID to search.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String tempRegID = txtRegistrationID.getText();
        clearForm();
        txtRegistrationID.setText(tempRegID);

        String sql = "SELECT StudentName, Faculty, ProjectTitle, ContactNumber, EmailAddress, ProjectImage FROM Participants WHERE RegistrationID = ?";
        try (Connection conn = getDatabaseConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, regID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                txtStudentName.setText(rs.getString("StudentName"));
                txtFaculty.setText(rs.getString("Faculty"));
                txtProjectTitle.setText(rs.getString("ProjectTitle"));
                txtContactNumber.setText(rs.getString("ContactNumber"));
                txtEmailAddress.setText(rs.getString("EmailAddress"));
                String imagePathFromDB = rs.getString("ProjectImage");
                displayImage(imagePathFromDB);
                JOptionPane.showMessageDialog(this, "Participant found.", "Search Success", JOptionPane.INFORMATION_MESSAGE);
                txtRegistrationID.setEditable(false);
                btnRegister.setEnabled(false);
                loadedRegistrationID = regID;
            } else {
                JOptionPane.showMessageDialog(this, "No participant found with Registration ID: " + regID, "Not Found", JOptionPane.WARNING_MESSAGE);
                clearForm();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error during search: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void updateParticipant() {
        String regIDFromField = txtRegistrationID.getText().trim(); // Not used for WHERE, but can be used for validation if needed
        if (loadedRegistrationID == null || loadedRegistrationID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please search for a participant before attempting to update.", "Update Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String name = txtStudentName.getText().trim();
        String faculty = txtFaculty.getText().trim();
        String projectTitle = txtProjectTitle.getText().trim();
        String contact = txtContactNumber.getText().trim();
        String email = txtEmailAddress.getText().trim();

        // For update, Registration ID (loadedRegistrationID) is the key, not data being changed by user here.
        // Validate other fields.
        if (!validateInput(loadedRegistrationID, name, faculty, projectTitle, contact, email)) {
            return; // Stop if validation fails
        }

        String sql = "UPDATE Participants SET StudentName = ?, Faculty = ?, ProjectTitle = ?, ContactNumber = ?, EmailAddress = ?, ProjectImage = ? WHERE RegistrationID = ?";
        try (Connection conn = getDatabaseConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, faculty);
            pstmt.setString(3, projectTitle);
            pstmt.setString(4, contact);
            pstmt.setString(5, email);
            pstmt.setString(6, currentImagePath);
            pstmt.setString(7, loadedRegistrationID);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                JOptionPane.showMessageDialog(this, "Participant details updated successfully for ID: " + loadedRegistrationID, "Update Success", JOptionPane.INFORMATION_MESSAGE);
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Update failed. No participant found with ID: " + loadedRegistrationID + " (or data was unchanged).", "Update Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error during update: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void deleteParticipant() {
        // ... (Delete logic remains the same)
        String regIDToDelete = txtRegistrationID.getText().trim();
        final String idToDelete;
        if (loadedRegistrationID != null && !txtRegistrationID.isEditable() && loadedRegistrationID.equals(regIDToDelete)) {
            idToDelete = loadedRegistrationID;
        } else {
            idToDelete = regIDToDelete;
            if(loadedRegistrationID != null && !loadedRegistrationID.equals(idToDelete)) {
                 int confirmChange = JOptionPane.showConfirmDialog(this,
                        "The Registration ID in the field ('" + idToDelete + "') is different from the loaded one ('" + loadedRegistrationID + "').\n" +
                        "Delete record with ID: '" + idToDelete + "'?",
                        "Confirm Deletion ID", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (confirmChange == JOptionPane.NO_OPTION) return;
            }
        }
        if (idToDelete.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter or search for a Registration ID to delete.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete participant with Registration ID: " + idToDelete + "?", "Confirm Deletion", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (confirm == JOptionPane.NO_OPTION) return;
        String sql = "DELETE FROM Participants WHERE RegistrationID = ?";
        try (Connection conn = getDatabaseConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, idToDelete);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                JOptionPane.showMessageDialog(this, "Participant with ID: " + idToDelete + " deleted successfully.", "Delete Success", JOptionPane.INFORMATION_MESSAGE);
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Delete failed. No participant found with ID: " + idToDelete, "Delete Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error during deletion: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ExhibitionRegistrationGUI gui = new ExhibitionRegistrationGUI();
            gui.setVisible(true);
        });
    }
}

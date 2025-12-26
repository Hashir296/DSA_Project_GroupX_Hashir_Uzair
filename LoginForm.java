/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
//package gui;
//
//import utils.SessionManager; // Make sure this import exists
//import javax.swing.*;
//import javax.swing.border.EmptyBorder;
//import javax.swing.border.LineBorder;
//import java.awt.*;
//import java.awt.event.*;
//import java.util.regex.Pattern;
//
///**
// * Fully professional Login Form - Modern Card Style with Premium Design
// * Features: Login / Register / Forgot Password, Remember Me, Inline validation
// */
//public class LoginForm extends JFrame {
//
//    private static final long serialVersionUID = 1L;
//
//    // Theme Colors
//    private static final Color BG_COLOR = new Color(248, 244, 240);
//    private static final Color CARD_COLOR = new Color(255, 255, 255);
//    private static final Color ACCENT_COLOR = new Color(139, 117, 97);
//    private static final Color TEXT_COLOR = new Color(46, 41, 37);
//    private static final Color ERROR_COLOR = new Color(200, 80, 80);
//    private static final Color SUCCESS_COLOR = new Color(80, 180, 80);
//    private static final Color INPUT_BORDER_COLOR = new Color(200, 200, 200);
//
//    // Fonts
//    private static final Font TITLE_FONT = new Font("Georgia", Font.BOLD, 30);
//    private static final Font SUBTITLE_FONT = new Font("Serif", Font.ITALIC, 16);
//    private static final Font LABEL_FONT = new Font("Serif", Font.PLAIN, 16);
//    private static final Font INPUT_FONT = new Font("Serif", Font.PLAIN, 16);
//    private static final Font BUTTON_FONT = new Font("Serif", Font.BOLD, 16);
//    private static final Font ERROR_FONT = new Font("Serif", Font.ITALIC, 12);
//
//    // Components
//    private JTextField usernameField;
//    private JPasswordField passwordField;
//    private JCheckBox rememberMeCheckBox;
//    private JButton loginButton, registerButton, forgotButton;
//    private JLabel usernameErrorLabel, passwordErrorLabel, generalErrorLabel;
//
//    // Validation Patterns
//    private static final Pattern USER_PATTERN = Pattern.compile("^[a-zA-Z0-9_]{3,20}$");
//    private static final Pattern PASS_PATTERN = Pattern.compile("^.{6,}$");
//
//    /**
//     * Constructor - initialize UI and listeners
//     */
//    public LoginForm() {
//        initializeFrame();
//        setupLayout();
//        addEventListeners();
//    }
//
//    /**
//     * Initialize JFrame properties
//     */
//    private void initializeFrame() {
//        setTitle("AI-Powered Personal Finance Analyzer - Login");
//        setSize(1024, 768);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLocationRelativeTo(null);
//        setResizable(false);
//        getContentPane().setBackground(BG_COLOR);
//
//        try {
//            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * Setup full layout with card panel
//     */
//    private void setupLayout() {
//        setLayout(new BorderLayout());
//
//        // Main Card Panel
//        JPanel cardPanel = new JPanel(new GridBagLayout());
//        cardPanel.setBackground(CARD_COLOR);
//        cardPanel.setBorder(BorderFactory.createCompoundBorder(
//                new LineBorder(INPUT_BORDER_COLOR, 1),
//                new EmptyBorder(40, 40, 40, 40)));
//
//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.insets = new Insets(10, 10, 10, 10);
//        gbc.fill = GridBagConstraints.HORIZONTAL;
//
//        // Title
//        JLabel titleLabel = new JLabel("AI-Powered Personal Finance Analyzer");
//        titleLabel.setFont(TITLE_FONT);
//        titleLabel.setForeground(TEXT_COLOR);
//        titleLabel.setHorizontalAlignment(JLabel.CENTER);
//        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
//        cardPanel.add(titleLabel, gbc);
//
//        // Subtitle
//        JLabel subtitleLabel = new JLabel("Secure Financial Insights at Your Fingertips");
//        subtitleLabel.setFont(SUBTITLE_FONT);
//        subtitleLabel.setForeground(ACCENT_COLOR);
//        subtitleLabel.setHorizontalAlignment(JLabel.CENTER);
//        gbc.gridy = 1;
//        cardPanel.add(subtitleLabel, gbc);
//
//        // Username Label
//        JLabel usernameLabel = new JLabel("Username:");
//        usernameLabel.setFont(LABEL_FONT);
//        usernameLabel.setForeground(TEXT_COLOR);
//        gbc.gridy = 2; gbc.gridwidth = 1; gbc.gridx = 0;
//        gbc.anchor = GridBagConstraints.EAST;
//        cardPanel.add(usernameLabel, gbc);
//
//        // Username Field
//        usernameField = new JTextField(20);
//        styleTextField(usernameField);
//        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
//        cardPanel.add(usernameField, gbc);
//
//        // Username error
//        usernameErrorLabel = new JLabel(" ");
//        usernameErrorLabel.setFont(ERROR_FONT);
//        usernameErrorLabel.setForeground(ERROR_COLOR);
//        gbc.gridy = 3;
//        cardPanel.add(usernameErrorLabel, gbc);
//
//        // Password Label
//        JLabel passwordLabel = new JLabel("Password:");
//        passwordLabel.setFont(LABEL_FONT);
//        passwordLabel.setForeground(TEXT_COLOR);
//        gbc.gridy = 4; gbc.gridx = 0; gbc.anchor = GridBagConstraints.EAST;
//        cardPanel.add(passwordLabel, gbc);
//
//        // Password Field
//        passwordField = new JPasswordField(20);
//        styleTextField(passwordField);
//        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
//        cardPanel.add(passwordField, gbc);
//
//        // Password error
//        passwordErrorLabel = new JLabel(" ");
//        passwordErrorLabel.setFont(ERROR_FONT);
//        passwordErrorLabel.setForeground(ERROR_COLOR);
//        gbc.gridy = 5;
//        cardPanel.add(passwordErrorLabel, gbc);
//
//        // Remember Me
//        rememberMeCheckBox = new JCheckBox("Remember Me");
//        rememberMeCheckBox.setBackground(CARD_COLOR);
//        rememberMeCheckBox.setForeground(TEXT_COLOR);
//        rememberMeCheckBox.setFont(LABEL_FONT);
//        gbc.gridy = 6; gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
//        cardPanel.add(rememberMeCheckBox, gbc);
//
//        // General error
//        generalErrorLabel = new JLabel(" ");
//        generalErrorLabel.setFont(ERROR_FONT);
//        generalErrorLabel.setForeground(ERROR_COLOR);
//        gbc.gridy = 7; gbc.gridx = 0; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
//        cardPanel.add(generalErrorLabel, gbc);
//
//        // Login Button
//        loginButton = createStyledButton("Login", ACCENT_COLOR);
//        gbc.gridy = 8; gbc.gridwidth = 2;
//        cardPanel.add(loginButton, gbc);
//
//        // Register Button
//        registerButton = createStyledButton("Create New Account", ACCENT_COLOR);
//        gbc.gridy = 9;
//        cardPanel.add(registerButton, gbc);
//
//        // Forgot Password Button
//        forgotButton = createStyledButton("Forgot Password?", ACCENT_COLOR);
//        gbc.gridy = 10;
//        cardPanel.add(forgotButton, gbc);
//
//        add(cardPanel, BorderLayout.CENTER);
//
//        // Footer
//        JPanel footer = new JPanel();
//        footer.setBackground(BG_COLOR);
//        JLabel footerLabel = new JLabel("© 2025 AI-Powered Personal Finance Analyzer. All rights reserved.");
//        footerLabel.setFont(ERROR_FONT);
//        footerLabel.setForeground(ACCENT_COLOR);
//        footer.add(footerLabel);
//        add(footer, BorderLayout.SOUTH);
//    }
//
//    /**
//     * Apply custom styles to text fields
//     */
//    private void styleTextField(JTextField field) {
//        field.setFont(INPUT_FONT);
//        field.setBackground(Color.WHITE);
//        field.setForeground(TEXT_COLOR);
//        field.setBorder(BorderFactory.createCompoundBorder(
//                new LineBorder(INPUT_BORDER_COLOR, 1),
//                new EmptyBorder(5, 8, 5, 8)));
//    }
//
//    /**
//     * Create styled button
//     */
//    private JButton createStyledButton(String text, Color bgColor) {
//        JButton btn = new JButton(text);
//        btn.setFont(BUTTON_FONT);
//        btn.setBackground(bgColor);
//        btn.setForeground(Color.WHITE);
//        btn.setFocusPainted(false);
//        btn.setBorderPainted(false);
//        btn.setOpaque(true);
//        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
//        return btn;
//    }
//
//    /**
//     * Add all event listeners
//     */
//    private void addEventListeners() {
//        loginButton.addActionListener(e -> handleLogin());
//        registerButton.addActionListener(e -> handleRegister());
//        forgotButton.addActionListener(e -> handleForgotPassword());
//
//        usernameField.addFocusListener(new FocusAdapter() {
//            public void focusLost(FocusEvent e) { validateUsername(); }
//        });
//
//        passwordField.addFocusListener(new FocusAdapter() {
//            public void focusLost(FocusEvent e) { validatePassword(); }
//        });
//    }
//
//    /**
//     * Handle login
//     */
//    private void handleLogin() {
//        clearErrors();
//        if (!validateUsername() || !validatePassword()) return;
//
//        String user = usernameField.getText().trim();
//        String pass = new String(passwordField.getPassword());
//
//        // Mock authentication
//        if (("admin".equals(user) && "password".equals(pass)) ||
//            ("user1".equals(user) && "pass123".equals(pass)) ||
//            ("demo".equals(user) && "demo123".equals(pass))) {
//            
//            // Set session manager
//            SessionManager.setLoggedIn(true);
//            SessionManager.setUsername(user);
//            
//            JOptionPane.showMessageDialog(this, "Login successful! Welcome, " + user);
//            
//            // Open dashboard and close login form
//            SwingUtilities.invokeLater(() -> {
//                new Dashboard().setVisible(true);
//                dispose(); // Close login form
//            });
//        } else {
//            generalErrorLabel.setText("Invalid username or password.");
//        }
//    }
//
//    /**
//     * Validate username
//     */
//    private boolean validateUsername() {
//        String user = usernameField.getText().trim();
//        if (user.isEmpty()) {
//            usernameErrorLabel.setText("Username required.");
//            return false;
//        } else if (!USER_PATTERN.matcher(user).matches()) {
//            usernameErrorLabel.setText("3-20 chars: letters, numbers, underscore.");
//            return false;
//        } else {
//            usernameErrorLabel.setText(" ");
//            return true;
//        }
//    }
//
//    /**
//     * Validate password
//     */
//    private boolean validatePassword() {
//        String pass = new String(passwordField.getPassword());
//        if (pass.isEmpty()) {
//            passwordErrorLabel.setText("Password required.");
//            return false;
//        } else if (!PASS_PATTERN.matcher(pass).matches()) {
//            passwordErrorLabel.setText("Min 6 characters.");
//            return false;
//        } else {
//            passwordErrorLabel.setText(" ");
//            return true;
//        }
//    }
//
//    private void clearErrors() {
//        usernameErrorLabel.setText(" ");
//        passwordErrorLabel.setText(" ");
//        generalErrorLabel.setText(" ");
//    }
//
//    /**
//     * Handle registration
//     */
//    private void handleRegister() {
//        // Show registration dialog
//        JPanel registerPanel = new JPanel(new GridBagLayout());
//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.insets = new Insets(5, 5, 5, 5);
//
//        JLabel newUserLabel = new JLabel("New Username:");
//        JTextField newUserField = new JTextField(20);
//        JLabel newPassLabel = new JLabel("New Password:");
//        JPasswordField newPassField = new JPasswordField(20);
//
//        gbc.gridx = 0; gbc.gridy = 0; registerPanel.add(newUserLabel, gbc);
//        gbc.gridx = 1; gbc.gridy = 0; registerPanel.add(newUserField, gbc);
//        gbc.gridx = 0; gbc.gridy = 1; registerPanel.add(newPassLabel, gbc);
//        gbc.gridx = 1; gbc.gridy = 1; registerPanel.add(newPassField, gbc);
//
//        int result = JOptionPane.showConfirmDialog(this,
//            registerPanel,
//            "Create New Account",
//            JOptionPane.OK_CANCEL_OPTION,
//            JOptionPane.PLAIN_MESSAGE);
//
//        if (result == JOptionPane.OK_OPTION) {
//            String newUsername = newUserField.getText().trim();
//            String newPassword = new String(newPassField.getPassword());
//
//            if (newUsername.isEmpty() || newPassword.isEmpty()) {
//                JOptionPane.showMessageDialog(this, 
//                    "Username and password cannot be empty.", 
//                    "Registration Error", 
//                    JOptionPane.ERROR_MESSAGE);
//                return;
//            }
//
//            if (!USER_PATTERN.matcher(newUsername).matches()) {
//                JOptionPane.showMessageDialog(this, 
//                    "Username must be 3-20 characters (letters, numbers, underscore).", 
//                    "Registration Error", 
//                    JOptionPane.ERROR_MESSAGE);
//                return;
//            }
//
//            if (newPassword.length() < 6) {
//                JOptionPane.showMessageDialog(this, 
//                    "Password must be at least 6 characters.", 
//                    "Registration Error", 
//                    JOptionPane.ERROR_MESSAGE);
//                return;
//            }
//
//            // In a real app, you'd save to database here
//            JOptionPane.showMessageDialog(this, 
//                "Account created successfully! You can now log in.", 
//                "Registration Success", 
//                JOptionPane.INFORMATION_MESSAGE);
//        }
//    }
//
//    /**
//     * Handle forgot password
//     */
//    private void handleForgotPassword() {
//        String email = JOptionPane.showInputDialog(this, 
//            "Please enter your email address to reset password:", 
//            "Forgot Password", 
//            JOptionPane.QUESTION_MESSAGE);
//        
//        if (email != null && !email.isEmpty()) {
//            if (email.contains("@") && email.contains(".")) {
//                JOptionPane.showMessageDialog(this,
//                    "Password reset instructions have been sent to: " + email,
//                    "Password Reset",
//                    JOptionPane.INFORMATION_MESSAGE);
//            } else {
//                JOptionPane.showMessageDialog(this,
//                    "Please enter a valid email address.",
//                    "Invalid Email",
//                    JOptionPane.ERROR_MESSAGE);
//            }
//        }
//    }
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> new LoginForm().setVisible(true));
//    }
//}
//




//
//
//
//package gui;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.*;
//import java.awt.geom.*;
//
//public class LoginForm extends JFrame {
//    private JTextField usernameField;
//    private JPasswordField passwordField;
//    private JButton loginButton;
//    private JButton clearButton;
//    private JLabel statusLabel;
//    
//    // Old Money Theme Colors
//    private static final Color BACKGROUND_PRIMARY = new Color(15, 23, 30);
//    private static final Color BACKGROUND_SECONDARY = new Color(25, 35, 45);
//    private static final Color ACCENT_GOLD = new Color(212, 175, 55);
//    private static final Color TEXT_PRIMARY = new Color(240, 240, 240);
//    private static final Color TEXT_SECONDARY = new Color(180, 180, 180);
//    private static final Color BORDER_COLOR = new Color(60, 70, 80);
//    
//    public LoginForm() {
//        initializeComponents();
//        setupLayout();
//        attachEventHandlers();
//    }
//    
//    private void initializeComponents() {
//        setTitle("AI-Powered Personal Finance Analyzer");
//        setSize(800, 600);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLocationRelativeTo(null);
//        setResizable(false);
//        getContentPane().setBackground(BACKGROUND_PRIMARY);
//    }
//    
//    private void setupLayout() {
//        JPanel mainPanel = new JPanel() {
//            @Override
//            protected void paintComponent(Graphics g) {
//                super.paintComponent(g);
//                Graphics2D g2d = (Graphics2D) g;
//                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//                
//                // Gradient background
//                GradientPaint gradient = new GradientPaint(0, 0, BACKGROUND_PRIMARY, 
//                                                          0, getHeight(), BACKGROUND_SECONDARY);
//                g2d.setPaint(gradient);
//                g2d.fillRect(0, 0, getWidth(), getHeight());
//            }
//        };
//        mainPanel.setLayout(null);
//        
//        // Title Label
//        JLabel titleLabel = new JLabel("FINANCE ANALYZER", SwingConstants.CENTER);
//        titleLabel.setFont(new Font("Serif", Font.BOLD, 42));
//        titleLabel.setForeground(ACCENT_GOLD);
//        titleLabel.setBounds(150, 80, 500, 60);
//        mainPanel.add(titleLabel);
//        
//        // Subtitle
//        JLabel subtitleLabel = new JLabel("AI-Powered Investment Intelligence", SwingConstants.CENTER);
//        subtitleLabel.setFont(new Font("Serif", Font.ITALIC, 18));
//        subtitleLabel.setForeground(TEXT_SECONDARY);
//        subtitleLabel.setBounds(150, 140, 500, 30);
//        mainPanel.add(subtitleLabel);
//        
//        // Login Panel
//        JPanel loginPanel = createStyledPanel();
//        loginPanel.setBounds(225, 220, 350, 280);
//        loginPanel.setLayout(null);
//        
//        // Username Label
//        JLabel userLabel = new JLabel("USERNAME");
//        userLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
//        userLabel.setForeground(TEXT_SECONDARY);
//        userLabel.setBounds(30, 30, 290, 25);
//        loginPanel.add(userLabel);
//        
//        // Username Field
//        usernameField = createStyledTextField();
//        usernameField.setBounds(30, 55, 290, 40);
//        loginPanel.add(usernameField);
//        
//        // Password Label
//        JLabel passLabel = new JLabel("PASSWORD");
//        passLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
//        passLabel.setForeground(TEXT_SECONDARY);
//        passLabel.setBounds(30, 105, 290, 25);
//        loginPanel.add(passLabel);
//        
//        // Password Field
//        passwordField = createStyledPasswordField();
//        passwordField.setBounds(30, 130, 290, 40);
//        loginPanel.add(passwordField);
//        
//        // Login Button
//        loginButton = createStyledButton("LOGIN", true);
//        loginButton.setBounds(30, 190, 290, 45);
//        loginPanel.add(loginButton);
//        
//        // Clear Button
//        clearButton = createStyledButton("CLEAR", false);
//        clearButton.setBounds(30, 240, 140, 35);
//        loginPanel.add(clearButton);
//        
//        mainPanel.add(loginPanel);
//        
//        // Status Label
//        statusLabel = new JLabel("", SwingConstants.CENTER);
//        statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
//        statusLabel.setForeground(new Color(255, 100, 100));
//        statusLabel.setBounds(150, 510, 500, 25);
//        mainPanel.add(statusLabel);
//        
//        // Footer
//        JLabel footerLabel = new JLabel("© 2024 Finance Analyzer. All Rights Reserved.", SwingConstants.CENTER);
//        footerLabel.setFont(new Font("SansSerif", Font.PLAIN, 11));
//        footerLabel.setForeground(TEXT_SECONDARY);
//        footerLabel.setBounds(150, 545, 500, 20);
//        mainPanel.add(footerLabel);
//        
//        add(mainPanel);
//    }
//    
//    private JPanel createStyledPanel() {
//        JPanel panel = new JPanel() {
//            @Override
//            protected void paintComponent(Graphics g) {
//                super.paintComponent(g);
//                Graphics2D g2d = (Graphics2D) g;
//                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//                
//                // Background with border
//                g2d.setColor(BACKGROUND_SECONDARY);
//                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
//                
//                // Border
//                g2d.setColor(BORDER_COLOR);
//                g2d.setStroke(new BasicStroke(2));
//                g2d.drawRoundRect(1, 1, getWidth()-2, getHeight()-2, 15, 15);
//                
//                // Gold accent line
//                g2d.setColor(ACCENT_GOLD);
//                g2d.drawRoundRect(3, 3, getWidth()-6, getHeight()-6, 12, 12);
//            }
//        };
//        panel.setOpaque(false);
//        return panel;
//    }
//    
//    private JTextField createStyledTextField() {
//        JTextField field = new JTextField();
//        field.setFont(new Font("SansSerif", Font.PLAIN, 15));
//        field.setBackground(BACKGROUND_PRIMARY);
//        field.setForeground(TEXT_PRIMARY);
//        field.setCaretColor(ACCENT_GOLD);
//        field.setBorder(BorderFactory.createCompoundBorder(
//            BorderFactory.createLineBorder(BORDER_COLOR, 2),
//            BorderFactory.createEmptyBorder(5, 10, 5, 10)
//        ));
//        return field;
//    }
//    
//    private JPasswordField createStyledPasswordField() {
//        JPasswordField field = new JPasswordField();
//        field.setFont(new Font("SansSerif", Font.PLAIN, 15));
//        field.setBackground(BACKGROUND_PRIMARY);
//        field.setForeground(TEXT_PRIMARY);
//        field.setCaretColor(ACCENT_GOLD);
//        field.setBorder(BorderFactory.createCompoundBorder(
//            BorderFactory.createLineBorder(BORDER_COLOR, 2),
//            BorderFactory.createEmptyBorder(5, 10, 5, 10)
//        ));
//        return field;
//    }
//    
//    private JButton createStyledButton(String text, boolean isPrimary) {
//        JButton button = new JButton(text) {
//            @Override
//            protected void paintComponent(Graphics g) {
//                Graphics2D g2d = (Graphics2D) g;
//                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//                
//                if (getModel().isPressed()) {
//                    g2d.setColor(isPrimary ? ACCENT_GOLD.darker() : BACKGROUND_PRIMARY);
//                } else if (getModel().isRollover()) {
//                    g2d.setColor(isPrimary ? ACCENT_GOLD.brighter() : BACKGROUND_SECONDARY.brighter());
//                } else {
//                    g2d.setColor(isPrimary ? ACCENT_GOLD : BACKGROUND_SECONDARY);
//                }
//                
//                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
//                
//                // Border
//                g2d.setColor(isPrimary ? ACCENT_GOLD.darker() : BORDER_COLOR);
//                g2d.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 8, 8);
//                
//                // Text
//                FontMetrics fm = g2d.getFontMetrics();
//                int textWidth = fm.stringWidth(getText());
//                int textHeight = fm.getAscent();
//                
//                g2d.setColor(isPrimary ? BACKGROUND_PRIMARY : TEXT_PRIMARY);
//                g2d.setFont(getFont());
//                g2d.drawString(getText(), (getWidth() - textWidth) / 2, 
//                              (getHeight() + textHeight) / 2 - 2);
//            }
//        };
//        button.setFont(new Font("SansSerif", Font.BOLD, 14));
//        button.setFocusPainted(false);
//        button.setBorderPainted(false);
//        button.setContentAreaFilled(false);
//        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
//        return button;
//    }
//    
//    private void attachEventHandlers() {
//        loginButton.addActionListener(e -> handleLogin());
//        clearButton.addActionListener(e -> handleClear());
//        
//        // Enter key on password field triggers login
//        passwordField.addActionListener(e -> handleLogin());
//        
//        // Clear status on field focus
//        usernameField.addFocusListener(new FocusAdapter() {
//            @Override
//            public void focusGained(FocusEvent e) {
//                statusLabel.setText("");
//            }
//        });
//        
//        passwordField.addFocusListener(new FocusAdapter() {
//            @Override
//            public void focusGained(FocusEvent e) {
//                statusLabel.setText("");
//            }
//        });
//    }
//    
//    private void handleLogin() {
//        String username = usernameField.getText().trim();
//        String password = new String(passwordField.getPassword()).trim();
//        
//        if (username.isEmpty() || password.isEmpty()) {
//            statusLabel.setText("Please enter both username and password");
//            statusLabel.setForeground(new Color(255, 100, 100));
//            return;
//        }
//        
//        // Simple validation (in real application, use proper authentication)
//        if (username.length() >= 3 && password.length() >= 4) {
//            statusLabel.setText("Login successful! Loading dashboard...");
//            statusLabel.setForeground(ACCENT_GOLD);
//            
//            // Delay to show success message
//            Timer timer = new Timer(1000, e -> {
//                this.dispose();
//                SwingUtilities.invokeLater(() -> {
//                    Dashboard dashboard = new Dashboard(username);
//                    dashboard.setVisible(true);
//                });
//            });
//            timer.setRepeats(false);
//            timer.start();
//        } else {
//            statusLabel.setText("Invalid credentials. Username must be 3+ chars, password 4+ chars");
//            statusLabel.setForeground(new Color(255, 100, 100));
//        }
//    }
//    
//    private void handleClear() {
//        usernameField.setText("");
//        passwordField.setText("");
//        statusLabel.setText("");
//        usernameField.requestFocus();
//    }
//    
//    public static void main(String[] args) {
//        try {
//            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        
//        SwingUtilities.invokeLater(() -> {
//            LoginForm loginForm = new LoginForm();
//            loginForm.setVisible(true);
//        });
//    }
//}




package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.util.HashMap;
import java.util.Map;

public class LoginForm extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel statusLabel;
    private JLabel forgotPasswordLabel;
    private JButton togglePasswordButton;

    // Professional Theme Colors
    private static final Color BG_DARK = new Color(18, 25, 33);
    private static final Color BG_PANEL = new Color(28, 38, 50);
    private static final Color ACCENT = new Color(212, 175, 55); // Gold
    private static final Color TEXT_LIGHT = Color.WHITE;
    private static final Color TEXT_GRAY = new Color(180, 185, 195);
    private static final Color BORDER = new Color(60, 75, 90);
    private static final Color ERROR_RED = new Color(230, 80, 80);
    private static final Color SUCCESS_GREEN = new Color(80, 200, 120);

    private static final Map<String, String> VALID_USERS = new HashMap<>();
    static {
        VALID_USERS.put("admin", "secure123");
        VALID_USERS.put("user", "pass123");
        VALID_USERS.put("analyst", "finance2025");
    }

    public LoginForm() {
        initializeComponents();
        setupLayout();
        attachEventHandlers();
    }

    private void initializeComponents() {
        setTitle("AI-Powered Personal Finance Analyzer");
        setSize(1100, 680); // ✅ Reduced height — footer now fits neatly
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(BG_DARK);
    }

    private void setupLayout() {
        setLayout(null);

        // Title
        JLabel titleLabel = new JLabel("Finance Analyzer", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 48));
        titleLabel.setForeground(ACCENT);
        titleLabel.setBounds(0, 80, 1100, 60);
        add(titleLabel);

        // Subtitle
        JLabel subtitle = new JLabel("AI-Powered Investment Intelligence", SwingConstants.CENTER);
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        subtitle.setForeground(TEXT_GRAY);
        subtitle.setBounds(0, 140, 1100, 25);
        add(subtitle);

        // Login Panel
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(BG_PANEL);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 25, 25));
                g2.dispose();
            }
        };
        panel.setBounds(375, 200, 350, 260); // Centered in reduced height
        panel.setLayout(null);
        panel.setOpaque(false);
        add(panel);

        // Username
        JLabel userLabel = new JLabel("Username");
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        userLabel.setForeground(TEXT_GRAY);
        userLabel.setBounds(30, 20, 290, 25);
        panel.add(userLabel);

        usernameField = createTextField(16);
        usernameField.setBounds(30, 45, 290, 45);
        panel.add(usernameField);

        // Password
        JLabel passLabel = new JLabel("Password");
        passLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        passLabel.setForeground(TEXT_GRAY);
        passLabel.setBounds(30, 100, 290, 25);
        panel.add(passLabel);

        passwordField = createPasswordField(16);
        passwordField.setBounds(30, 125, 250, 45);
        panel.add(passwordField);

        // Toggle Password
        togglePasswordButton = new JButton("👁");
        togglePasswordButton.setFont(new Font("Dialog", Font.PLAIN, 18));
        togglePasswordButton.setBounds(285, 125, 35, 45);
        togglePasswordButton.setFocusable(false);
        togglePasswordButton.setOpaque(false);
        togglePasswordButton.setContentAreaFilled(false);
        togglePasswordButton.setBorder(BorderFactory.createEmptyBorder());
        togglePasswordButton.setForeground(TEXT_GRAY);
        togglePasswordButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        panel.add(togglePasswordButton);

        // Login Button
        loginButton = createButton("Login", true, 17);
        loginButton.setBounds(30, 185, 290, 50);
        panel.add(loginButton);

        // Forgot Password (no Clear button)
        forgotPasswordLabel = new JLabel("<html><u>Forgot Password?</u></html>");
        forgotPasswordLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        forgotPasswordLabel.setForeground(ACCENT);
        forgotPasswordLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        forgotPasswordLabel.setBounds(110, 240, 180, 25); // Centered in panel
        panel.add(forgotPasswordLabel);

        // Status
        statusLabel = new JLabel("", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        statusLabel.setBounds(0, 580, 1100, 25);
        add(statusLabel);

        // Footer — now fully visible and clean
        JLabel footer = new JLabel("© 2025 Finance Analyzer. All Rights Reserved.", SwingConstants.CENTER);
        footer.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        footer.setForeground(TEXT_GRAY);
        footer.setBounds(0, 645, 1100, 20);
        add(footer);
    }

    private JTextField createTextField(int fontSize) {
        JTextField tf = new JTextField();
        tf.setFont(new Font("Segoe UI", Font.PLAIN, fontSize));
        tf.setBackground(BG_DARK);
        tf.setForeground(TEXT_LIGHT);
        tf.setCaretColor(ACCENT);
        tf.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER, 2),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        return tf;
    }

    private JPasswordField createPasswordField(int fontSize) {
        JPasswordField pf = new JPasswordField();
        pf.setFont(new Font("Segoe UI", Font.PLAIN, fontSize));
        pf.setBackground(BG_DARK);
        pf.setForeground(TEXT_LIGHT);
        pf.setCaretColor(ACCENT);
        pf.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER, 2),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        return pf;
    }

    private JButton createButton(String text, boolean primary, int fontSize) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                Color base = primary ? ACCENT : new Color(50, 60, 75);
                if (getModel().isPressed()) {
                    base = primary ? ACCENT.darker() : base.darker();
                } else if (getModel().isRollover()) {
                    base = primary ? ACCENT.brighter() : base.brighter();
                }
                g2.setColor(base);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                g2.dispose();

                super.paintComponent(g); // ✅ Ensures text is visible
            }

            @Override
            protected void paintBorder(Graphics g) {}
        };

        btn.setFont(new Font("Segoe UI", Font.BOLD, fontSize));
        btn.setForeground(primary ? BG_DARK : TEXT_LIGHT);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void attachEventHandlers() {
        togglePasswordButton.addActionListener(e -> {
            if (passwordField.getEchoChar() == '\u2022') {
                passwordField.setEchoChar((char) 0);
                togglePasswordButton.setText("🔒");
            } else {
                passwordField.setEchoChar('\u2022');
                togglePasswordButton.setText("👁");
            }
        });

        loginButton.addActionListener(e -> handleLogin());
        passwordField.addActionListener(e -> handleLogin());

        // Forgot Password
        forgotPasswordLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(
                    LoginForm.this,
                    "<html><b>Forgot your password?</b><br>" +
                    "Contact system administrator:<br>" +
                    "<i>support@financeanalyzer.com</i></html>",
                    "Password Recovery",
                    JOptionPane.INFORMATION_MESSAGE
                );
            }
        });

        FocusListener clearStatus = new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                statusLabel.setText("");
            }
        };
        usernameField.addFocusListener(clearStatus);
        passwordField.addFocusListener(clearStatus);
    }

    private void handleLogin() {
        String user = usernameField.getText().trim();
        String pass = new String(passwordField.getPassword()).trim();

        if (user.isEmpty() || pass.isEmpty()) {
            showError("Please enter both username and password.");
            return;
        }

        if (VALID_USERS.containsKey(user) && VALID_USERS.get(user).equals(pass)) {
            showSuccess("Authentication successful. Launching dashboard...");
            Timer timer = new Timer(1200, ev -> {
                dispose();
                SwingUtilities.invokeLater(() -> {
                    Dashboard dashboard = new Dashboard(user);
                    dashboard.setVisible(true);
                });
            });
            timer.setRepeats(false);
            timer.start();
        } else {
            showError("Invalid username or password.");
        }
    }

    private void showError(String msg) {
        statusLabel.setForeground(ERROR_RED);
        statusLabel.setText(msg);
    }

    private void showSuccess(String msg) {
        statusLabel.setForeground(SUCCESS_GREEN);
        statusLabel.setText(msg);
    }

    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> {
            new LoginForm().setVisible(true);
        });
    }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


package gui;

import integration.JNIHandler;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class RecommendationForm extends JPanel {
    private JLabel decisionLabel;
    private JLabel confidenceLabel;
    private JTextArea explanationArea;
    private JButton getRecommendationButton;
    private JButton resetButton;
    private JSlider riskAppetiteSlider;
    private JProgressBar confidenceBar;
    
    private static final Color BACKGROUND_PRIMARY = new Color(15, 23, 30);
    private static final Color BACKGROUND_SECONDARY = new Color(25, 35, 45);
    private static final Color ACCENT_GOLD = new Color(212, 175, 55);
    private static final Color TEXT_PRIMARY = new Color(240, 240, 240);
    private static final Color TEXT_SECONDARY = new Color(180, 180, 180);
    private static final Color BORDER_COLOR = new Color(60, 70, 80);
    private static final Color SUCCESS_GREEN = new Color(80, 200, 120);
    private static final Color WARNING_RED = new Color(255, 100, 100);
    private static final Color HOLD_ORANGE = new Color(255, 180, 60);
    
    private JNIHandler jniHandler;
    
    public RecommendationForm() {
        jniHandler = new JNIHandler();
        setupLayout();
        attachEventHandlers();
    }
    
    private void setupLayout() {
        setLayout(null);
        setOpaque(false);
        
        // Title
        JLabel titleLabel = new JLabel("AI RECOMMENDATION MODULE");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        titleLabel.setForeground(ACCENT_GOLD);
        titleLabel.setBounds(50, 20, 450, 35);
        add(titleLabel);
        
        // Input Panel
        JPanel inputPanel = createStyledPanel();
        inputPanel.setBounds(50, 70, 350, 200);
        inputPanel.setLayout(null);
        
        JLabel riskLabel = createLabel("RISK APPETITE");
        riskLabel.setBounds(20, 20, 310, 20);
        inputPanel.add(riskLabel);
        
        riskAppetiteSlider = new JSlider(1, 10, 5);
        riskAppetiteSlider.setMajorTickSpacing(3);
        riskAppetiteSlider.setMinorTickSpacing(1);
        riskAppetiteSlider.setPaintTicks(true);
        riskAppetiteSlider.setPaintLabels(true);
        riskAppetiteSlider.setBackground(BACKGROUND_SECONDARY);
        riskAppetiteSlider.setForeground(TEXT_SECONDARY);
        riskAppetiteSlider.setBounds(20, 45, 310, 50);
        inputPanel.add(riskAppetiteSlider);
        
        JLabel lowLabel = new JLabel("Conservative");
        lowLabel.setFont(new Font("SansSerif", Font.ITALIC, 10));
        lowLabel.setForeground(TEXT_SECONDARY);
        lowLabel.setBounds(20, 95, 100, 15);
        inputPanel.add(lowLabel);
        
        JLabel highLabel = new JLabel("Aggressive");
        highLabel.setFont(new Font("SansSerif", Font.ITALIC, 10));
        highLabel.setForeground(TEXT_SECONDARY);
        highLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        highLabel.setBounds(230, 95, 100, 15);
        inputPanel.add(highLabel);
        
        getRecommendationButton = createStyledButton("GET RECOMMENDATION", true);
        getRecommendationButton.setBounds(20, 125, 310, 40);
        inputPanel.add(getRecommendationButton);
        
        resetButton = createStyledButton("RESET", false);
        resetButton.setBounds(20, 170, 150, 25);
        inputPanel.add(resetButton);
        
        add(inputPanel);
        
        // Decision Panel
        JPanel decisionPanel = createStyledPanel();
        decisionPanel.setBounds(420, 70, 330, 200);
        decisionPanel.setLayout(null);
        
        JLabel decisionTitle = new JLabel("AI DECISION", SwingConstants.CENTER);
        decisionTitle.setFont(new Font("SansSerif", Font.BOLD, 14));
        decisionTitle.setForeground(ACCENT_GOLD);
        decisionTitle.setBounds(20, 15, 290, 25);
        decisionPanel.add(decisionTitle);
        
        decisionLabel = new JLabel("--", SwingConstants.CENTER);
        decisionLabel.setFont(new Font("Serif", Font.BOLD, 36));
        decisionLabel.setForeground(TEXT_PRIMARY);
        decisionLabel.setBounds(20, 50, 290, 50);
        decisionPanel.add(decisionLabel);
        
        JLabel confidenceTitle = new JLabel("Confidence Level:", SwingConstants.CENTER);
        confidenceTitle.setFont(new Font("SansSerif", Font.PLAIN, 12));
        confidenceTitle.setForeground(TEXT_SECONDARY);
        confidenceTitle.setBounds(20, 110, 290, 20);
        decisionPanel.add(confidenceTitle);
        
        confidenceLabel = new JLabel("--", SwingConstants.CENTER);
        confidenceLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        confidenceLabel.setForeground(ACCENT_GOLD);
        confidenceLabel.setBounds(20, 135, 290, 30);
        decisionPanel.add(confidenceLabel);
        
        confidenceBar = new JProgressBar(0, 100);
        confidenceBar.setValue(0);
        confidenceBar.setStringPainted(false);
        confidenceBar.setBackground(BACKGROUND_PRIMARY);
        confidenceBar.setForeground(ACCENT_GOLD);
        confidenceBar.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 2));
        confidenceBar.setBounds(60, 170, 210, 20);
        decisionPanel.add(confidenceBar);
        
        add(decisionPanel);
        
        // Explanation Panel
        JPanel explanationPanel = createStyledPanel();
        explanationPanel.setBounds(50, 285, 700, 185);
        explanationPanel.setLayout(null);
        
        JLabel explanationTitle = new JLabel("RECOMMENDATION EXPLANATION");
        explanationTitle.setFont(new Font("SansSerif", Font.BOLD, 13));
        explanationTitle.setForeground(ACCENT_GOLD);
        explanationTitle.setBounds(20, 15, 660, 25);
        explanationPanel.add(explanationTitle);
        
        explanationArea = new JTextArea();
        explanationArea.setFont(new Font("SansSerif", Font.PLAIN, 12));
        explanationArea.setBackground(BACKGROUND_PRIMARY);
        explanationArea.setForeground(TEXT_PRIMARY);
        explanationArea.setCaretColor(ACCENT_GOLD);
        explanationArea.setEditable(false);
        explanationArea.setLineWrap(true);
        explanationArea.setWrapStyleWord(true);
        explanationArea.setText("Configure your risk appetite and click 'Get Recommendation' to receive AI-powered investment advice based on trend analysis.");
        
        JScrollPane scrollPane = new JScrollPane(explanationArea);
        scrollPane.setBounds(20, 45, 660, 125);
        scrollPane.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 2));
        scrollPane.getViewport().setBackground(BACKGROUND_PRIMARY);
        explanationPanel.add(scrollPane);
        
        add(explanationPanel);
    }
    
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.BOLD, 11));
        label.setForeground(TEXT_SECONDARY);
        return label;
    }
    
    private JPanel createStyledPanel() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                g2d.setColor(BACKGROUND_SECONDARY);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                
                g2d.setColor(BORDER_COLOR);
                g2d.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 12, 12);
            }
        };
        panel.setOpaque(false);
        return panel;
    }
    
    private JButton createStyledButton(String text, boolean isPrimary) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (getModel().isPressed()) {
                    g2d.setColor(isPrimary ? ACCENT_GOLD.darker() : BACKGROUND_PRIMARY);
                } else if (getModel().isRollover()) {
                    g2d.setColor(isPrimary ? ACCENT_GOLD.brighter() : BACKGROUND_SECONDARY.brighter());
                } else {
                    g2d.setColor(isPrimary ? ACCENT_GOLD : BACKGROUND_SECONDARY);
                }
                
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                
                g2d.setColor(isPrimary ? ACCENT_GOLD.darker() : BORDER_COLOR);
                g2d.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 8, 8);
                
                FontMetrics fm = g2d.getFontMetrics();
                int textWidth = fm.stringWidth(getText());
                int textHeight = fm.getAscent();
                
                g2d.setColor(isPrimary ? BACKGROUND_PRIMARY : TEXT_PRIMARY);
                g2d.setFont(getFont());
                g2d.drawString(getText(), (getWidth() - textWidth) / 2, 
                              (getHeight() + textHeight) / 2 - 2);
            }
        };
        button.setFont(new Font("SansSerif", Font.BOLD, 13));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }
    
    private void attachEventHandlers() {
        getRecommendationButton.addActionListener(e -> handleGetRecommendation());
        resetButton.addActionListener(e -> handleReset());
    }
    
    private void handleGetRecommendation() {
        int riskAppetite = riskAppetiteSlider.getValue();
        
        try {
            String result = jniHandler.getRecommendationFromCpp(riskAppetite);
            parseAndDisplayResults(result);
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Error getting recommendation: " + ex.getMessage() + 
                "\n\nPlease ensure you have loaded data and performed trend analysis first.", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void parseAndDisplayResults(String result) {
        String[] parts = result.split("\\|");
        
        if (parts.length >= 3) {
            String decision = parts[0];
            String confidence = parts[1];
            String explanation = parts[2];
            
            decisionLabel.setText(decision);
            
            if (decision.equals("BUY")) {
                decisionLabel.setForeground(SUCCESS_GREEN);
            } else if (decision.equals("SELL")) {
                decisionLabel.setForeground(WARNING_RED);
            } else {
                decisionLabel.setForeground(HOLD_ORANGE);
            }
            
            confidenceLabel.setText(confidence);
            
            try {
                int confidenceValue = Integer.parseInt(confidence.replace("%", ""));
                confidenceBar.setValue(confidenceValue);
                
                if (confidenceValue >= 75) {
                    confidenceBar.setForeground(SUCCESS_GREEN);
                } else if (confidenceValue >= 50) {
                    confidenceBar.setForeground(ACCENT_GOLD);
                } else {
                    confidenceBar.setForeground(WARNING_RED);
                }
            } catch (NumberFormatException e) {
                confidenceBar.setValue(0);
            }
            
            explanationArea.setText(explanation);
            
            JOptionPane.showMessageDialog(this, 
                "Recommendation generated successfully!", 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
                
        } else {
            explanationArea.setText(result);
        }
    }
    
    private void handleReset() {
        riskAppetiteSlider.setValue(5);
        decisionLabel.setText("--");
        decisionLabel.setForeground(TEXT_PRIMARY);
        confidenceLabel.setText("--");
        confidenceBar.setValue(0);
        confidenceBar.setForeground(ACCENT_GOLD);
        explanationArea.setText("Configure your risk appetite and click 'Get Recommendation' to receive AI-powered investment advice based on trend analysis.");
    }
    
    public void refreshForm() {
        // Called when switching to this panel
    }
}
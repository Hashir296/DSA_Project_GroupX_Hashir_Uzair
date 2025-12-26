/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

import integration.JNIHandler;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class TrendAnalysisForm extends JPanel {
    private JTextField windowSizeField;
    private JLabel smaLabel;
    private JLabel trendLabel;
    private JLabel volatilityLabel;
    private JLabel riskLabel;
    private JTextArea analysisArea;
    private JButton analyzeButton;
    private JButton resetButton;
    private JProgressBar analysisProgress;
    
    private static final Color BACKGROUND_PRIMARY = new Color(15, 23, 30);
    private static final Color BACKGROUND_SECONDARY = new Color(25, 35, 45);
    private static final Color ACCENT_GOLD = new Color(212, 175, 55);
    private static final Color TEXT_PRIMARY = new Color(240, 240, 240);
    private static final Color TEXT_SECONDARY = new Color(180, 180, 180);
    private static final Color BORDER_COLOR = new Color(60, 70, 80);
    private static final Color SUCCESS_GREEN = new Color(80, 200, 120);
    private static final Color WARNING_RED = new Color(255, 100, 100);
    
    private JNIHandler jniHandler;
    
    public TrendAnalysisForm() {
        jniHandler = new JNIHandler();
        setupLayout();
        attachEventHandlers();
    }
    
    private void setupLayout() {
        setLayout(null);
        setOpaque(false);
        
        // Title
        JLabel titleLabel = new JLabel("TREND & RISK ANALYSIS MODULE");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        titleLabel.setForeground(ACCENT_GOLD);
        titleLabel.setBounds(50, 20, 450, 35);
        add(titleLabel);
        
        // Input Panel
        JPanel inputPanel = createStyledPanel();
        inputPanel.setBounds(50, 70, 350, 180);
        inputPanel.setLayout(null);
        
        JLabel windowLabel = createLabel("TIME WINDOW (Days)");
        windowLabel.setBounds(20, 20, 310, 20);
        inputPanel.add(windowLabel);
        
        windowSizeField = createStyledTextField();
        windowSizeField.setText("14");
        windowSizeField.setBounds(20, 45, 310, 35);
        inputPanel.add(windowSizeField);
        
        analyzeButton = createStyledButton("ANALYZE TREND", true);
        analyzeButton.setBounds(20, 95, 310, 40);
        inputPanel.add(analyzeButton);
        
        resetButton = createStyledButton("RESET", false);
        resetButton.setBounds(20, 140, 150, 30);
        inputPanel.add(resetButton);
        
        add(inputPanel);
        
        // Progress Bar
        analysisProgress = new JProgressBar();
        analysisProgress.setBounds(50, 260, 350, 25);
        analysisProgress.setVisible(false);
        analysisProgress.setStringPainted(true);
        analysisProgress.setBackground(BACKGROUND_PRIMARY);
        analysisProgress.setForeground(ACCENT_GOLD);
        analysisProgress.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 2));
        add(analysisProgress);
        
        // Results Panel
        JPanel resultsPanel = createStyledPanel();
        resultsPanel.setBounds(420, 70, 330, 400);
        resultsPanel.setLayout(null);
        
        JLabel resultsTitle = new JLabel("ANALYSIS RESULTS");
        resultsTitle.setFont(new Font("SansSerif", Font.BOLD, 14));
        resultsTitle.setForeground(ACCENT_GOLD);
        resultsTitle.setBounds(20, 15, 290, 25);
        resultsPanel.add(resultsTitle);
        
        // Metrics Panel
        JPanel metricsPanel = createStyledPanel();
        metricsPanel.setBounds(20, 50, 290, 180);
        metricsPanel.setLayout(null);
        
        JLabel smaLabelText = new JLabel("Simple Moving Avg:");
        smaLabelText.setFont(new Font("SansSerif", Font.PLAIN, 12));
        smaLabelText.setForeground(TEXT_SECONDARY);
        smaLabelText.setBounds(15, 15, 130, 20);
        metricsPanel.add(smaLabelText);
        
        smaLabel = new JLabel("--");
        smaLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        smaLabel.setForeground(ACCENT_GOLD);
        smaLabel.setBounds(150, 15, 125, 20);
        metricsPanel.add(smaLabel);
        
        JLabel trendLabelText = new JLabel("Trend Direction:");
        trendLabelText.setFont(new Font("SansSerif", Font.PLAIN, 12));
        trendLabelText.setForeground(TEXT_SECONDARY);
        trendLabelText.setBounds(15, 50, 130, 20);
        metricsPanel.add(trendLabelText);
        
        trendLabel = new JLabel("--");
        trendLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        trendLabel.setForeground(TEXT_PRIMARY);
        trendLabel.setBounds(150, 50, 125, 20);
        metricsPanel.add(trendLabel);
        
        JLabel volatilityLabelText = new JLabel("Volatility:");
        volatilityLabelText.setFont(new Font("SansSerif", Font.PLAIN, 12));
        volatilityLabelText.setForeground(TEXT_SECONDARY);
        volatilityLabelText.setBounds(15, 85, 130, 20);
        metricsPanel.add(volatilityLabelText);
        
        volatilityLabel = new JLabel("--");
        volatilityLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        volatilityLabel.setForeground(TEXT_PRIMARY);
        volatilityLabel.setBounds(150, 85, 125, 20);
        metricsPanel.add(volatilityLabel);
        
        JLabel riskLabelText = new JLabel("Risk Level:");
        riskLabelText.setFont(new Font("SansSerif", Font.PLAIN, 12));
        riskLabelText.setForeground(TEXT_SECONDARY);
        riskLabelText.setBounds(15, 120, 130, 20);
        metricsPanel.add(riskLabelText);
        
        riskLabel = new JLabel("--");
        riskLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        riskLabel.setForeground(TEXT_PRIMARY);
        riskLabel.setBounds(150, 120, 125, 20);
        metricsPanel.add(riskLabel);
        
        resultsPanel.add(metricsPanel);
        
        // Analysis Text Area
        JLabel analysisLabelText = new JLabel("Detailed Analysis:");
        analysisLabelText.setFont(new Font("SansSerif", Font.PLAIN, 12));
        analysisLabelText.setForeground(TEXT_SECONDARY);
        analysisLabelText.setBounds(20, 240, 290, 20);
        resultsPanel.add(analysisLabelText);
        
        analysisArea = new JTextArea();
        analysisArea.setFont(new Font("SansSerif", Font.PLAIN, 11));
        analysisArea.setBackground(BACKGROUND_PRIMARY);
        analysisArea.setForeground(TEXT_PRIMARY);
        analysisArea.setCaretColor(ACCENT_GOLD);
        analysisArea.setEditable(false);
        analysisArea.setLineWrap(true);
        analysisArea.setWrapStyleWord(true);
        
        JScrollPane scrollPane = new JScrollPane(analysisArea);
        scrollPane.setBounds(20, 265, 290, 120);
        scrollPane.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 2));
        scrollPane.getViewport().setBackground(BACKGROUND_PRIMARY);
        resultsPanel.add(scrollPane);
        
        add(resultsPanel);
        
        // Info Panel
        JPanel infoPanel = createStyledPanel();
        infoPanel.setBounds(50, 300, 350, 170);
        infoPanel.setLayout(null);
        
        JLabel infoTitle = new JLabel("ANALYSIS METRICS");
        infoTitle.setFont(new Font("SansSerif", Font.BOLD, 12));
        infoTitle.setForeground(ACCENT_GOLD);
        infoTitle.setBounds(15, 10, 320, 20);
        infoPanel.add(infoTitle);
        
        JTextArea infoText = new JTextArea();
        infoText.setText(
            "• SMA: Average price over time window\n" +
            "• Trend: Market direction (Up/Down/Sideways)\n" +
            "• Volatility: Price fluctuation measure\n" +
            "• Risk: Investment risk classification\n\n" +
            "Note: Load data first from Data Module"
        );
        infoText.setFont(new Font("SansSerif", Font.PLAIN, 11));
        infoText.setBackground(BACKGROUND_SECONDARY);
        infoText.setForeground(TEXT_SECONDARY);
        infoText.setEditable(false);
        infoText.setLineWrap(true);
        infoText.setWrapStyleWord(true);
        infoText.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        infoText.setBounds(15, 35, 320, 125);
        infoPanel.add(infoText);
        
        add(infoPanel);
    }
    
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.BOLD, 11));
        label.setForeground(TEXT_SECONDARY);
        return label;
    }
    
    private JTextField createStyledTextField() {
        JTextField field = new JTextField();
        field.setFont(new Font("SansSerif", Font.PLAIN, 14));
        field.setBackground(BACKGROUND_PRIMARY);
        field.setForeground(TEXT_PRIMARY);
        field.setCaretColor(ACCENT_GOLD);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 2),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        return field;
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
        analyzeButton.addActionListener(e -> handleAnalyze());
        resetButton.addActionListener(e -> handleReset());
    }
    
    private void handleAnalyze() {
        String windowText = windowSizeField.getText().trim();
        
        if (windowText.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Please enter time window size", 
                "Validation Error", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            int window = Integer.parseInt(windowText);
            if (window <= 0 || window > 365) {
                throw new NumberFormatException();
            }
            
            analysisProgress.setVisible(true);
            analysisProgress.setValue(0);
            
            SwingWorker<String, Integer> worker = new SwingWorker<String, Integer>() {
                @Override
                protected String doInBackground() throws Exception {
                    publish(25);
                    Thread.sleep(300);
                    String result = jniHandler.analyzeTrendFromCpp(window);
                    publish(75);
                    Thread.sleep(300);
                    publish(100);
                    return result;
                }
                
                @Override
                protected void process(java.util.List<Integer> chunks) {
                    int progress = chunks.get(chunks.size() - 1);
                    analysisProgress.setValue(progress);
                }
                
                @Override
                protected void done() {
                    try {
                        String result = get();
                        parseAndDisplayResults(result);
                        analysisProgress.setVisible(false);
                        
                        JOptionPane.showMessageDialog(TrendAnalysisForm.this, 
                            "Analysis completed successfully!", 
                            "Success", 
                            JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception ex) {
                        analysisProgress.setVisible(false);
                        JOptionPane.showMessageDialog(TrendAnalysisForm.this, 
                            "Error during analysis: " + ex.getMessage(), 
                            "Error", 
                            JOptionPane.ERROR_MESSAGE);
                    }
                }
            };
            
            worker.execute();
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, 
                "Invalid window size. Enter number between 1-365", 
                "Validation Error", 
                JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void parseAndDisplayResults(String result) {
        String[] parts = result.split("\\|");
        
        if (parts.length >= 4) {
            smaLabel.setText("$" + parts[0]);
            
            String trend = parts[1];
            trendLabel.setText(trend);
            if (trend.equals("UPTREND")) {
                trendLabel.setForeground(SUCCESS_GREEN);
            } else if (trend.equals("DOWNTREND")) {
                trendLabel.setForeground(WARNING_RED);
            } else {
                trendLabel.setForeground(TEXT_PRIMARY);
            }
            
            volatilityLabel.setText(parts[2] + "%");
            
            String risk = parts[3];
            riskLabel.setText(risk);
            if (risk.equals("LOW")) {
                riskLabel.setForeground(SUCCESS_GREEN);
            } else if (risk.equals("HIGH")) {
                riskLabel.setForeground(WARNING_RED);
            } else {
                riskLabel.setForeground(ACCENT_GOLD);
            }
            
            if (parts.length > 4) {
                analysisArea.setText(parts[4]);
            }
        } else {
            analysisArea.setText(result);
        }
    }
    
    private void handleReset() {
        windowSizeField.setText("14");
        smaLabel.setText("--");
        trendLabel.setText("--");
        trendLabel.setForeground(TEXT_PRIMARY);
        volatilityLabel.setText("--");
        riskLabel.setText("--");
        riskLabel.setForeground(TEXT_PRIMARY);
        analysisArea.setText("");
        analysisProgress.setVisible(false);
    }
    
    public void refreshForm() {
        // Called when switching to this panel
    }
}
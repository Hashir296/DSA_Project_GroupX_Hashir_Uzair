/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


//package gui;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.*;
//import java.awt.geom.*;
//
//public class Dashboard extends JFrame {
//    private String username;
//    private JPanel contentPanel;
//    private CardLayout cardLayout;
//    
//    // Old Money Theme Colors
//    private static final Color BACKGROUND_PRIMARY = new Color(15, 23, 30);
//    private static final Color BACKGROUND_SECONDARY = new Color(25, 35, 45);
//    private static final Color ACCENT_GOLD = new Color(212, 175, 55);
//    private static final Color TEXT_PRIMARY = new Color(240, 240, 240);
//    private static final Color TEXT_SECONDARY = new Color(180, 180, 180);
//    private static final Color BORDER_COLOR = new Color(60, 70, 80);
//    private static final Color SUCCESS_GREEN = new Color(80, 200, 120);
//    
//    private DataInputForm dataInputForm;
//    private TrendAnalysisForm trendAnalysisForm;
//    private RecommendationForm recommendationForm;
//    
//    public Dashboard(String username) {
//        this.username = username;
//        initializeComponents();
//        setupLayout();
//    }
//    
//    private void initializeComponents() {
//        setTitle("Finance Analyzer - Dashboard");
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
//                GradientPaint gradient = new GradientPaint(0, 0, BACKGROUND_PRIMARY, 
//                                                          0, getHeight(), BACKGROUND_SECONDARY);
//                g2d.setPaint(gradient);
//                g2d.fillRect(0, 0, getWidth(), getHeight());
//            }
//        };
//        mainPanel.setLayout(null);
//        
//        // Header Panel
//        JPanel headerPanel = createHeaderPanel();
//        headerPanel.setBounds(0, 0, 800, 80);
//        mainPanel.add(headerPanel);
//        
//        // Content Panel with CardLayout
//        cardLayout = new CardLayout();
//        contentPanel = new JPanel(cardLayout);
//        contentPanel.setBounds(0, 80, 800, 520);
//        contentPanel.setOpaque(false);
//        
//        // Initialize module forms
//        dataInputForm = new DataInputForm();
//        trendAnalysisForm = new TrendAnalysisForm();
//        recommendationForm = new RecommendationForm();
//        
//        // Add forms to card layout
//        contentPanel.add(createWelcomePanel(), "WELCOME");
//        contentPanel.add(dataInputForm, "DATA");
//        contentPanel.add(trendAnalysisForm, "TREND");
//        contentPanel.add(recommendationForm, "RECOMMENDATION");
//        
//        mainPanel.add(contentPanel);
//        
//        add(mainPanel);
//    }
//    
//    private JPanel createHeaderPanel() {
//        JPanel headerPanel = new JPanel() {
//            @Override
//            protected void paintComponent(Graphics g) {
//                super.paintComponent(g);
//                Graphics2D g2d = (Graphics2D) g;
//                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//                
//                g2d.setColor(BACKGROUND_SECONDARY);
//                g2d.fillRect(0, 0, getWidth(), getHeight());
//                
//                g2d.setColor(ACCENT_GOLD);
//                g2d.fillRect(0, getHeight()-3, getWidth(), 3);
//            }
//        };
//        headerPanel.setLayout(null);
//        
//        // Logo/Title
//        JLabel titleLabel = new JLabel("FINANCE ANALYZER");
//        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
//        titleLabel.setForeground(ACCENT_GOLD);
//        titleLabel.setBounds(30, 15, 300, 35);
//        headerPanel.add(titleLabel);
//        
//        // User info
//        JLabel userLabel = new JLabel("Welcome, " + username);
//        userLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
//        userLabel.setForeground(TEXT_SECONDARY);
//        userLabel.setBounds(30, 50, 250, 20);
//        headerPanel.add(userLabel);
//        
//        // Navigation buttons
//        JButton homeBtn = createNavButton("HOME");
//        homeBtn.setBounds(400, 20, 90, 35);
//        homeBtn.addActionListener(e -> cardLayout.show(contentPanel, "WELCOME"));
//        headerPanel.add(homeBtn);
//        
//        JButton dataBtn = createNavButton("DATA");
//        dataBtn.setBounds(500, 20, 90, 35);
//        dataBtn.addActionListener(e -> {
//            cardLayout.show(contentPanel, "DATA");
//            dataInputForm.refreshForm();
//        });
//        headerPanel.add(dataBtn);
//        
//        JButton trendBtn = createNavButton("TREND");
//        trendBtn.setBounds(600, 20, 90, 35);
//        trendBtn.addActionListener(e -> {
//            cardLayout.show(contentPanel, "TREND");
//            trendAnalysisForm.refreshForm();
//        });
//        headerPanel.add(trendBtn);
//        
//        JButton aiBtn = createNavButton("AI");
//        aiBtn.setBounds(700, 20, 70, 35);
//        aiBtn.addActionListener(e -> {
//            cardLayout.show(contentPanel, "RECOMMENDATION");
//            recommendationForm.refreshForm();
//        });
//        headerPanel.add(aiBtn);
//        
//        return headerPanel;
//    }
//    
//    private JButton createNavButton(String text) {
//        JButton button = new JButton(text) {
//            @Override
//            protected void paintComponent(Graphics g) {
//                Graphics2D g2d = (Graphics2D) g;
//                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//                
//                if (getModel().isPressed()) {
//                    g2d.setColor(ACCENT_GOLD.darker());
//                } else if (getModel().isRollover()) {
//                    g2d.setColor(ACCENT_GOLD);
//                } else {
//                    g2d.setColor(BACKGROUND_PRIMARY);
//                }
//                
//                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 6, 6);
//                
//                g2d.setColor(BORDER_COLOR);
//                g2d.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 6, 6);
//                
//                FontMetrics fm = g2d.getFontMetrics();
//                int textWidth = fm.stringWidth(getText());
//                int textHeight = fm.getAscent();
//                
//                g2d.setColor(getModel().isRollover() ? BACKGROUND_PRIMARY : TEXT_PRIMARY);
//                g2d.setFont(getFont());
//                g2d.drawString(getText(), (getWidth() - textWidth) / 2, 
//                              (getHeight() + textHeight) / 2 - 2);
//            }
//        };
//        button.setFont(new Font("SansSerif", Font.BOLD, 12));
//        button.setFocusPainted(false);
//        button.setBorderPainted(false);
//        button.setContentAreaFilled(false);
//        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
//        return button;
//    }
//    
//    private JPanel createWelcomePanel() {
//        JPanel panel = new JPanel() {
//            @Override
//            protected void paintComponent(Graphics g) {
//                super.paintComponent(g);
//                Graphics2D g2d = (Graphics2D) g;
//                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//            }
//        };
//        panel.setLayout(null);
//        panel.setOpaque(false);
//        
//        // Welcome message
//        JLabel welcomeLabel = new JLabel("Welcome to Finance Analyzer", SwingConstants.CENTER);
//        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 32));
//        welcomeLabel.setForeground(ACCENT_GOLD);
//        welcomeLabel.setBounds(100, 60, 600, 50);
//        panel.add(welcomeLabel);
//        
//        JLabel subtitleLabel = new JLabel("AI-Powered Investment Intelligence System", SwingConstants.CENTER);
//        subtitleLabel.setFont(new Font("Serif", Font.ITALIC, 16));
//        subtitleLabel.setForeground(TEXT_SECONDARY);
//        subtitleLabel.setBounds(100, 110, 600, 30);
//        panel.add(subtitleLabel);
//        
//        // Module cards
//        JPanel card1 = createModuleCard("DATA MANAGEMENT", 
//            "Load and analyze financial data from stocks and forex markets",
//            150, 180, e -> cardLayout.show(contentPanel, "DATA"));
//        panel.add(card1);
//        
//        JPanel card2 = createModuleCard("TREND ANALYSIS", 
//            "Analyze market trends, risk, and volatility patterns",
//            150, 280, e -> cardLayout.show(contentPanel, "TREND"));
//        panel.add(card2);
//        
//        JPanel card3 = createModuleCard("AI RECOMMENDATIONS", 
//            "Get intelligent BUY/SELL/HOLD recommendations",
//            150, 380, e -> cardLayout.show(contentPanel, "RECOMMENDATION"));
//        panel.add(card3);
//        
//        return panel;
//    }
//    
//    private JPanel createModuleCard(String title, String description, int x, int y, ActionListener action) {
//        JPanel card = new JPanel() {
//            @Override
//            protected void paintComponent(Graphics g) {
//                super.paintComponent(g);
//                Graphics2D g2d = (Graphics2D) g;
//                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//                
//                g2d.setColor(BACKGROUND_SECONDARY);
//                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
//                
//                g2d.setColor(BORDER_COLOR);
//                g2d.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 12, 12);
//            }
//        };
//        card.setLayout(null);
//        card.setBounds(x, y, 500, 80);
//        card.setOpaque(false);
//        card.setCursor(new Cursor(Cursor.HAND_CURSOR));
//        
//        JLabel titleLabel = new JLabel(title);
//        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
//        titleLabel.setForeground(ACCENT_GOLD);
//        titleLabel.setBounds(20, 15, 400, 25);
//        card.add(titleLabel);
//        
//        JLabel descLabel = new JLabel(description);
//        descLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
//        descLabel.setForeground(TEXT_SECONDARY);
//        descLabel.setBounds(20, 40, 460, 25);
//        card.add(descLabel);
//        
//        card.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                action.actionPerformed(new ActionEvent(card, ActionEvent.ACTION_PERFORMED, ""));
//            }
//            
//            @Override
//            public void mouseEntered(MouseEvent e) {
//                card.repaint();
//            }
//            
//            @Override
//            public void mouseExited(MouseEvent e) {
//                card.repaint();
//            }
//        });
//        
//        return card;
//    }
//}


package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Dashboard extends JFrame {
    private String username;
    private CardLayout cardLayout;
    private JPanel cardPanel;

    // Colors (same as login)
    private static final Color BG_DARK = new Color(18, 25, 33);
    private static final Color BG_PANEL = new Color(28, 38, 50);
    private static final Color ACCENT = new Color(212, 175, 55);
    private static final Color TEXT_LIGHT = Color.WHITE;
    private static final Color TEXT_GRAY = new Color(180, 185, 195);
    private static final Color BORDER = new Color(60, 75, 90);

    // Real module instances
    private DataInputForm dataInputForm;
    private TrendAnalysisForm trendAnalysisForm;
    private RecommendationForm recommendationForm;

    public Dashboard(String username) {
        this.username = username;
        initialize();
    }

    private void initialize() {
        setTitle("Finance Analyzer - Dashboard");
        setSize(1100, 680);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(BG_DARK);
        getContentPane().setLayout(null);

        // Initialize modules FIRST
        dataInputForm = new DataInputForm();
        trendAnalysisForm = new TrendAnalysisForm();
        recommendationForm = new RecommendationForm();

        // Header
        JPanel header = createHeader();
        header.setBounds(0, 0, 1100, 70);
        getContentPane().add(header);

        // Card Panel
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setBounds(0, 70, 1100, 610);
        cardPanel.setBackground(BG_DARK);
        getContentPane().add(cardPanel);

        // Add REAL modules (not placeholders)
        cardPanel.add(createWelcomePanel(), "WELCOME");
        cardPanel.add(dataInputForm, "DATA");          // ✅ Real form
        cardPanel.add(trendAnalysisForm, "TREND");     // ✅ Real form
        cardPanel.add(recommendationForm, "AI");       // ✅ Real form

        cardLayout.show(cardPanel, "WELCOME");
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(null);
        header.setBackground(BG_PANEL);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, ACCENT));

        JLabel title = new JLabel("Finance Analyzer");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(ACCENT);
        title.setBounds(30, 18, 300, 35);
        header.add(title);

        JLabel user = new JLabel("Welcome, " + username);
        user.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        user.setForeground(TEXT_GRAY);
        user.setBounds(30, 45, 200, 20);
        header.add(user);

        // Nav buttons with refresh
        addNavButton(header, "HOME", 520, e -> cardLayout.show(cardPanel, "WELCOME"));
        addNavButton(header, "DATA", 620, e -> {
            cardLayout.show(cardPanel, "DATA");
            if (dataInputForm != null) dataInputForm.refreshForm();
        });
        addNavButton(header, "TREND", 720, e -> {
            cardLayout.show(cardPanel, "TREND");
            if (trendAnalysisForm != null) trendAnalysisForm.refreshForm();
        });
        addNavButton(header, "AI", 830, e -> {
            cardLayout.show(cardPanel, "AI");
            if (recommendationForm != null) recommendationForm.refreshForm();
        });

        JButton logout = createButton("Logout", ACCENT, BG_DARK);
        logout.setBounds(950, 20, 90, 32);
        logout.addActionListener(e -> {
            dispose();
            new LoginForm().setVisible(true);
        });
        header.add(logout);

        return header;
    }

    private void addNavButton(JPanel parent, String text, int x, ActionListener action) {
        JButton btn = createButton(text, BG_DARK, TEXT_LIGHT);
        btn.setBounds(x, 20, 80, 32);
        btn.addActionListener(action);
        parent.add(btn);
    }

    private JButton createButton(String text, Color bg, Color fg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(BORDER, 1));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setOpaque(true);

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(fg);
                btn.setForeground(bg);
            }
            public void mouseExited(MouseEvent e) {
                btn.setBackground(bg);
                btn.setForeground(fg);
            }
        });
        return btn;
    }

    private JPanel createWelcomePanel() {
        JPanel panel = new JPanel(null);
        panel.setBackground(BG_DARK);

        JLabel title = new JLabel("Welcome to Finance Analyzer", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 34));
        title.setForeground(ACCENT);
        title.setBounds(0, 40, 1100, 50);
        panel.add(title);

        JLabel sub = new JLabel("AI-Powered Investment Intelligence System", JLabel.CENTER);
        sub.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        sub.setForeground(TEXT_GRAY);
        sub.setBounds(0, 90, 1100, 25);
        panel.add(sub);

        createCard(panel, "📊 Data Management", "Import & analyze financial data", 200, 200, 
            e -> { cardLayout.show(cardPanel, "DATA"); dataInputForm.refreshForm(); });
        createCard(panel, "📈 Trend Analysis", "Market trends & risk patterns", 200, 320, 
            e -> { cardLayout.show(cardPanel, "TREND"); trendAnalysisForm.refreshForm(); });
        createCard(panel, "🤖 AI Recommendations", "Smart BUY/SELL/HOLD signals", 200, 440, 
            e -> { cardLayout.show(cardPanel, "AI"); recommendationForm.refreshForm(); });

        return panel;
    }

    private void createCard(JPanel parent, String title, String desc, int x, int y, ActionListener action) {
        JPanel card = new JPanel(null);
        card.setBackground(BG_PANEL);
        card.setBorder(BorderFactory.createLineBorder(BORDER, 1));
        card.setBounds(x, y, 700, 80);
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(ACCENT);
        titleLabel.setBounds(25, 15, 300, 25);
        card.add(titleLabel);

        JLabel descLabel = new JLabel(desc);
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        descLabel.setForeground(TEXT_GRAY);
        descLabel.setBounds(25, 45, 600, 20);
        card.add(descLabel);

        card.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                action.actionPerformed(new ActionEvent(card, ActionEvent.ACTION_PERFORMED, ""));
            }
            public void mouseEntered(MouseEvent e) {
                card.setBackground(ACCENT);
                titleLabel.setForeground(BG_DARK);
                descLabel.setForeground(BG_DARK);
            }
            public void mouseExited(MouseEvent e) {
                card.setBackground(BG_PANEL);
                titleLabel.setForeground(ACCENT);
                descLabel.setForeground(TEXT_GRAY);
            }
        });
        parent.add(card);
    }

    // ====== REAL FORMS (Replace with your actual classes) ======
    // Ensure these classes exist in your project with refreshForm() method

    // No need to redefine them here — just use your existing ones!
}

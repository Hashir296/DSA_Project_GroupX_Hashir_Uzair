/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

//package gui;
//
//import javax.swing.*;
//import javax.swing.border.LineBorder;
//import javax.swing.table.DefaultTableModel;
//import javax.swing.table.DefaultTableCellRenderer;
//import java.awt.*;
//import java.awt.event.*;
//import java.io.*;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.*;
//import java.util.List;
//
///**
// * DataInputForm.java - Professional Trading Data Input Module
// * Features:
// * - Load CSV (past trades)
// * - Filters: Symbol/Pair, Type, Date Range
// * - Add new trade (update table + CSV)
// * - Reset filters, Export table to CSV
// * - Broker integration for automatic trade execution
// * - AI recommendation system
// * - Trend analysis capabilities
// */
//public class DataInputForm extends JPanel {
//
//    // ================= COLORS / THEME =================
//    private static final Color BG_COLOR      = new Color(246,247,249);
//    private static final Color CARD_BG       = new Color(255,255,255);
//    private static final Color TEXT_COLOR    = new Color(45,45,45);
//    private static final Color BUTTON_COLOR  = new Color(90,74,60);
//    private static final Color BUTTON_HOVER  = new Color(150,125,100);
//    private static final Color HEADER_COLOR  = new Color(220,215,200);
//    private static final Color ERROR_COLOR   = new Color(180,70,70);
//    private static final Color SUCCESS_COLOR = new Color(70,180,70);
//    private static final Color AI_COLOR      = new Color(106,90,205);
//    private static final Color TRENDS_COLOR  = new Color(30,144,255);
//    private static final Color BORDER_COLOR  = new Color(150,150,150);
//
//    // ================= GUI COMPONENTS =================
//    private JTextField symbolField;
//    private JComboBox<String> typeCombo;
//    private JTextField fromDateField;
//    private JTextField toDateField;
//
//    private JTable dataTable;
//    private DefaultTableModel tableModel;
//
//    private JTextField newSymbolField;
//    private JComboBox<String> newTypeCombo;
//    private JTextField newDateField;
//    private JTextField newPriceField;
//    private JTextField newStopLossField;
//    private JTextField newTakeProfitField;
//
//    private List<Map<String,String>> currentData = new ArrayList<>();
//    private List<Map<String,String>> stockData   = new ArrayList<>();
//    private List<Map<String,String>> forexData   = new ArrayList<>();
//
//    private static final String STOCK_CSV = "D:\\OAPFA\\stocks.csv";
//    private static final String FOREX_CSV = "D:\\OAPFA\\forex.csv";
//
//    // Broker connection simulation
//    private JComboBox<String> brokerCombo;
//    private JLabel brokerStatusLabel;
//    private JButton connectBtn;
//    private JButton disconnectBtn;
//    
//    // Statistics labels
//    private JLabel totalTradesLabel;
//    private JLabel totalProfitLabel;
//    private JLabel winRateLabel;
//
//    public DataInputForm(){
//        setLayout(new BorderLayout(10,10));
//        setBackground(BG_COLOR);
//
//        // Load CSV
//        loadCSVData();
//
//        // Build GUI sections
//        JPanel topPanel = buildTopSection();
//        JPanel brokerPanel = buildBrokerSection();
//        JPanel filterPanel = buildFilterSection();
//        JPanel dataPanel = buildDataSection();
//        JPanel addTradePanel = buildAddTradeSection();
//
//        // Layout using JSplitPanes
//        JSplitPane mainSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
//        mainSplit.setTopComponent(topPanel);
//        
//        JSplitPane middleSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
//        middleSplit.setTopComponent(brokerPanel);
//        middleSplit.setBottomComponent(filterPanel);
//        
//        JSplitPane bottomSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
//        bottomSplit.setTopComponent(dataPanel);
//        bottomSplit.setBottomComponent(addTradePanel);
//        
//        mainSplit.setBottomComponent(middleSplit);
//        middleSplit.setBottomComponent(bottomSplit);
//        
//        // Set divider locations
//        mainSplit.setDividerLocation(60);
//        middleSplit.setDividerLocation(80);
//        bottomSplit.setDividerLocation(200); // Trade data panel height
//        
//        add(mainSplit, BorderLayout.CENTER);
//
//        // Populate table
//        populateTable(currentData);
//        updateStatistics();
//    }
//
//    // ================= TOP SECTION =================
//    private JPanel buildTopSection(){
//        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
//        topPanel.setBackground(HEADER_COLOR);
//        topPanel.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));
//
//        // Refresh button only (removed back button)
//        JButton refreshBtn = new JButton("Refresh Data");
//        styleButton(refreshBtn, BUTTON_COLOR);
//        refreshBtn.addActionListener(e -> refreshAllData());
//
//        // Statistics panel
//        JPanel statsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 5));
//        statsPanel.setBackground(HEADER_COLOR);
//        
//        totalTradesLabel = new JLabel("Total Trades: 0");
//        totalProfitLabel = new JLabel("Total P&L: $0.00");
//        winRateLabel = new JLabel("Win Rate: 0%");
//        
//        statsPanel.add(totalTradesLabel);
//        statsPanel.add(totalProfitLabel);
//        statsPanel.add(winRateLabel);
//        
//        topPanel.add(refreshBtn);
//        topPanel.add(Box.createHorizontalGlue());
//        topPanel.add(statsPanel);
//
//        return topPanel;
//    }
//
//    // ================= BROKER CONNECTION SECTION =================
//    private JPanel buildBrokerSection() {
//        JPanel brokerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
//        brokerPanel.setBackground(BG_COLOR);
//        brokerPanel.setBorder(BorderFactory.createTitledBorder(
//            new LineBorder(BORDER_COLOR, 1), "Broker Connection"));
//        
//        JLabel brokerLabel = new JLabel("Broker:");
//        brokerCombo = new JComboBox<>(new String[]{"Interactive Brokers", "TD Ameritrade", "MetaTrader 5", "E*TRADE"});
//        connectBtn = new JButton("Connect");
//        disconnectBtn = new JButton("Disconnect");
//        brokerStatusLabel = new JLabel("Status: Disconnected");
//        brokerStatusLabel.setForeground(ERROR_COLOR);
//
//        styleButton(connectBtn, SUCCESS_COLOR);
//        styleButton(disconnectBtn, ERROR_COLOR);
//
//        connectBtn.addActionListener(e -> connectToBroker());
//        disconnectBtn.addActionListener(e -> disconnectFromBroker());
//
//        brokerPanel.add(brokerLabel);
//        brokerPanel.add(brokerCombo);
//        brokerPanel.add(connectBtn);
//        brokerPanel.add(disconnectBtn);
//        brokerPanel.add(brokerStatusLabel);
//
//        return brokerPanel;
//    }
//
//    // ================= FILTER SECTION =================
//    private JPanel buildFilterSection(){
//        JPanel filterPanel = new JPanel(new GridBagLayout());
//        filterPanel.setBackground(BG_COLOR);
//        filterPanel.setBorder(BorderFactory.createTitledBorder(
//            new LineBorder(BORDER_COLOR, 1), "Filters"));
//
//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.insets = new Insets(5,10,5,10);
//        gbc.anchor = GridBagConstraints.WEST;
//
//        // Symbol
//        gbc.gridx=0; gbc.gridy=0;
//        filterPanel.add(new JLabel("Symbol / Pair:"),gbc);
//        symbolField = new JTextField(15);
//        gbc.gridx=1;
//        filterPanel.add(symbolField,gbc);
//
//        // Type
//        gbc.gridx=2;
//        filterPanel.add(new JLabel("Type:"),gbc);
//        typeCombo = new JComboBox<>(new String[]{"ALL","BUY","SELL"});
//        gbc.gridx=3;
//        filterPanel.add(typeCombo,gbc);
//
//        // From Date
//        gbc.gridx=0; gbc.gridy=1;
//        filterPanel.add(new JLabel("From Date (yyyy-MM-dd):"),gbc);
//        fromDateField = new JTextField(15);
//        gbc.gridx=1;
//        filterPanel.add(fromDateField,gbc);
//
//        // To Date
//        gbc.gridx=2;
//        filterPanel.add(new JLabel("To Date (yyyy-MM-dd):"),gbc);
//        toDateField = new JTextField(15);
//        gbc.gridx=3;
//        filterPanel.add(toDateField,gbc);
//
//        // Buttons
//        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
//        btnPanel.setBackground(BG_COLOR);
//        JButton searchBtn = new JButton("Search");
//        JButton resetBtn  = new JButton("Reset Filters");
//        JButton exportBtn = new JButton("Export CSV");
//
//        styleButton(searchBtn, BUTTON_COLOR);
//        styleButton(resetBtn, BUTTON_COLOR);
//        styleButton(exportBtn, BUTTON_COLOR);
//
//        btnPanel.add(searchBtn);
//        btnPanel.add(resetBtn);
//        btnPanel.add(exportBtn);
//
//        gbc.gridx=0; gbc.gridy=2; gbc.gridwidth=4;
//        filterPanel.add(btnPanel,gbc);
//
//        // ===== EVENTS =====
//        searchBtn.addActionListener(e -> filterData());
//        resetBtn.addActionListener(e -> resetFilters());
//        exportBtn.addActionListener(e -> exportCSV());
//
//        return filterPanel;
//    }
//
//    // ================= DATA TABLE SECTION =================
//    private JPanel buildDataSection(){
//        JPanel dataPanel = new JPanel(new BorderLayout());
//        dataPanel.setBackground(BG_COLOR);
//        dataPanel.setBorder(BorderFactory.createTitledBorder(
//            new LineBorder(BORDER_COLOR, 1), "Trade Data"));
//
//        tableModel = new DefaultTableModel();
//        dataTable = new JTable(tableModel);
//        dataTable.setFillsViewportHeight(true);
//        dataTable.setRowHeight(22);
//        dataTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
//        dataTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
//        dataTable.getTableHeader().setBackground(Color.WHITE);
//        dataTable.getTableHeader().setForeground(TEXT_COLOR);
//        dataTable.setSelectionBackground(new Color(200,220,255));
//        dataTable.setSelectionForeground(TEXT_COLOR);
//        
//        // Alternate row colors for better readability
//        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
//            @Override
//            public Component getTableCellRendererComponent(JTable table, Object value,
//                    boolean isSelected, boolean hasFocus, int row, int column) {
//                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
//                
//                if (!isSelected) {
//                    setBackground(row % 2 == 0 ? Color.WHITE : new Color(240,240,240));
//                    setForeground(TEXT_COLOR);
//                }
//                
//                return this;
//            }
//        };
//        dataTable.setDefaultRenderer(Object.class, renderer);
//
//        JScrollPane scrollPane = new JScrollPane(dataTable);
//        dataPanel.add(scrollPane, BorderLayout.CENTER);
//
//        return dataPanel;
//    }
//
//    // ================= ADD NEW TRADE SECTION =================
//    private JPanel buildAddTradeSection(){
//        JPanel addTradePanel = new JPanel(new GridBagLayout());
//        addTradePanel.setBackground(BG_COLOR);
//        addTradePanel.setBorder(BorderFactory.createTitledBorder(
//            new LineBorder(BORDER_COLOR, 1), "Add New Trade"));
//
//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.insets = new Insets(5,10,5,10);
//        gbc.anchor = GridBagConstraints.WEST;
//
//        // Symbol
//        gbc.gridx=0; gbc.gridy=0;
//        addTradePanel.add(new JLabel("Symbol / Pair:"),gbc);
//        newSymbolField = new JTextField(15);
//        gbc.gridx=1;
//        addTradePanel.add(newSymbolField,gbc);
//
//        // Type
//        gbc.gridx=2;
//        addTradePanel.add(new JLabel("Type:"),gbc);
//        newTypeCombo = new JComboBox<>(new String[]{"BUY","SELL"});
//        gbc.gridx=3;
//        addTradePanel.add(newTypeCombo,gbc);
//
//        // Date
//        gbc.gridx=0; gbc.gridy=1;
//        addTradePanel.add(new JLabel("Date (yyyy-MM-dd):"),gbc);
//        newDateField = new JTextField(15);
//        newDateField.setText(LocalDate.now().toString());
//        gbc.gridx=1;
//        addTradePanel.add(newDateField,gbc);
//
//        // Price
//        gbc.gridx=2;
//        addTradePanel.add(new JLabel("Price:"),gbc);
//        newPriceField = new JTextField(15);
//        gbc.gridx=3;
//        addTradePanel.add(newPriceField,gbc);
//
//        // Stop Loss
//        gbc.gridx=0; gbc.gridy=2;
//        addTradePanel.add(new JLabel("Stop Loss:"),gbc);
//        newStopLossField = new JTextField(15);
//        gbc.gridx=1;
//        addTradePanel.add(newStopLossField,gbc);
//
//        // Take Profit
//        gbc.gridx=2;
//        addTradePanel.add(new JLabel("Take Profit:"),gbc);
//        newTakeProfitField = new JTextField(15);
//        gbc.gridx=3;
//        addTradePanel.add(newTakeProfitField,gbc);
//
//        // Add Button
//        JButton addBtn = new JButton("Add Trade");
//        JButton aiRecommendBtn = new JButton("Get AI Recommendation");
//        JButton trendRecommendBtn = new JButton("Get Trend Analysis");
//        
//        styleButton(addBtn, BUTTON_COLOR);
//        styleButton(aiRecommendBtn, AI_COLOR);
//        styleButton(trendRecommendBtn, TRENDS_COLOR);
//
//        gbc.gridx=0; gbc.gridy=3; gbc.gridwidth=4;
//        gbc.anchor = GridBagConstraints.CENTER;
//        
//        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
//        buttonPanel.setBackground(BG_COLOR);
//        buttonPanel.add(addBtn);
//        buttonPanel.add(aiRecommendBtn);
//        buttonPanel.add(trendRecommendBtn);
//        
//        addTradePanel.add(buttonPanel,gbc);
//
//        addBtn.addActionListener(e -> addTrade());
//        aiRecommendBtn.addActionListener(e -> getAIRecommendationForSymbol());
//        trendRecommendBtn.addActionListener(e -> getTrendAnalysisForSymbol());
//
//        return addTradePanel;
//    }
//
//    // ================= BUTTON STYLE =================
//    private void styleButton(JButton b, Color bgColor){
//        b.setBackground(bgColor);
//        b.setForeground(Color.WHITE);
//        b.setFocusPainted(false);
//        b.setBorder(new LineBorder(Color.WHITE,1,true));
//        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
//        b.setFont(new Font("Segoe UI", Font.BOLD, 12));
//        
//        b.addMouseListener(new MouseAdapter() {
//            public void mouseEntered(MouseEvent evt){ 
//                b.setBackground(bgColor.darker()); 
//            }
//            public void mouseExited(MouseEvent evt){ 
//                b.setBackground(bgColor); 
//            }
//        });
//    }
//
//    // ================= CSV DATA =================
//    private void loadCSVData(){
//        stockData = readCSV(STOCK_CSV);
//        forexData = readCSV(FOREX_CSV);
//
//        currentData.clear();
//        currentData.addAll(stockData);
//        currentData.addAll(forexData);
//
//        if(currentData.isEmpty()){
//            Map<String,String> sample = new HashMap<>();
//            sample.put("Symbol","SAMPLE");
//            sample.put("Type","BUY");
//            sample.put("Date","2025-12-17");
//            sample.put("Price","100");
//            sample.put("StopLoss","95");
//            sample.put("TakeProfit","110");
//            sample.put("Source","Manual");
//            currentData.add(sample);
//        }
//    }
//
//    private List<Map<String,String>> readCSV(String file){
//        List<Map<String,String>> data = new ArrayList<>();
//        try(BufferedReader br = new BufferedReader(new FileReader(file))){
//            String headerLine = br.readLine();
//            if(headerLine==null) return data;
//            String[] headers = headerLine.split(",");
//            String line;
//            while((line=br.readLine())!=null){
//                String[] vals = line.split(",");
//                Map<String,String> map = new HashMap<>();
//                for(int i=0;i<vals.length && i<headers.length;i++) {
//                    map.put(headers[i],vals[i]);
//                }
//                data.add(map);
//            }
//        }catch(Exception e){ 
//            JOptionPane.showMessageDialog(this,"Error reading CSV: "+e.getMessage()); 
//        }
//        return data;
//    }
//
//    // ================= TABLE LOGIC =================
//    private void populateTable(List<Map<String,String>> data){
//        tableModel.setRowCount(0);
//        tableModel.setColumnCount(0);
//
//        if(data.isEmpty()) return;
//
//        // Define columns in desired order
//        String[] columns = {"Type", "TakeProfit", "Price", "Symbol", "StopLoss", "Date"};
//        for(String col : columns) tableModel.addColumn(col);
//
//        for(Map<String,String> row:data){
//            Object[] r = new Object[columns.length];
//            for(int i=0; i<columns.length; i++) {
//                String key = columns[i];
//                r[i] = row.getOrDefault(key, "");
//            }
//            tableModel.addRow(r);
//        }
//    }
//
//    // ================= FILTER LOGIC =================
//    private void filterData(){
//        String symbol = symbolField.getText().trim();
//        String type   = typeCombo.getSelectedItem().toString();
//        String from   = fromDateField.getText().trim();
//        String to     = toDateField.getText().trim();
//
//        List<Map<String,String>> filtered = new ArrayList<>();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//
//        for(Map<String,String> row: currentData){
//            boolean match = true;
//
//            // Symbol
//            if(!symbol.isEmpty() && !row.getOrDefault("Symbol","").equalsIgnoreCase(symbol)) match=false;
//            // Type
//            if(!type.equals("ALL") && !row.getOrDefault("Type","").equalsIgnoreCase(type)) match=false;
//            // Date
//            if(!from.isEmpty() || !to.isEmpty()){
//                try{
//                    Date rowDate = sdf.parse(row.getOrDefault("Date","1970-01-01"));
//                    if(!from.isEmpty() && rowDate.before(sdf.parse(from))) match=false;
//                    if(!to.isEmpty() && rowDate.after(sdf.parse(to))) match=false;
//                }catch(ParseException e){ 
//                    JOptionPane.showMessageDialog(this,"Invalid date format"); 
//                    return; 
//                }
//            }
//
//            if(match) filtered.add(row);
//        }
//
//        populateTable(filtered);
//        updateStatistics();
//    }
//
//    private void resetFilters(){
//        symbolField.setText("");
//        typeCombo.setSelectedIndex(0);
//        fromDateField.setText("");
//        toDateField.setText("");
//        populateTable(currentData);
//        updateStatistics();
//    }
//
//    // ================= EXPORT CSV =================
//    private void exportCSV(){
//        JFileChooser fileChooser = new JFileChooser();
//        fileChooser.setDialogTitle("Export CSV");
//        int userSelection = fileChooser.showSaveDialog(this);
//        if(userSelection == JFileChooser.APPROVE_OPTION){
//            File fileToSave = fileChooser.getSelectedFile();
//            try(PrintWriter pw = new PrintWriter(fileToSave)){
//                // Columns
//                for(int i=0;i<dataTable.getColumnCount();i++){
//                    pw.print(dataTable.getColumnName(i));
//                    if(i<dataTable.getColumnCount()-1) pw.print(",");
//                }
//                pw.println();
//                // Rows
//                for(int r=0;r<dataTable.getRowCount();r++){
//                    for(int c=0;c<dataTable.getColumnCount();c++){
//                        pw.print(dataTable.getValueAt(r,c));
//                        if(c<dataTable.getColumnCount()-1) pw.print(",");
//                    }
//                    pw.println();
//                }
//                JOptionPane.showMessageDialog(this,"Data exported successfully!");
//            }catch(Exception e){ 
//                JOptionPane.showMessageDialog(this,"Export failed: "+e.getMessage()); 
//            }
//        }
//    }
//
//    // ================= ADD NEW TRADE =================
//    private void addTrade(){
//        String symbol = newSymbolField.getText().trim();
//        String type   = newTypeCombo.getSelectedItem().toString();
//        String date   = newDateField.getText().trim();
//        String price  = newPriceField.getText().trim();
//        String stopLoss = newStopLossField.getText().trim();
//        String takeProfit = newTakeProfitField.getText().trim();
//
//        if(symbol.isEmpty() || date.isEmpty() || price.isEmpty()){
//            JOptionPane.showMessageDialog(this,"Symbol, Date, and Price are required!","Error",JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//
//        // Validate date
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        try{ 
//            sdf.parse(date); 
//        }catch(ParseException e){ 
//            JOptionPane.showMessageDialog(this,"Invalid date format"); 
//            return; 
//        }
//
//        // Validate price
//        try{ 
//            Double.parseDouble(price); 
//        }catch(NumberFormatException e){ 
//            JOptionPane.showMessageDialog(this,"Price must be a number!"); 
//            return; 
//        }
//
//        // Add to currentData + table
//        Map<String,String> trade = new HashMap<>();
//        trade.put("Symbol",symbol);
//        trade.put("Type",type);
//        trade.put("Date",date);
//        trade.put("Price",price);
//        trade.put("StopLoss",stopLoss.isEmpty() ? "N/A" : stopLoss);
//        trade.put("TakeProfit",takeProfit.isEmpty() ? "N/A" : takeProfit);
//        trade.put("Source","Manual");
//
//        currentData.add(trade);
//        populateTable(currentData);
//        updateStatistics();
//
//        // Append to CSV (default STOCK_CSV for simplicity, you can extend for FOREX)
//        try(PrintWriter pw = new PrintWriter(new FileWriter(STOCK_CSV,true))){
//            pw.println(symbol+","+type+","+date+","+price+","+stopLoss+","+takeProfit+",Manual");
//        }catch(Exception e){ 
//            JOptionPane.showMessageDialog(this,"Failed to write CSV: "+e.getMessage()); 
//        }
//
//        // Clear fields
//        newSymbolField.setText("");
//        newDateField.setText(LocalDate.now().toString());
//        newPriceField.setText("");
//        newStopLossField.setText("");
//        newTakeProfitField.setText("");
//        newTypeCombo.setSelectedIndex(0);
//
//        // Simulate broker execution if connected
//        if (isBrokerConnected()) {
//            executeTradeOnBroker(symbol, type, price, stopLoss, takeProfit);
//        }
//
//        JOptionPane.showMessageDialog(this,"Trade added successfully!");
//    }
//
//    // ================= BROKER INTEGRATION =================
//    private boolean isBrokerConnected() {
//        return brokerStatusLabel.getText().contains("Connected");
//    }
//
//    private void connectToBroker() {
//        String selectedBroker = (String) brokerCombo.getSelectedItem();
//        // Simulate connection
//        Random random = new Random();
//        boolean success = random.nextBoolean(); // 50% chance of success
//        
//        if (success) {
//            brokerStatusLabel.setText("Status: Connected to " + selectedBroker);
//            brokerStatusLabel.setForeground(SUCCESS_COLOR);
//            connectBtn.setEnabled(false);
//            disconnectBtn.setEnabled(true);
//            JOptionPane.showMessageDialog(this, "Successfully connected to " + selectedBroker);
//        } else {
//            brokerStatusLabel.setText("Status: Connection Failed");
//            brokerStatusLabel.setForeground(ERROR_COLOR);
//            JOptionPane.showMessageDialog(this, "Failed to connect to " + selectedBroker, "Connection Error", JOptionPane.ERROR_MESSAGE);
//        }
//    }
//
//    private void disconnectFromBroker() {
//        brokerStatusLabel.setText("Status: Disconnected");
//        brokerStatusLabel.setForeground(ERROR_COLOR);
//        connectBtn.setEnabled(true);
//        disconnectBtn.setEnabled(false);
//        JOptionPane.showMessageDialog(this, "Disconnected from broker");
//    }
//
//    private void executeTradeOnBroker(String symbol, String type, String price, String stopLoss, String takeProfit) {
//        // Simulate broker execution
//        JOptionPane.showMessageDialog(this, 
//            "Executing trade on broker:\n" +
//            "Symbol: " + symbol + "\n" +
//            "Type: " + type + "\n" +
//            "Price: " + price + "\n" +
//            "Stop Loss: " + stopLoss + "\n" +
//            "Take Profit: " + takeProfit,
//            "Trade Executed", JOptionPane.INFORMATION_MESSAGE);
//    }
//
//    // ================= AI RECOMMENDATION SYSTEM =================
//    private void getAIRecommendationForSymbol() {
//        String symbol = newSymbolField.getText().trim();
//        if (symbol.isEmpty()) {
//            JOptionPane.showMessageDialog(this, "Enter a symbol first!");
//            return;
//        }
//        
//        // Simulate AI recommendation for specific symbol
//        Random random = new Random();
//        String direction = random.nextBoolean() ? "BUY" : "SELL";
//        double entryPrice = Double.parseDouble(newPriceField.getText().isEmpty() ? "100" : newPriceField.getText());
//        double targetPrice = direction.equals("BUY") ? 
//            entryPrice * (1 + (random.nextDouble() * 0.05 + 0.02)) : 
//            entryPrice * (1 - (random.nextDouble() * 0.05 + 0.02));
//        double stopLoss = direction.equals("BUY") ? 
//            entryPrice * (1 - (random.nextDouble() * 0.03 + 0.01)) : 
//            entryPrice * (1 + (random.nextDouble() * 0.03 + 0.01));
//        String confidence = random.nextDouble() > 0.7 ? "HIGH" : random.nextDouble() > 0.4 ? "MEDIUM" : "LOW";
//        
//        // Update fields with AI recommendation
//        newTypeCombo.setSelectedItem(direction);
//        newPriceField.setText(String.format("%.2f", entryPrice));
//        newStopLossField.setText(String.format("%.2f", stopLoss));
//        newTakeProfitField.setText(String.format("%.2f", targetPrice));
//        
//        JOptionPane.showMessageDialog(this, 
//            "AI Recommendation for " + symbol + ":\n" +
//            "Direction: " + direction + "\n" +
//            "Entry Price: $" + String.format("%.2f", entryPrice) + "\n" +
//            "Target Price: $" + String.format("%.2f", targetPrice) + "\n" +
//            "Stop Loss: $" + String.format("%.2f", stopLoss) + "\n" +
//            "Confidence: " + confidence,
//            "AI Recommendation", JOptionPane.INFORMATION_MESSAGE);
//    }
//
//    // ================= TREND ANALYSIS SYSTEM =================
//    private void getTrendAnalysisForSymbol() {
//        String symbol = newSymbolField.getText().trim();
//        if (symbol.isEmpty()) {
//            JOptionPane.showMessageDialog(this, "Enter a symbol first!");
//            return;
//        }
//        
//        // Simulate trend analysis for specific symbol
//        Random random = new Random();
//        String trend = random.nextBoolean() ? "BULLISH" : "BEARISH";
//        String strength = random.nextDouble() > 0.7 ? "STRONG" : random.nextDouble() > 0.4 ? "MODERATE" : "WEAK";
//        double entryPrice = Double.parseDouble(newPriceField.getText().isEmpty() ? "100" : newPriceField.getText());
//        double target = trend.equals("BULLISH") ? 
//            entryPrice * (1 + (random.nextDouble() * 0.05 + 0.02)) : 
//            entryPrice * (1 - (random.nextDouble() * 0.05 + 0.02));
//        double stopLoss = trend.equals("BULLISH") ? 
//            entryPrice * (1 - (random.nextDouble() * 0.03 + 0.01)) : 
//            entryPrice * (1 + (random.nextDouble() * 0.03 + 0.01));
//        
//        // Update fields with trend analysis
//        newTypeCombo.setSelectedItem(trend.equals("BULLISH") ? "BUY" : "SELL");
//        newPriceField.setText(String.format("%.2f", entryPrice));
//        newStopLossField.setText(String.format("%.2f", stopLoss));
//        newTakeProfitField.setText(String.format("%.2f", target));
//        
//        JOptionPane.showMessageDialog(this, 
//            "Trend Analysis for " + symbol + ":\n" +
//            "Trend: " + trend + "\n" +
//            "Strength: " + strength + "\n" +
//            "Entry Price: $" + String.format("%.2f", entryPrice) + "\n" +
//            "Target Price: $" + String.format("%.2f", target) + "\n" +
//            "Stop Loss: $" + String.format("%.2f", stopLoss),
//            "Trend Analysis", JOptionPane.INFORMATION_MESSAGE);
//    }
//
//    // ================= STATISTICS =================
//    private void updateStatistics() {
//        int totalTrades = currentData.size();
//        double totalProfit = 0.0;
//        int winningTrades = 0;
//        
//        // Calculate statistics (simplified for demo)
//        for (Map<String, String> trade : currentData) {
//            if (trade.containsKey("Price")) {
//                double price = Double.parseDouble(trade.get("Price"));
//                // Simulate profit calculation
//                double profit = (Math.random() - 0.5) * 10; // Random profit/loss
//                totalProfit += profit;
//                if (profit > 0) winningTrades++;
//            }
//        }
//        
//        double winRate = totalTrades > 0 ? (double) winningTrades / totalTrades * 100 : 0;
//        
//        totalTradesLabel.setText("Total Trades: " + totalTrades);
//        totalProfitLabel.setText("Total P&L: $" + String.format("%.2f", totalProfit));
//        winRateLabel.setText("Win Rate: " + String.format("%.1f", winRate) + "%");
//    }
//
//    // ================= REFRESH DATA =================
//    private void refreshAllData() {
//        loadCSVData();
//        populateTable(currentData);
//        updateStatistics();
//        JOptionPane.showMessageDialog(this, "Data refreshed successfully!");
//    }
//}  

//
//package gui;
//
//import javax.swing.*;
//import javax.swing.border.LineBorder;
//import javax.swing.table.DefaultTableModel;
//import javax.swing.table.DefaultTableCellRenderer;
//import java.awt.*;
//import java.awt.event.*;
//import java.io.*;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
////import java.time.format DateTimeFormatter;
//import java.util.*;
//import java.util.List;
//
///**
// * DataInputForm.java - Professional Trading Data Input Module
// * Features:
// * - Load CSV (past trades)
// * - Filters: Symbol/Pair, Type, Date Range
// * - Add new trade (update table + CSV)
// * - Reset filters, Export table to CSV
// * - Broker integration for automatic trade execution
// * - AI recommendation system
// * - Trend analysis capabilities
// */
//public class DataInputForm extends JPanel {
//
//    // ================= COLORS / THEME =================
//    private static final Color BG_COLOR      = new Color(246,247,249);
//    private static final Color CARD_BG       = new Color(255,255,255);
//    private static final Color TEXT_COLOR    = new Color(45,45,45);
//    private static final Color BUTTON_COLOR  = new Color(90,74,60);
//    private static final Color BUTTON_HOVER  = new Color(150,125,100);
//    private static final Color HEADER_COLOR  = new Color(220,215,200);
//    private static final Color ERROR_COLOR   = new Color(180,70,70);
//    private static final Color SUCCESS_COLOR = new Color(70,180,70);
//    private static final Color AI_COLOR      = new Color(106,90,205);
//    private static final Color TRENDS_COLOR  = new Color(30,144,255);
//    private static final Color BORDER_COLOR  = new Color(150,150,150);
//
//    // ================= GUI COMPONENTS =================
//    private JTextField symbolField;
//    private JComboBox<String> typeCombo;
//    private JTextField fromDateField;
//    private JTextField toDateField;
//
//    private JTable dataTable;
//    private DefaultTableModel tableModel;
//
//    private JTextField newSymbolField;
//    private JComboBox<String> newTypeCombo;
//    private JTextField newDateField;
//    private JTextField newPriceField;
//    private JTextField newStopLossField;
//    private JTextField newTakeProfitField;
//
//    private List<Map<String,String>> currentData = new ArrayList<>();
//    private List<Map<String,String>> stockData   = new ArrayList<>();
//    private List<Map<String,String>> forexData   = new ArrayList<>();
//
//    private static final String STOCK_CSV = "D:\\OAPFA\\stocks.csv";
//    private static final String FOREX_CSV = "D:\\OAPFA\\forex.csv";
//
//    // Broker connection simulation
//    private JComboBox<String> brokerCombo;
//    private JLabel brokerStatusLabel;
//    private JButton connectBtn;
//    private JButton disconnectBtn;
//    
//    // Statistics labels
//    private JLabel totalTradesLabel;
//    private JLabel totalProfitLabel;
//    private JLabel winRateLabel;
//
//    public DataInputForm(){
//        setLayout(new BorderLayout(10,10));
//        setBackground(BG_COLOR);
//
//        // Load CSV
//        loadCSVData();
//
//        // Build GUI sections
//        JPanel topPanel = buildTopSection();
//        JPanel brokerAndFilterPanel = buildBrokerAndFilterSection();
//        JPanel dataPanel = buildDataSection();
//        JPanel addTradePanel = buildAddTradeSection();
//        JPanel addPreviousTradePanel = buildAddPreviousTradeSection();
//
//        // Layout using JSplitPanes
//        JSplitPane mainSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
//        mainSplit.setTopComponent(topPanel);
//        
//        JSplitPane bottomSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
//        bottomSplit.setLeftComponent(addTradePanel);
//        bottomSplit.setRightComponent(addPreviousTradePanel);
//        
//        JSplitPane contentSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
//        contentSplit.setTopComponent(dataPanel);
//        contentSplit.setBottomComponent(bottomSplit);
//        
//        mainSplit.setBottomComponent(brokerAndFilterPanel);
//        brokerAndFilterPanel.add(contentSplit, BorderLayout.CENTER);
//        
//        // Set divider locations
//        mainSplit.setDividerLocation(60);
//        contentSplit.setDividerLocation(200); // Trade data panel height
//        
//        add(mainSplit, BorderLayout.CENTER);
//
//        // Populate table
//        populateTable(currentData);
//        updateStatistics();
//    }
//
//    // ================= TOP SECTION =================
//    private JPanel buildTopSection(){
//        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
//        topPanel.setBackground(HEADER_COLOR);
//        topPanel.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));
//
//        // Refresh button only (removed back button)
//        JButton refreshBtn = new JButton("Refresh Data");
//        styleButton(refreshBtn, BUTTON_COLOR);
//        refreshBtn.addActionListener(e -> refreshAllData());
//
//        // Statistics panel
//        JPanel statsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 5));
//        statsPanel.setBackground(HEADER_COLOR);
//        
//        totalTradesLabel = new JLabel("Total Trades: 0");
//        totalProfitLabel = new JLabel("Total P&L: $0.00");
//        winRateLabel = new JLabel("Win Rate: 0%");
//        
//        statsPanel.add(totalTradesLabel);
//        statsPanel.add(totalProfitLabel);
//        statsPanel.add(winRateLabel);
//        
//        topPanel.add(refreshBtn);
//        topPanel.add(Box.createHorizontalGlue());
//        topPanel.add(statsPanel);
//
//        return topPanel;
//    }
//
//    // ================= BROKER AND FILTER SECTION =================
//    private JPanel buildBrokerAndFilterSection(){
//        JPanel brokerAndFilterPanel = new JPanel(new BorderLayout());
//        brokerAndFilterPanel.setBackground(BG_COLOR);
//        
//        // Broker connection panel
//        JPanel brokerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
//        brokerPanel.setBackground(BG_COLOR);
//        brokerPanel.setBorder(BorderFactory.createTitledBorder(
//            new LineBorder(BORDER_COLOR, 1), "Broker Connection"));
//        
//        JLabel brokerLabel = new JLabel("Broker:");
//        brokerCombo = new JComboBox<>(new String[]{"Interactive Brokers", "TD Ameritrade", "MetaTrader 5", "E*TRADE"});
//        connectBtn = new JButton("Connect");
//        disconnectBtn = new JButton("Disconnect");
//        brokerStatusLabel = new JLabel("Status: Disconnected");
//        brokerStatusLabel.setForeground(ERROR_COLOR);
//
//        styleButton(connectBtn, SUCCESS_COLOR);
//        styleButton(disconnectBtn, ERROR_COLOR);
//
//        connectBtn.addActionListener(e -> connectToBroker());
//        disconnectBtn.addActionListener(e -> disconnectFromBroker());
//
//        brokerPanel.add(brokerLabel);
//        brokerPanel.add(brokerCombo);
//        brokerPanel.add(connectBtn);
//        brokerPanel.add(disconnectBtn);
//        brokerPanel.add(brokerStatusLabel);
//
//        // Filter panel
//        JPanel filterPanel = new JPanel(new GridBagLayout());
//        filterPanel.setBackground(BG_COLOR);
//        filterPanel.setBorder(BorderFactory.createTitledBorder(
//            new LineBorder(BORDER_COLOR, 1), "Filters"));
//
//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.insets = new Insets(5,10,5,10);
//        gbc.anchor = GridBagConstraints.WEST;
//
//        // Symbol
//        gbc.gridx=0; gbc.gridy=0;
//        filterPanel.add(new JLabel("Symbol / Pair:"),gbc);
//        symbolField = new JTextField(25);
//        gbc.gridx=1;
//        filterPanel.add(symbolField,gbc);
//
//        // Type
//        gbc.gridx=2;
//        filterPanel.add(new JLabel("Type:"),gbc);
//        typeCombo = new JComboBox<>(new String[]{"ALL","BUY","SELL"});
//        gbc.gridx=3;
//        filterPanel.add(typeCombo,gbc);
//
//        // From Date
//        gbc.gridx=0; gbc.gridy=1;
//        filterPanel.add(new JLabel("From Date (yyyy-MM-dd):"),gbc);
//        fromDateField = new JTextField(25);
//        gbc.gridx=1;
//        filterPanel.add(fromDateField,gbc);
//
//        // To Date
//        gbc.gridx=2;
//        filterPanel.add(new JLabel("To Date (yyyy-MM-dd):"),gbc);
//        toDateField = new JTextField(25);
//        gbc.gridx=3;
//        filterPanel.add(toDateField,gbc);
//
//        // Buttons
//        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
//        btnPanel.setBackground(BG_COLOR);
//        JButton searchBtn = new JButton("Search");
//        JButton resetBtn  = new JButton("Reset Filters");
//        JButton exportBtn = new JButton("Export CSV");
//
//        styleButton(searchBtn, BUTTON_COLOR);
//        styleButton(resetBtn, BUTTON_COLOR);
//        styleButton(exportBtn, BUTTON_COLOR);
//
//        btnPanel.add(searchBtn);
//        btnPanel.add(resetBtn);
//        btnPanel.add(exportBtn);
//
//        gbc.gridx=0; gbc.gridy=2; gbc.gridwidth=4;
//        filterPanel.add(btnPanel,gbc);
//
//        // ===== EVENTS =====
//        searchBtn.addActionListener(e -> filterData());
//        resetBtn.addActionListener(e -> resetFilters());
//        exportBtn.addActionListener(e -> exportCSV());
//
//        // Combine broker and filter panels
//        JPanel combinedPanel = new JPanel(new BorderLayout());
//        combinedPanel.add(brokerPanel, BorderLayout.NORTH);
//        combinedPanel.add(filterPanel, BorderLayout.CENTER);
//
//        return combinedPanel;
//    }
//
//    // ================= DATA TABLE SECTION =================
//    private JPanel buildDataSection(){
//        JPanel dataPanel = new JPanel(new BorderLayout());
//        dataPanel.setBackground(BG_COLOR);
//        dataPanel.setBorder(BorderFactory.createTitledBorder(
//            new LineBorder(BORDER_COLOR, 1), "Trade Data"));
//
//        tableModel = new DefaultTableModel();
//        dataTable = new JTable(tableModel);
//        dataTable.setFillsViewportHeight(true);
//        dataTable.setRowHeight(22);
//        dataTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
//        dataTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
//        dataTable.getTableHeader().setBackground(Color.WHITE);
//        dataTable.getTableHeader().setForeground(TEXT_COLOR);
//        dataTable.setSelectionBackground(new Color(200,220,255));
//        dataTable.setSelectionForeground(TEXT_COLOR);
//        
//        // Alternate row colors for better readability
//        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
//            @Override
//            public Component getTableCellRendererComponent(JTable table, Object value,
//                    boolean isSelected, boolean hasFocus, int row, int column) {
//                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
//                
//                if (!isSelected) {
//                    setBackground(row % 2 == 0 ? Color.WHITE : new Color(240,240,240));
//                    setForeground(TEXT_COLOR);
//                }
//                
//                return this;
//            }
//        };
//        dataTable.setDefaultRenderer(Object.class, renderer);
//
//        JScrollPane scrollPane = new JScrollPane(dataTable);
//        dataPanel.add(scrollPane, BorderLayout.CENTER);
//
//        return dataPanel;
//    }
//
//    // ================= ADD NEW TRADE SECTION (LEFT) =================
//    private JPanel buildAddTradeSection(){
//        JPanel addTradePanel = new JPanel(new GridBagLayout());
//        addTradePanel.setBackground(BG_COLOR);
//        addTradePanel.setBorder(BorderFactory.createTitledBorder(
//            new LineBorder(BORDER_COLOR, 1), "Add New Trade"));
//
//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.insets = new Insets(5,10,5,10);
//        gbc.anchor = GridBagConstraints.WEST;
//
//        // Symbol
//        gbc.gridx=0; gbc.gridy=0;
//        addTradePanel.add(new JLabel("Symbol / Pair:"),gbc);
//        newSymbolField = new JTextField(30);
//        gbc.gridx=1;
//        addTradePanel.add(newSymbolField,gbc);
//
//        // Type
//        gbc.gridx=2;
//        addTradePanel.add(new JLabel("Type:"),gbc);
//        newTypeCombo = new JComboBox<>(new String[]{"BUY","SELL"});
//        gbc.gridx=3;
//        addTradePanel.add(newTypeCombo,gbc);
//
//        // Date
//        gbc.gridx=0; gbc.gridy=1;
//        addTradePanel.add(new JLabel("Date (yyyy-MM-dd):"),gbc);
//        newDateField = new JTextField(30);
//        newDateField.setText(LocalDate.now().toString());
//        gbc.gridx=1;
//        addTradePanel.add(newDateField,gbc);
//
//        // Price
//        gbc.gridx=2;
//        addTradePanel.add(new JLabel("Price:"),gbc);
//        newPriceField = new JTextField(30);
//        gbc.gridx=3;
//        addTradePanel.add(newPriceField,gbc);
//
//        // Stop Loss
//        gbc.gridx=0; gbc.gridy=2;
//        addTradePanel.add(new JLabel("Stop Loss:"),gbc);
//        newStopLossField = new JTextField(30);
//        gbc.gridx=1;
//        addTradePanel.add(newStopLossField,gbc);
//
//        // Take Profit
//        gbc.gridx=2;
//        addTradePanel.add(new JLabel("Take Profit:"),gbc);
//        newTakeProfitField = new JTextField(30);
//        gbc.gridx=3;
//        addTradePanel.add(newTakeProfitField,gbc);
//
//        // Add Button
//        JButton addBtn = new JButton("Add New Trade");
//        JButton aiRecommendBtn = new JButton("Get AI Recommendation");
//        JButton trendRecommendBtn = new JButton("Get Trend Analysis");
//        
//        styleButton(addBtn, BUTTON_COLOR);
//        styleButton(aiRecommendBtn, AI_COLOR);
//        styleButton(trendRecommendBtn, TRENDS_COLOR);
//
//        gbc.gridx=0; gbc.gridy=3; gbc.gridwidth=4;
//        gbc.anchor = GridBagConstraints.CENTER;
//        
//        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
//        buttonPanel.setBackground(BG_COLOR);
//        buttonPanel.add(addBtn);
//        buttonPanel.add(aiRecommendBtn);
//        buttonPanel.add(trendRecommendBtn);
//        
//        addTradePanel.add(buttonPanel,gbc);
//
//        addBtn.addActionListener(e -> addTrade());
//        aiRecommendBtn.addActionListener(e -> getAIRecommendationForSymbol());
//        trendRecommendBtn.addActionListener(e -> getTrendAnalysisForSymbol());
//
//        return addTradePanel;
//    }
//
//    // ================= ADD PREVIOUS TRADE SECTION (RIGHT) =================
//    private JPanel buildAddPreviousTradeSection(){
//        JPanel addPrevTradePanel = new JPanel(new GridBagLayout());
//        addPrevTradePanel.setBackground(BG_COLOR);
//        addPrevTradePanel.setBorder(BorderFactory.createTitledBorder(
//            new LineBorder(BORDER_COLOR, 1), "Add Previous Trade"));
//
//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.insets = new Insets(5,10,5,10);
//        gbc.anchor = GridBagConstraints.WEST;
//
//        // Symbol
//        gbc.gridx=0; gbc.gridy=0;
//        addPrevTradePanel.add(new JLabel("Symbol / Pair:"),gbc);
//        JTextField prevSymbolField = new JTextField(30);
//        gbc.gridx=1;
//        addPrevTradePanel.add(prevSymbolField,gbc);
//
//        // Type
//        gbc.gridx=2;
//        addPrevTradePanel.add(new JLabel("Type:"),gbc);
//        JComboBox<String> prevTypeCombo = new JComboBox<>(new String[]{"BUY","SELL"});
//        gbc.gridx=3;
//        addPrevTradePanel.add(prevTypeCombo,gbc);
//
//        // Date
//        gbc.gridx=0; gbc.gridy=1;
//        addPrevTradePanel.add(new JLabel("Date (yyyy-MM-dd):"),gbc);
//        JTextField prevDateField = new JTextField(30);
//        prevDateField.setText(LocalDate.now().toString());
//        gbc.gridx=1;
//        addPrevTradePanel.add(prevDateField,gbc);
//
//        // Price
//        gbc.gridx=2;
//        addPrevTradePanel.add(new JLabel("Price:"),gbc);
//        JTextField prevPriceField = new JTextField(30);
//        gbc.gridx=3;
//        addPrevTradePanel.add(prevPriceField,gbc);
//
//        // Stop Loss
//        gbc.gridx=0; gbc.gridy=2;
//        addPrevTradePanel.add(new JLabel("Stop Loss:"),gbc);
//        JTextField prevStopLossField = new JTextField(30);
//        gbc.gridx=1;
//        addPrevTradePanel.add(prevStopLossField,gbc);
//
//        // Take Profit
//        gbc.gridx=2;
//        addPrevTradePanel.add(new JLabel("Take Profit:"),gbc);
//        JTextField prevTakeProfitField = new JTextField(30);
//        gbc.gridx=3;
//        addPrevTradePanel.add(prevTakeProfitField,gbc);
//
//        // Add Previous Trade Button
//        JButton addPrevBtn = new JButton("Add Previous Trade");
//        styleButton(addPrevBtn, BUTTON_COLOR);
//        
//        gbc.gridx=0; gbc.gridy=3; gbc.gridwidth=4;
//        gbc.anchor = GridBagConstraints.CENTER;
//        addPrevTradePanel.add(addPrevBtn,gbc);
//
//        addPrevBtn.addActionListener(e -> {
//            String symbol = prevSymbolField.getText().trim();
//            String type = (String) prevTypeCombo.getSelectedItem();
//            String date = prevDateField.getText().trim();
//            String price = prevPriceField.getText().trim();
//            String stopLoss = prevStopLossField.getText().trim();
//            String takeProfit = prevTakeProfitField.getText().trim();
//
//            if(symbol.isEmpty() || date.isEmpty() || price.isEmpty()){
//                JOptionPane.showMessageDialog(this,"Symbol, Date, and Price are required!","Error",JOptionPane.ERROR_MESSAGE);
//                return;
//            }
//
//            // Validate date
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//            try{ 
//                sdf.parse(date); 
//            }catch(ParseException ex){ 
//                JOptionPane.showMessageDialog(this,"Invalid date format"); 
//                return; 
//            }
//
//            // Validate price
//            try{ 
//                Double.parseDouble(price); 
//            }catch(NumberFormatException ex){ 
//                JOptionPane.showMessageDialog(this,"Price must be a number!"); 
//                return; 
//            }
//
//            // Add to currentData + table
//            Map<String,String> trade = new HashMap<>();
//            trade.put("Symbol",symbol);
//            trade.put("Type",type);
//            trade.put("Date",date);
//            trade.put("Price",price);
//            trade.put("StopLoss",stopLoss.isEmpty() ? "N/A" : stopLoss);
//            trade.put("TakeProfit",takeProfit.isEmpty() ? "N/A" : takeProfit);
//            trade.put("Source","Previous Trade");
//
//            currentData.add(trade);
//            populateTable(currentData);
//            updateStatistics();
//
//            // Append to CSV (default STOCK_CSV for simplicity, you can extend for FOREX)
//            try(PrintWriter pw = new PrintWriter(new FileWriter(STOCK_CSV,true))){
//                pw.println(symbol+","+type+","+date+","+price+","+stopLoss+","+takeProfit+",Previous Trade");
//            }catch(Exception ex){ 
//                JOptionPane.showMessageDialog(this,"Failed to write CSV: "+ex.getMessage()); 
//            }
//
//            // Clear fields
//            prevSymbolField.setText("");
//            prevDateField.setText(LocalDate.now().toString());
//            prevPriceField.setText("");
//            prevStopLossField.setText("");
//            prevTakeProfitField.setText("");
//            prevTypeCombo.setSelectedIndex(0);
//
//            JOptionPane.showMessageDialog(this,"Previous trade added successfully!");
//        });
//
//        return addPrevTradePanel;
//    }
//
//    // ================= BUTTON STYLE =================
//    private void styleButton(JButton b, Color bgColor){
//        b.setBackground(bgColor);
//        b.setForeground(Color.WHITE);
//        b.setFocusPainted(false);
//        b.setBorder(new LineBorder(Color.WHITE,1,true));
//        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
//        b.setFont(new Font("Segoe UI", Font.BOLD, 12));
//        
//        b.addMouseListener(new MouseAdapter() {
//            public void mouseEntered(MouseEvent evt){ 
//                b.setBackground(bgColor.darker()); 
//            }
//            public void mouseExited(MouseEvent evt){ 
//                b.setBackground(bgColor); 
//            }
//        });
//    }
//
//    // ================= CSV DATA =================
//    private void loadCSVData(){
//        stockData = readCSV(STOCK_CSV);
//        forexData = readCSV(FOREX_CSV);
//
//        currentData.clear();
//        currentData.addAll(stockData);
//        currentData.addAll(forexData);
//
//        if(currentData.isEmpty()){
//            Map<String,String> sample = new HashMap<>();
//            sample.put("Symbol","SAMPLE");
//            sample.put("Type","BUY");
//            sample.put("Date","2025-12-17");
//            sample.put("Price","100");
//            sample.put("StopLoss","95");
//            sample.put("TakeProfit","110");
//            sample.put("Source","Manual");
//            currentData.add(sample);
//        }
//    }
//
//    private List<Map<String,String>> readCSV(String file){
//        List<Map<String,String>> data = new ArrayList<>();
//        try(BufferedReader br = new BufferedReader(new FileReader(file))){
//            String headerLine = br.readLine();
//            if(headerLine==null) return data;
//            String[] headers = headerLine.split(",");
//            String line;
//            while((line=br.readLine())!=null){
//                String[] vals = line.split(",");
//                Map<String,String> map = new HashMap<>();
//                for(int i=0;i<vals.length && i<headers.length;i++) {
//                    map.put(headers[i],vals[i]);
//                }
//                data.add(map);
//            }
//        }catch(Exception e){ 
//            JOptionPane.showMessageDialog(this,"Error reading CSV: "+e.getMessage()); 
//        }
//        return data;
//    }
//
//    // ================= TABLE LOGIC =================
//    private void populateTable(List<Map<String,String>> data){
//        tableModel.setRowCount(0);
//        tableModel.setColumnCount(0);
//
//        if(data.isEmpty()) return;
//
//        // Define columns in desired order
//        String[] columns = {"Type", "TakeProfit", "Price", "Symbol", "StopLoss", "Date"};
//        for(String col : columns) tableModel.addColumn(col);
//
//        for(Map<String,String> row:data){
//            Object[] r = new Object[columns.length];
//            for(int i=0; i<columns.length; i++) {
//                String key = columns[i];
//                r[i] = row.getOrDefault(key, "");
//            }
//            tableModel.addRow(r);
//        }
//    }
//
//    // ================= FILTER LOGIC =================
//    private void filterData(){
//        String symbol = symbolField.getText().trim();
//        String type   = typeCombo.getSelectedItem().toString();
//        String from   = fromDateField.getText().trim();
//        String to     = toDateField.getText().trim();
//
//        List<Map<String,String>> filtered = new ArrayList<>();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//
//        for(Map<String,String> row: currentData){
//            boolean match = true;
//
//            // Symbol
//            if(!symbol.isEmpty() && !row.getOrDefault("Symbol","").equalsIgnoreCase(symbol)) match=false;
//            // Type
//            if(!type.equals("ALL") && !row.getOrDefault("Type","").equalsIgnoreCase(type)) match=false;
//            // Date
//            if(!from.isEmpty() || !to.isEmpty()){
//                try{
//                    Date rowDate = sdf.parse(row.getOrDefault("Date","1970-01-01"));
//                    if(!from.isEmpty() && rowDate.before(sdf.parse(from))) match=false;
//                    if(!to.isEmpty() && rowDate.after(sdf.parse(to))) match=false;
//                }catch(ParseException e){ 
//                    JOptionPane.showMessageDialog(this,"Invalid date format"); 
//                    return; 
//                }
//            }
//
//            if(match) filtered.add(row);
//        }
//
//        populateTable(filtered);
//        updateStatistics();
//    }
//
//    private void resetFilters(){
//        symbolField.setText("");
//        typeCombo.setSelectedIndex(0);
//        fromDateField.setText("");
//        toDateField.setText("");
//        populateTable(currentData);
//        updateStatistics();
//    }
//
//    // ================= EXPORT CSV =================
//    private void exportCSV(){
//        JFileChooser fileChooser = new JFileChooser();
//        fileChooser.setDialogTitle("Export CSV");
//        int userSelection = fileChooser.showSaveDialog(this);
//        if(userSelection == JFileChooser.APPROVE_OPTION){
//            File fileToSave = fileChooser.getSelectedFile();
//            try(PrintWriter pw = new PrintWriter(fileToSave)){
//                // Columns
//                for(int i=0;i<dataTable.getColumnCount();i++){
//                    pw.print(dataTable.getColumnName(i));
//                    if(i<dataTable.getColumnCount()-1) pw.print(",");
//                }
//                pw.println();
//                // Rows
//                for(int r=0;r<dataTable.getRowCount();r++){
//                    for(int c=0;c<dataTable.getColumnCount();c++){
//                        pw.print(dataTable.getValueAt(r,c));
//                        if(c<dataTable.getColumnCount()-1) pw.print(",");
//                    }
//                    pw.println();
//                }
//                JOptionPane.showMessageDialog(this,"Data exported successfully!");
//            }catch(Exception e){ 
//                JOptionPane.showMessageDialog(this,"Export failed: "+e.getMessage()); 
//            }
//        }
//    }
//
//    // ================= ADD NEW TRADE =================
//    private void addTrade(){
//        String symbol = newSymbolField.getText().trim();
//        String type   = newTypeCombo.getSelectedItem().toString();
//        String date   = newDateField.getText().trim();
//        String price  = newPriceField.getText().trim();
//        String stopLoss = newStopLossField.getText().trim();
//        String takeProfit = newTakeProfitField.getText().trim();
//
//        if(symbol.isEmpty() || date.isEmpty() || price.isEmpty()){
//            JOptionPane.showMessageDialog(this,"Symbol, Date, and Price are required!","Error",JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//
//        // Validate date
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        try{ 
//            sdf.parse(date); 
//        }catch(ParseException e){ 
//            JOptionPane.showMessageDialog(this,"Invalid date format"); 
//            return; 
//        }
//
//        // Validate price
//        try{ 
//            Double.parseDouble(price); 
//        }catch(NumberFormatException e){ 
//            JOptionPane.showMessageDialog(this,"Price must be a number!"); 
//            return; 
//        }
//
//        // Add to currentData + table
//        Map<String,String> trade = new HashMap<>();
//        trade.put("Symbol",symbol);
//        trade.put("Type",type);
//        trade.put("Date",date);
//        trade.put("Price",price);
//        trade.put("StopLoss",stopLoss.isEmpty() ? "N/A" : stopLoss);
//        trade.put("TakeProfit",takeProfit.isEmpty() ? "N/A" : takeProfit);
//        trade.put("Source","Manual");
//
//        currentData.add(trade);
//        populateTable(currentData);
//        updateStatistics();
//
//        // Append to CSV (default STOCK_CSV for simplicity, you can extend for FOREX)
//        try(PrintWriter pw = new PrintWriter(new FileWriter(STOCK_CSV,true))){
//            pw.println(symbol+","+type+","+date+","+price+","+stopLoss+","+takeProfit+",Manual");
//        }catch(Exception e){ 
//            JOptionPane.showMessageDialog(this,"Failed to write CSV: "+e.getMessage()); 
//        }
//
//        // Clear fields
//        newSymbolField.setText("");
//        newDateField.setText(LocalDate.now().toString());
//        newPriceField.setText("");
//        newStopLossField.setText("");
//        newTakeProfitField.setText("");
//        newTypeCombo.setSelectedIndex(0);
//
//        // Simulate broker execution if connected
//        if (isBrokerConnected()) {
//            executeTradeOnBroker(symbol, type, price, stopLoss, takeProfit);
//        }
//
//        JOptionPane.showMessageDialog(this,"Trade added successfully!");
//    }
//
//    // ================= BROKER INTEGRATION =================
//    private boolean isBrokerConnected() {
//        return brokerStatusLabel.getText().contains("Connected");
//    }
//
//    private void connectToBroker() {
//        String selectedBroker = (String) brokerCombo.getSelectedItem();
//        // Simulate connection
//        Random random = new Random();
//        boolean success = random.nextBoolean(); // 50% chance of success
//        
//        if (success) {
//            brokerStatusLabel.setText("Status: Connected to " + selectedBroker);
//            brokerStatusLabel.setForeground(SUCCESS_COLOR);
//            connectBtn.setEnabled(false);
//            disconnectBtn.setEnabled(true);
//            JOptionPane.showMessageDialog(this, "Successfully connected to " + selectedBroker);
//        } else {
//            brokerStatusLabel.setText("Status: Connection Failed");
//            brokerStatusLabel.setForeground(ERROR_COLOR);
//            JOptionPane.showMessageDialog(this, "Failed to connect to " + selectedBroker, "Connection Error", JOptionPane.ERROR_MESSAGE);
//        }
//    }
//
//    private void disconnectFromBroker() {
//        brokerStatusLabel.setText("Status: Disconnected");
//        brokerStatusLabel.setForeground(ERROR_COLOR);
//        connectBtn.setEnabled(true);
//        disconnectBtn.setEnabled(false);
//        JOptionPane.showMessageDialog(this, "Disconnected from broker");
//    }
//
//    private void executeTradeOnBroker(String symbol, String type, String price, String stopLoss, String takeProfit) {
//        // Simulate broker execution
//        JOptionPane.showMessageDialog(this, 
//            "Executing trade on broker:\n" +
//            "Symbol: " + symbol + "\n" +
//            "Type: " + type + "\n" +
//            "Price: " + price + "\n" +
//            "Stop Loss: " + stopLoss + "\n" +
//            "Take Profit: " + takeProfit,
//            "Trade Executed", JOptionPane.INFORMATION_MESSAGE);
//    }
//
//    // ================= AI RECOMMENDATION SYSTEM =================
//    private void getAIRecommendationForSymbol() {
//        String symbol = newSymbolField.getText().trim();
//        if (symbol.isEmpty()) {
//            JOptionPane.showMessageDialog(this, "Enter a symbol first!");
//            return;
//        }
//        
//        // Simulate AI recommendation for specific symbol
//        Random random = new Random();
//        String direction = random.nextBoolean() ? "BUY" : "SELL";
//        double entryPrice = Double.parseDouble(newPriceField.getText().isEmpty() ? "100" : newPriceField.getText());
//        double targetPrice = direction.equals("BUY") ? 
//            entryPrice * (1 + (random.nextDouble() * 0.05 + 0.02)) : 
//            entryPrice * (1 - (random.nextDouble() * 0.05 + 0.02));
//        double stopLoss = direction.equals("BUY") ? 
//            entryPrice * (1 - (random.nextDouble() * 0.03 + 0.01)) : 
//            entryPrice * (1 + (random.nextDouble() * 0.03 + 0.01));
//        String confidence = random.nextDouble() > 0.7 ? "HIGH" : random.nextDouble() > 0.4 ? "MEDIUM" : "LOW";
//        
//        // Update fields with AI recommendation
//        newTypeCombo.setSelectedItem(direction);
//        newPriceField.setText(String.format("%.2f", entryPrice));
//        newStopLossField.setText(String.format("%.2f", stopLoss));
//        newTakeProfitField.setText(String.format("%.2f", targetPrice));
//        
//        JOptionPane.showMessageDialog(this, 
//            "AI Recommendation for " + symbol + ":\n" +
//            "Direction: " + direction + "\n" +
//            "Entry Price: $" + String.format("%.2f", entryPrice) + "\n" +
//            "Target Price: $" + String.format("%.2f", targetPrice) + "\n" +
//            "Stop Loss: $" + String.format("%.2f", stopLoss) + "\n" +
//            "Confidence: " + confidence,
//            "AI Recommendation", JOptionPane.INFORMATION_MESSAGE);
//    }
//
//    // ================= TREND ANALYSIS SYSTEM =================
//    private void getTrendAnalysisForSymbol() {
//        String symbol = newSymbolField.getText().trim();
//        if (symbol.isEmpty()) {
//            JOptionPane.showMessageDialog(this, "Enter a symbol first!");
//            return;
//        }
//        
//        // Simulate trend analysis for specific symbol
//        Random random = new Random();
//        String trend = random.nextBoolean() ? "BULLISH" : "BEARISH";
//        String strength = random.nextDouble() > 0.7 ? "STRONG" : random.nextDouble() > 0.4 ? "MODERATE" : "WEAK";
//        double entryPrice = Double.parseDouble(newPriceField.getText().isEmpty() ? "100" : newPriceField.getText());
//        double target = trend.equals("BULLISH") ? 
//            entryPrice * (1 + (random.nextDouble() * 0.05 + 0.02)) : 
//            entryPrice * (1 - (random.nextDouble() * 0.05 + 0.02));
//        double stopLoss = trend.equals("BULLISH") ? 
//            entryPrice * (1 - (random.nextDouble() * 0.03 + 0.01)) : 
//            entryPrice * (1 + (random.nextDouble() * 0.03 + 0.01));
//        
//        // Update fields with trend analysis
//        newTypeCombo.setSelectedItem(trend.equals("BULLISH") ? "BUY" : "SELL");
//        newPriceField.setText(String.format("%.2f", entryPrice));
//        newStopLossField.setText(String.format("%.2f", stopLoss));
//        newTakeProfitField.setText(String.format("%.2f", target));
//        
//        JOptionPane.showMessageDialog(this, 
//            "Trend Analysis for " + symbol + ":\n" +
//            "Trend: " + trend + "\n" +
//            "Strength: " + strength + "\n" +
//            "Entry Price: $" + String.format("%.2f", entryPrice) + "\n" +
//            "Target Price: $" + String.format("%.2f", target) + "\n" +
//            "Stop Loss: $" + String.format("%.2f", stopLoss),
//            "Trend Analysis", JOptionPane.INFORMATION_MESSAGE);
//    }
//
//    // ================= STATISTICS =================
//    private void updateStatistics() {
//        int totalTrades = currentData.size();
//        double totalProfit = 0.0;
//        int winningTrades = 0;
//        
//        // Calculate statistics (simplified for demo)
//        for (Map<String, String> trade : currentData) {
//            if (trade.containsKey("Price")) {
//                double price = Double.parseDouble(trade.get("Price"));
//                // Simulate profit calculation
//                double profit = (Math.random() - 0.5) * 10; // Random profit/loss
//                totalProfit += profit;
//                if (profit > 0) winningTrades++;
//            }
//        }
//        
//        double winRate = totalTrades > 0 ? (double) winningTrades / totalTrades * 100 : 0;
//        
//        totalTradesLabel.setText("Total Trades: " + totalTrades);
//        totalProfitLabel.setText("Total P&L: $" + String.format("%.2f", totalProfit));
//        winRateLabel.setText("Win Rate: " + String.format("%.1f", winRate) + "%");
//    }
//
//    // ================= REFRESH DATA =================
//    private void refreshAllData() {
//        loadCSVData();
//        populateTable(currentData);
//        updateStatistics();
//        JOptionPane.showMessageDialog(this, "Data refreshed successfully!");
//    }
//}  




package gui;

import integration.JNIHandler;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class DataInputForm extends JPanel {
    private JTextField symbolField;
    private JTextField startDateField;
    private JTextField endDateField;
    private JComboBox<String> dataTypeCombo;
    private JTextArea resultArea;
    private JLabel maxPriceLabel;
    private JLabel minPriceLabel;
    private JLabel recordCountLabel;
    private JButton loadButton;
    private JButton resetButton;
    
    private static final Color BACKGROUND_PRIMARY = new Color(15, 23, 30);
    private static final Color BACKGROUND_SECONDARY = new Color(25, 35, 45);
    private static final Color ACCENT_GOLD = new Color(212, 175, 55);
    private static final Color TEXT_PRIMARY = new Color(240, 240, 240);
    private static final Color TEXT_SECONDARY = new Color(180, 180, 180);
    private static final Color BORDER_COLOR = new Color(60, 70, 80);
    
    private JNIHandler jniHandler;
    
    public DataInputForm() {
        jniHandler = new JNIHandler();
        setupLayout();
        attachEventHandlers();
    }
    
    private void setupLayout() {
        setLayout(null);
        setOpaque(false);
        
        // Title
        JLabel titleLabel = new JLabel("DATA MANAGEMENT MODULE");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        titleLabel.setForeground(ACCENT_GOLD);
        titleLabel.setBounds(50, 20, 400, 35);
        add(titleLabel);
        
        // Input Panel
        JPanel inputPanel = createStyledPanel();
        inputPanel.setBounds(50, 70, 350, 400);
        inputPanel.setLayout(null);
        
        // Data Type
        JLabel typeLabel = createLabel("DATA TYPE");
        typeLabel.setBounds(20, 20, 310, 20);
        inputPanel.add(typeLabel);
        
        String[] types = {"Stock Market", "Forex Pair"};
        dataTypeCombo = new JComboBox<>(types);
        styleComboBox(dataTypeCombo);
        dataTypeCombo.setBounds(20, 45, 310, 35);
        inputPanel.add(dataTypeCombo);
        
        // Symbol
        JLabel symbolLabel = createLabel("SYMBOL / PAIR");
        symbolLabel.setBounds(20, 90, 310, 20);
        inputPanel.add(symbolLabel);
        
        symbolField = createStyledTextField();
        symbolField.setBounds(20, 115, 310, 35);
        inputPanel.add(symbolField);
        
        // Start Date
        JLabel startLabel = createLabel("START DATE (YYYY-MM-DD)");
        startLabel.setBounds(20, 160, 310, 20);
        inputPanel.add(startLabel);
        
        startDateField = createStyledTextField();
        startDateField.setBounds(20, 185, 310, 35);
        inputPanel.add(startDateField);
        
        // End Date
        JLabel endLabel = createLabel("END DATE (YYYY-MM-DD)");
        endLabel.setBounds(20, 230, 310, 20);
        inputPanel.add(endLabel);
        
        endDateField = createStyledTextField();
        endDateField.setBounds(20, 255, 310, 35);
        inputPanel.add(endDateField);
        
        // Load Button
        loadButton = createStyledButton("LOAD DATA", true);
        loadButton.setBounds(20, 310, 310, 40);
        inputPanel.add(loadButton);
        
        // Reset Button
        resetButton = createStyledButton("RESET", false);
        resetButton.setBounds(20, 355, 150, 35);
        inputPanel.add(resetButton);
        
        add(inputPanel);
        
        // Results Panel
        JPanel resultsPanel = createStyledPanel();
        resultsPanel.setBounds(420, 70, 330, 400);
        resultsPanel.setLayout(null);
        
        JLabel resultsTitle = new JLabel("ANALYSIS RESULTS");
        resultsTitle.setFont(new Font("SansSerif", Font.BOLD, 14));
        resultsTitle.setForeground(ACCENT_GOLD);
        resultsTitle.setBounds(20, 15, 290, 25);
        resultsPanel.add(resultsTitle);
        
        // Statistics Panel
        JPanel statsPanel = createStyledPanel();
        statsPanel.setBounds(20, 50, 290, 120);
        statsPanel.setLayout(null);
        
        JLabel maxLabel = new JLabel("Maximum Price:");
        maxLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        maxLabel.setForeground(TEXT_SECONDARY);
        maxLabel.setBounds(15, 15, 120, 20);
        statsPanel.add(maxLabel);
        
        maxPriceLabel = new JLabel("--");
        maxPriceLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        maxPriceLabel.setForeground(ACCENT_GOLD);
        maxPriceLabel.setBounds(140, 15, 135, 20);
        statsPanel.add(maxPriceLabel);
        
        JLabel minLabel = new JLabel("Minimum Price:");
        minLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        minLabel.setForeground(TEXT_SECONDARY);
        minLabel.setBounds(15, 45, 120, 20);
        statsPanel.add(minLabel);
        
        minPriceLabel = new JLabel("--");
        minPriceLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        minPriceLabel.setForeground(ACCENT_GOLD);
        minPriceLabel.setBounds(140, 45, 135, 20);
        statsPanel.add(minPriceLabel);
        
        JLabel countLabel = new JLabel("Records Loaded:");
        countLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        countLabel.setForeground(TEXT_SECONDARY);
        countLabel.setBounds(15, 75, 120, 20);
        statsPanel.add(countLabel);
        
        recordCountLabel = new JLabel("0");
        recordCountLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        recordCountLabel.setForeground(ACCENT_GOLD);
        recordCountLabel.setBounds(140, 75, 135, 20);
        statsPanel.add(recordCountLabel);
        
        resultsPanel.add(statsPanel);
        
        // Result Text Area
        JLabel dataLabel = new JLabel("Data Preview:");
        dataLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        dataLabel.setForeground(TEXT_SECONDARY);
        dataLabel.setBounds(20, 180, 290, 20);
        resultsPanel.add(dataLabel);
        
        resultArea = new JTextArea();
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 11));
        resultArea.setBackground(BACKGROUND_PRIMARY);
        resultArea.setForeground(TEXT_PRIMARY);
        resultArea.setCaretColor(ACCENT_GOLD);
        resultArea.setEditable(false);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        
        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setBounds(20, 205, 290, 180);
        scrollPane.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 2));
        scrollPane.getViewport().setBackground(BACKGROUND_PRIMARY);
        resultsPanel.add(scrollPane);
        
        add(resultsPanel);
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
    
    private void styleComboBox(JComboBox<String> combo) {
        combo.setFont(new Font("SansSerif", Font.PLAIN, 14));
        combo.setBackground(BACKGROUND_PRIMARY);
        combo.setForeground(TEXT_PRIMARY);
        combo.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 2));
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
        loadButton.addActionListener(e -> handleLoadData());
        resetButton.addActionListener(e -> handleReset());
    }
    
    private void handleLoadData() {
        String symbol = symbolField.getText().trim();
        String startDate = startDateField.getText().trim();
        String endDate = endDateField.getText().trim();
        String dataType = (String) dataTypeCombo.getSelectedItem();
        
        if (symbol.isEmpty() || startDate.isEmpty() || endDate.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Please fill all fields", 
                "Validation Error", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (!isValidDate(startDate) || !isValidDate(endDate)) {
            JOptionPane.showMessageDialog(this, 
                "Invalid date format. Use YYYY-MM-DD", 
                "Validation Error", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            String result = jniHandler.loadDataFromCpp(symbol, startDate, endDate, dataType);
            
            String[] parts = result.split("\\|");
            if (parts.length >= 4) {
                maxPriceLabel.setText("$" + parts[0]);
                minPriceLabel.setText("$" + parts[1]);
                recordCountLabel.setText(parts[2]);
                resultArea.setText(parts[3]);
            } else {
                resultArea.setText(result);
            }
            
            JOptionPane.showMessageDialog(this, 
                "Data loaded successfully!", 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
                
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Error loading data: " + ex.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void handleReset() {
        symbolField.setText("");
        startDateField.setText("");
        endDateField.setText("");
        dataTypeCombo.setSelectedIndex(0);
        resultArea.setText("");
        maxPriceLabel.setText("--");
        minPriceLabel.setText("--");
        recordCountLabel.setText("0");
    }
    
    private boolean isValidDate(String date) {
        return date.matches("\\d{4}-\\d{2}-\\d{2}");
    }
    
    public void refreshForm() {
        // Called when switching to this panel
    }
}
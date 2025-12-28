/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
//package integration;
//
///**
// *
// * @author Hashir
// */
//public class JNIHandler {
//    
//    // Native function declare karo
//    public native void testPrint();
//
//    // DLL load karo
//    static {
//        System.loadLibrary("FinanceBackend");
//    }
//
//    // Test ke liye main method
//    public static void main(String[] args) {
//        new JNIHandler().testPrint(); // ← Yeh C++ function ko trigger karega
//    }
//}

//
//
//
//
//
//
//package integration;
//
//public class JNIHandler {
//    static {
//        // When you build your C++ native DLL and add to path, load it here:
//        // System.loadLibrary("FinanceBackendNative");
//        // For now we leave it commented.
//    }
//
//    // Example native method signature - implement this in C++ with JNI
//    // public static native String getRecommendationNative(String trend, double volatility);
//
//    // If JNI is not ready yet, you can call pure Java fallbacks.
//    public static String getRecommendation(String trend, double volatility) {
//        // fallback simple logic (mirrors RecommendationForm.simpleDecision)
//        trend = trend == null ? "" : trend.toUpperCase();
//        if (trend.contains("UP") && volatility < 2.5) return "BUY";
//        if (trend.contains("DOWN") && volatility > 1.0) return "SELL";
//        return "HOLD";
//    }
//}



//
//public class JNIHandler {
//    // Load the native library
//    static {
//        System.loadLibrary("FinanceAnalyzer"); // FinanceAnalyzer.dll/.so
//    }
//    
//    // Native method declarations for Data Module
//    public native double[] loadData(String symbol, String startDate, String endDate);
//    public native double[] getMinMaxPrices(double[] prices);
//    
//    // Native method declarations for Trend Analysis Module
//    public native double calculateSMA(double[] prices, int window);
//    public native String analyzeTrend(double[] prices);
//    public native double calculateVolatility(double[] prices);
//    public native int calculateRiskScore(double[] prices);
//    
//    // Native method declarations for AI Recommendation Module
//    public native String getRecommendation(double sma, String trend, double volatility, int riskScore);
//    public native String getRecommendationExplanation(String recommendation);
//    
//    // Method to initialize the native library
//    public native void initialize();
//    
//    // Method to cleanup resources
//    public native void cleanup();
//}




//
//package integration;
//
//public class JNIHandler {
//    
//    // Load native library
//    static {
//        try {
//            System.loadLibrary("FinanceAnalyzer");
//        } catch (UnsatisfiedLinkError e) {
//            System.err.println("Native library failed to load: " + e.getMessage());
//            System.err.println("Make sure FinanceAnalyzer.dll is in java.library.path");
//        }
//    }
//    
//    // Native method declarations
//    public native String loadDataFromCpp(String symbol, String startDate, String endDate, String dataType);
//    
//    public native String analyzeTrendFromCpp(int windowSize);
//    
//    public native String getRecommendationFromCpp(int riskAppetite);
//    
//    // Test method to verify JNI connection
//    public native String testConnection();
//    
//    // Constructor
//    public JNIHandler() {
//        // Test connection on initialization
//        try {
//            String testResult = testConnection();
//            System.out.println("JNI Connection: " + testResult);
//        } catch (UnsatisfiedLinkError e) {
//            System.err.println("JNI methods not available: " + e.getMessage());
//        }
//    }
//    
//    // Utility method to check if native library is loaded
//    public boolean isNativeLibraryLoaded() {
//        try {
//            testConnection();
//            return true;
//        } catch (UnsatisfiedLinkError e) {
//            return false;
//        }
//    }
//}


//
//package integration;
//
//public class JNIHandler {
//
//    // Load native library
//    static {
//        try {
//            System.loadLibrary("FinanceBackend"); // DLL ka naam bina .dll extension
//            System.out.println("DLL loaded successfully!");
//        } catch (UnsatisfiedLinkError e) {
//            System.err.println("Native library failed to load: " + e.getMessage());
//            System.err.println("Make sure FinanceBackend.dll is in java.library.path");
//        }
//    }
//
//    // Native method declarations
//    public native String loadDataFromCpp(String symbol, String startDate, String endDate, String dataType);
//    public native String analyzeTrendFromCpp(int windowSize);
//    public native String getRecommendationFromCpp(int riskAppetite);
//    public native String testConnection();
//
//    // Constructor
//    public JNIHandler() {
//        try {
//            String testResult = testConnection();
//            System.out.println("JNI Connection: " + testResult);
//        } catch (UnsatisfiedLinkError e) {
//            System.err.println("JNI methods not available: " + e.getMessage());
//        }
//    }
//
//    // Check if DLL loaded
//    public boolean isNativeLibraryLoaded() {
//        try {
//            testConnection();
//            return true;
//        } catch (UnsatisfiedLinkError e) {
//            return false;
//        }
//    }
//}


package integration;

public class JNIHandler {
    
    private static boolean libraryLoaded = false;
    
    // Load native library
    static {
        try {
            // Try multiple locations
            String[] possibleNames = {
                "FinanceAnalyzer",
                "libFinanceAnalyzer",
                "./FinanceAnalyzer",
                "../FinanceAnalyzer"
            };
            
            boolean loaded = false;
            for (String name : possibleNames) {
                try {
                    System.loadLibrary(name);
                    System.out.println("DLL loaded successfully with name: " + name);
                    libraryLoaded = true;
                    loaded = true;
                    break;
                } catch (UnsatisfiedLinkError e) {
                    // Try next name
                }
            }
            
            if (!loaded) {
                System.err.println("Failed to load native library");
                System.err.println("Current directory: " + System.getProperty("user.dir"));
                System.err.println("Library path: " + System.getProperty("java.library.path"));
            }
            
        } catch (Exception e) {
            System.err.println("Exception loading library: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // Native method declarations - EXACT signatures
    public native String testConnection();
    
    public native String loadDataFromCpp(String symbol, String startDate, String endDate, String dataType);
    
    public native String analyzeTrendFromCpp(int windowSize);
    
    public native String getRecommendationFromCpp(int riskAppetite);
    
    // Constructor
    public JNIHandler() {
        if (libraryLoaded) {
            try {
                String testResult = testConnection();
                System.out.println("JNI Connection Test: " + testResult);
            } catch (UnsatisfiedLinkError e) {
                System.err.println("JNI methods not properly loaded: " + e.getMessage());
                System.err.println("\nTroubleshooting steps:");
                System.err.println("1. Check if FinanceAnalyzer.dll exists");
                System.err.println("2. Verify DLL is in java.library.path");
                System.err.println("3. Check DLL architecture matches JDK (32/64 bit)");
                System.err.println("4. Use Dependency Walker to check DLL dependencies");
            }
        } else {
            System.err.println("Native library was not loaded in static block");
        }
    }
    
    // Utility method to check if native library is loaded
    public boolean isNativeLibraryLoaded() {
        return libraryLoaded;
    }
    
    // Test method with error handling
    public String safeTestConnection() {
        if (!libraryLoaded) {
            return "ERROR: Native library not loaded";
        }
        try {
            return testConnection();
        } catch (UnsatisfiedLinkError e) {
            return "ERROR: " + e.getMessage();
        }
    }
}
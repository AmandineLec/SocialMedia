package validationacquis.utils;

import java.sql.*;

public class DBManager {
    public static Connection conn = null;
    public static String user = "M2I";
    public static String password = "H3ll0M2I";
    public static String database = "Validation_Amandine";
    private static String server = "51.68.227.19";

    public static void init() {
        try {
            DBManager.conn = DriverManager.getConnection("jdbc:mysql://" + DBManager.server + 
                                                        "/" + database, user, password);
        } catch (SQLException ex) {
            System.out.println("SQLException : " + ex.getMessage());
            System.out.println("SQLState : " + ex.getSQLState());
            System.out.println("VendorError : " + ex.getErrorCode());
        }
    }
    
    public static ResultSet query(String sql) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet resultat = stmt.executeQuery(sql);
            return resultat;
        } catch (SQLException ex) {
            System.out.println("SQLException : " + ex.getMessage());
            System.out.println("SQLState : " + ex.getSQLState());
            System.out.println("VendorError : " + ex.getErrorCode());
            return null;
        }
    }
    public static PreparedStatement preparedStatement(String sql) {
        try {
            return DBManager.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); 

        } catch (SQLException ex) {
          // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return null;
    }
}   

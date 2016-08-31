package twm.pickums.model;

import twm.pickums.exceptions.DataAccessException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.Dependent;

/**
 * This low-level JDBC code represents a DBStrategy implementation specifically
 * for MySql database server. Note that all public methods are declared
 * final in respect for the Open/Closed Principle.
 * 
 * @author jlombardo
 */
@Dependent
public class MySqlDBStrategy implements DBStrategy, Serializable {

    private Connection conn;

    /** default constructor required for injectable objects */
    public MySqlDBStrategy() {
    }

    @Override
    public final void openConnection(String driverClass, String url,
            String userName, String password) throws DataAccessException {

        try {
            Class.forName(driverClass);
            conn = DriverManager.getConnection(url, userName, password);
            
        } catch(ClassNotFoundException | SQLException e) {
            throw new DataAccessException(e.getMessage(), e.getCause());
        }
//        } catch(ClassNotFoundException e1) {
//            throw new DataAccessException(e1.getMessage(), e1.getCause());
//            
//        } catch(SQLException e2) {
//            throw new DataAccessException(e2.getMessage(), e2.getCause());
//        }
    }

    @Override
    public final void closeConnection() throws DataAccessException {
        try {
            conn.close();
            
        } catch(SQLException e) {
            throw new DataAccessException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public final List<Map<String, Object>> findAllRecords(String tableName,
            int maxRecords) throws DataAccessException {

        String sql;
        if (maxRecords < 1) {
            sql = "select * from " + tableName;
        } else {
            sql = "select * from " + tableName + " limit " + maxRecords;
        }
        
        List<Map<String, Object>> recordList = new ArrayList<>();
        Statement stmt = null;

        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (rs.next()) {
                Map<String, Object> record = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    record.put(metaData.getColumnName(i), rs.getObject(i));
                }
                recordList.add(record);
            }
            
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage(),e.getCause());
            
        } finally {
            try {
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                throw new DataAccessException(e.getMessage(),e.getCause());
            } // end try
        } // end finally    
        
        return recordList;
    }

    @Override
    public final Map<String, Object> findById(String tableName, String primaryKeyFieldName,
            Object primaryKeyValue) throws DataAccessException {

        String sql = "SELECT * FROM " + tableName + " WHERE " + primaryKeyFieldName + " = ?";
        PreparedStatement stmt = null;
        final Map<String, Object> record = new HashMap();

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setObject(1, primaryKeyValue);
            ResultSet rs = stmt.executeQuery();
            final ResultSetMetaData metaData = rs.getMetaData();
            final int fields = metaData.getColumnCount();

            // Retrieve the raw data from the ResultSet and copy the values into a Map
            // with the keys being the column names of the table.
            if (rs.next()) {
                for (int i = 1; i <= fields; i++) {
                    record.put(metaData.getColumnName(i), rs.getObject(i));
                }
            }
            
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage(),e.getCause());
        } finally {
            try {
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                throw new DataAccessException(e.getMessage(),e.getCause());
            } // end try
        } // end finally

        return record;
    }

    @Override
    public final int deleteById(String tableName, String primaryKeyFieldName,
            Object primaryKeyValue) throws DataAccessException {

        String sql = "DELETE FROM " + tableName + " WHERE " + primaryKeyFieldName + " = ?";
        PreparedStatement stmt = null;
        
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setObject(1, primaryKeyValue);
            // recordsDeleted count
            return stmt.executeUpdate();
            
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage(),e.getCause());
        } finally {
            try {
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                throw new DataAccessException(e.getMessage(),e.getCause());
            } // end try
        }
    }

    /**
     * Inserts a record into a table based on a <code>List</code> of column
     * descriptors and a one-to-one mapping of an associated <code>List</code>
     * of column values.
     *
     * @param tableName - a <code>String</code> representing the table name
     * @param colDescriptors - <code>List</code> containing the column
     * descriptors
     * @param colValues - <code>List</code> containing the column values. The
     * order of these values must match the order of the column descriptors.
     * @return <code>true</code> if successfull; <code>false</code> otherwise
     * @throws DataAccessException if database access error or illegal sql
     */
    @Override
    public final boolean insertRecord(String tableName, List colDescriptors,
            List colValues) throws DataAccessException {

        PreparedStatement pstmt = null;
        int recsUpdated = 0;

		// do this in an excpetion handler so that we can depend on the
        // finally clause to close the connection
        try {
            pstmt = buildInsertStatement(conn, tableName, colDescriptors);

            final Iterator i = colValues.iterator();
            int index = 1;
            while (i.hasNext()) {
                final Object obj = i.next();
                pstmt.setObject(index++, obj);
            }
            recsUpdated = pstmt.executeUpdate();

        } catch (SQLException sqle) {
            throw new DataAccessException(sqle.getMessage(),sqle.getCause());
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage(),e.getCause());
        } finally {
            try {
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                throw new DataAccessException(e.getMessage(),e.getCause());
            } // end try
        } // end finally

        if (recsUpdated == 1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Updates one or more records in a table based on a single, matching field
     * value.
     *
     * @param tableName - a <code>String</code> representing the table name
     * @param colDescriptors - a <code>List</code> containing the column
     * descriptors for the fields that can be updated.
     * @param colValues - a <code>List</code> containing the values for the
     * fields that can be updated.
     * @param whereField - a <code>String</code> representing the field name for
     * the search criteria.
     * @param whereValue - an <code>Object</code> containing the value for the
     * search criteria.
     * @return an <code>int</code> containing the number of records updated.
     * @throws DataAccessException if database access error or illegal sql
     */
    @Override
    public final int updateRecords(String tableName, List colDescriptors, List colValues,
            String whereField, Object whereValue) throws DataAccessException {
        
        PreparedStatement pstmt = null;
        int recsUpdated = 0;

		// do this in an excpetion handler so that we can depend on the
        // finally clause to close the connection
        try {
            pstmt = buildUpdateStatement(conn, tableName, colDescriptors, whereField);

            final Iterator i = colValues.iterator();
            int index = 1;
            Object obj = null;

            // set params for column values
            while (i.hasNext()) {
                obj = i.next();
                pstmt.setObject(index++, obj);
            }
            // and finally set param for wehere value
            pstmt.setObject(index, whereValue);

            recsUpdated = pstmt.executeUpdate();

        } catch (SQLException sqle) {
            throw new DataAccessException(sqle.getMessage(),sqle.getCause());
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage(),e.getCause());
        } finally {
            try {
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                throw new DataAccessException(e.getMessage(),e.getCause());
            } // end try
        } // end finally

        return recsUpdated;
    }

    /*
     * Builds a java.sql.PreparedStatement for an sql insert
     * @param conn - a valid connection
     * @param tableName - a <code>String</code> representing the table name
     * @param colDescriptors - a <code>List</code> containing the column descriptors for
     * the fields that can be inserted.
     * @return java.sql.PreparedStatement
     * @throws DataAccessException
     */
    private PreparedStatement buildInsertStatement(Connection conn, String tableName, List colDescriptors)
            throws DataAccessException {
        StringBuffer sql = new StringBuffer("INSERT INTO ");
        (sql.append(tableName)).append(" (");
        final Iterator i = colDescriptors.iterator();
        while (i.hasNext()) {
            (sql.append((String) i.next())).append(", ");
        }
        sql = new StringBuffer((sql.toString()).substring(0, (sql.toString()).lastIndexOf(", ")) + ") VALUES (");
        for (int j = 0; j < colDescriptors.size(); j++) {
            sql.append("?, ");
        }
        final String finalSQL = (sql.toString()).substring(0, (sql.toString()).lastIndexOf(", ")) + ")";
        //System.out.println(finalSQL);
        PreparedStatement psmt = null;
        try {
            psmt = conn.prepareStatement(finalSQL);
        } catch(SQLException e) {
            throw new DataAccessException(e.getMessage(),e.getCause());
        }
        return psmt;
    }

    /*
     * Builds a java.sql.PreparedStatement for an sql update using only one where clause test
     * @param conn - a JDBC <code>Connection</code> object
     * @param tableName - a <code>String</code> representing the table name
     * @param colDescriptors - a <code>List</code> containing the column descriptors for
     * the fields that can be updated.
     * @param whereField - a <code>String</code> representing the field name for the
     * search criteria.
     * @return java.sql.PreparedStatement
     * @throws DataAccessException
     */
    private PreparedStatement buildUpdateStatement(Connection conn, String tableName,
            List colDescriptors, String whereField) throws DataAccessException {

        StringBuffer sql = new StringBuffer("UPDATE ");
        (sql.append(tableName)).append(" SET ");
        final Iterator i = colDescriptors.iterator();
        while (i.hasNext()) {
            (sql.append((String) i.next())).append(" = ?, ");
        }
        sql = new StringBuffer((sql.toString()).substring(0, (sql.toString()).lastIndexOf(", ")));
        ((sql.append(" WHERE ")).append(whereField)).append(" = ?");
        final String finalSQL = sql.toString();
        PreparedStatement psmt = null;
        try {
            psmt = conn.prepareStatement(finalSQL);
        } catch(SQLException e) {
            throw new DataAccessException(e.getMessage(),e.getCause());
        }
        return psmt;
    }
    

    // Test harness - comment out for production
    public static void main(String[] args)
            throws DataAccessException {

        DBStrategy db = new MySqlDBStrategy();
        db.openConnection("com.mysql.jdbc.Driver",
                "jdbc:mysql://localhost:3306/picks",
                "root", "admin");

        List<Map<String, Object>> rawData
                = db.findAllRecords("matches", 0);
        System.out.println(rawData);
        
        db.closeConnection();
        
//        db.openConnection("com.mysql.jdbc.Driver",
//                "jdbc:mysql://localhost:3306/book2",
//                "root", "admin");
//        
//        List<String> colNames = Arrays.asList("author_name","date_added");
//        List<Object> colValues = Arrays.asList("Lucifer","2000-02-11");
//        int result = db.updateRecords("author", colNames, colValues, "author_id", 1);
//        
//           db.closeConnection();
//
////        int result = db.deleteById("author", "author_id", 2);
//
//        db.openConnection("com.mysql.jdbc.Driver",
//                "jdbc:mysql://localhost:3306/book2",
//                "root", "admin");
//
//        rawData
//                = db.findAllRecords("author", 0);
//        System.out.println(rawData);
//
//        db.closeConnection();
//
//        System.out.println(rawData);
    }

}

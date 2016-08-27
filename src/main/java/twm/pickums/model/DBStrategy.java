package twm.pickums.model;

import twm.pickums.exceptions.DataAccessException;
import java.util.List;
import java.util.Map;

/**
 * The general contract for all db strategy classes. Create one for each
 * database server used.
 * @author jlombardo
 */
public interface DBStrategy {
    
    Map<String,Object> findById(String tableName, String primaryKeyFieldName, 
            Object primaryKeyValue) throws DataAccessException;

    
    int deleteById(String tableName, String primaryKeyFieldName, Object primaryKeyValue) throws DataAccessException;

    List<Map<String, Object>> findAllRecords(String tableName, int maxRecords) throws DataAccessException;

    /**
     * Inserts a record into a table based on a <code>List</code> of column descriptors
     * and a one-to-one mapping of an associated <code>List</code> of column values.
     *
     * @param tableName - a <code>String</code> representing the table name
     * @param colDescriptors - <code>List</code> containing the column descriptors
     * @param colValues - <code>List</code> containing the column values. The order of
     * these values must match the order of the column descriptors.
     * @return <code>true</code> if successfull; <code>false</code> otherwise
     * @throws DataAccessException if database access error or illegal sql
     */
    boolean insertRecord(String tableName, List colDescriptors, List colValues) throws DataAccessException;

    /**
     * Open a connection manually, without pooling.
     * @param driverClass
     * @param url
     * @param userName
     * @param password
     * @throws Exception
     */
    void openConnection(String driverClass, String url, String userName, String password) throws DataAccessException;
    
    /**
     * Closes a manual connection or returns connection to pool if available.
     * @throws DataAccessException 
     */
    public void closeConnection() throws DataAccessException;

    /**
     * Updates one or more records in a table based on a single, matching field value.
     *
     * @param tableName - a <code>String</code> representing the table name
     * @param colDescriptors - a <code>List</code> containing the column descriptors for
     * the fields that can be updated.
     * @param colValues - a <code>List</code> containing the values for the fields that
     * can be updated.
     * @param whereField - a <code>String</code> representing the field name for the
     * search criteria.
     * @param whereValue - an <code>Object</code> containing the value for the search criteria.
     * @return an <code>int</code> containing the number of records updated.
     * @throws DataAccessException if database access error or illegal sql
     */
    int updateRecords(String tableName, List colDescriptors, List colValues, String whereField, Object whereValue) throws DataAccessException;
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package twm.pickums.model;

import java.util.List;
import twm.pickums.exceptions.DataAccessException;

/**
 *
 * @author Taylor
 */
public interface UserDaoStrategy {
    
    List <User> getUserList() throws DataAccessException;
    
    User getUserById(Integer userId) throws DataAccessException;
    
    public void deleteUser(Integer userId) throws DataAccessException;
    
    public void saveUser(Integer userId, String username, String password, String email) throws DataAccessException;
    
    public void newUser(String username, String password, String email) throws DataAccessException;
    
    public void setDb(DBStrategy db);
    
    public void initDao(String driver, String url, String user, String password);
    
    public String getDriver();
    
    public void setDriver(String driver);
    
    public String getUrl();

    public void setUrl(String url);

    public String getUserName();

    public void setUserName(String user);

    public String getPassword();

    public void setPassword(String pwd);
}

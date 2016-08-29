/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package twm.pickums.model;

import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import twm.pickums.exceptions.DataAccessException;

/**
 *
 * @author Taylor
 */
public class UserService implements Serializable {
    
    @Inject
    private UserDaoStrategy dao;
    
    public UserService() {
        
    }
    
    public List<User> getAllUsers() throws DataAccessException {
        return dao.getUserList();
    }
    public User getUserById(String userId) throws DataAccessException {
        return dao.getUserById(Integer.parseInt(userId));
    }
    
    public void deleteUserById(String userId) throws DataAccessException {
        dao.deleteUser(Integer.parseInt(userId));
    }
    
    public void updateUser(String userId, String username, String password, String email) throws DataAccessException {
        Integer id = Integer.parseInt(userId);
        
        dao.saveUser(id, username, password, email);
    }

    public void addUser(String username, String password, String email) throws DataAccessException {
        dao.newUser(username, password, email);
    }
    public UserDaoStrategy getDao() {
    return dao;    
    }
    
    public void setDao(UserDaoStrategy dao) {
        this.dao = dao;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package twm.pickums.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import twm.pickums.exceptions.DataAccessException;

/**
 *
 * @author Taylor
 */
public class UserDao implements UserDaoStrategy, Serializable {

    @Inject
    private DBStrategy db;

    private String driver;
    private String url;
    private String user;
    private String pwd;

    public UserDao() {

    }

    @Override
    public void initDao(String driver, String url, String user, String password) {
        setDriver(driver);
        setUrl(url);
        setUserName(user);
        setPassword(password);
    }

    public DBStrategy getDb() {
        return db;
    }

    @Override
    public List<User> getUserList() throws DataAccessException {
        db.openConnection(driver, url, user, pwd);
        List<User> records = new ArrayList<>();

        List<Map<String, Object>> rawData = db.findAllRecords("users", 0);
        for (Map rawRec : rawData) {
            User user = new User();
            Object obj = rawRec.get("user_id");
            user.setUserId(Integer.parseInt(obj.toString()));

            String userName = rawRec.get("username") == null ? "" : rawRec.get("username").toString();
            user.setUserName(userName);

            String password = rawRec.get("password") == null ? "" : rawRec.get("password").toString();
            user.setPassword(password);

            String email = rawRec.get("email") == null ? "" : rawRec.get("email").toString();
            user.setEmail(email);
            records.add(user);
        }
        return records;
    }

    @Override
    public User getUserById(Integer userId) throws DataAccessException {
        db.openConnection(driver, url, user, pwd);

        Map<String, Object> rawRec = db.findById("users", "user_id", userId);
        User user = new User();
        user.setUserId((Integer) rawRec.get("user_id"));
        user.setUserName(rawRec.get("username").toString());
        user.setPassword(rawRec.get("password").toString());
        user.setEmail(rawRec.get("email").toString());

        return user;
    }

    @Override
    public void deleteUser(Integer userId) throws DataAccessException {
        db.openConnection(driver, url, user, pwd);
        db.deleteById("users", "user_id", userId);
    }

    @Override
    public void saveUser(Integer userId, String username, String password, String email) throws DataAccessException {
        db.openConnection(driver, url, user, pwd);

        // must be an update of an existing record
        db.updateRecords("users", Arrays.asList("username", "password", "email"),
                Arrays.asList(username, password, email),
                "user_id", userId);

    }

    @Override
    public void newUser(String username, String password, String email) throws DataAccessException {
        db.openConnection(driver, url, user, pwd);

        db.insertRecord("users", Arrays.asList("username", "password", "email"),
                Arrays.asList(username, password, email));
    }

    @Override
    public void setDb(DBStrategy db
    ) {
        this.db = db;
    }

    @Override
    public String getDriver() {
        return driver;
    }

    @Override
    public void setDriver(String driver
    ) {
        this.driver = driver;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public void setUrl(String url
    ) {
        this.url = url;
    }

    @Override
    public String getUserName() {
        return user;
    }

    @Override
    public void setUserName(String user
    ) {
        this.user = user;
    }

    @Override
    public String getPassword() {
        return pwd;
    }

    @Override
    public void setPassword(String pwd
    ) {
        this.pwd = pwd;
    }

}

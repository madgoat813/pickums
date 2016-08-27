/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package twm.pickums.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import twm.pickums.exceptions.DataAccessException;

/**
 *
 * @author Taylor
 */
public class TeamDao implements TeamDaoStrategy, Serializable {

    @Inject
    private DBStrategy db;

    private String driver;
    private String url;
    private String user;
    private String pwd;

    public TeamDao() {

    }

    @Override
    public void initDao(String driver, String url, String user, String password) {
        setDriver(driver);
        setUrl(url);
        setUser(user);
        setPwd(password);
    }
    
    public DBStrategy getDb() {
        return db;
    }

    @Override
    public List<Team> getTeamList() throws DataAccessException {
        db.openConnection(driver, url, user, pwd);
        List<Team> records = new ArrayList<>();
        
        List<Map<String, Object>> rawData = db.findAllRecords("teams", 0);
        for(Map rawRec : rawData) {
            Team team = new Team();
            Object obj = rawRec.get("team_id");
            team.setTeamId(Integer.parseInt(obj.toString()));
            
            String name = rawRec.get("team_name") == null ? "" : rawRec.get("team_name").toString();
            team.setTeamName(name);
            
            String city = rawRec.get("team_city") == null ? "" : rawRec.get("team_city").toString();
            team.setTeamCity(city);
            records.add(team);
        }
        return records;
    }

    @Override
    public void setDb(DBStrategy db) {
        this.db = db;
    }

    @Override
    public String getDriver() {
        return driver;
    }

    @Override
    public void setDriver(String driver) {
        this.driver = driver;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String getUser() {
        return user;
    }

    @Override
    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public String getPwd() {
        return pwd;
    }

    @Override
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

}

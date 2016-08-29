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
public interface TeamDaoStrategy {
    
    List <Team> getTeamList() throws DataAccessException;
    
    Team getTeamById(Integer teamId) throws DataAccessException;
    
    public void deleteTeam(Integer teamId) throws DataAccessException;
    
    public void saveTeam(Integer teamId, String teamName, String teamCity) throws DataAccessException;
    
    public void newTeam(String teamName, String teamCity) throws DataAccessException;
    
    public void setDb(DBStrategy db);
    
    public void initDao(String driver, String url, String user, String password);
    
    public String getDriver();
    
    public void setDriver(String driver);
    
    public String getUrl();

    public void setUrl(String url);

    public String getUser();

    public void setUser(String user);

    public String getPwd();

    public void setPwd(String pwd);
}


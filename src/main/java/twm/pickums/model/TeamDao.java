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
        for (Map rawRec : rawData) {
            Team team = new Team();
            Object obj = rawRec.get("team_id");
            team.setTeamId(Integer.parseInt(obj.toString()));

            String name = rawRec.get("team_name") == null ? "" : rawRec.get("team_name").toString();
            team.setTeamName(name);

            String city = rawRec.get("team_city") == null ? "" : rawRec.get("team_city").toString();
            team.setTeamCity(city);
            
            
            Object match = rawRec.get("match_no");
            team.setMatchNo(Integer.parseInt(match.toString()));
            records.add(team);
        }
        return records;
    }

    @Override
    public Team getTeamById(Integer teamId) throws DataAccessException {
        db.openConnection(driver, url, user, pwd);

        Map<String, Object> rawRec = db.findById("teams", "team_id", teamId);
        Team team = new Team();
        team.setTeamId((Integer) rawRec.get("team_id"));
        team.setTeamName(rawRec.get("team_name").toString());
        team.setTeamCity(rawRec.get("team_city").toString());
        team.setMatchNo((Integer) rawRec.get("match_no"));
        

        return team;
    }

    @Override
    public void deleteTeam(Integer teamId) throws DataAccessException {
        db.openConnection(driver, url, user, pwd);
        db.deleteById("teams", "team_id", teamId);
    }

    @Override
    public void saveTeam(Integer teamId, String teamName, String teamCity) throws DataAccessException {
        db.openConnection(driver, url, user, pwd);

        // must be an update of an existing record
        db.updateRecords("teams", Arrays.asList("team_name", "team_city"),
                Arrays.asList(teamName, teamCity),
                "team_id", teamId);

    }

    @Override
    public void newTeam(String teamName, String teamCity) throws DataAccessException {
        db.openConnection(driver, url, user, pwd);

        db.insertRecord("teams", Arrays.asList("team_name", "team_city"),
                Arrays.asList(teamName, teamCity));
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

    
    public static void main(String[] args) throws DataAccessException {
        
        
        
        TeamDaoStrategy dao = new TeamDao();
        List<Team> teams = dao.getTeamList();
        System.out.println(teams);
    }
    
    
}






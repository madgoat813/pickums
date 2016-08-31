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
public class MatchDao implements MatchDaoStrategy, Serializable {

    @Inject
    private DBStrategy db;

    private String driver;
    private String url;
    private String user;
    private String pwd;

    public MatchDao() {

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
    public List<Match> getMatchList() throws DataAccessException {
        db.openConnection(driver, url, user, pwd);
        List<Match> records = new ArrayList<>();

        List<Map<String, Object>> rawData = db.findAllRecords("matches", 0);
        for (Map rawRec : rawData) {
            Match match = new Match();
            Object obj = rawRec.get("match_id");
            match.setMatchId(Integer.parseInt(obj.toString()));

            String home = rawRec.get("home_team") == null ? "" : rawRec.get("home_team").toString();
            match.setHomeTeam(home);

            String away = rawRec.get("away_team") == null ? "" : rawRec.get("away_team").toString();
            match.setAwayTeam(away);
//            
            records.add(match);
        }
        return records;
    }

    @Override
    public Match getMatchById(Integer matchId) throws DataAccessException {
        db.openConnection(driver, url, user, pwd);

        Map<String, Object> rawRec = db.findById("matches", "match_id", matchId);
        Match match = new Match();
        match.setMatchId((Integer) rawRec.get("match_id"));
        match.setHomeTeam(rawRec.get("home_team").toString());
        match.setAwayTeam(rawRec.get("away_team").toString());

        return match;
    }

    @Override
    public void deleteMatch(Integer matchId) throws DataAccessException {
        db.openConnection(driver, url, user, pwd);
        db.deleteById("matches", "match_id", matchId);
    }

    @Override
    public void saveMatch(Integer matchId, String homeTeam, String awayTeam) throws DataAccessException {
        db.openConnection(driver, url, user, pwd);

        // must be an update of an existing record
        db.updateRecords("matches", Arrays.asList("home_team", "away_team"),
                Arrays.asList(homeTeam, awayTeam),
                "match_id", matchId);

    }

    @Override
    public void newMatch(Integer matchId, String homeTeam, String awayTeam) throws DataAccessException {
        db.openConnection(driver, url, user, pwd);
        
        db.insertRecord("matches", Arrays.asList("match_id", "home_team", "away_team"),
                Arrays.asList(matchId, homeTeam, awayTeam));
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

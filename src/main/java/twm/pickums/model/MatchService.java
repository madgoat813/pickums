/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package twm.pickums.model;

import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import twm.pickums.exceptions.DataAccessException;

/**
 *
 * @author Taylor
 */
@SessionScoped
public class MatchService implements Serializable{
    
    @Inject
    private MatchDaoStrategy dao;
    
    public MatchService() {
        
    }
    public List<Match> getAllMatches() throws DataAccessException {
        return dao.getMatchList();
    }

    public Match getMatchById(String matchId) throws DataAccessException {
        return dao.getMatchById(Integer.parseInt(matchId));
    }

    public void deleteMatchById(String matchId) throws DataAccessException {
        dao.deleteMatch(Integer.parseInt(matchId));
    }

    public void updateMatch(String matchId, String homeTeam, String awayTeam) throws DataAccessException {
        Integer id = Integer.parseInt(matchId);
        

        dao.saveMatch(id, homeTeam, awayTeam);
    }

    public void addMatch(String matchId, String homeTeam, String awayTeam) throws DataAccessException {
        Integer id = Integer.parseInt(matchId);
        
        dao.newMatch(id, homeTeam, awayTeam);
    }

    public MatchDaoStrategy getDao() {
        return dao;
    }

    public void setDao(MatchDaoStrategy dao) {
        this.dao = dao;
    }
}

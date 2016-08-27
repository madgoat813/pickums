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
public class TeamService implements Serializable { 
    
    @Inject
    private TeamDaoStrategy dao;
    
    public TeamService() {
        
    }
    
    public List<Team> getAllTeams() throws DataAccessException {
        return dao.getTeamList();
    }
    
    public TeamDaoStrategy getDao() {
        return dao;
    }
    
    public void setDao(TeamDaoStrategy dao) {
        this.dao = dao;
    }
}

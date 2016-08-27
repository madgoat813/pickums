/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package twm.pickums.model;

import java.util.Objects;

/**
 *
 * @author Taylor
 */
public class Team {
    private Integer teamId;
    private String teamName;
    private String teamCity;
    
    public Team() {
        
    }

    public Team(Integer teamId, String teamName, String teamCity) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.teamCity = teamCity;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamCity() {
        return teamCity;
    }

    public void setTeamCity(String teamCity) {
        this.teamCity = teamCity;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.teamId);
        hash = 23 * hash + Objects.hashCode(this.teamName);
        hash = 23 * hash + Objects.hashCode(this.teamCity);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Team other = (Team) obj;
        if (!Objects.equals(this.teamName, other.teamName)) {
            return false;
        }
        if (!Objects.equals(this.teamCity, other.teamCity)) {
            return false;
        }
        if (!Objects.equals(this.teamId, other.teamId)) {
            return false;
        }
        return true;
    }
    
    
}

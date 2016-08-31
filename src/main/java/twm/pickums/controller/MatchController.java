/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package twm.pickums.controller;

import java.io.IOException;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import twm.pickums.model.Match;
import twm.pickums.model.MatchService;
import twm.pickums.model.Team;
import twm.pickums.model.TeamService;

/**
 *
 * @author Taylor
 */
@WebServlet(name = "MatchController", urlPatterns = {"/MatchController"})
public class MatchController extends HttpServlet {

    private static final String LIST_PAGE = "/listMatchUp.jsp";
    private static final String ADD_PAGE = "/addMatchUp.jsp";
    private static final String EDIT_PAGE = "/editMatchUp.jsp";

    private static final String LIST_ACTION = "list";
    private static final String CRUD_ACTION = "crud";
    private static final String SAVE_ACTION = "save";
    private static final String EDIT_ACTION = "edit";
    private static final String ADD_ACTION = "add";
    private static final String DELETE_ACTION = "delete";
    private static final String CANCEL_ACTION = "cancel";

    private static final String ACTION_PARAM = "action";
    private static final String SUBMIT_ACTION = "submit";

    private static final String MATCH_ID = "matchId";
    private static final String HOME_TEAM = "homeTeam";
    private static final String AWAY_TEAM = "awayTeam";

// db config init params from web.xml
    private String driverClass;
    private String url;
    private String userName;
    private String password;

    @Inject
    private MatchService matchService;
    

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String destination = LIST_PAGE;
        String action = request.getParameter(ACTION_PARAM);
        Match match = null;
        try {
            configDbConnection();
            
            switch (action) {
                case LIST_ACTION:
                    this.refreshList(request, matchService);
                    destination = LIST_PAGE;
                    break;

                case CRUD_ACTION:
                    String subAction = request.getParameter(SUBMIT_ACTION);
                    switch (subAction) {
                        case DELETE_ACTION:
                            String[] matchIds = request.getParameterValues(MATCH_ID);
                            for (String id : matchIds) {
                                matchService.deleteMatchById(id);
                            }
                            this.refreshList(request, matchService);
                            destination = LIST_PAGE;
                            break;
                        case ADD_ACTION:
                            this.refreshList(request, matchService);
                            destination = ADD_PAGE;
                            break;
                        case EDIT_ACTION:
                            String matchId = request.getParameter(MATCH_ID);

                            match = matchService.getMatchById(matchId);
                            request.setAttribute("match", match);
                            destination = EDIT_PAGE;
                            break;
                    }
                    break;
                case SAVE_ACTION:
                    String tId = request.getParameter(MATCH_ID);
                    String tHome = request.getParameter(HOME_TEAM);
                    String tAway = request.getParameter(AWAY_TEAM);

                    matchService.updateMatch(tId, tHome, tAway);
                    this.refreshList(request, matchService);
                    destination = LIST_PAGE;
                    break;
                case ADD_ACTION:
                    String id = request.getParameter("matchId");
                    String homeTeam = request.getParameter("t1");
                    String awayTeam = request.getParameter("t2");

                    matchService.addMatch(id, homeTeam, awayTeam);
                    this.refreshList(request, matchService);
                    destination = LIST_PAGE;
                    break;
                case CANCEL_ACTION:
                    this.refreshList(request, matchService);
                    destination = LIST_PAGE;
                    break;
            }

        } catch (Exception e) {

        } finally {
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(destination);

            dispatcher.forward(request, response);
        }
    }
    // Avoid D-R-Y

    private void refreshList(HttpServletRequest request, MatchService matchService) throws Exception {
        List<Match> match = matchService.getAllMatches();
        request.setAttribute("match", match);
        
    }
    
    private void configDbConnection() {
        matchService.getDao().initDao(driverClass, url, userName, password);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    @Override
    public void init() throws ServletException {
        // Get init params from web.xml
        driverClass = getServletContext().getInitParameter("db.driver.class");
        url = getServletContext().getInitParameter("db.url");
        userName = getServletContext().getInitParameter("db.username");
        password = getServletContext().getInitParameter("db.password");
    }
}

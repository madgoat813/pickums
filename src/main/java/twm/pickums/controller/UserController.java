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
import javax.servlet.http.HttpSession;
import twm.pickums.model.User;
import twm.pickums.model.UserService;

/**
 *
 * @author Taylor
 */
@WebServlet(name = "UserController", urlPatterns = {"/UserController"})
public class UserController extends HttpServlet {
    
    private static final String LOGIN_PAGE = "/index.html";
    private static final String HOME_PAGE = "/homePage.jsp";
    private static final String LIST_PAGE = "/listUsers.jsp";
    private static final String ADD_PAGE = "/addUser.jsp";
    private static final String EDIT_PAGE = "/editUser.jsp";
    

    private static final String LIST_ACTION = "list";
    private static final String LOGIN_ACTION = "login";
    private static final String LOGOUT_ACTION = "logout";
    private static final String CRUD_ACTION = "crud";
    private static final String SAVE_ACTION = "save";
    private static final String EDIT_ACTION = "edit";
    private static final String ADD_ACTION = "add";
    private static final String DELETE_ACTION = "delete";
    private static final String CANCEL_ACTION = "cancel";

    private static final String ACTION_PARAM = "action";
    private static final String SUBMIT_ACTION = "submit";
    
    private static final String USER_ID = "userId";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String EMAIL = "email";
    

    // db config init params from web.xml
    private String driverClass;
    private String url;
    private String userName;
    private String password;

    @Inject
    private UserService userService;

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

        HttpSession session = request.getSession();
        String destination = LIST_PAGE;
        String action = request.getParameter(ACTION_PARAM);
        
        User user = null;

        try {
            configDbConnection();
            switch (action) {
                case LIST_ACTION:
                    this.refreshList(request, userService);
                    destination = LIST_PAGE;
                    break;

                case LOGIN_ACTION:
                    String sessionName = request.getParameter("userLogin");
                    session.setAttribute("userLogin", sessionName);
                    
                    this.refreshList(request, userService);
                    destination = HOME_PAGE;
                    break;
                
                case LOGOUT_ACTION:
                    session.invalidate();
                    this.refreshList(request, userService);
                    destination = LOGIN_PAGE;
                    break;
            case CRUD_ACTION:
                    String subAction = request.getParameter(SUBMIT_ACTION);
                    switch (subAction) {
                        case DELETE_ACTION:
                            String[] userIds = request.getParameterValues(USER_ID);
                            for (String id : userIds) {
                                userService.deleteUserById(id);
                                
                            }
                            this.refreshList(request, userService);
                            destination = LIST_PAGE;
                            break;
                        case ADD_ACTION:
                            destination = ADD_PAGE;
                            break;
                        case EDIT_ACTION:
                            String userId = request.getParameter(USER_ID);
                            
                            user = userService.getUserById(userId);
                            request.setAttribute("user", user);
                            destination = EDIT_PAGE;
                            break;

                    }
                    break;
                case SAVE_ACTION:
                    String uId = request.getParameter(USER_ID);
                    String uName = request.getParameter(USERNAME);
                    String uPass = request.getParameter(PASSWORD);
                    String uEmail = request.getParameter(EMAIL);
                    
                    userService.updateUser(uId, uName, uPass, uEmail);
                    this.refreshList(request, userService);
                    destination = LIST_PAGE;
                    break;
                case ADD_ACTION:
                    String useName = request.getParameter(USERNAME);
                    String usePass = request.getParameter(PASSWORD);
                    String useEmail = request.getParameter(EMAIL);
                    
                    
                    userService.addUser(useName, usePass, useEmail);
                    this.refreshList(request, userService);
                    destination = LIST_PAGE;
                    break;
                case CANCEL_ACTION:
                    this.refreshList(request, userService);
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

    private void refreshList(HttpServletRequest request, UserService userService) throws Exception {
        List<User> users = userService.getAllUsers();
        request.setAttribute("users", users);
    }

    private void configDbConnection() {
        userService.getDao().initDao(driverClass, url, userName, password);
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

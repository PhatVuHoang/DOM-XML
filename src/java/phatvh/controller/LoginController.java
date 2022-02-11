/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phatvh.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import phatvh.utils.XMLUtils;

/**
 *
 * @author reymy
 */
@WebServlet(name = "LoginController", urlPatterns = {"/LoginController"})
public class LoginController extends HttpServlet {

    private static final String xmlFile = "/WEB-INF/studentAccounts.xml";
    private String fullName;
    private boolean found;
    private static final String ERROR = "error.jsp";
    private static final String SUCCESS = "search.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            String username = request.getParameter("txtUsername");
            String password = request.getParameter("txtPassword");
            fullName = "";
            found = false;
            String realPath = getServletContext().getRealPath("/");
            String filePath = realPath + xmlFile;
            Document doc = XMLUtils.parseFileToDom(filePath);
            checkLogin(username, password, doc);
            if (found) {
                url = SUCCESS;
                HttpSession session = request.getSession();
                session.setAttribute("FULLNAME", fullName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    private void checkLogin(String username, String password, Node node) {
        if (node == null) {
            return;
        }
        if (node.getNodeName().equals("student")) {
            String id = node.getAttributes().getNamedItem("id").getNodeValue();
            if (id.equals(username)) {
                NodeList childrenOfStudent = node.getChildNodes();
                for (int i = 0; i < childrenOfStudent.getLength(); i++) {
                    Node tmp = childrenOfStudent.item(i);
                    if (tmp.getNodeName().equals("lastname")) {
                        fullName = tmp.getTextContent().trim();
                    } else if (tmp.getNodeName().equals("middlename")) {
                        fullName = fullName + " " + tmp.getTextContent().trim();
                    } else if (tmp.getNodeName().equals("firstname")) {
                        fullName = fullName + " " + tmp.getTextContent().trim();
                    } else if (tmp.getNodeName().equals("password")) {
                        String pword = tmp.getTextContent().trim();
                        if (!pword.equals(password)) {
                            break;
                        }
                    } else if (tmp.getNodeName().equals("status")) {
                        String status = tmp.getTextContent().trim();
                        if (!status.equals("dropout")) {
                            found = true;
                            return;
                        }
                    }
                }
            }
        } // if student
        NodeList children = node.getChildNodes();
        int count = 0;
        while (count < children.getLength()) {
            checkLogin(username, password, children.item(count++));
        }
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

}

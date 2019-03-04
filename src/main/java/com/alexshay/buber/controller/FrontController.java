package com.alexshay.buber.controller;

import com.alexshay.buber.command.Command;
import com.alexshay.buber.command.CommandProvider;
import com.alexshay.buber.command.Router;
import com.alexshay.buber.dto.ResponseContent;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Buber", urlPatterns = "/buber")
public class FrontController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Command command = CommandProvider.getInstance().takeCommand(request.getParameter("command"));
        ResponseContent responseContent = command.execute(request);

        if (responseContent.getRouter().getType().equals(Router.Type.FORWARD)) {
            request.getRequestDispatcher(responseContent.getRouter().getRoute()).forward(request, response);
        } else {
            response.sendRedirect(responseContent.getRouter().getRoute());
        }


    }
}

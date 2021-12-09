package ua.lyubchenko.servlets;


import lombok.SneakyThrows;
import ua.lyubchenko.services.CompanyService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



@WebServlet("/companies/*")
public class CompanyServlet extends HttpServlet {
    private final CompanyService companyService = new CompanyService();

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)  {
        String requestURI = req.getRequestURI();
        if (requestURI.contains("/createCompany")) {
            req.getRequestDispatcher("/WEB-INF/views/companyViews/create.jsp").forward(req, resp);
            return;
        } else if (requestURI.contains("/updateCompany")) {
            companyService.getUpdatePage(req, resp);
            return;
        } else if (requestURI.contains("/about")) {
            companyService.getAboutPage(req, resp);
            return;
        } else if (requestURI.contains("/addDeveloper")) {
            companyService.getAddDeveloperPage(req, resp);
            return;
        } else if (requestURI.contains("/addProject")) {
            companyService.getAddProjectPage(req, resp);
            return;
        } else if (requestURI.contains("/deleteProject")) {
            companyService.deleteProject(req, resp);
            return;
        } else if (requestURI.contains("/deleteDeveloper")) {
            companyService.deleteDeveloper(req, resp);
            return;
        } else if (requestURI.contains("/remove")) {
            companyService.remove(req, resp);
            return;
        }
        companyService.read(req, resp);

    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)  {
        String requestURI = req.getRequestURI();
        if (requestURI.contains("/createCompany")) {
            companyService.create(req, resp);

        } else if (requestURI.contains("/updateCompany")) {
            companyService.update(req, resp);

        } else if (requestURI.contains("/addDeveloper")) {
            companyService.addDeveloper(req, resp);

        } else if (requestURI.contains("/addProject")) {
            companyService.addProject(req, resp);
        }
    }

}



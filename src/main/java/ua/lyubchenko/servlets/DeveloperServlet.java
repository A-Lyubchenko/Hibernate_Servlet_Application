package ua.lyubchenko.servlets;

import lombok.SneakyThrows;
import ua.lyubchenko.services.DeveloperService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/developers/*")
public class DeveloperServlet extends HttpServlet {
    private final DeveloperService developerService = new DeveloperService();

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String requestURI = req.getRequestURI();
        if (requestURI.contains("/createDeveloper")) {
            developerService.getCreateDeveloperPage(req, resp);
            return;

        } else if (requestURI.contains("/updateDeveloper")) {
            req.setAttribute("updateId", req.getParameter("updateId"));
            req.getRequestDispatcher("/WEB-INF/views/developerViews/update.jsp").forward(req, resp);
            return;
        } else if (requestURI.contains("/deleteProject")) {
            developerService.deleteProject(req, resp);
            return;
        } else if (requestURI.contains("/deleteCompany")) {
            developerService.deleteCompany(req, resp);
            return;
        } else if (requestURI.contains("/deleteSkill")) {
            developerService.deleteSkill(req, resp);
            return;
        } else if (requestURI.contains("/addProject")) {
            developerService.getAddProjectPage(req, resp);
            return;
        } else if (requestURI.contains("/addCompany")) {
            developerService.getAddCompanyPage(req, resp);
            return;
        } else if (requestURI.contains("/addSkill")) {
            developerService.getAddSkillPage(req, resp);
            return;

        } else if (requestURI.contains("/about")) {
            developerService.getAboutPage(req, resp);
            return;
        } else if (requestURI.contains("/remove")) {
            developerService.remove(req, resp);
            return;
        }
        developerService.read(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String requestURI = req.getRequestURI();
        if (requestURI.contains("/createDeveloper")) {
            developerService.create(req, resp);

        } else if (requestURI.contains("/updateDeveloper")) {
            developerService.update(req, resp);

        } else if (requestURI.contains("/addProject")) {
            developerService.addProject(req, resp);

        } else if (requestURI.contains("/addCompany")) {
            developerService.addCompany(req, resp);

        } else if (requestURI.contains("/addSkill")) {
            developerService.addSkill(req, resp);
        }
    }
}

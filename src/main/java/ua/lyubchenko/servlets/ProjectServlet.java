package ua.lyubchenko.servlets;

import lombok.SneakyThrows;
import ua.lyubchenko.services.ProjectService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/projects/*")
public class ProjectServlet extends HttpServlet {
    private final ProjectService projectService = new ProjectService();

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String requestURI = req.getRequestURI();
        if (requestURI.contains("/createProject")) {
            projectService.getCreatePage(req, resp);
            return;
        } else if (requestURI.contains("/updateProject")) {
            req.setAttribute("updateId", req.getParameter("updateId"));
            req.getRequestDispatcher("/WEB-INF/views/projectViews/update.jsp").forward(req, resp);
            return;
        } else if (requestURI.contains("/about")) {
            projectService.getAboutPage(req, resp);
            return;
        } else if (requestURI.contains("/remove")) {
            projectService.remove(req, resp);
            return;
        }
        projectService.read(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String requestURI = req.getRequestURI();
        if (requestURI.contains("/createProject")) {
            projectService.create(req, resp);

        } else if (requestURI.contains("/updateProject")) {
            projectService.update(req, resp);
        }
    }
}

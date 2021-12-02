package ua.lyubchenko.servlets;

import ua.lyubchenko.domains.Developer;
import ua.lyubchenko.domains.Project;
import ua.lyubchenko.repositories.EntityRepository;
import ua.lyubchenko.repositories.ICrud;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;

@WebServlet("/projects/*")
public class ProjectServlet extends HttpServlet {
    private final ICrud<Project> projectRepository= new EntityRepository<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestURI = req.getRequestURI();
        if (requestURI.contains("/createProject")) {
            req.getRequestDispatcher("/WEB-INF/views/projectViews/create.jsp").forward(req, resp);
            return;
        } else if (requestURI.contains("/updateProject")) {
            req.setAttribute("updateId", req.getParameter("updateId"));
            req.getRequestDispatcher("/WEB-INF/views/projectViews/update.jsp").forward(req, resp);
            return;
        }
        String deleteId = req.getParameter("deleteId");
        if (deleteId != null) {
            Project project = new Project();
            project.setId(Long.parseLong(deleteId));
            projectRepository.delete(project);
            resp.sendRedirect("/projects");

        } else {
            req.setAttribute("projects", projectRepository.read(Project.class));
            req.getRequestDispatcher("/WEB-INF/views/projectViews/read.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestURI = req.getRequestURI();
        if (requestURI.contains("/createProject")) {
            String name = req.getParameter("name");
            String start = req.getParameter("start");
            String coast = req.getParameter("coast");
            if (name == null  || name.matches("\\d+") || name.equals("")  || start.equals("")
                    || !start.matches("\\d{4}-\\d{2}-\\d{2}") || !coast.matches("\\d{3,8}")) {
                resp.sendRedirect("/projects/createProject");
                return;

            }
            Project project = new Project();
            project.setName(name);
            project.setStart(Date.valueOf(start));
            project.setCoast(Integer.parseInt(coast));

            projectRepository.create(project);
            req.getSession().setAttribute("project", project);
            resp.sendRedirect("/projects");

        } else if (requestURI.contains("/updateProject")) {
            String name = req.getParameter("name");
            String start = req.getParameter("start");
            String coast = req.getParameter("coast");
            if (name == null || name.matches("\\d+") || name.equals("") || start.equals("")
                    || !start.matches("\\d{4}-\\d{2}-\\d{2}") || !coast.matches("\\d{3,8}")) {
                resp.sendRedirect("/projects/updateProject");
                return;

            }
            Project project = new Project(Long.parseLong(req.getParameter("updateId")), name, Date.valueOf(start), Integer.parseInt(coast));
            projectRepository.update(project);
            req.getSession().setAttribute("project", project);
            resp.sendRedirect("/projects");
        }
    }
}

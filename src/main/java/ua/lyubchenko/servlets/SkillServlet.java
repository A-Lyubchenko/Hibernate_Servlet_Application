package ua.lyubchenko.servlets;

import lombok.SneakyThrows;
import ua.lyubchenko.services.SkillService;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/skills/*")
public class SkillServlet extends HttpServlet {

    private final SkillService skillService = new SkillService();

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)  {
        String requestURI = req.getRequestURI();
        if (requestURI.contains("/createSkill")) {
            req.getRequestDispatcher("/WEB-INF/views/skillViews/create.jsp").forward(req, resp);
            return;
        } else if (requestURI.contains("/updateSkill")) {
            req.setAttribute("updateId", req.getParameter("updateId"));
            req.getRequestDispatcher("/WEB-INF/views/skillViews/update.jsp").forward(req, resp);
            return;
        } else if (requestURI.contains("/about")) {
            skillService.getAboutPage(req,resp);
            return;
        }
        else if (requestURI.contains("/remove")) {
            skillService.remove(req, resp);
            return;
        }
        skillService.read(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)  {
        String requestURI = req.getRequestURI();
        if (requestURI.contains("/createSkill")) {
            skillService.create(req, resp);

        } else if (requestURI.contains("/updateSkill")) {
            skillService.update(req, resp);
        }
    }
}


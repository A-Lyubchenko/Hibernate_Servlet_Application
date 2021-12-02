package ua.lyubchenko.servlets;

import ua.lyubchenko.domains.Skill;
import ua.lyubchenko.repositories.EntityRepository;
import ua.lyubchenko.repositories.ICrud;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/skills/*")
public class SkillServlet extends HttpServlet {
    private final ICrud<Skill> skillRepository= new EntityRepository<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestURI = req.getRequestURI();
        if (requestURI.contains("/createSkill")) {
            req.getRequestDispatcher("/WEB-INF/views/skillViews/create.jsp").forward(req, resp);
            return;
        } else if (requestURI.contains("/updateSkill")) {
            req.setAttribute("updateId", req.getParameter("updateId"));
            req.getRequestDispatcher("/WEB-INF/views/skillViews/update.jsp").forward(req, resp);
            return;
        }
        String deleteId = req.getParameter("deleteId");
        if (deleteId != null) {
            Skill skill = new Skill();
            skill.setId(Long.parseLong(deleteId));
            skillRepository.delete(skill);
            resp.sendRedirect("/skills");
            return;
        }
        req.setAttribute("skills", skillRepository.read(Skill.class));
        req.getRequestDispatcher("/WEB-INF/views/skillViews/read.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestURI = req.getRequestURI();
        if (requestURI.contains("/createSkill")) {
            String department = req.getParameter("department");
            String level = req.getParameter("level");
            if (department == null || department.equals("")
                    || department.matches("\\d+") || level==null || level.equals("") || level.matches("\\d+")) {
                resp.sendRedirect("/skills/createSkill");
                return;

            }
            Skill skill = new Skill();
            skill.setDepartment(department);
            skill.setLevel(level);

            skillRepository.create(skill);
            req.getSession().setAttribute("skill", skill);
            resp.sendRedirect("/skills");

        } else if (requestURI.contains("/updateSkill")) {
            String department = req.getParameter("department");
            String level = req.getParameter("level");
            if (department == null || department.equals("")
                    || department.matches("\\d+") || level==null || level.equals("") || level.matches("\\d+")) {
                resp.sendRedirect("/skills/updateSkill");
                return;

            }
            Skill skill = new Skill(Long.parseLong(req.getParameter("updateId")), department, level);

            skillRepository.update(skill);
            req.getSession().setAttribute("skill", skill);
            resp.sendRedirect("/skills");
        }
    }
}


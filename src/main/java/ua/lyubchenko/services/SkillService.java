package ua.lyubchenko.services;

import lombok.SneakyThrows;
import ua.lyubchenko.domains.Skill;
import ua.lyubchenko.repositories.EntityRepository;
import ua.lyubchenko.repositories.ICrud;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SkillService {
    private final ICrud<Skill> skillRepository= new EntityRepository<>();

    @SneakyThrows
    public void getAboutPage(HttpServletRequest req, HttpServletResponse resp) {
        Skill skill = skillRepository.getById(Skill.class, Long.parseLong(req.getParameter("aboutId")));
        req.setAttribute("skill", skill);
        req.setAttribute("developers", skill.getDevelopers());
        req.getRequestDispatcher("/WEB-INF/views/skillViews/about.jsp").forward(req, resp);
    }

    public void remove(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Skill skill = new Skill();
        skill.setId(Long.parseLong(req.getParameter("deleteId")));
        skillRepository.delete(skill);
        resp.sendRedirect("/skills");
    }

    @SneakyThrows
    public void read(HttpServletRequest req, HttpServletResponse resp) {
        req.setAttribute("skills", skillRepository.read(Skill.class));
        req.getRequestDispatcher("/WEB-INF/views/skillViews/read.jsp").forward(req, resp);
    }

    @SneakyThrows
    public void create(HttpServletRequest req, HttpServletResponse resp) {
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
    }

    @SneakyThrows
    public void update(HttpServletRequest req, HttpServletResponse resp) {
        String department = req.getParameter("department");
        String level = req.getParameter("level");
        if (department == null || department.equals("")
                || department.matches("\\d+") || level==null || level.equals("") || level.matches("\\d+")) {
            resp.sendRedirect("/skills/updateSkill?updateId=" + req.getParameter("updateId"));
            return;

        }
        Skill skill = skillRepository.getById(Skill.class, Long.parseLong(req.getParameter("updateId")));
        skill.setId(Long.parseLong(req.getParameter("updateId")));
        skill.setDepartment(department);
        skill.setLevel(level);

        skillRepository.update(skill);
        req.getSession().setAttribute("skill", skill);
        resp.sendRedirect("/skills");
    }
}

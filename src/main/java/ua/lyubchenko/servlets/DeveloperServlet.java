package ua.lyubchenko.servlets;

import org.hibernate.Session;
import ua.lyubchenko.connection.ApplicationConnection;
import ua.lyubchenko.domains.Company;
import ua.lyubchenko.domains.Developer;
import ua.lyubchenko.domains.Project;
import ua.lyubchenko.domains.Skill;
import ua.lyubchenko.repositories.EntityRepository;
import ua.lyubchenko.repositories.ICrud;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@WebServlet("/developers/*")
public class DeveloperServlet extends HttpServlet {
    private final ICrud<Company> companyRepository = new EntityRepository<>();
    private final ICrud<Developer> developerRepository= new EntityRepository<>();
    private final ICrud<Project> projectRepository = new EntityRepository<>();
    private final ICrud<Skill> skillRepository= new EntityRepository<>();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestURI = req.getRequestURI();
        if (requestURI.contains("/createDeveloper")) {
            req.setAttribute("companies",companyRepository.read(Company.class));
            req.setAttribute("projects", projectRepository.read(Project.class));
            req.setAttribute("skills", skillRepository.read(Skill.class));
            req.getRequestDispatcher("/WEB-INF/views/developerViews/create.jsp").forward(req, resp);
            return;

        } else if (requestURI.contains("/updateDeveloper")) {
            req.setAttribute("updateId", req.getParameter("updateId"));
            req.getRequestDispatcher("/WEB-INF/views/developerViews/update.jsp").forward(req, resp);
            return;
        }
        else if (requestURI.contains("/about")) {
            Developer developer = developerRepository.getById(Developer.class, Long.parseLong(req.getParameter("aboutId")));
            req.setAttribute("developer", developer);
            req.getRequestDispatcher("/WEB-INF/views/developerViews/about.jsp").forward(req, resp);
            return;
        }

        String deleteId = req.getParameter("deleteId");
        if (deleteId != null) {
            Developer developer = new Developer();
            developer.setId(Long.parseLong(deleteId));
            developerRepository.delete(developer);
            resp.sendRedirect("/developers");

        } else {
            req.setAttribute("developers", developerRepository.read(Developer.class));
            req.getRequestDispatcher("/WEB-INF/views/developerViews/read.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestURI = req.getRequestURI();
        if (requestURI.contains("/createDeveloper")) {
            String name = req.getParameter("name");
            String age = req.getParameter("age");
            String sex = req.getParameter("sex");
            String phone_number = req.getParameter("phone_number");
            String salary = req.getParameter("salary");
            String choseCompany = req.getParameter("company");
            String chooseProject = req.getParameter("project");
            String chooseSkill = req.getParameter("skill");

            if (name == null || name.matches("\\d+") || name.equals("") || age.matches("\\W+")
                    || sex == null || sex.equals("") || !sex.equals("male") && !sex.equals("female")
                    || !phone_number.matches("\\d{10}") || !salary.matches("\\d{1,5}")) {
                resp.sendRedirect("/developers/createDeveloper");
                return;

            }
            Company company = companyRepository.getById(Company.class, Long.valueOf(choseCompany));
            Project project = projectRepository.getById(Project.class, Long.valueOf(chooseProject));
            Skill skill = skillRepository.getById(Skill.class, Long.valueOf(chooseSkill));

            Developer developer = new Developer();
            developer.setName(name);
            developer.setAge(Integer.parseInt(age));
            developer.setSex(sex);
            developer.setPhone_number(phone_number);
            developer.setSalary(Integer.parseInt(salary));

            developer.setCompanies(List.of(company));

            developer.setProjects(List.of(project));

            developer.setSkills(List.of(skill));

            developerRepository.create(developer);
            req.getSession().setAttribute("developer", developer);
            resp.sendRedirect("/developers");

        } else if (requestURI.contains("/updateDeveloper")) {
            String name = req.getParameter("name");
            String age = req.getParameter("age");
            String sex = req.getParameter("sex");
            String phone_number = req.getParameter("phone_number");
            String salary = req.getParameter("salary");
            if (name == null || name.matches("\\d+") || name.equals("") || age.matches("\\W+")
                    || sex == null || sex.equals("") || !sex.equals("male") && !sex.equals("female")
                    || !phone_number.matches("\\d{10}") || !salary.matches("\\d{1,5}")) {
                resp.sendRedirect("/developers/updateDeveloper?updateId=" + req.getParameter("updateId"));
                return;
            }

            Developer developer = developerRepository.getById(Developer.class, Long.parseLong(req.getParameter("updateId")));
            developer.setId(Long.parseLong(req.getParameter("updateId")));
            developer.setName(name);
            developer.setAge(Integer.parseInt(age));
            developer.setSex(sex);
            developer.setPhone_number(phone_number);
            developer.setSalary(Integer.parseInt(salary));

            developerRepository.update(developer);
            req.getSession().setAttribute("developer", developer);
            resp.sendRedirect("/developers");
        }
    }
}

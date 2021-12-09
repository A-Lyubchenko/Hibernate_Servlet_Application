package ua.lyubchenko.services;

import lombok.SneakyThrows;
import ua.lyubchenko.domains.Company;
import ua.lyubchenko.domains.Developer;
import ua.lyubchenko.domains.Project;
import ua.lyubchenko.domains.Skill;
import ua.lyubchenko.repositories.EntityRepository;
import ua.lyubchenko.repositories.ICrud;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class DeveloperService {
    private final ICrud<Company> companyRepository = new EntityRepository<>();
    private final ICrud<Developer> developerRepository = new EntityRepository<>();
    private final ICrud<Project> projectRepository = new EntityRepository<>();
    private final ICrud<Skill> skillRepository = new EntityRepository<>();


    @SneakyThrows
    public void getCreateDeveloperPage(HttpServletRequest req, HttpServletResponse resp) {
        req.setAttribute("companies", companyRepository.read(Company.class));
        req.setAttribute("projects", projectRepository.read(Project.class));
        req.setAttribute("skills", skillRepository.read(Skill.class));
        req.getRequestDispatcher("/WEB-INF/views/developerViews/create.jsp").forward(req, resp);
    }

    @SneakyThrows
    public void deleteProject(HttpServletRequest req, HttpServletResponse resp) {
        Developer developer = developerRepository.getById(Developer.class, Long.parseLong(req.getParameter("developerId")));
        List<Project> projects = developer.getProjects();
        projects.removeIf(project -> project.getId() == Long.parseLong(req.getParameter("projectId")));
        resp.sendRedirect("/developers/about?aboutId=" + req.getParameter("developerId"));
    }

    @SneakyThrows
    public void deleteCompany(HttpServletRequest req, HttpServletResponse resp) {
        Developer developer = developerRepository.getById(Developer.class, Long.parseLong(req.getParameter("developerId")));
        List<Company> companies = developer.getCompanies();
        companies.removeIf(company -> company.getId() == Long.parseLong(req.getParameter("companyId")));
        resp.sendRedirect("/developers/about?aboutId=" + req.getParameter("developerId"));
    }

    @SneakyThrows
    public void deleteSkill(HttpServletRequest req, HttpServletResponse resp) {
        Developer developer = developerRepository.getById(Developer.class, Long.parseLong(req.getParameter("developerId")));
        List<Skill> skills = developer.getSkills();
        skills.removeIf(skill -> skill.getId() == Long.parseLong(req.getParameter("skillId")));
        resp.sendRedirect("/developers/about?aboutId=" + req.getParameter("developerId"));
    }

    @SneakyThrows
    public void getAddProjectPage(HttpServletRequest req, HttpServletResponse resp) {
        req.setAttribute("projects", projectRepository.read(Project.class));
        req.setAttribute("developerId", req.getParameter("developerId"));
        req.getRequestDispatcher("/WEB-INF/views/developerViews/addProject.jsp").forward(req, resp);
    }

    @SneakyThrows
    public void getAddCompanyPage(HttpServletRequest req, HttpServletResponse resp) {
        req.setAttribute("companies", companyRepository.read(Company.class));
        req.setAttribute("developerId", req.getParameter("developerId"));
        req.getRequestDispatcher("/WEB-INF/views/developerViews/addCompany.jsp").forward(req, resp);
    }

    @SneakyThrows
    public void getAddSkillPage(HttpServletRequest req, HttpServletResponse resp) {
        req.setAttribute("skills", skillRepository.read(Skill.class));
        req.setAttribute("developerId", req.getParameter("developerId"));
        req.getRequestDispatcher("/WEB-INF/views/developerViews/addSkill.jsp").forward(req, resp);
    }

    @SneakyThrows
    public void getAboutPage(HttpServletRequest req, HttpServletResponse resp) {
        Developer developer = developerRepository.getById(Developer.class, Long.parseLong(req.getParameter("aboutId")));
        req.setAttribute("developer", developer);
        req.getRequestDispatcher("/WEB-INF/views/developerViews/about.jsp").forward(req, resp);
    }

    @SneakyThrows
    public void remove(HttpServletRequest req, HttpServletResponse resp) {
        Developer developer = new Developer();
        developer.setId(Long.parseLong(req.getParameter("deleteId")));
        developerRepository.delete(developer);
        resp.sendRedirect("/developers");
    }

    @SneakyThrows
    public void read(HttpServletRequest req, HttpServletResponse resp) {
        req.setAttribute("developers", developerRepository.read(Developer.class));
        req.getRequestDispatcher("/WEB-INF/views/developerViews/read.jsp").forward(req, resp);
    }

    @SneakyThrows
    public void create(HttpServletRequest req, HttpServletResponse resp) {
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
    }

    @SneakyThrows
    public void update(HttpServletRequest req, HttpServletResponse resp) {
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

    @SneakyThrows
    public void addProject(HttpServletRequest req, HttpServletResponse resp) {
        Project project = projectRepository.getById(Project.class, Long.parseLong(req.getParameter("project")));
        Developer developer = developerRepository.getById(Developer.class, Long.parseLong(req.getParameter("developerId")));
        List<Project> projects = developer.getProjects();
        if (!projects.contains(project)) {
            projects.add(project);
            resp.sendRedirect("/developers/about?aboutId=" + req.getParameter("developerId"));
            return;
        }
        resp.sendRedirect("/developers/about?aboutId=" + req.getParameter("developerId"));
    }

    @SneakyThrows
    public void addCompany(HttpServletRequest req, HttpServletResponse resp) {
        Company company = companyRepository.getById(Company.class, Long.parseLong(req.getParameter("companyId")));
        Developer developer = developerRepository.getById(Developer.class, Long.parseLong(req.getParameter("developerId")));
        List<Company> companies = developer.getCompanies();
        if (!companies.contains(company)) {
            companies.add(company);
            resp.sendRedirect("/developers/about?aboutId=" + req.getParameter("developerId"));
            return;
        }
        resp.sendRedirect("/developers/about?aboutId=" + req.getParameter("developerId"));
    }

    @SneakyThrows
    public void addSkill(HttpServletRequest req, HttpServletResponse resp) {
        Skill skill = skillRepository.getById(Skill.class, Long.parseLong(req.getParameter("skillId")));
        Developer developer = developerRepository.getById(Developer.class, Long.parseLong(req.getParameter("developerId")));
        List<Skill> skills = developer.getSkills();
        if (!skills.contains(skill)) {
            skills.add(skill);
            resp.sendRedirect("/developers/about?aboutId=" + req.getParameter("developerId"));
            return;
        }
        resp.sendRedirect("/developers/about?aboutId=" + req.getParameter("developerId"));
    }
}

package ua.lyubchenko.services;

import lombok.SneakyThrows;
import ua.lyubchenko.domains.Company;
import ua.lyubchenko.domains.Developer;
import ua.lyubchenko.domains.Project;
import ua.lyubchenko.repositories.EntityRepository;
import ua.lyubchenko.repositories.ICrud;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class CompanyService {

    private final ICrud<Company> companyRepository = new EntityRepository<>();
    private final ICrud<Project> projectRepository = new EntityRepository<>();
    private final ICrud<Developer> developerRepository = new EntityRepository<>();


    @SneakyThrows
    public void getUpdatePage(HttpServletRequest req, HttpServletResponse resp) {
        req.setAttribute("updateId", req.getParameter("updateId"));
        Company company = companyRepository.getById(Company.class, Long.parseLong(req.getParameter("updateId")));
        req.setAttribute("company", company);
        req.setAttribute("projects", projectRepository.read(Project.class));
        req.getRequestDispatcher("/WEB-INF/views/companyViews/update.jsp").forward(req, resp);
    }

    @SneakyThrows
    public void getAboutPage(HttpServletRequest req, HttpServletResponse resp) {
        Company company = companyRepository.getById(Company.class, Long.parseLong(req.getParameter("aboutId")));
        req.setAttribute("company", company);
        req.getRequestDispatcher("/WEB-INF/views/companyViews/about.jsp").forward(req, resp);
    }

    @SneakyThrows
    public void getAddDeveloperPage(HttpServletRequest req, HttpServletResponse resp) {
        req.setAttribute("developers", developerRepository.read(Developer.class));
        req.setAttribute("companyId", req.getParameter("companyId"));
        req.getRequestDispatcher("/WEB-INF/views/companyViews/addDeveloper.jsp").forward(req, resp);
    }

    @SneakyThrows
    public void getAddProjectPage(HttpServletRequest req, HttpServletResponse resp) {
        req.setAttribute("projects", projectRepository.read(Project.class));
        req.setAttribute("companyId", req.getParameter("companyId"));
        req.getRequestDispatcher("/WEB-INF/views/companyViews/addProject.jsp").forward(req, resp);
    }

    @SneakyThrows
    public void deleteProject(HttpServletRequest req, HttpServletResponse resp) {
        Company company = companyRepository.getById(Company.class, Long.parseLong(req.getParameter("companyId")));
        List<Project> projects = company.getProjects();
        projects.removeIf(project -> project.getId() == Long.parseLong(req.getParameter("projectId")));
        resp.sendRedirect("/companies/about?aboutId=" + req.getParameter("companyId"));
    }

    @SneakyThrows
    public void deleteDeveloper(HttpServletRequest req, HttpServletResponse resp) {
        Company company = companyRepository.getById(Company.class, Long.parseLong(req.getParameter("companyId")));
        List<Developer> developers = company.getDevelopers();
        developers.removeIf(developer -> developer.getId() == Long.parseLong(req.getParameter("developerId")));
        resp.sendRedirect("/companies/about?aboutId=" + req.getParameter("companyId"));
    }

    @SneakyThrows
    public void remove(HttpServletRequest req, HttpServletResponse resp) {
        Company company = new Company();
        company.setId(Long.parseLong(req.getParameter("deleteId")));
        companyRepository.delete(company);
        resp.sendRedirect("/companies");
    }

    @SneakyThrows
    public void read(HttpServletRequest req, HttpServletResponse resp) {
        req.setAttribute("companies", companyRepository.read(Company.class));
        req.getRequestDispatcher("/WEB-INF/views/companyViews/read.jsp").forward(req, resp);
    }

    @SneakyThrows
    public void create(HttpServletRequest req, HttpServletResponse resp) {
        String name = req.getParameter("name");
        String location = req.getParameter("location");
        if (name == null || location == null || name.equals("") || location.equals("")
                || name.matches("\\d+") || location.matches("\\d+")) {
            resp.sendRedirect("/companies/createCompany");
            return;

        }
        Company company = new Company();
        company.setName(name);
        company.setLocation(location);

        companyRepository.create(company);
        req.getSession().setAttribute("company", company);
        resp.sendRedirect("/companies");
    }

    @SneakyThrows
    public void update(HttpServletRequest req, HttpServletResponse resp) {
        String name = req.getParameter("name");
        String location = req.getParameter("location");
        if (name == null || location == null || name.equals("") || location.equals("")
                || name.matches("\\d+") || location.matches("\\d+")) {
            resp.sendRedirect("/companies/updateCompany?updateId=" + req.getParameter("updateId"));
            return;

        }
        Company company = companyRepository.getById(Company.class, Long.parseLong(req.getParameter("updateId")));
        company.setId(Long.parseLong(req.getParameter("updateId")));
        company.setName(name);
        company.setLocation(location);

        companyRepository.update(company);
        req.getSession().setAttribute("company", company);
        resp.sendRedirect("/companies");
    }

    @SneakyThrows
    public void addDeveloper(HttpServletRequest req, HttpServletResponse resp) {
        Company company = companyRepository.getById(Company.class, Long.parseLong(req.getParameter("companyId")));
        Developer developer = developerRepository.getById(Developer.class, Long.parseLong(req.getParameter("developerId")));
        List<Developer> developers = company.getDevelopers();
        if (!developers.contains(developer)) {
            developers.add(developer);
            resp.sendRedirect("/companies/about?aboutId=" + req.getParameter("companyId"));
            return;
        }
        resp.sendRedirect("/companies/about?aboutId=" + req.getParameter("companyId"));
    }

    @SneakyThrows
    public void addProject(HttpServletRequest req, HttpServletResponse resp) {
        Company company = companyRepository.getById(Company.class, Long.parseLong(req.getParameter("companyId")));
        Project project = projectRepository.getById(Project.class, Long.parseLong(req.getParameter("project")));
        List<Project> projects = company.getProjects();
        if (!projects.contains(project)) {
            projects.add(project);
            resp.sendRedirect("/companies/about?aboutId=" + req.getParameter("companyId"));
            return;
        }
        resp.sendRedirect("/companies/about?aboutId=" + req.getParameter("companyId"));
    }
}

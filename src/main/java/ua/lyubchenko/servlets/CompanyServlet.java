package ua.lyubchenko.servlets;


import ua.lyubchenko.domains.Company;
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
import java.util.List;


@WebServlet("/companies/*")
public class CompanyServlet extends HttpServlet {
    private final ICrud<Company> companyRepository = new EntityRepository<>();
    private final ICrud<Project> projectRepository = new EntityRepository<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestURI = req.getRequestURI();
        if (requestURI.contains("/createCompany")) {
            req.getRequestDispatcher("/WEB-INF/views/companyViews/create.jsp").forward(req, resp);
            return;
        } else if (requestURI.contains("/updateCompany")) {
            req.setAttribute("updateId", req.getParameter("updateId"));
            Company company = companyRepository.getById(Company.class, Long.parseLong(req.getParameter("updateId")));
            req.setAttribute("company", company);
            req.setAttribute("projects", projectRepository.read(Project.class));
            req.getRequestDispatcher("/WEB-INF/views/companyViews/update.jsp").forward(req, resp);
            return;
        } else if (requestURI.contains("/about")) {
            Company company = companyRepository.getById(Company.class, Long.parseLong(req.getParameter("aboutId")));
            req.setAttribute("company", company);
            req.getRequestDispatcher("/WEB-INF/views/companyViews/about.jsp").forward(req, resp);
            return;
        }
        String deleteId = req.getParameter("deleteId");
        if (deleteId != null) {
            Company company = new Company();
            company.setId(Long.parseLong(deleteId));
            companyRepository.delete(company);
            resp.sendRedirect("/companies");
            return;
        }

        req.setAttribute("companies", companyRepository.read(Company.class));
        req.getRequestDispatcher("/WEB-INF/views/companyViews/read.jsp").forward(req, resp);


    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestURI = req.getRequestURI();
        if (requestURI.contains("/createCompany")) {
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

        } else if (requestURI.contains("/updateCompany")) {
            String name = req.getParameter("name");
            String location = req.getParameter("location");
            String chooseProject = req.getParameter("project");
            if (name == null || location == null || name.equals("") || location.equals("")
                    || name.matches("\\d+") || location.matches("\\d+")) {
                resp.sendRedirect("/companies/updateCompany?updateId=" + req.getParameter("updateId"));
                return;

            }
            Project project = projectRepository.getById(Project.class, Long.valueOf(chooseProject));
            Company company = companyRepository.getById(Company.class, Long.parseLong(req.getParameter("updateId")));
            company.setId(Long.parseLong(req.getParameter("updateId")));
            company.setName(name);
            company.setLocation(location);
            company.setProjects(List.of(project));

            companyRepository.update(company);
            req.getSession().setAttribute("company", company);
            resp.sendRedirect("/companies");
        }
    }

}



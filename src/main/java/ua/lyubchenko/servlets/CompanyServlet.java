package ua.lyubchenko.servlets;


import org.hibernate.Session;
import ua.lyubchenko.connection.ApplicationConnection;
import ua.lyubchenko.domains.Company;
import ua.lyubchenko.repositories.EntityRepository;
import ua.lyubchenko.repositories.ICrud;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/companies/*")
public class CompanyServlet extends HttpServlet {
    private final ICrud<Company> companyRepository = new EntityRepository<>();
    private final Session session = ApplicationConnection.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestURI = req.getRequestURI();
        if (requestURI.contains("/createCompany")) {
            req.getRequestDispatcher("/WEB-INF/views/companyViews/create.jsp").forward(req, resp);
            return;
        } else if (requestURI.contains("/updateCompany")) {
            req.setAttribute("updateId", req.getParameter("updateId"));
            req.getRequestDispatcher("/WEB-INF/views/companyViews/update.jsp").forward(req, resp);
            return;
        }
        else if (requestURI.contains("/about")) {
            Company company = session.get(Company.class, Long.parseLong(req.getParameter("aboutId")));
            req.setAttribute("company", companyRepository.getById(Company.class, Long.parseLong(req.getParameter("aboutId"))));
            req.setAttribute("developers", company.getDevelopers());
            req.setAttribute("projects", company.getProjects());
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

            if (name == null || location == null || name.equals("") || location.equals("")
                    || name.matches("\\d+") || location.matches("\\d+")) {
                resp.sendRedirect("/companies/createCompany");
                return;

            }
            Company company = new Company();
            company.setId(Long.parseLong(req.getParameter("updateId")));
            company.setName(name);
            company.setLocation(location);

            companyRepository.update(company);
            req.getSession().setAttribute("company", company);
            resp.sendRedirect("/companies");
        }
    }

}



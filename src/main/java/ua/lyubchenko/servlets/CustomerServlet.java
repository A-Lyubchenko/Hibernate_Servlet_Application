package ua.lyubchenko.servlets;

import ua.lyubchenko.domains.Company;
import ua.lyubchenko.domains.Customer;
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

@WebServlet("/customers/*")
public class CustomerServlet extends HttpServlet {
    private final ICrud<Customer> customerRepository= new EntityRepository<>();
    private final ICrud<Project> projectRepository = new EntityRepository<>();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestURI = req.getRequestURI();
        if (requestURI.contains("/createCustomer")) {
            req.getRequestDispatcher("/WEB-INF/views/customerViews/create.jsp").forward(req, resp);
            return;

        } else if (requestURI.contains("/updateCustomer")) {
            req.setAttribute("updateId", req.getParameter("updateId"));
            Customer customer = customerRepository.getById(Customer.class, Long.parseLong(req.getParameter("updateId")));
            req.setAttribute("customer", customer);
            req.setAttribute("projects", projectRepository.read(Project.class));
            req.getRequestDispatcher("/WEB-INF/views/customerViews/update.jsp").forward(req, resp);
            return;
        }
        else if (requestURI.contains("/about")) {
            Customer customer = customerRepository.getById(Customer.class, Long.parseLong(req.getParameter("aboutId")));
            req.setAttribute("customer", customer);
            req.getRequestDispatcher("/WEB-INF/views/customerViews/about.jsp").forward(req, resp);
            return;
        }

        String deleteId = req.getParameter("deleteId");
        if (deleteId != null) {
            Customer customer = new Customer();
            customer.setId(Long.parseLong(deleteId));
            customerRepository.delete(customer);
            resp.sendRedirect("/customers");
            return;

        }
        req.setAttribute("customers", customerRepository.read(Customer.class));
        req.getRequestDispatcher("/WEB-INF/views/customerViews/read.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestURI = req.getRequestURI();
        if (requestURI.contains("/createCustomer")) {
            String name = req.getParameter("name");
            String location = req.getParameter("location");
            if (name == null || location == null || name.equals("") || location.equals("")
                    || name.matches("\\d+ | \\d") || location.matches("\\d+ | \\d")) {
                resp.sendRedirect("/customers/createCustomer");
                return;

            }
            Customer customer = new Customer();
            customer.setName(name);
            customer.setLocation(location);
            customerRepository.create(customer);
            req.getSession().setAttribute("customer", customer);
            resp.sendRedirect("/customers");

        } else if (requestURI.contains("/updateCustomer")) {
            String name = req.getParameter("name");
            String location = req.getParameter("location");
            String chooseProject = req.getParameter("project");

            Project project = projectRepository.getById(Project.class, Long.valueOf(chooseProject));
            Customer customer = customerRepository.getById(Customer.class, Long.parseLong(req.getParameter("updateId")));

            customer.setId(Long.parseLong(req.getParameter("updateId")));
            customer.setName(name);
            customer.setLocation(location);
            customer.setProjects(List.of(project));

            if (name == null || location == null || name.equals("") || location.equals("")
                    || name.matches("\\d+") || location.matches("\\d+")) {
                resp.sendRedirect("/customers/updateCustomer?updateId=" + req.getParameter("updateId"));
                return;
            }

            customerRepository.update(customer);
            req.getSession().setAttribute("customer", customer);
            resp.sendRedirect("/customers");
        }

    }
}



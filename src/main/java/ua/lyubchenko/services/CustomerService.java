package ua.lyubchenko.services;

import lombok.SneakyThrows;
import ua.lyubchenko.domains.Customer;
import ua.lyubchenko.domains.Project;
import ua.lyubchenko.repositories.EntityRepository;
import ua.lyubchenko.repositories.ICrud;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class CustomerService {
    private final ICrud<Customer> customerRepository = new EntityRepository<>();
    private final ICrud<Project> projectRepository = new EntityRepository<>();


    @SneakyThrows
    public void getUpdatePage(HttpServletRequest req, HttpServletResponse resp) {
        req.setAttribute("updateId", req.getParameter("updateId"));
        Customer customer = customerRepository.getById(Customer.class, Long.parseLong(req.getParameter("updateId")));
        req.setAttribute("customer", customer);
        req.setAttribute("projects", projectRepository.read(Project.class));
        req.getRequestDispatcher("/WEB-INF/views/customerViews/update.jsp").forward(req, resp);
    }

    @SneakyThrows
    public void getAboutPage(HttpServletRequest req, HttpServletResponse resp) {
        Customer customer = customerRepository.getById(Customer.class, Long.parseLong(req.getParameter("aboutId")));
        req.setAttribute("customer", customer);
        req.getRequestDispatcher("/WEB-INF/views/customerViews/about.jsp").forward(req, resp);
    }

    @SneakyThrows
    public void getAddProjectPage(HttpServletRequest req, HttpServletResponse resp) {
        req.setAttribute("projects", projectRepository.read(Project.class));
        req.setAttribute("customerId", req.getParameter("customerId"));
        req.getRequestDispatcher("/WEB-INF/views/customerViews/addProject.jsp").forward(req, resp);
    }

    @SneakyThrows
    public void deleteProject(HttpServletRequest req, HttpServletResponse resp) {
        Customer customer = customerRepository.getById(Customer.class, Long.parseLong(req.getParameter("customerId")));
        List<Project> projects = customer.getProjects();
        projects.removeIf(project -> project.getId() == Long.parseLong(req.getParameter("projectId")));
        resp.sendRedirect("/customers/about?aboutId=" + req.getParameter("customerId"));
    }

    @SneakyThrows
    public void remove(HttpServletRequest req, HttpServletResponse resp) {
        Customer customer = new Customer();
        customer.setId(Long.parseLong(req.getParameter("deleteId")));
        customerRepository.delete(customer);
        resp.sendRedirect("/customers");
    }

    @SneakyThrows
    public void read(HttpServletRequest req, HttpServletResponse resp) {
        req.setAttribute("customers", customerRepository.read(Customer.class));
        req.getRequestDispatcher("/WEB-INF/views/customerViews/read.jsp").forward(req, resp);
    }

    @SneakyThrows
    public void create(HttpServletRequest req, HttpServletResponse resp) {
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
    }

    @SneakyThrows
    public void update(HttpServletRequest req, HttpServletResponse resp) {
        String name = req.getParameter("name");
        String location = req.getParameter("location");


        Customer customer = customerRepository.getById(Customer.class, Long.parseLong(req.getParameter("updateId")));

        customer.setId(Long.parseLong(req.getParameter("updateId")));
        customer.setName(name);
        customer.setLocation(location);

        if (name == null || location == null || name.equals("") || location.equals("")
                || name.matches("\\d+") || location.matches("\\d+")) {
            resp.sendRedirect("/customers/updateCustomer?updateId=" + req.getParameter("updateId"));
            return;
        }

        customerRepository.update(customer);
        req.getSession().setAttribute("customer", customer);
        resp.sendRedirect("/customers");
    }

    @SneakyThrows
    public void addProject(HttpServletRequest req, HttpServletResponse resp) {
        Customer customer = customerRepository.getById(Customer.class, Long.parseLong(req.getParameter("customerId")));
        Project project = projectRepository.getById(Project.class, Long.parseLong(req.getParameter("project")));
        List<Project> projects = customer.getProjects();
        if (!projects.contains(project)) {
            projects.add(project);
            resp.sendRedirect("/customers/about?aboutId=" + req.getParameter("customerId"));
            return;
        }
        resp.sendRedirect("/customers/about?aboutId=" + req.getParameter("customerId"));
    }
}

package ua.lyubchenko.servlets;

import lombok.SneakyThrows;
import ua.lyubchenko.services.CustomerService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/customers/*")
public class CustomerServlet extends HttpServlet {

    private final CustomerService customerService = new CustomerService();


    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)  {
        String requestURI = req.getRequestURI();
        if (requestURI.contains("/createCustomer")) {
            req.getRequestDispatcher("/WEB-INF/views/customerViews/create.jsp").forward(req, resp);
            return;

        } else if (requestURI.contains("/updateCustomer")) {
            customerService.getUpdatePage(req, resp);
            return;
        } else if (requestURI.contains("/about")) {
            customerService.getAboutPage(req, resp);
            return;

        } else if (requestURI.contains("/addProject")) {
            customerService.getAddProjectPage(req, resp);
            return;
        } else if (requestURI.contains("/deleteProject")) {
            customerService.deleteProject(req, resp);
            return;
        } else if (requestURI.contains("/remove")) {
            customerService.remove(req, resp);
            return;
        }
        customerService.read(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)  {
        String requestURI = req.getRequestURI();
        if (requestURI.contains("/createCustomer")) {
            customerService.create(req, resp);

        } else if (requestURI.contains("/updateCustomer")) {
            customerService.update(req, resp);

        } else if (requestURI.contains("/addProject")) {
            customerService.addProject(req, resp);

        }
    }
}



package ua.goit.servlets.controller;

import ua.goit.servlets.config.DatabaseConnectionManager;
import ua.goit.servlets.dao.*;
import ua.goit.servlets.dto.*;
import ua.goit.servlets.service.Service;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@WebServlet("/companies/*")
public class CompanyServlet extends AbstractServlet<Company> {
    private final Service<Project> projectService = new Service<>(new ProjectDAO(DatabaseConnectionManager.getDataSource()));

    @Override
    protected Service<Company> initService() {
        return new Service<>(new CompanyDAO(DatabaseConnectionManager.getDataSource()));
    }

    @Override
    protected String getServletPath() {
        return "/companies";
    }

    @Override
    protected String getEntitiesPage() {
        return "/view/companies.jsp";
    }

    @Override
    protected String getEntityPage() {
        return "/view/company.jsp";
    }

    @Override
    protected String getFormPage() {
        return "/view/companyForm.jsp";
    }

    @Override
    protected Company readJSPForm(HttpServletRequest req, Company company) {

        if (Objects.isNull(company)) {
            company = new Company();
        }

        company.setName(req.getParameter("name"));
        company.setHeadquarters(req.getParameter("headquarters"));

        if (company.getId() != 0) {

            String[] listOfProjectId = req.getParameterValues("projects");
            List<Project> projects = new ArrayList<>();
            if (listOfProjectId != null && listOfProjectId.length > 0) {
                projects = Arrays.stream(listOfProjectId)
                        .mapToInt(Integer::parseInt)
                        .mapToObj(proj -> findById(proj, projectService))
                        .collect(Collectors.toList());
            }
            company.setProjects(projects);

        }

        return company;
    }

    @Override
    protected void setAdditionalAttributesInForm(HttpServletRequest req) {
        List<Project> projectList = projectService.readAll();
        req.setAttribute("projectList", projectList);
    }
}

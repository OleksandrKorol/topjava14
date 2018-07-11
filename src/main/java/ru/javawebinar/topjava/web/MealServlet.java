package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.mock.MockMealRepositoryImpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.TimeUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);
    private MockMealRepositoryImpl repository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        repository = new MockMealRepositoryImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("get meals");

        req.setAttribute("meals", MealsUtil.getMealWithExceed(repository.getAll(), 2000));

        String action = req.getParameter("action");

        if (action == null) {
            req.getRequestDispatcher("/meals.jsp").forward(req, resp);

        } else if (action.equals("edit")) {
            log.debug("Edit meals");
            int id = Integer.parseInt(req.getParameter("id"));
            req.setAttribute("meal", repository.get(id));
            req.getRequestDispatcher("/meals.jsp").forward(req, resp);

        } else if (action.equals("remove")) {
            log.debug("delete meals");
            int id = Integer.parseInt(req.getParameter("id"));
            repository.remove(id);
            resp.sendRedirect("meals");

        } else {
            req.getRequestDispatcher("/meals.jsp").forward(req, resp);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String paramId = req.getParameter("id");

        repository.save(new Meal(paramId != null && !paramId.isEmpty() ? Integer.parseInt(paramId) : null,
                TimeUtil.parseDateTime(req.getParameter("dateTime")),
                req.getParameter("description"),
                Integer.parseInt(req.getParameter("calories"))
        ));

        resp.sendRedirect("meals");
    }
}

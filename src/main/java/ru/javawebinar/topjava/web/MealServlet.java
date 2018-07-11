package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.mock.MockMealRepositoryImpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.TimeUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);
    private MockMealRepositoryImpl mealMock = new MockMealRepositoryImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("forward to meals");

        req.setAttribute("meals", MealsUtil.getMealWithExceed(mealMock.getAll(), 2000));

        String action = req.getParameter("action");

        if (action == null) {
            req.getRequestDispatcher("/meals.jsp").forward(req, resp);

        } else if (action.equals("edit")) {
            int id = Integer.parseInt(req.getParameter("id"));
            req.setAttribute("meal", mealMock.get(id));
            req.getRequestDispatcher("/meals.jsp").forward(req, resp);

        } else if (action.equals("remove")) {
            int id = Integer.parseInt(req.getParameter("id"));
            mealMock.remove(id);
            resp.sendRedirect("meals");

        } else {
            req.getRequestDispatcher("/meals.jsp").forward(req, resp);
        }


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String paramId = req.getParameter("id");

        mealMock.add(new Meal(paramId != null && !paramId.isEmpty() ? Integer.parseInt(paramId) : null,
                TimeUtil.parseDateTime(req.getParameter("dateTime")),
                req.getParameter("description"),
                Integer.parseInt(req.getParameter("calories"))
        ));

        resp.sendRedirect("meals");
    }
}

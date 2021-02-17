package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.web.user.AbstractUserController;
import ru.javawebinar.topjava.web.user.AdminRestController;
import ru.javawebinar.topjava.web.user.ProfileRestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

public class UserServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);

    private AbstractUserController userController;
    private final ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");

    @Override
    public void init() {
        userController = appCtx.getBean(ProfileRestController.class);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("forward to users");
//        int num = Integer.parseInt(request.getParameter("num"));
//        SecurityUtil.setAuthUserId(num);
//        if (SecurityUtil.authUserId == 1) {
//            userController = appCtx.getBean(ProfileRestController.class);
//        } else {
//            userController = appCtx.getBean(AdminRestController.class);
//        }
        request.setAttribute("users", userController.getAll());
        request.getRequestDispatcher("/users.jsp").forward(request, response);
    }
}

package vn.hcmute.de4.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import vn.hcmute.de4.model.User_24162037;

public class AdminInterceptor_24162037 implements HandlerInterceptor {
    @Override public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object value = request.getSession().getAttribute("currentUser");
        if (value instanceof User_24162037 user && user.isAdmin()) return true;
        response.sendRedirect(request.getContextPath() + "/login?error=admin"); return false;
    }
}

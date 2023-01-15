package toyproject1.shopping.web.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import toyproject1.shopping.web.member.MemberController;
import toyproject1.shopping.web.session.SessionConst;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class MemberInfoCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        HttpSession session = request.getSession(false);
        boolean infoCheck = (boolean) session.getAttribute(SessionConst.INFO_CHECK);
        if(!infoCheck){
            response.sendRedirect("/members/check");
            return false;
        }


        return true;
    }
}

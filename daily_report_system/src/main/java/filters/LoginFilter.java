package filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import actions.views.EmployeeView;
import constants.AttributeConst;
import constants.ForwardConst;

/**
 * Servlet Filter implementation class LoginFilter
 */
@WebFilter(description = "LoginFilter", urlPatterns = { "/*" })
public class LoginFilter implements Filter {
       
    /**
     * @see HttpFilter#HttpFilter()
     */
    public LoginFilter() {
        super();
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String contextPath = ((HttpServletRequest) request).getContextPath();
        String servletPath = ((HttpServletRequest) request).getServletPath();

        if (servletPath.matches("/css.*")) {
            // CSS�t�H���_���͔F�؏������珜�O����
            chain.doFilter(request, response);

        } else {
            HttpSession session = ((HttpServletRequest) request).getSession();

            //�N�G���p�����[�^����action��command���擾
            String action = request.getParameter(ForwardConst.ACT.getValue());
            String command = request.getParameter(ForwardConst.CMD.getValue());

            //�Z�b�V�������烍�O�C�����Ă���]�ƈ��̏����擾
            EmployeeView ev = (EmployeeView) session.getAttribute(AttributeConst.LOGIN_EMP.getValue());

            if (ev == null) {
                //�����O�C��

                if (!(ForwardConst.ACT_AUTH.getValue().equals(action)
                        && (ForwardConst.CMD_SHOW_LOGIN.getValue().equals(command)
                                || ForwardConst.CMD_LOGIN.getValue().equals(command)))) {

                    //���O�C���y�[�W�̕\���܂��̓��O�C�����s�ȊO�̓��O�C���y�[�W�Ƀ��_�C���N�g
                    ((HttpServletResponse) response).sendRedirect(
                            contextPath
                                    + "?action=" + ForwardConst.ACT_AUTH.getValue()
                                    + "&command=" + ForwardConst.CMD_SHOW_LOGIN.getValue());
                    return;
                }
            } else {
                //���O�C����

                if (ForwardConst.ACT_AUTH.getValue().equals(action)) {
                    //�F�،nAction���s�����Ƃ��Ă���ꍇ

                    if (ForwardConst.CMD_SHOW_LOGIN.getValue().equals(command)) {
                        //���O�C���y�[�W�̕\���̓g�b�v��ʂɃ��_�C���N�g
                        ((HttpServletResponse) response).sendRedirect(
                                contextPath
                                        + "?action=" + ForwardConst.ACT_TOP.getValue()
                                        + "&command=" + ForwardConst.CMD_INDEX.getValue());
                        return;

                    } else if (ForwardConst.CMD_LOGOUT.getValue().equals(command)) {
                        //���O�A�E�g�̎��{�͋���

                    } else {
                        //��L�ȊO�̔F�،nAction�̓G���[���

                        String forward = String.format("/WEB-INF/views/%s.jsp", "error/unknown");
                        RequestDispatcher dispatcher = request.getRequestDispatcher(forward);
                        dispatcher.forward(request, response);

                        return;

                    }
                }
            }

            //���̃t�B���^�[�܂��̓T�[�u���b�g���Ăяo��
            chain.doFilter(request, response);
        }
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}

}

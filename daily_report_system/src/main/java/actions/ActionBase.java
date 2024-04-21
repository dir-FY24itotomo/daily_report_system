package actions;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import constants.AttributeConst;
import constants.ForwardConst;
import constants.PropertyConst;

/**
 * �eAction�N���X�̐e�N���X�B���ʏ������s���B
 *
 */
public abstract class ActionBase {
    protected ServletContext context;
    protected HttpServletRequest request;
    protected HttpServletResponse response;

    /**
     * ����������
     * �T�[�u���b�g�R���e�L�X�g�A���N�G�X�g�A���X�|���X���N���X�t�B�[���h�ɐݒ�
     * @param servletContext
     * @param servletRequest
     * @param servletResponse
     */
    public void init(
            ServletContext servletContext,
            HttpServletRequest servletRequest,
            HttpServletResponse servletResponse) {
        this.context = servletContext;
        this.request = servletRequest;
        this.response = servletResponse;
    }

    /**
     * �t�����g�R���g���[���[����Ăяo����郁�\�b�h
     * @throws ServletException
     * @throws IOException
     */
    public abstract void process() throws ServletException, IOException;

    /**
     * �p�����[�^�[��command�̒l�ɊY�����郁�\�b�h�����s����
     * @throws ServletException
     * @throws IOException
     */
    protected void invoke()
            throws ServletException, IOException {

        Method commandMethod;
        try {

            //�p�����[�^�[����command���擾
            String command = request.getParameter(ForwardConst.CMD.getValue());

            //command�ɊY�����郁�\�b�h�����s����
            //(��: action=Employee command=show �̏ꍇ EmployeeAction�N���X��show()���\�b�h�����s����)
            commandMethod = this.getClass().getDeclaredMethod(command, new Class[0]);
            commandMethod.invoke(this, new Object[0]); //���\�b�h�ɓn�������͂Ȃ�

        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException | NullPointerException e) {

            //����������O���R���\�[���ɕ\��
            e.printStackTrace();
            //command�̒l���s���Ŏ��s�ł��Ȃ��ꍇ�G���[��ʂ��Ăяo��
            forward(ForwardConst.FW_ERR_UNKNOWN);
        }

    }

    /**
     * �w�肳�ꂽjsp�̌Ăяo�����s��
     * @param target �J�ڐ�jsp��ʂ̃t�@�C����(�g���q���܂܂Ȃ�)
     * @throws ServletException
     * @throws IOException
     */
    protected void forward(ForwardConst target) throws ServletException, IOException {

        //jsp�t�@�C���̑��΃p�X���쐬
        String forward = String.format("/WEB-INF/views/%s.jsp", target.getValue());
        RequestDispatcher dispatcher = request.getRequestDispatcher(forward);

        //jsp�t�@�C���̌Ăяo��
        dispatcher.forward(request, response);

    }

    /**
     * URL���\�z�����_�C���N�g���s��
     * @param action �p�����[�^�[�ɐݒ肷��l
     * @param command �p�����[�^�[�ɐݒ肷��l
     * @throws ServletException
     * @throws IOException
     */
    protected void redirect(ForwardConst action, ForwardConst command)
            throws ServletException, IOException {

        //URL���\�z
        String redirectUrl = request.getContextPath() + "/?action=" + action.getValue();
        if (command != null) {
            redirectUrl = redirectUrl + "&command=" + command.getValue();
        }

        //URL�փ��_�C���N�g
        response.sendRedirect(redirectUrl);

    }

    /**
     * CSRF�΍� token�s���̏ꍇ�̓G���[��ʂ�\��
     * @return true: token�L�� false: token�s��
     * @throws ServletException
     * @throws IOException
     */
    protected boolean checkToken() throws ServletException, IOException {

        //�p�����[�^�[����token�̒l���擾
        String _token = getRequestParam(AttributeConst.TOKEN);

        if (_token == null || !(_token.equals(getTokenId()))) {

            //token���ݒ肳��Ă��Ȃ��A�܂��̓Z�b�V����ID�ƈ�v���Ȃ��ꍇ�̓G���[��ʂ�\��
            forward(ForwardConst.FW_ERR_UNKNOWN);

            return false;
        } else {
            return true;
        }

    }

    /**
     * �Z�b�V����ID���擾����
     * @return �Z�b�V����ID
     */
    protected String getTokenId() {
        return request.getSession().getId();
    }

    /**
     * ���N�G�X�g����\����v������Ă���y�[�W�����擾���A�ԋp����
     * @return �v������Ă���y�[�W��(�v�����Ȃ��ꍇ��1)
     */
    protected int getPage() {
        int page;
        page = toNumber(request.getParameter(AttributeConst.PAGE.getValue()));
        if (page == Integer.MIN_VALUE) {
            page = 1;
        }
        return page;
    }

    /**
     * ������𐔒l�ɕϊ�����
     * @param strNumber �ϊ��O������
     * @return �ϊ��㐔�l
     */
    protected int toNumber(String strNumber) {
        int number = 0;
        try {
            number = Integer.parseInt(strNumber);
        } catch (Exception e) {
            number = Integer.MIN_VALUE;
        }
        return number;
    }

    /**
     * �������LocalDate�^�ɕϊ�����
     * @param strDate �ϊ��O������
     * @return �ϊ���LocalDate�C���X�^���X
     */
    protected LocalDate toLocalDate(String strDate) {
        if (strDate == null || strDate.equals("")) {
            return LocalDate.now();
        }
        return LocalDate.parse(strDate);
    }

    /**
     * ���N�G�X�g�p�����[�^�[��������Ŏw�肵���p�����[�^�[���̒l��ԋp����
     * @param key �p�����[�^�[��
     * @return �p�����[�^�[�̒l
     */
    protected String getRequestParam(AttributeConst key) {
        return request.getParameter(key.getValue());
    }

    /**
     * ���N�G�X�g�X�R�[�v�Ƀp�����[�^�[��ݒ肷��
     * @param key �p�����[�^�[��
     * @param value �p�����[�^�[�̒l
     */
    protected <V> void putRequestScope(AttributeConst key, V value) {
        request.setAttribute(key.getValue(), value);
    }

    /**
     * �Z�b�V�����X�R�[�v����w�肳�ꂽ�p�����[�^�[�̒l���擾���A�ԋp����
     * @param key �p�����[�^�[��
     * @return �p�����[�^�[�̒l
     */
    @SuppressWarnings("unchecked")
    protected <R> R getSessionScope(AttributeConst key) {
        return (R) request.getSession().getAttribute(key.getValue());
    }

    /**
     * �Z�b�V�����X�R�[�v�Ƀp�����[�^�[��ݒ肷��
     * @param key �p�����[�^�[��
     * @param value �p�����[�^�[�̒l
     */
    protected <V> void putSessionScope(AttributeConst key, V value) {
        request.getSession().setAttribute(key.getValue(), value);
    }

    /**
     * �Z�b�V�����X�R�[�v����w�肳�ꂽ���O�̃p�����[�^�[����������
     * @param key �p�����[�^�[��
     */
    protected void removeSessionScope(AttributeConst key) {
        request.getSession().removeAttribute(key.getValue());
    }

    /**
     * �A�v���P�[�V�����X�R�[�v����w�肳�ꂽ�p�����[�^�[�̒l���擾���A�ԋp����
     * @param key �p�����[�^�[��
     * @return �p�����[�^�[�̒l
     */
    @SuppressWarnings("unchecked")
    protected <R> R getContextScope(PropertyConst key) {
        return (R) context.getAttribute(key.getValue());
    }

}

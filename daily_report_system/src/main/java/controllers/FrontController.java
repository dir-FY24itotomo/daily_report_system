package controllers;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import actions.ActionBase;
import actions.UnknownAction;
import constants.ForwardConst;

/**
 * Servlet implementation class FrontController
 */
@WebServlet(description = "FrontController", urlPatterns = { "/" })
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FrontController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //�p�����[�^�ɊY������Action�N���X�̃C���X�^���X
        ActionBase action = getAction(request, response);

        //�T�[�u���b�g�R���e�L�X�g�A���N�G�X�g�A���X�|���X��Action�C���X�^���X�̃t�B�[���h�ɐݒ�
        action.init(getServletContext(), request, response);

        //Action�N���X�̏������Ăяo��
        action.process();
    }


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" }) //�R���p�C���x����}��
    private ActionBase getAction(HttpServletRequest request, HttpServletResponse response) {
        Class type = null;
        ActionBase action = null;
        try {

            //���N�G�X�g����p�����[�^"action"�̒l���擾 (��:"Employee"�A"Report")
            String actionString = request.getParameter(ForwardConst.ACT.getValue());

            //�Y������Action�I�u�W�F�N�g���쐬 (��:���N�G�X�g����p�����[�^ action=Employee �̏ꍇ�Aactions.EmployeeAction�I�u�W�F�N�g)
            type = Class.forName(String.format("actions.%sAction", actionString));

            //ActionBase�̃I�u�W�F�N�g�ɃL���X�g(��:actions.EmployeeAction�I�u�W�F�N�g��actions.ActionBase�I�u�W�F�N�g)
            action = (ActionBase) (type.asSubclass(ActionBase.class)
                    .getDeclaredConstructor()
                    .newInstance());

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SecurityException
                | IllegalArgumentException | InvocationTargetException| NoSuchMethodException e) {

            //���N�G�X�g�p�����[�^�ɐݒ肳��Ă���"action"�̒l���s���̏ꍇ(��:action=xxxxx ���A�Y������Action�N���X���Ȃ��ꍇ)
            //�G���[�������s��Action�I�u�W�F�N�g���쐬
            action = new UnknownAction();
        }
        return action;
    }

}

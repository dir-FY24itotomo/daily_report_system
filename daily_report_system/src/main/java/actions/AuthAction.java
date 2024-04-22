package actions;

import java.io.IOException;

import javax.servlet.ServletException;

import actions.views.EmployeeView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.MessageConst;
import constants.PropertyConst;
import services.EmployeeService;

/**
 * �F�؂Ɋւ��鏈�����s��Action�N���X
 *
 */
public class AuthAction extends ActionBase {

    private EmployeeService service;

    /**
     * ���\�b�h�����s����
     */
    @Override
    public void process() throws ServletException, IOException {

        service = new EmployeeService();

        //���\�b�h�����s
        invoke();

        service.close();
    }

    /**
     * ���O�C����ʂ�\������
     * @throws ServletException
     * @throws IOException
     */
    public void showLogin() throws ServletException, IOException {

        //CSRF�΍��p�g�[�N����ݒ�
        putRequestScope(AttributeConst.TOKEN, getTokenId());

        //�Z�b�V�����Ƀt���b�V�����b�Z�[�W���o�^����Ă���ꍇ�̓��N�G�X�g�X�R�[�v�ɐݒ肷��
        String flush = getSessionScope(AttributeConst.FLUSH);
        if (flush != null) {
            putRequestScope(AttributeConst.FLUSH,flush);
            removeSessionScope(AttributeConst.FLUSH);
        }

        //���O�C����ʂ�\��
        forward(ForwardConst.FW_LOGIN);
    }
    
    /**
     * ���O�C���������s��
     * @throws ServletException
     * @throws IOException
     */
    public void login() throws ServletException, IOException {

        String code = getRequestParam(AttributeConst.EMP_CODE);
        String plainPass = getRequestParam(AttributeConst.EMP_PASS);
        String pepper = getContextScope(PropertyConst.PEPPER);

        //�L���ȏ]�ƈ����F�؂���
        Boolean isValidEmployee = service.validateLogin(code, plainPass, pepper);

        if (isValidEmployee) {
            //�F�ؐ����̏ꍇ

            //CSRF�΍� token�̃`�F�b�N
            if (checkToken()) {

                //���O�C�������]�ƈ���DB�f�[�^���擾
                EmployeeView ev = service.findOne(code, plainPass, pepper);
                //�Z�b�V�����Ƀ��O�C�������]�ƈ���ݒ�
                putSessionScope(AttributeConst.LOGIN_EMP, ev);
                //�Z�b�V�����Ƀ��O�C�������̃t���b�V�����b�Z�[�W��ݒ�
                putSessionScope(AttributeConst.FLUSH, MessageConst.I_LOGINED.getMessage());
                //�g�b�v�y�[�W�փ��_�C���N�g
                redirect(ForwardConst.ACT_TOP, ForwardConst.CMD_INDEX);
            }
        } else {
            //�F�؎��s�̏ꍇ

            //CSRF�΍��p�g�[�N����ݒ�
            putRequestScope(AttributeConst.TOKEN, getTokenId());
            //�F�؎��s�G���[���b�Z�[�W�\���t���O�����Ă�
            putRequestScope(AttributeConst.LOGIN_ERR, true);
            //���͂��ꂽ�]�ƈ��R�[�h��ݒ�
            putRequestScope(AttributeConst.EMP_CODE, code);

            //���O�C����ʂ�\��
            forward(ForwardConst.FW_LOGIN);
        }
    }
    
    /**
     * ���O�A�E�g�������s��
     * @throws ServletException
     * @throws IOException
     */
    public void logout() throws ServletException, IOException {

        //�Z�b�V�������烍�O�C���]�ƈ��̃p�����[�^���폜
        removeSessionScope(AttributeConst.LOGIN_EMP);

        //�Z�b�V�����Ƀ��O�A�E�g���̃t���b�V�����b�Z�[�W��ǉ�
        putSessionScope(AttributeConst.FLUSH, MessageConst.I_LOGOUT.getMessage());

        //���O�C����ʂɃ��_�C���N�g
        redirect(ForwardConst.ACT_AUTH, ForwardConst.CMD_SHOW_LOGIN);

    }

}
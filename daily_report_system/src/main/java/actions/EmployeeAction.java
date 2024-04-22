package actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.EmployeeView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import services.EmployeeService;

/**
 * �]�ƈ��Ɋւ�鏈�����s��Action�N���X
 *
 */
public class EmployeeAction extends ActionBase {

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
     * �ꗗ��ʂ�\������
     * @throws ServletException
     * @throws IOException
     */
    public void index() throws ServletException, IOException {

        //�w�肳�ꂽ�y�[�W���̈ꗗ��ʂɕ\������f�[�^���擾
        int page = getPage();
        List<EmployeeView> employees = service.getPerPage(page);

        //�S�Ă̏]�ƈ��f�[�^�̌������擾
        long employeeCount = service.countAll();

        putRequestScope(AttributeConst.EMPLOYEES, employees); //�擾�����]�ƈ��f�[�^
        putRequestScope(AttributeConst.EMP_COUNT, employeeCount); //�S�Ă̏]�ƈ��f�[�^�̌���
        putRequestScope(AttributeConst.PAGE, page); //�y�[�W��
        putRequestScope(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE); //1�y�[�W�ɕ\�����郌�R�[�h�̐�

        //�Z�b�V�����Ƀt���b�V�����b�Z�[�W���ݒ肳��Ă���ꍇ�̓��N�G�X�g�X�R�[�v�Ɉڂ��ւ��A�Z�b�V��������͍폜����
        String flush = getSessionScope(AttributeConst.FLUSH);
        if (flush != null) {
            putRequestScope(AttributeConst.FLUSH, flush);
            removeSessionScope(AttributeConst.FLUSH);
        }

        //�ꗗ��ʂ�\��
        forward(ForwardConst.FW_EMP_INDEX);

    }
    /**
     * �V�K�o�^��ʂ�\������
     * @throws ServletException
     * @throws IOException
     */
    public void entryNew() throws ServletException, IOException {

        putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF�΍��p�g�[�N��
        putRequestScope(AttributeConst.EMPLOYEE, new EmployeeView()); //��̏]�ƈ��C���X�^���X

        //�V�K�o�^��ʂ�\��
        forward(ForwardConst.FW_EMP_NEW);
    }

}
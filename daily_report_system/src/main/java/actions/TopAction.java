package actions;

import java.io.IOException;
import java.util.List; //�ǋL

import javax.servlet.ServletException;

import actions.views.EmployeeView; //�ǋL
import actions.views.ReportView; //�ǋL
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;  //�ǋL
import services.ReportService;  //�ǋL

/**
 * �g�b�v�y�[�W�Ɋւ��鏈�����s��Action�N���X
 *
 */
public class TopAction extends ActionBase {

    private ReportService service; //�ǋL

    /**
     * index���\�b�h�����s����
     */
    @Override
    public void process() throws ServletException, IOException {

        service = new ReportService(); //�ǋL

        //���\�b�h�����s
        invoke();

        service.close(); //�ǋL

    }

    /**
     * �ꗗ��ʂ�\������
     */
    public void index() throws ServletException, IOException {

        // �ȉ��ǋL

        //�Z�b�V�������烍�O�C�����̏]�ƈ������擾
        EmployeeView loginEmployee = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);

        //���O�C�����̏]�ƈ����쐬��������f�[�^���A�w�肳�ꂽ�y�[�W���̈ꗗ��ʂɕ\�����镪�擾����
        int page = getPage();
        List<ReportView> reports = service.getMinePerPage(loginEmployee, page);

        //���O�C�����̏]�ƈ����쐬��������f�[�^�̌������擾
        long myReportsCount = service.countAllMine(loginEmployee);

        putRequestScope(AttributeConst.REPORTS, reports); //�擾��������f�[�^
        putRequestScope(AttributeConst.REP_COUNT, myReportsCount); //���O�C�����̏]�ƈ����쐬��������̐�
        putRequestScope(AttributeConst.PAGE, page); //�y�[�W��
        putRequestScope(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE); //1�y�[�W�ɕ\�����郌�R�[�h�̐�

        //�������܂ŒǋL

        //�Z�b�V�����Ƀt���b�V�����b�Z�[�W���ݒ肳��Ă���ꍇ�̓��N�G�X�g�X�R�[�v�Ɉڂ��ւ��A�Z�b�V��������͍폜����
        String flush = getSessionScope(AttributeConst.FLUSH);
        if (flush != null) {
            putRequestScope(AttributeConst.FLUSH, flush);
            removeSessionScope(AttributeConst.FLUSH);
        }

        //�ꗗ��ʂ�\��
        forward(ForwardConst.FW_TOP_INDEX);
    }

}

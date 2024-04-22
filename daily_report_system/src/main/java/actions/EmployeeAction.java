package actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.EmployeeView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import constants.MessageConst;
import constants.PropertyConst;
import services.EmployeeService;

// http://localhost:8080/daily_report_system/?action=Employee&command=index

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
    
    /**
     * �V�K�o�^���s��
     * @throws ServletException
     * @throws IOException
     */
    public void create() throws ServletException, IOException {

        //CSRF�΍� token�̃`�F�b�N
        if (checkToken()) {

            //�p�����[�^�̒l�����ɏ]�ƈ����̃C���X�^���X���쐬����
            EmployeeView ev = new EmployeeView(
                    null,
                    getRequestParam(AttributeConst.EMP_CODE),
                    getRequestParam(AttributeConst.EMP_NAME),
                    getRequestParam(AttributeConst.EMP_PASS),
                    toNumber(getRequestParam(AttributeConst.EMP_ADMIN_FLG)),
                    null,
                    null,
                    AttributeConst.DEL_FLAG_FALSE.getIntegerValue());

            //�A�v���P�[�V�����X�R�[�v����pepper��������擾
            String pepper = getContextScope(PropertyConst.PEPPER);

            //�]�ƈ����o�^
            List<String> errors = service.create(ev, pepper);

            if (errors.size() > 0) {
                //�o�^���ɃG���[���������ꍇ

                putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF�΍��p�g�[�N��
                putRequestScope(AttributeConst.EMPLOYEE, ev); //���͂��ꂽ�]�ƈ����
                putRequestScope(AttributeConst.ERR, errors); //�G���[�̃��X�g

                //�V�K�o�^��ʂ��ĕ\��
                forward(ForwardConst.FW_EMP_NEW);

            } else {
                //�o�^���ɃG���[���Ȃ������ꍇ

                //�Z�b�V�����ɓo�^�����̃t���b�V�����b�Z�[�W��ݒ�
                putSessionScope(AttributeConst.FLUSH, MessageConst.I_REGISTERED.getMessage());

                //�ꗗ��ʂɃ��_�C���N�g
                redirect(ForwardConst.ACT_EMP, ForwardConst.CMD_INDEX);
            }

        }
    }
    
    public void show() throws ServletException, IOException {

        //id�������ɏ]�ƈ��f�[�^���擾����
        EmployeeView ev = service.findOne(toNumber(getRequestParam(AttributeConst.EMP_ID)));

        if (ev == null || ev.getDeleteFlag() == AttributeConst.DEL_FLAG_TRUE.getIntegerValue()) {

            //�f�[�^���擾�ł��Ȃ������A�܂��͘_���폜����Ă���ꍇ�̓G���[��ʂ�\��
            forward(ForwardConst.FW_ERR_UNKNOWN);
            return;
        }

        putRequestScope(AttributeConst.EMPLOYEE, ev); //�擾�����]�ƈ����

        //�ڍ׉�ʂ�\��
        forward(ForwardConst.FW_EMP_SHOW);
    }
    
    /**
     * �ҏW��ʂ�\������
     * @throws ServletException
     * @throws IOException
     */
    public void edit() throws ServletException, IOException {

        //id�������ɏ]�ƈ��f�[�^���擾����
        EmployeeView ev = service.findOne(toNumber(getRequestParam(AttributeConst.EMP_ID)));

        if (ev == null || ev.getDeleteFlag() == AttributeConst.DEL_FLAG_TRUE.getIntegerValue()) {

            //�f�[�^���擾�ł��Ȃ������A�܂��͘_���폜����Ă���ꍇ�̓G���[��ʂ�\��
            forward(ForwardConst.FW_ERR_UNKNOWN);
            return;
        }

        putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF�΍��p�g�[�N��
        putRequestScope(AttributeConst.EMPLOYEE, ev); //�擾�����]�ƈ����

        //�ҏW��ʂ�\������
        forward(ForwardConst.FW_EMP_EDIT);

    }
    
    /**
     * �X�V���s��
     * @throws ServletException
     * @throws IOException
     */
    public void update() throws ServletException, IOException {

        //CSRF�΍� token�̃`�F�b�N
        if (checkToken()) {
            //�p�����[�^�̒l�����ɏ]�ƈ����̃C���X�^���X���쐬����
            EmployeeView ev = new EmployeeView(
                    toNumber(getRequestParam(AttributeConst.EMP_ID)),
                    getRequestParam(AttributeConst.EMP_CODE),
                    getRequestParam(AttributeConst.EMP_NAME),
                    getRequestParam(AttributeConst.EMP_PASS),
                    toNumber(getRequestParam(AttributeConst.EMP_ADMIN_FLG)),
                    null,
                    null,
                    AttributeConst.DEL_FLAG_FALSE.getIntegerValue());

            //�A�v���P�[�V�����X�R�[�v����pepper��������擾
            String pepper = getContextScope(PropertyConst.PEPPER);

            //�]�ƈ����X�V
            List<String> errors = service.update(ev, pepper);

            if (errors.size() > 0) {
                //�X�V���ɃG���[�����������ꍇ

                putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF�΍��p�g�[�N��
                putRequestScope(AttributeConst.EMPLOYEE, ev); //���͂��ꂽ�]�ƈ����
                putRequestScope(AttributeConst.ERR, errors); //�G���[�̃��X�g

                //�ҏW��ʂ��ĕ\��
                forward(ForwardConst.FW_EMP_EDIT);
            } else {
                //�X�V���ɃG���[���Ȃ������ꍇ

                //�Z�b�V�����ɍX�V�����̃t���b�V�����b�Z�[�W��ݒ�
                putSessionScope(AttributeConst.FLUSH, MessageConst.I_UPDATED.getMessage());

                //�ꗗ��ʂɃ��_�C���N�g
                redirect(ForwardConst.ACT_EMP, ForwardConst.CMD_INDEX);
            }
        }
    }
    
    /**
     * �_���폜���s��
     * @throws ServletException
     * @throws IOException
     */
    public void destroy() throws ServletException, IOException {

        //CSRF�΍� token�̃`�F�b�N
        if (checkToken()) {

            //id�������ɏ]�ƈ��f�[�^��_���폜����
            service.destroy(toNumber(getRequestParam(AttributeConst.EMP_ID)));

            //�Z�b�V�����ɍ폜�����̃t���b�V�����b�Z�[�W��ݒ�
            putSessionScope(AttributeConst.FLUSH, MessageConst.I_DELETED.getMessage());

            //�ꗗ��ʂɃ��_�C���N�g
            redirect(ForwardConst.ACT_EMP, ForwardConst.CMD_INDEX);
        }
    }
}
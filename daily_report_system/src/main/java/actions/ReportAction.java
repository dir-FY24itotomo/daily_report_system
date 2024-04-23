package actions;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.EmployeeView;
import actions.views.ReportView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import constants.MessageConst;
import services.ReportService;

/**
 * ����Ɋւ��鏈�����s��Action�N���X
 *
 */
public class ReportAction extends ActionBase {

    private ReportService service;

    /**
     * ���\�b�h�����s����
     */
    @Override
    public void process() throws ServletException, IOException {

        service = new ReportService();

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

        //�w�肳�ꂽ�y�[�W���̈ꗗ��ʂɕ\���������f�[�^���擾
        int page = getPage();
        List<ReportView> reports = service.getAllPerPage(page);

        //�S����f�[�^�̌������擾
        long reportsCount = service.countAll();

        putRequestScope(AttributeConst.REPORTS, reports); //�擾��������f�[�^
        putRequestScope(AttributeConst.REP_COUNT, reportsCount); //�S�Ă̓���f�[�^�̌���
        putRequestScope(AttributeConst.PAGE, page); //�y�[�W��
        putRequestScope(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE); //1�y�[�W�ɕ\�����郌�R�[�h�̐�

        //�Z�b�V�����Ƀt���b�V�����b�Z�[�W���ݒ肳��Ă���ꍇ�̓��N�G�X�g�X�R�[�v�Ɉڂ��ւ��A�Z�b�V��������͍폜����
        String flush = getSessionScope(AttributeConst.FLUSH);
        if (flush != null) {
            putRequestScope(AttributeConst.FLUSH, flush);
            removeSessionScope(AttributeConst.FLUSH);
        }

        //�ꗗ��ʂ�\��
        forward(ForwardConst.FW_REP_INDEX);
    }
    
    /**
     * �V�K�o�^��ʂ�\������
     * @throws ServletException
     * @throws IOException
     */
    public void entryNew() throws ServletException, IOException {

        putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF�΍��p�g�[�N��

        //������̋�C���X�^���X�ɁA����̓��t�������̓��t��ݒ肷��
        ReportView rv = new ReportView();
        rv.setReportDate(LocalDate.now());
        putRequestScope(AttributeConst.REPORT, rv); //���t�̂ݐݒ�ς݂̓���C���X�^���X

        //�V�K�o�^��ʂ�\��
        forward(ForwardConst.FW_REP_NEW);

    }
    
    /**
     * �V�K�o�^���s��
     * @throws ServletException
     * @throws IOException
     */
    public void create() throws ServletException, IOException {

        //CSRF�΍� token�̃`�F�b�N
        if (checkToken()) {

            //����̓��t�����͂���Ă��Ȃ���΁A�����̓��t��ݒ�
            LocalDate day = null;
            if (getRequestParam(AttributeConst.REP_DATE) == null
                    || getRequestParam(AttributeConst.REP_DATE).equals("")) {
                day = LocalDate.now();
            } else {
                day = LocalDate.parse(getRequestParam(AttributeConst.REP_DATE));
            }

            //�Z�b�V�������烍�O�C�����̏]�ƈ������擾
            EmployeeView ev = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);

            //�p�����[�^�̒l�����Ƃɓ�����̃C���X�^���X���쐬����
            ReportView rv = new ReportView(
                    null,
                    ev, //���O�C�����Ă���]�ƈ����A����쐬�҂Ƃ��ēo�^����
                    day,
                    getRequestParam(AttributeConst.REP_TITLE),
                    getRequestParam(AttributeConst.REP_CONTENT),
                    null,
                    null);

            //������o�^
            List<String> errors = service.create(rv);

            if (errors.size() > 0) {
                //�o�^���ɃG���[���������ꍇ

                putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF�΍��p�g�[�N��
                putRequestScope(AttributeConst.REPORT, rv);//���͂��ꂽ������
                putRequestScope(AttributeConst.ERR, errors);//�G���[�̃��X�g

                //�V�K�o�^��ʂ��ĕ\��
                forward(ForwardConst.FW_REP_NEW);

            } else {
                //�o�^���ɃG���[���Ȃ������ꍇ

                //�Z�b�V�����ɓo�^�����̃t���b�V�����b�Z�[�W��ݒ�
                putSessionScope(AttributeConst.FLUSH, MessageConst.I_REGISTERED.getMessage());

                //�ꗗ��ʂɃ��_�C���N�g
                redirect(ForwardConst.ACT_REP, ForwardConst.CMD_INDEX);
            }
        }
    }
    /**
     * �ڍ׉�ʂ�\������
     * @throws ServletException
     * @throws IOException
     */
    public void show() throws ServletException, IOException {

        //id�������ɓ���f�[�^���擾����
        ReportView rv = service.findOne(toNumber(getRequestParam(AttributeConst.REP_ID)));

        if (rv == null) {
            //�Y���̓���f�[�^�����݂��Ȃ��ꍇ�̓G���[��ʂ�\��
            forward(ForwardConst.FW_ERR_UNKNOWN);

        } else {

            putRequestScope(AttributeConst.REPORT, rv); //�擾��������f�[�^

            //�ڍ׉�ʂ�\��
            forward(ForwardConst.FW_REP_SHOW);
        }
    }
    /**
     * �ҏW��ʂ�\������
     * @throws ServletException
     * @throws IOException
     */
    public void edit() throws ServletException, IOException {

        //id�������ɓ���f�[�^���擾����
        ReportView rv = service.findOne(toNumber(getRequestParam(AttributeConst.REP_ID)));

        //�Z�b�V�������烍�O�C�����̏]�ƈ������擾
        EmployeeView ev = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);

        if (rv == null || ev.getId() != rv.getEmployee().getId()) {
            //�Y���̓���f�[�^�����݂��Ȃ��A�܂���
            //���O�C�����Ă���]�ƈ�������̍쐬�҂łȂ��ꍇ�̓G���[��ʂ�\��
            forward(ForwardConst.FW_ERR_UNKNOWN);

        } else {

            putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF�΍��p�g�[�N��
            putRequestScope(AttributeConst.REPORT, rv); //�擾��������f�[�^

            //�ҏW��ʂ�\��
            forward(ForwardConst.FW_REP_EDIT);
        }

    }
    
    /**
     * �X�V���s��
     * @throws ServletException
     * @throws IOException
     */
    public void update() throws ServletException, IOException {

        //CSRF�΍� token�̃`�F�b�N
        if (checkToken()) {

            //id�������ɓ���f�[�^���擾����
            ReportView rv = service.findOne(toNumber(getRequestParam(AttributeConst.REP_ID)));

            //���͂��ꂽ������e��ݒ肷��
            rv.setReportDate(toLocalDate(getRequestParam(AttributeConst.REP_DATE)));
            rv.setTitle(getRequestParam(AttributeConst.REP_TITLE));
            rv.setContent(getRequestParam(AttributeConst.REP_CONTENT));

            //����f�[�^���X�V����
            List<String> errors = service.update(rv);

            if (errors.size() > 0) {
                //�X�V���ɃG���[�����������ꍇ

                putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF�΍��p�g�[�N��
                putRequestScope(AttributeConst.REPORT, rv); //���͂��ꂽ������
                putRequestScope(AttributeConst.ERR, errors); //�G���[�̃��X�g

                //�ҏW��ʂ��ĕ\��
                forward(ForwardConst.FW_REP_EDIT);
            } else {
                //�X�V���ɃG���[���Ȃ������ꍇ

                //�Z�b�V�����ɍX�V�����̃t���b�V�����b�Z�[�W��ݒ�
                putSessionScope(AttributeConst.FLUSH, MessageConst.I_UPDATED.getMessage());

                //�ꗗ��ʂɃ��_�C���N�g
                redirect(ForwardConst.ACT_REP, ForwardConst.CMD_INDEX);

            }
        }
    }
}
package services;

import java.time.LocalDateTime;
import java.util.List;

import actions.views.EmployeeConverter;
import actions.views.EmployeeView;
import actions.views.ReportConverter;
import actions.views.ReportView;
import constants.JpaConst;
import models.Report;
import models.validators.ReportValidator;

/**
 * ����e�[�u���̑���Ɋւ�鏈�����s���N���X
 */
public class ReportService extends ServiceBase {

    /**
     * �w�肵���]�ƈ����쐬��������f�[�^���A�w�肳�ꂽ�y�[�W���̈ꗗ��ʂɕ\�����镪�擾��ReportView�̃��X�g�ŕԋp����
     * @param employee �]�ƈ�
     * @param page �y�[�W��
     * @return �ꗗ��ʂɕ\������f�[�^�̃��X�g
     */
    public List<ReportView> getMinePerPage(EmployeeView employee, int page) {

        List<Report> reports = em.createNamedQuery(JpaConst.Q_REP_GET_ALL_MINE, Report.class)
                .setParameter(JpaConst.JPQL_PARM_EMPLOYEE, EmployeeConverter.toModel(employee))
                .setFirstResult(JpaConst.ROW_PER_PAGE * (page - 1))
                .setMaxResults(JpaConst.ROW_PER_PAGE)
                .getResultList();
        return ReportConverter.toViewList(reports);
    }

    /**
     * �w�肵���]�ƈ����쐬��������f�[�^�̌������擾���A�ԋp����
     * @param employee
     * @return ����f�[�^�̌���
     */
    public long countAllMine(EmployeeView employee) {

        long count = (long) em.createNamedQuery(JpaConst.Q_REP_COUNT_ALL_MINE, Long.class)
                .setParameter(JpaConst.JPQL_PARM_EMPLOYEE, EmployeeConverter.toModel(employee))
                .getSingleResult();

        return count;
    }

    /**
     * �w�肳�ꂽ�y�[�W���̈ꗗ��ʂɕ\���������f�[�^���擾���AReportView�̃��X�g�ŕԋp����
     * @param page �y�[�W��
     * @return �ꗗ��ʂɕ\������f�[�^�̃��X�g
     */
    public List<ReportView> getAllPerPage(int page) {

        List<Report> reports = em.createNamedQuery(JpaConst.Q_REP_GET_ALL, Report.class)
                .setFirstResult(JpaConst.ROW_PER_PAGE * (page - 1))
                .setMaxResults(JpaConst.ROW_PER_PAGE)
                .getResultList();
        return ReportConverter.toViewList(reports);
    }

    /**
     * ����e�[�u���̃f�[�^�̌������擾���A�ԋp����
     * @return �f�[�^�̌���
     */
    public long countAll() {
        long reports_count = (long) em.createNamedQuery(JpaConst.Q_REP_COUNT, Long.class)
                .getSingleResult();
        return reports_count;
    }

    /**
     * id�������Ɏ擾�����f�[�^��ReportView�̃C���X�^���X�ŕԋp����
     * @param id
     * @return �擾�f�[�^�̃C���X�^���X
     */
    public ReportView findOne(int id) {
        return ReportConverter.toView(findOneInternal(id));
    }

    /**
     * ��ʂ�����͂��ꂽ����̓o�^���e�����Ƀf�[�^��1���쐬���A����e�[�u���ɓo�^����
     * @param rv ����̓o�^���e
     * @return �o���f�[�V�����Ŕ��������G���[�̃��X�g
     */
    public List<String> create(ReportView rv) {
        List<String> errors = ReportValidator.validate(rv);
        if (errors.size() == 0) {
            LocalDateTime ldt = LocalDateTime.now();
            rv.setCreatedAt(ldt);
            rv.setUpdatedAt(ldt);
            createInternal(rv);
        }

        //�o���f�[�V�����Ŕ��������G���[��ԋp�i�G���[���Ȃ����0���̋󃊃X�g�j
        return errors;
    }

    /**
     * ��ʂ�����͂��ꂽ����̓o�^���e�����ɁA����f�[�^���X�V����
     * @param rv ����̍X�V���e
     * @return �o���f�[�V�����Ŕ��������G���[�̃��X�g
     */
    public List<String> update(ReportView rv) {

        //�o���f�[�V�������s��
        List<String> errors = ReportValidator.validate(rv);

        if (errors.size() == 0) {

            //�X�V���������ݎ����ɐݒ�
            LocalDateTime ldt = LocalDateTime.now();
            rv.setUpdatedAt(ldt);

            updateInternal(rv);
        }

        //�o���f�[�V�����Ŕ��������G���[��ԋp�i�G���[���Ȃ����0���̋󃊃X�g�j
        return errors;
    }

    /**
     * id�������Ƀf�[�^��1���擾����
     * @param id
     * @return �擾�f�[�^�̃C���X�^���X
     */
    private Report findOneInternal(int id) {
        return em.find(Report.class, id);
    }

    /**
     * ����f�[�^��1���o�^����
     * @param rv ����f�[�^
     */
    private void createInternal(ReportView rv) {

        em.getTransaction().begin();
        em.persist(ReportConverter.toModel(rv));
        em.getTransaction().commit();

    }

    /**
     * ����f�[�^���X�V����
     * @param rv ����f�[�^
     */
    private void updateInternal(ReportView rv) {

        em.getTransaction().begin();
        Report r = findOneInternal(rv.getId());
        ReportConverter.copyViewToModel(r, rv);
        em.getTransaction().commit();

    }

}
package services;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.NoResultException;

import actions.views.EmployeeConverter;
import actions.views.EmployeeView;
import constants.JpaConst;
import models.Employee;
import models.validators.EmployeeValidator;
import utils.EncryptUtil;

/**
 * �]�ƈ��e�[�u���̑���Ɋւ�鏈�����s���N���X
 */
public class EmployeeService extends ServiceBase {

    /**
     * �w�肳�ꂽ�y�[�W���̈ꗗ��ʂɕ\������f�[�^���擾���AEmployeeView�̃��X�g�ŕԋp����
     * @param page �y�[�W��
     * @return �\������f�[�^�̃��X�g
     */
    public List<EmployeeView> getPerPage(int page) {
        List<Employee> employees = em.createNamedQuery(JpaConst.Q_EMP_GET_ALL, Employee.class)
                .setFirstResult(JpaConst.ROW_PER_PAGE * (page - 1))
                .setMaxResults(JpaConst.ROW_PER_PAGE)
                .getResultList();

        return EmployeeConverter.toViewList(employees);
    }

    /**
     * �]�ƈ��e�[�u���̃f�[�^�̌������擾���A�ԋp����
     * @return �]�ƈ��e�[�u���̃f�[�^�̌���
     */
    public long countAll() {
        long empCount = (long) em.createNamedQuery(JpaConst.Q_EMP_COUNT, Long.class)
                .getSingleResult();

        return empCount;
    }

    /**
     * �Ј��ԍ��A�p�X���[�h�������Ɏ擾�����f�[�^��EmployeeView�̃C���X�^���X�ŕԋp����
     * @param code �Ј��ԍ�
     * @param plainPass �p�X���[�h������
     * @param pepper pepper������
     * @return �擾�f�[�^�̃C���X�^���X �擾�ł��Ȃ��ꍇnull
     */
    public EmployeeView findOne(String code, String plainPass, String pepper) {
        Employee e = null;
        try {
            //�p�X���[�h�̃n�b�V����
            String pass = EncryptUtil.getPasswordEncrypt(plainPass, pepper);

            //�Ј��ԍ��ƃn�b�V�����σp�X���[�h�������ɖ��폜�̏]�ƈ���1���擾����
            e = em.createNamedQuery(JpaConst.Q_EMP_GET_BY_CODE_AND_PASS, Employee.class)
                    .setParameter(JpaConst.JPQL_PARM_CODE, code)
                    .setParameter(JpaConst.JPQL_PARM_PASSWORD, pass)
                    .getSingleResult();

        } catch (NoResultException ex) {
        }

        return EmployeeConverter.toView(e);

    }

    /**
     * id�������Ɏ擾�����f�[�^��EmployeeView�̃C���X�^���X�ŕԋp����
     * @param id
     * @return �擾�f�[�^�̃C���X�^���X
     */
    public EmployeeView findOne(int id) {
        Employee e = findOneInternal(id);
        return EmployeeConverter.toView(e);
    }

    /**
     * �Ј��ԍ��������ɊY������f�[�^�̌������擾���A�ԋp����
     * @param code �Ј��ԍ�
     * @return �Y������f�[�^�̌���
     */
    public long countByCode(String code) {

        //�w�肵���Ј��ԍ���ێ�����]�ƈ��̌������擾����
        long employees_count = (long) em.createNamedQuery(JpaConst.Q_EMP_COUNT_REGISTERED_BY_CODE, Long.class)
                .setParameter(JpaConst.JPQL_PARM_CODE, code)
                .getSingleResult();
        return employees_count;
    }

    /**
     * ��ʂ�����͂��ꂽ�]�ƈ��̓o�^���e�����Ƀf�[�^��1���쐬���A�]�ƈ��e�[�u���ɓo�^����
     * @param ev ��ʂ�����͂��ꂽ�]�ƈ��̓o�^���e
     * @param pepper pepper������
     * @return �o���f�[�V������o�^�������ɔ��������G���[�̃��X�g
     */
    public List<String> create(EmployeeView ev, String pepper) {

        //�p�X���[�h���n�b�V�������Đݒ�
        String pass = EncryptUtil.getPasswordEncrypt(ev.getPassword(), pepper);
        ev.setPassword(pass);

        //�o�^�����A�X�V�����͌��ݎ�����ݒ肷��
        LocalDateTime now = LocalDateTime.now();
        ev.setCreatedAt(now);
        ev.setUpdatedAt(now);

        //�o�^���e�̃o���f�[�V�������s��
        List<String> errors = EmployeeValidator.validate(this, ev, true, true);

        //�o���f�[�V�����G���[���Ȃ���΃f�[�^��o�^����
        if (errors.size() == 0) {
            create(ev);
        }

        //�G���[��ԋp�i�G���[���Ȃ����0���̋󃊃X�g�j
        return errors;
    }

    /**
     * ��ʂ�����͂��ꂽ�]�ƈ��̍X�V���e�����Ƀf�[�^��1���쐬���A�]�ƈ��e�[�u�����X�V����
     * @param ev ��ʂ�����͂��ꂽ�]�ƈ��̓o�^���e
     * @param pepper pepper������
     * @return �o���f�[�V������X�V�������ɔ��������G���[�̃��X�g
     */
    public List<String> update(EmployeeView ev, String pepper) {

        //id�������ɓo�^�ς݂̏]�ƈ������擾����
        EmployeeView savedEmp = findOne(ev.getId());

        boolean validateCode = false;
        if (!savedEmp.getCode().equals(ev.getCode())) {
            //�Ј��ԍ����X�V����ꍇ

            //�Ј��ԍ��ɂ��Ẵo���f�[�V�������s��
            validateCode = true;
            //�ύX��̎Ј��ԍ���ݒ肷��
            savedEmp.setCode(ev.getCode());
        }

        boolean validatePass = false;
        if (ev.getPassword() != null && !ev.getPassword().equals("")) {
            //�p�X���[�h�ɓ��͂�����ꍇ

            //�p�X���[�h�ɂ��Ẵo���f�[�V�������s��
            validatePass = true;

            //�ύX��̃p�X���[�h���n�b�V�������ݒ肷��
            savedEmp.setPassword(
                    EncryptUtil.getPasswordEncrypt(ev.getPassword(), pepper));
        }

        savedEmp.setName(ev.getName()); //�ύX��̎�����ݒ肷��
        savedEmp.setAdminFlag(ev.getAdminFlag()); //�ύX��̊Ǘ��҃t���O��ݒ肷��

        //�X�V�����Ɍ��ݎ�����ݒ肷��
        LocalDateTime today = LocalDateTime.now();
        savedEmp.setUpdatedAt(today);

        //�X�V���e�ɂ��ăo���f�[�V�������s��
        List<String> errors = EmployeeValidator.validate(this, savedEmp, validateCode, validatePass);

        //�o���f�[�V�����G���[���Ȃ���΃f�[�^���X�V����
        if (errors.size() == 0) {
            update(savedEmp);
        }

        //�G���[��ԋp�i�G���[���Ȃ����0���̋󃊃X�g�j
        return errors;
    }

    /**
     * id�������ɏ]�ƈ��f�[�^��_���폜����
     * @param id
     */
    public void destroy(Integer id) {

        //id�������ɓo�^�ς݂̏]�ƈ������擾����
        EmployeeView savedEmp = findOne(id);

        //�X�V�����Ɍ��ݎ�����ݒ肷��
        LocalDateTime today = LocalDateTime.now();
        savedEmp.setUpdatedAt(today);

        //�_���폜�t���O�����Ă�
        savedEmp.setDeleteFlag(JpaConst.EMP_DEL_TRUE);

        //�X�V�������s��
        update(savedEmp);

    }

    /**
     * �Ј��ԍ��ƃp�X���[�h�������Ɍ������A�f�[�^���擾�ł��邩�ǂ����ŔF�،��ʂ�ԋp����
     * @param code �Ј��ԍ�
     * @param plainPass �p�X���[�h
     * @param pepper pepper������
     * @return �F�،��ʂ�ԋp��(����:true ���s:false)
     */
    public Boolean validateLogin(String code, String plainPass, String pepper) {

        boolean isValidEmployee = false;
        if (code != null && !code.equals("") && plainPass != null && !plainPass.equals("")) {
            EmployeeView ev = findOne(code, plainPass, pepper);

            if (ev != null && ev.getId() != null) {

                //�f�[�^���擾�ł����ꍇ�A�F�ؐ���
                isValidEmployee = true;
            }
        }

        //�F�،��ʂ�ԋp����
        return isValidEmployee;
    }

    /**
     * id�������Ƀf�[�^��1���擾���AEmployee�̃C���X�^���X�ŕԋp����
     * @param id
     * @return �擾�f�[�^�̃C���X�^���X
     */
    private Employee findOneInternal(int id) {
        Employee e = em.find(Employee.class, id);

        return e;
    }

    /**
     * �]�ƈ��f�[�^��1���o�^����
     * @param ev �]�ƈ��f�[�^
     * @return �o�^����(����:true ���s:false)
     */
    private void create(EmployeeView ev) {

        em.getTransaction().begin();
        em.persist(EmployeeConverter.toModel(ev));
        em.getTransaction().commit();

    }

    /**
     * �]�ƈ��f�[�^���X�V����
     * @param ev ��ʂ�����͂��ꂽ�]�ƈ��̓o�^���e
     */
    private void update(EmployeeView ev) {

        em.getTransaction().begin();
        Employee e = findOneInternal(ev.getId());
        EmployeeConverter.copyViewToModel(e, ev);
        em.getTransaction().commit();

    }

}

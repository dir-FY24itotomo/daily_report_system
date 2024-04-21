package models.validators;

import java.util.ArrayList;
import java.util.List;

import actions.views.EmployeeView;
import constants.MessageConst;
import services.EmployeeService;

/**
 * �]�ƈ��C���X�^���X�ɐݒ肳��Ă���l�̃o���f�[�V�������s���N���X
 *
 */
public class EmployeeValidator {

    /**
     * �]�ƈ��C���X�^���X�̊e���ڂɂ��ăo���f�[�V�������s��
     * @param service �Ăяo����Service�N���X�̃C���X�^���X
     * @param ev EmployeeView�̃C���X�^���X
     * @param codeDuplicateCheckFlag �Ј��ԍ��̏d���`�F�b�N�����{���邩�ǂ���(���{����:true ���{���Ȃ�:false)
     * @param passwordCheckFlag �p�X���[�h�̓��̓`�F�b�N�����{���邩�ǂ���(���{����:true ���{���Ȃ�:false)
     * @return �G���[�̃��X�g
     */
    public static List<String> validate(
            EmployeeService service, EmployeeView ev, Boolean codeDuplicateCheckFlag, Boolean passwordCheckFlag) {
        List<String> errors = new ArrayList<String>();

        //�Ј��ԍ��̃`�F�b�N
        String codeError = validateCode(service, ev.getCode(), codeDuplicateCheckFlag);
        if (!codeError.equals("")) {
            errors.add(codeError);
        }

        //�����̃`�F�b�N
        String nameError = validateName(ev.getName());
        if (!nameError.equals("")) {
            errors.add(nameError);
        }

        //�p�X���[�h�̃`�F�b�N
        String passError = validatePassword(ev.getPassword(), passwordCheckFlag);
        if (!passError.equals("")) {
            errors.add(passError);
        }

        return errors;
    }

    /**
     * �Ј��ԍ��̓��̓`�F�b�N���s���A�G���[���b�Z�[�W��ԋp
     * @param service EmployeeService�̃C���X�^���X
     * @param code �Ј��ԍ�
     * @param codeDuplicateCheckFlag �Ј��ԍ��̏d���`�F�b�N�����{���邩�ǂ���(���{����:true ���{���Ȃ�:false)
     * @return �G���[���b�Z�[�W
     */
    private static String validateCode(EmployeeService service, String code, Boolean codeDuplicateCheckFlag) {

        //���͒l���Ȃ���΃G���[���b�Z�[�W��ԋp
        if (code == null || code.equals("")) {
            return MessageConst.E_NOEMP_CODE.getMessage();
        }

        if (codeDuplicateCheckFlag) {
            //�Ј��ԍ��̏d���`�F�b�N�����{

            long employeesCount = isDuplicateEmployee(service, code);

            //����Ј��ԍ������ɓo�^����Ă���ꍇ�̓G���[���b�Z�[�W��ԋp
            if (employeesCount > 0) {
                return MessageConst.E_EMP_CODE_EXIST.getMessage();
            }
        }

        //�G���[���Ȃ��ꍇ�͋󕶎���ԋp
        return "";
    }

    /**
     * @param service EmployeeService�̃C���X�^���X
     * @param code �Ј��ԍ�
     * @return �]�ƈ��e�[�u���ɓo�^����Ă��铯��Ј��ԍ��̃f�[�^�̌���
     */
    private static long isDuplicateEmployee(EmployeeService service, String code) {

        long employeesCount = service.countByCode(code);
        return employeesCount;
    }

    /**
     * �����ɓ��͒l�����邩���`�F�b�N���A���͒l���Ȃ���΃G���[���b�Z�[�W��ԋp
     * @param name ����
     * @return �G���[���b�Z�[�W
     */
    private static String validateName(String name) {

        if (name == null || name.equals("")) {
            return MessageConst.E_NONAME.getMessage();
        }

        //���͒l������ꍇ�͋󕶎���ԋp
        return "";
    }

    /**
     * �p�X���[�h�̓��̓`�F�b�N���s���A�G���[���b�Z�[�W��ԋp
     * @param password �p�X���[�h
     * @param passwordCheckFlag �p�X���[�h�̓��̓`�F�b�N�����{���邩�ǂ���(���{����:true ���{���Ȃ�:false)
     * @return �G���[���b�Z�[�W
     */
    private static String validatePassword(String password, Boolean passwordCheckFlag) {

        //���̓`�F�b�N�����{ ���� ���͒l���Ȃ���΃G���[���b�Z�[�W��ԋp
        if (passwordCheckFlag && (password == null || password.equals(""))) {
            return MessageConst.E_NOPASSWORD.getMessage();
        }

        //�G���[���Ȃ��ꍇ�͋󕶎���ԋp
        return "";
    }
}

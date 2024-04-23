package models.validators;

import java.util.ArrayList;
import java.util.List;

import actions.views.ReportView;
import constants.MessageConst;

/**
 * ����C���X�^���X�ɐݒ肳��Ă���l�̃o���f�[�V�������s���N���X
 */
public class ReportValidator {

    /**
     * ����C���X�^���X�̊e���ڂɂ��ăo���f�[�V�������s��
     * @param rv ����C���X�^���X
     * @return �G���[�̃��X�g
     */
    public static List<String> validate(ReportView rv) {
        List<String> errors = new ArrayList<String>();

        //�^�C�g���̃`�F�b�N
        String titleError = validateTitle(rv.getTitle());
        if (!titleError.equals("")) {
            errors.add(titleError);
        }

        //���e�̃`�F�b�N
        String contentError = validateContent(rv.getContent());
        if (!contentError.equals("")) {
            errors.add(contentError);
        }

        return errors;
    }

    /**
     * �^�C�g���ɓ��͒l�����邩���`�F�b�N���A���͒l���Ȃ���΃G���[���b�Z�[�W��ԋp
     * @param title �^�C�g��
     * @return �G���[���b�Z�[�W
     */
    private static String validateTitle(String title) {
        if (title == null || title.equals("")) {
            return MessageConst.E_NOTITLE.getMessage();
        }

        //���͒l������ꍇ�͋󕶎���ԋp
        return "";
    }

    /**
     * ���e�ɓ��͒l�����邩���`�F�b�N���A���͒l���Ȃ���΃G���[���b�Z�[�W��ԋp
     * @param content ���e
     * @return �G���[���b�Z�[�W
     */
    private static String validateContent(String content) {
        if (content == null || content.equals("")) {
            return MessageConst.E_NOCONTENT.getMessage();
        }

        //���͒l������ꍇ�͋󕶎���ԋp
        return "";
    }
}
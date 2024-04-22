package actions;

import java.io.IOException;

import javax.servlet.ServletException;

import constants.AttributeConst;
import constants.ForwardConst;

/**
 * �g�b�v�y�[�W�Ɋւ��鏈�����s��Action�N���X
 *
 */
public class TopAction extends ActionBase {

    /**
     * index���\�b�h�����s����
     */
    @Override
    public void process() throws ServletException, IOException {

        //���\�b�h�����s
        invoke();

    }

    /**
     * �ꗗ��ʂ�\������
     */
    public void index() throws ServletException, IOException {

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

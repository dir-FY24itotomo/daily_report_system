package actions;

import java.io.IOException;

import javax.servlet.ServletException;

import constants.ForwardConst;

/**
 * �G���[�������̏����s��Action�N���X
 *
 */
public class UnknownAction extends ActionBase {

    /**
     * ���ʃG���[��ʁu���T���̃y�[�W�͌�����܂���ł����B�v��\������
     */
    @Override
    public void process() throws ServletException, IOException {

        //�G���[��ʂ�\��
        forward(ForwardConst.FW_ERR_UNKNOWN);

    }
}
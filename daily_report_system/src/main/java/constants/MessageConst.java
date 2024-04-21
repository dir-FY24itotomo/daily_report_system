package constants;

/**
 * �e�o�̓��b�Z�[�W���`����Enum�N���X
 *
 */
public enum MessageConst {

    //�F��
    I_LOGINED("���O�C�����܂���"),
    E_LOGINED("���O�C���Ɏ��s���܂����B"),
    I_LOGOUT("���O�A�E�g���܂����B"),

    //DB�X�V
    I_REGISTERED("�o�^���������܂����B"),
    I_UPDATED("�X�V���������܂����B"),
    I_DELETED("�폜���������܂����B"),

    //�o���f�[�V����
    E_NONAME("��������͂��Ă��������B"),
    E_NOPASSWORD("�p�X���[�h����͂��Ă��������B"),
    E_NOEMP_CODE("�Ј��ԍ�����͂��Ă��������B"),
    E_EMP_CODE_EXIST("���͂��ꂽ�Ј��ԍ��̏��͊��ɑ��݂��Ă��܂��B"),
    E_NOTITLE("�^�C�g������͂��Ă��������B"),
    E_NOCONTENT("���e����͂��Ă��������B");


    /**
     * ������
     */
    private final String text;

    /**
     * �R���X�g���N�^
     */
    private MessageConst(final String text) {
        this.text = text;
    }

    /**
     * �l(������)�擾
     */
    public String getMessage() {
        return this.text;
    }
}

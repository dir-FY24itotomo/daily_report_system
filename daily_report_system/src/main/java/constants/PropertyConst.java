package constants;

/**
 * �A�v���P�[�V�����X�R�[�v�̃p�����[�^�����`����Enum�N���X
 *
 */
public enum PropertyConst {

    //�y�b�p�[������
    PEPPER("pepper");

    private final String text;
    private PropertyConst(final String text) {
        this.text = text;
    }

    public String getValue() {
        return this.text;
    }
}
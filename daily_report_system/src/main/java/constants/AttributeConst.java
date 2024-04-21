package constants;

/**
 * ��ʂ̍��ڒl�����`����Enum�N���X
 *
 */
public enum AttributeConst {

    //�t���b�V�����b�Z�[�W
    FLUSH("flush"),

    //�ꗗ��ʋ���
    MAX_ROW("maxRow"),
    PAGE("page"),

    //���̓t�H�[������
    TOKEN("_token"),
    ERR("errors"),

    //���O�C�����̏]�ƈ�
    LOGIN_EMP("login_employee"),

    //���O�C�����
    LOGIN_ERR("loginError"),

    //�]�ƈ��Ǘ�
    EMPLOYEE("employee"),
    EMPLOYEES("employees"),
    EMP_COUNT("employees_count"),
    EMP_ID("id"),
    EMP_CODE("code"),
    EMP_PASS("password"),
    EMP_NAME("name"),
    EMP_ADMIN_FLG("admin_flag"),

    //�Ǘ��҃t���O
    ROLE_ADMIN(1),
    ROLE_GENERAL(0),

    //�폜�t���O
    DEL_FLAG_TRUE(1),
    DEL_FLAG_FALSE(0),

    //����Ǘ�
    REPORT("report"),
    REPORTS("reports"),
    REP_COUNT("reports_count"),
    REP_ID("id"),
    REP_DATE("report_date"),
    REP_TITLE("title"),
    REP_CONTENT("content_msg");

    private final String text;
    private final Integer i;

    private AttributeConst(final String text) {
        this.text = text;
        this.i = null;
    }

    private AttributeConst(final Integer i) {
        this.text = null;
        this.i = i;
    }

    public String getValue() {
        return this.text;
    }

    public Integer getIntegerValue() {
        return this.i;
    }

}
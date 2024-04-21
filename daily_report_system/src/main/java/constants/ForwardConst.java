package constants;

/**
 * ���N�G�X�g�p�����[�^�[�̕ϐ����A�ϐ��l�Ajsp�t�@�C���̖��O����ʑJ�ڂɊւ��l���`����Enum�N���X
 *
 */
public enum ForwardConst {

    //action
    ACT("action"),
    ACT_TOP("Top"),
    ACT_EMP("Employee"),
    ACT_REP("Report"),
    ACT_AUTH("Auth"),

    //command
    CMD("command"),
    CMD_NONE(""),
    CMD_INDEX("index"),
    CMD_SHOW("show"),
    CMD_SHOW_LOGIN("showLogin"),
    CMD_LOGIN("login"),
    CMD_LOGOUT("logout"),
    CMD_NEW("entryNew"),
    CMD_CREATE("create"),
    CMD_EDIT("edit"),
    CMD_UPDATE("update"),
    CMD_DESTROY("destroy"),

    //jsp
    FW_ERR_UNKNOWN("error/unknown"),
    FW_TOP_INDEX("topPage/index"),
    FW_LOGIN("login/login"),
    FW_EMP_INDEX("employees/index"),
    FW_EMP_SHOW("employees/show"),
    FW_EMP_NEW("employees/new"),
    FW_EMP_EDIT("employees/edit"),
    FW_REP_INDEX("reports/index"),
    FW_REP_SHOW("reports/show"),
    FW_REP_NEW("reports/new"),
    FW_REP_EDIT("reports/edit");

    /**
     * ������
     */
    private final String text;

    /**
     * �R���X�g���N�^
     */
    private ForwardConst(final String text) {
        this.text = text;
    }

    /**
     * �l(������)�擾
     */
    public String getValue() {
        return this.text;
    }

}
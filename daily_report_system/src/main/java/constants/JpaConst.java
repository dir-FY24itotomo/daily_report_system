package constants;

/**
 * DB�֘A�̍��ڒl���`����C���^�[�t�F�[�X
 * ���C���^�[�t�F�C�X�ɒ�`�����ϐ��� public static final �C���q�����Ă���Ƃ݂Ȃ����
 */
public interface JpaConst {

    //persistence-unit��
    String PERSISTENCE_UNIT_NAME = "daily_report_system";

    //�f�[�^�擾�����̍ő�l
    int ROW_PER_PAGE = 15; //1�y�[�W�ɕ\�����郌�R�[�h�̐�

    //�]�ƈ��e�[�u��
    String TABLE_EMP = "employees"; //�e�[�u����
    //�]�ƈ��e�[�u���J����
    String EMP_COL_ID = "id"; //id
    String EMP_COL_CODE = "code"; //�Ј��ԍ�
    String EMP_COL_NAME = "name"; //����
    String EMP_COL_PASS = "password"; //�p�X���[�h
    String EMP_COL_ADMIN_FLAG = "admin_flag"; //�Ǘ��Ҍ���
    String EMP_COL_CREATED_AT = "created_at"; //�o�^����
    String EMP_COL_UPDATED_AT = "updated_at"; //�X�V����
    String EMP_COL_DELETE_FLAG = "delete_flag"; //�폜�t���O

    int ROLE_ADMIN = 1; //�Ǘ��Ҍ���ON(�Ǘ���)
    int ROLE_GENERAL = 0; //�Ǘ��Ҍ���OFF(���)
    int EMP_DEL_TRUE = 1; //�폜�t���OON(�폜�ς�)
    int EMP_DEL_FALSE = 0; //�폜�t���OOFF(����)

    //����e�[�u��
    String TABLE_REP = "reports"; //�e�[�u����
    //����e�[�u���J����
    String REP_COL_ID = "id"; //id
    String REP_COL_EMP = "employee_id"; //������쐬�����]�ƈ���id
    String REP_COL_REP_DATE = "report_date"; //���̓��񂩂��������t
    String REP_COL_TITLE = "title"; //����̃^�C�g��
    String REP_COL_CONTENT = "content"; //����̓��e
    String REP_COL_CREATED_AT = "created_at"; //�o�^����
    String REP_COL_UPDATED_AT = "updated_at"; //�X�V����

    //Entity��
    String ENTITY_EMP = "employee"; //�]�ƈ�
    String ENTITY_REP = "report"; //����

    //JPQL���p�����[�^
    String JPQL_PARM_CODE = "code"; //�Ј��ԍ�
    String JPQL_PARM_PASSWORD = "password"; //�p�X���[�h
    String JPQL_PARM_EMPLOYEE = "employee"; //�]�ƈ�

    //NamedQuery�� name��query
    //�S�Ă̏]�ƈ���id�̍~���Ɏ擾����
    String Q_EMP_GET_ALL = ENTITY_EMP + ".getAll"; //name
    String Q_EMP_GET_ALL_DEF = "SELECT e FROM Employee AS e ORDER BY e.id DESC"; //query
    //�S�Ă̏]�ƈ��̌������擾����
    String Q_EMP_COUNT = ENTITY_EMP + ".count";
    String Q_EMP_COUNT_DEF = "SELECT COUNT(e) FROM Employee AS e";
    //�Ј��ԍ��ƃn�b�V�����σp�X���[�h�������ɖ��폜�̏]�ƈ����擾����
    String Q_EMP_GET_BY_CODE_AND_PASS = ENTITY_EMP + ".getByCodeAndPass";
    String Q_EMP_GET_BY_CODE_AND_PASS_DEF = "SELECT e FROM Employee AS e WHERE e.deleteFlag = 0 AND e.code = :" + JPQL_PARM_CODE + " AND e.password = :" + JPQL_PARM_PASSWORD;
    //�w�肵���Ј��ԍ���ێ�����]�ƈ��̌������擾����
    String Q_EMP_COUNT_REGISTERED_BY_CODE = ENTITY_EMP + ".countRegisteredByCode";
    String Q_EMP_COUNT_REGISTERED_BY_CODE_DEF = "SELECT COUNT(e) FROM Employee AS e WHERE e.code = :" + JPQL_PARM_CODE;
    //�S�Ă̓����id�̍~���Ɏ擾����
    String Q_REP_GET_ALL = ENTITY_REP + ".getAll";
    String Q_REP_GET_ALL_DEF = "SELECT r FROM Report AS r ORDER BY r.id DESC";
    //�S�Ă̓���̌������擾����
    String Q_REP_COUNT = ENTITY_REP + ".count";
    String Q_REP_COUNT_DEF = "SELECT COUNT(r) FROM Report AS r";
    //�w�肵���]�ƈ����쐬���������S��id�̍~���Ŏ擾����
    String Q_REP_GET_ALL_MINE = ENTITY_REP + ".getAllMine";
    String Q_REP_GET_ALL_MINE_DEF = "SELECT r FROM Report AS r WHERE r.employee = :" + JPQL_PARM_EMPLOYEE + " ORDER BY r.id DESC";
    //�w�肵���]�ƈ����쐬��������̌������擾����
    String Q_REP_COUNT_ALL_MINE = ENTITY_REP + ".countAllMine";
    String Q_REP_COUNT_ALL_MINE_DEF = "SELECT COUNT(r) FROM Report AS r WHERE r.employee = :" + JPQL_PARM_EMPLOYEE;

}

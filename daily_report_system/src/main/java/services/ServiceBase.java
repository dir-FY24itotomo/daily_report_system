package services;

import javax.persistence.EntityManager;

import utils.DBUtil;

/**
 * DB�ڑ��Ɋւ�鋤�ʏ������s���N���X
 */
public class ServiceBase {

    /**
     * EntityManager�C���X�^���X
     */
    protected EntityManager em = DBUtil.createEntityManager();

    /**
     * EntityManager�̃N���[�Y
     */
    public void close() {
        if (em.isOpen()) {
            em.close();
        }
    }
}

package models;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import constants.JpaConst;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * ����f�[�^��DTO���f��
 *
 */
@Table(name = JpaConst.TABLE_REP)
@NamedQueries({
    @NamedQuery(
            name = JpaConst.Q_REP_GET_ALL,
            query = JpaConst.Q_REP_GET_ALL_DEF),
    @NamedQuery(
            name = JpaConst.Q_REP_COUNT,
            query = JpaConst.Q_REP_COUNT_DEF),
    @NamedQuery(
            name = JpaConst.Q_REP_GET_ALL_MINE,
            query = JpaConst.Q_REP_GET_ALL_MINE_DEF),
    @NamedQuery(
            name = JpaConst.Q_REP_COUNT_ALL_MINE,
            query = JpaConst.Q_REP_COUNT_ALL_MINE_DEF)
})

@Getter //�S�ẴN���X�t�B�[���h�ɂ���getter��������������(Lombok)
@Setter //�S�ẴN���X�t�B�[���h�ɂ���setter��������������(Lombok)
@NoArgsConstructor //�����Ȃ��R���X�g���N�^��������������(Lombok)
@AllArgsConstructor //�S�ẴN���X�t�B�[���h�������ɂ���������R���X�g���N�^��������������(Lombok)
@Entity
public class Report {

    /**
     * id
     */
    @Id
    @Column(name = JpaConst.REP_COL_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * �����o�^�����]�ƈ�
     */
    @ManyToOne
    @JoinColumn(name = JpaConst.REP_COL_EMP, nullable = false)
    private Employee employee;

    /**
     * ���̓��񂩂��������t
     */
    @Column(name = JpaConst.REP_COL_REP_DATE, nullable = false)
    private LocalDate reportDate;

    /**
     * ����̃^�C�g��
     */
    @Column(name = JpaConst.REP_COL_TITLE, length = 255, nullable = false)
    private String title;

    /**
     * ����̓��e
     */
    @Lob
    @Column(name = JpaConst.REP_COL_CONTENT, nullable = false)
    private String content;

    /**
     * �o�^����
     */
    @Column(name = JpaConst.REP_COL_CREATED_AT, nullable = false)
    private LocalDateTime createdAt;

    /**
     * �X�V����
     */
    @Column(name = JpaConst.REP_COL_UPDATED_AT, nullable = false)
    private LocalDateTime updatedAt;

}
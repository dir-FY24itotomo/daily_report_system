package actions.views;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * ������ɂ��ĉ�ʂ̓��͒l�E�o�͒l������View���f��
 *
 */
@Getter //�S�ẴN���X�t�B�[���h�ɂ���getter��������������(Lombok)
@Setter //�S�ẴN���X�t�B�[���h�ɂ���setter��������������(Lombok)
@NoArgsConstructor //�����Ȃ��R���X�g���N�^��������������(Lombok)
@AllArgsConstructor //�S�ẴN���X�t�B�[���h�������ɂ���������R���X�g���N�^��������������(Lombok)
public class ReportView {

    /**
     * id
     */
    private Integer id;

    /**
     * �����o�^�����]�ƈ�
     */
    private EmployeeView employee;

    /**
     * ���̓��񂩂��������t
     */
    private LocalDate reportDate;

    /**
     * ����̃^�C�g��
     */
    private String title;

    /**
     * ����̓��e
     */
    private String content;

    /**
     * �o�^����
     */
    private LocalDateTime createdAt;

    /**
     * �X�V����
     */
    private LocalDateTime updatedAt;
}

package actions.views;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * �]�ƈ����ɂ��ĉ�ʂ̓��͒l�E�o�͒l������View���f��
 *
 */
@Getter //�S�ẴN���X�t�B�[���h�ɂ���getter��������������(Lombok)
@Setter //�S�ẴN���X�t�B�[���h�ɂ���setter��������������(Lombok)
@NoArgsConstructor //�����Ȃ��R���X�g���N�^��������������(Lombok)
@AllArgsConstructor //�S�ẴN���X�t�B�[���h�������ɂ���������R���X�g���N�^��������������(Lombok)
public class EmployeeView {

    /**
     * id
     */
    private Integer id;

    /**
     * �Ј��ԍ�
     */
    private String code;

    /**
     * ����
     */
    private String name;

    /**
     * �p�X���[�h
     */
    private String password;

    /**
     * �Ǘ��Ҍ��������邩�ǂ����i��ʁF0�A�Ǘ��ҁF1�j
     */
    private Integer adminFlag;

    /**
     *�o�^����
     */
    private LocalDateTime createdAt;

    /**
     * �X�V����
     */
    private LocalDateTime updatedAt;

    /**
     * �폜���ꂽ�]�ƈ����ǂ����i�����F0�A�폜�ς݁F1�j
     */
    private Integer deleteFlag;

}

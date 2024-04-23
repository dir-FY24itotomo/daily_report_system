package actions.views;

import java.util.ArrayList;
import java.util.List;

import models.Report;

/**
 * ����f�[�^��DTO���f����View���f���̕ϊ����s���N���X
 *
 */
public class ReportConverter {

    /**
     * View���f���̃C���X�^���X����DTO���f���̃C���X�^���X���쐬����
     * @param rv ReportView�̃C���X�^���X
     * @return Report�̃C���X�^���X
     */
    public static Report toModel(ReportView rv) {
        return new Report(
                rv.getId(),
                EmployeeConverter.toModel(rv.getEmployee()),
                rv.getReportDate(),
                rv.getTitle(),
                rv.getContent(),
                rv.getCreatedAt(),
                rv.getUpdatedAt());
    }

    /**
     * DTO���f���̃C���X�^���X����View���f���̃C���X�^���X���쐬����
     * @param r Report�̃C���X�^���X
     * @return ReportView�̃C���X�^���X
     */
    public static ReportView toView(Report r) {

        if (r == null) {
            return null;
        }

        return new ReportView(
                r.getId(),
                EmployeeConverter.toView(r.getEmployee()),
                r.getReportDate(),
                r.getTitle(),
                r.getContent(),
                r.getCreatedAt(),
                r.getUpdatedAt());
    }

    /**
     * DTO���f���̃��X�g����View���f���̃��X�g���쐬����
     * @param list DTO���f���̃��X�g
     * @return View���f���̃��X�g
     */
    public static List<ReportView> toViewList(List<Report> list) {
        List<ReportView> evs = new ArrayList<>();

        for (Report r : list) {
            evs.add(toView(r));
        }

        return evs;
    }

    /**
     * View���f���̑S�t�B�[���h�̓��e��DTO���f���̃t�B�[���h�ɃR�s�[����
     * @param r DTO���f��(�R�s�[��)
     * @param rv View���f��(�R�s�[��)
     */
    public static void copyViewToModel(Report r, ReportView rv) {
        r.setId(rv.getId());
        r.setEmployee(EmployeeConverter.toModel(rv.getEmployee()));
        r.setReportDate(rv.getReportDate());
        r.setTitle(rv.getTitle());
        r.setContent(rv.getContent());
        r.setCreatedAt(rv.getCreatedAt());
        r.setUpdatedAt(rv.getUpdatedAt());

    }

}
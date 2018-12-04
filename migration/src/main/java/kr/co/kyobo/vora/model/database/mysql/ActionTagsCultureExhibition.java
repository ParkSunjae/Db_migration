package kr.co.kyobo.vora.model.database.mysql;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table
public class ActionTagsCultureExhibition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx")
    private Integer idx;
    private Integer actionTagsIdx;
    private Integer id;
    private String cateName1;
    private String cImage;
    private String cLink;
    private String cLocation;
    private String startDate;
    private String endDate;
    private String cTitle;
}

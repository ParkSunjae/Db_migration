package kr.co.kyobo.vora.model.database.mysql;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table
public class DeleteMember {
    @Id
    @Column(name = "idx")
    private Integer idx;
    private Integer fileIdx;
    private String nickName;
    private String certYn;
    private Date emailCertDate;
}

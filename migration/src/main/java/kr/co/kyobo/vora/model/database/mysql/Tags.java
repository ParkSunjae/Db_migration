package kr.co.kyobo.vora.model.database.mysql;

import kr.co.kyobo.vora.model.database.mssql.SnsTag;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Data
@Entity
@Table
public class Tags extends BaseObject {
    private String tag;
    private Integer hits;
    private String useYn;
    private String adminCheckYn;
}

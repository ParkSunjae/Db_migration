package kr.co.kyobo.vora.model.database.mysql;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table
public class BusinessCategory2 extends BaseObject{
    private Integer parentIdx;
    private String categoryName;
}

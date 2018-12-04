package kr.co.kyobo.vora.model.database.mysql;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table
public class TagFollow extends BaseObject {
    private Integer tagIdx;
    private Integer uIdx;
}

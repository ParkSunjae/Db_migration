package kr.co.kyobo.vora.model.database.mysql;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table
public class SearchHistory extends BaseObject{
    private Integer uIdx;
    private String search;
    private String ip;
}

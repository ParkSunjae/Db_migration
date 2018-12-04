package kr.co.kyobo.vora.model.database.mysql;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table
public class Alarm extends BaseObject{
    private Integer uIdx;
    private String targetIdx;
    private String type;
    private String message;
    private String showYn;
    private Integer feedIdx;
}

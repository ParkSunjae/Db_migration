package kr.co.kyobo.vora.model.database;

import lombok.Data;

import java.util.Date;

@Data
public class BaseObject {
    private int idx;
    private Date createAt;
    private Date updateAt;

}

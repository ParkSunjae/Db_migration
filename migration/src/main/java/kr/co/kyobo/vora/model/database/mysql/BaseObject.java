package kr.co.kyobo.vora.model.database.mysql;

import lombok.Data;

import javax.persistence.*;

import java.util.Date;

@Data
@MappedSuperclass
public class BaseObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idx;
    @Column(nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date createAt;
    @Column(nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT ON UPDATE CURRENT_TIMESTAMP")
    private Date updateAt;

}

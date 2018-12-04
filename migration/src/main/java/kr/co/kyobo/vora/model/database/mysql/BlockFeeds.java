package kr.co.kyobo.vora.model.database.mysql;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table
public class BlockFeeds {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx")
    private Integer idx;
    private Integer feedIdx;
    private Integer uIdx;
    private String blockStatus;
}

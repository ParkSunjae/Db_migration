package kr.co.kyobo.vora.model.database.mssql;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "sns_feed_file")
public class SnsFeedFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private Long fidx;
    @Column(columnDefinition = "NVARCHAR")
    private String filename;
    @Column(columnDefinition = "NVARCHAR")
    private String uid;
}

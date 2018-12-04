package kr.co.kyobo.vora.model.database.mssql;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "sns_feed_auser")
public class SnsFeedAuser implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private Long fidx;
    private Long cidx;
    @Column(name="auser_name",columnDefinition = "nvarchar")
    private String auserName;
    @ManyToOne
    @JoinColumn(name="uid", referencedColumnName="strId", columnDefinition = "nvarchar")
    private MtbMember2 uid;
//    @Column(columnDefinition = "nvarchar")
//    private String uid;
    @Column(columnDefinition = "nvarchar")
    private String uname;
    @Column(columnDefinition = "nvarchar")
    private String regdate;
}

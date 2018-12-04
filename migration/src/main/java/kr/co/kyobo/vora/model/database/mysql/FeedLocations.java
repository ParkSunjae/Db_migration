package kr.co.kyobo.vora.model.database.mysql;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table
public class FeedLocations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idx;
    private Integer feedIdx;
    private String address;
    private String category;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String description;
    private String link;
    private String mapx;
    private String mapy;
    private String roadAddress;
    private String title;
}

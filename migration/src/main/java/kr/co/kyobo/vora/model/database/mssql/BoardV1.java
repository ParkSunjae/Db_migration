package kr.co.kyobo.vora.model.database.mssql;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "BOARD_v1")
public class BoardV1 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="b_idx")
    private Integer bIdx;
    @Column(name="board_code")
    private String boardCode;
    @Column(name="b_part")
    private String bPart;
    @Column(name="b_title")
    private String bTitle;
    @Column(name="b_name")
    private String bName;
    @Column(name="b_jumin1")
    private String bJumin1;
    @Column(name="b_jumin2")
    private String bJumin2;
    @Column(name="b_email")
    private String bEmail;
    @Lob
    @Column(name="b_text",columnDefinition = "TEXT")
    private String bText;
    @Column(name="file_1")
    private String file1;
    private String filename;
    private String filetype;
    private String filesize;
    @Column(name="b_pass")
    private String bPass;
    private Integer ref;
    @Column(name="re_level")
    private Integer reLevel;
    @Column(name="re_step")
    private Integer reStep;
    @Column(name="b_ip")
    private String bIp;
    @Column(name="b_read")
    private Integer bRead;
    @Column(name="b_writeday")
    private Date bWriteday;
    @Column(name="b_html")
    private String bHtml;
    private String userid;
    @Column(name="option_notice", columnDefinition = "BIT")
    private Boolean optionNotice;
    @Column(name="option_bimil", columnDefinition = "BIT")
    private Boolean optionBimil;
}

package kr.co.kyobo.vora.model.database.mysql;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Data
@Entity
@Table
public class Files extends BaseObject{
    private String fileName;
    private String fileThumbnail;
    private String fileType;
}

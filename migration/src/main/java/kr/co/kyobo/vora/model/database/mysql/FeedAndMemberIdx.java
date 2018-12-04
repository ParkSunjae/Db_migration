package kr.co.kyobo.vora.model.database.mysql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Embeddable
public class FeedAndMemberIdx implements Serializable {
    private Integer feedIdx;
    private Integer uIdx;
}

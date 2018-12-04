package kr.co.kyobo.vora.model.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtClaim {
    String dt;
    String midx;
    String at;
    String aidx;
}

package kr.co.kyobo.vora.model.api;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@Data
public class NaverLocationSearchReq {
    @NotEmpty
    private String query;       //	string	Y	-	검색을 원하는 문자열로서 UTF-8로 인코딩한다.
    private Integer display;    //	integer	N	10(기본값), 100(최대)	검색 결과 출력 건수 지정
    private Integer start;      //	integer	N	1(기본값), 1000(최대)	검색 시작 위치로 최대 1000까지 가능
    private String sort;        //  string	N	random (기본값), comment	정렬 옵션: random(유사도순), comment(카페/블로그 리뷰 개수 순)
}

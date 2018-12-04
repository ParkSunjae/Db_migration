package kr.co.kyobo.vora.model.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class NaverLocationSearchRes {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastBuildDate;     //  datetime	검색 결과를 생성한 시간이다.
    private int total;              //  integer	검색 결과 문서의 총 개수를 의미한다.
    private int start;              //  integer	검색 결과 문서 중, 문서의 시작점을 의미한다.페이징번호
    private int display;            //  integer	검색된 검색 결과의 개수이다.
    private int category;           //  string	검색 결과 업체, 기관의 분류 정보를 제공한다.
    private List<Item> items;       //	개별 검색 결과이며 title, link, description, telephone, address, mapx, mapy를 포함한다.

    @Data
    public class Item {
        private String title;       //	string	검색 결과 업체, 기관명을 나타낸다.
        private String link;        //	string	검색 결과 업체, 기관의 상세 정보가 제공되는 네이버 페이지의 하이퍼텍스트 link를 나타낸다.
        private String description; //	string	검색 결과 업체, 기관명에 대한 설명을 제공한다.
        private String telephone;   //	string	검색 결과 업체, 기관명의 전화번호를 제공한다.
        private String address;     //	string	검색 결과 업체, 기관명의 주소를 제공한다.
        private String roadAddress; //	string	검색 결과 업체, 기관명의 도로명 주소를 제공한다.
        private String mapx;        //	integer	검색 결과 업체, 기관명 위치 정보의 x좌표를 제공한다. 제공값은 카텍좌표계 값으로 제공된다. 이 좌표값은 지도 API와 연동 가능하다.
        private Double mapy;        //	integer	검색 결과 업체, 기관명 위치 정보의 y좌표를 제공한다. 제공값은 카텍 좌표계 값으로 제공된다. 이 좌표값은 지도 API와 연동 가능하다.
    }
}

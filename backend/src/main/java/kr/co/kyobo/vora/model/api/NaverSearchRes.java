package kr.co.kyobo.vora.model.api;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

public class NaverSearchRes {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastBuildDate;     //검색 결과를 생성한 시간이다.
    private int total;              //전체 개수
    private int start;              //페이징번호
    private int display;            //페이징당 개수
    private List<Item> items;        //	-	개별 검색 결과이며 title, link, image, subtitle, pubDate, director, actor, userRating을 포함한다.

    public class Item {
        private String title;       //	string	검색 결과 영화의 제목을 나타낸다. 제목에서 검색어와 일치하는 부분은 태그로 감싸져 있다.
        private String link;        //	string	검색 결과 영화의 하이퍼텍스트 link를 나타낸다.
        private String image;       //	string	검색 결과 영화의 썸네일 이미지의 URL이다. 이미지가 있는 경우만 나타난다.
        private String subtitle;    //	string	검색 결과 영화의 영문 제목이다.
        private String pubDate;     //	date	검색 결과 영화의 제작년도이다.
        private String director;    //	string	검색 결과 영화의 감독이다.
        private String actor;       //	string	검색 결과 영화의 출연 배우이다.
        private Double userRating;  //	integer	검색 결과 영화에 대한 유저들의 평점이다.

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getSubtitle() {
            return subtitle;
        }

        public void setSubtitle(String subtitle) {
            this.subtitle = subtitle;
        }

        public String getPubDate() {
            return pubDate;
        }

        public void setPubDate(String pubDate) {
            this.pubDate = pubDate;
        }

        public String getDirector() {
            return director;
        }

        public void setDirector(String director) {
            this.director = director;
        }

        public String getActor() {
            return actor;
        }

        public void setActor(String actor) {
            this.actor = actor;
        }

        public Double getUserRating() {
            return userRating;
        }

        public void setUserRating(Double userRating) {
            this.userRating = userRating;
        }
    }

    public Date getLastBuildDate() {
        return lastBuildDate;
    }

    public void setLastBuildDate(Date lastBuildDate) {
        this.lastBuildDate = lastBuildDate;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getDisplay() {
        return display;
    }

    public void setDisplay(int display) {
        this.display = display;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}

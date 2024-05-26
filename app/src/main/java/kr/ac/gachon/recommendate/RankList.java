package kr.ac.gachon.recommendate;

public class RankList {
    private String keyword, like;

    public String getKey(){
        return keyword;
    }
    public void setKey(String keyword){
        this.keyword = keyword;
    }
    public String getLike(){
        return like;
    }
    public void setLike(String like){
        this.like = like;
    }

    RankList(String keyword, String like){
        this.keyword = keyword;
        this.like = like;
    }

}

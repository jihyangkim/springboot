package com.sample.app.pds.model;

import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PdsOrderOption implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 검색조건. 0=제목, 1=내용 2=id*/
    private int cond;    
    
    /** 쿼리에 보낼 검색조건 문자열  */
    private String condquery = "";
    
    /** 검색어 */
    private String keyword = "";
    
    
    /** 총 게시물의 수 */
    private int totalSize = 10;
    
    /** 현재페이지 */
    private int pageNum = 1;
    
    /** 페이지에 보여줄 데이터 갯수 */
    private int recordCountPerPage = 10;
        
    /** 현재 페이지의 시작 seq     */
    private int startSeq = 1;
    
    /** 현재 페이지의 끝 seq */
    private int endSeq = 10;
        
    /**총 페이지 수 */
    private int totalPage = 1;
 
    /** 현재 페이지 네비게이션 사이즈 */
    private int pageNavSize = 5;
    
    /** 최대 페이지 네비게이션 사이즈 */
    private int maxNavSize = 10;
 
    /** 페이지 네비게이션 첫 인덱스 */
    private int firstNavIndex = 1;
 
    /** 페이지 네비게이션 마지막 인덱스 */
    private int lastNavIndex = 1;  
    
    /**No args constructor
     * 
     */
    public PdsOrderOption() {    }
    
    /**현재 페이지 번호와 총 게시글의 수를 매개변수로 받아서 페이징 객체 생성
     * @param pageNum
     * @param totalSize
     */
    @Builder
    public PdsOrderOption(int pageNum, int totalSize) {
    	this.setBasicData(pageNum, totalSize);
        this.setNav(totalSize);
    }
    
    /**검색조건, 검색어, 현재페이지, 총 게시글 수를 받는 생성자
     * @param cond
     * @param keyword
     * @param pageNum
     * @param totalSize
     */
    @Builder
	public PdsOrderOption(int cond, String keyword, int pageNum, int totalSize) {
    	this.setBasicData(cond, keyword, pageNum, totalSize);	
	}
    

	  /**All Args Constructor
	 * @param cond
	 * @param condquery
	 * @param keyword
	 * @param pageNum
	 * @param recordCountPerPage
	 * @param startSeq
	 * @param endSeq
	 * @param totalPage
	 * @param pageNavSize
	 * @param maxNavSize
	 * @param firstNavIndex
	 * @param lastNavIndex
	 */
	@Builder
    public PdsOrderOption(int cond, String condquery, String keyword, int totalSize, int pageNum, int recordCountPerPage, int startSeq,
			int endSeq, int totalPage, int pageNavSize, int maxNavSize, int firstNavIndex, int lastNavIndex) {
		super();
		this.cond = cond;
		this.condquery = condquery;
		this.keyword = keyword;
		this.pageNum = pageNum;
		this.recordCountPerPage = recordCountPerPage;
		this.startSeq = startSeq;
		this.endSeq = endSeq;
		this.totalPage = totalPage;
		this.pageNavSize = pageNavSize;
		this.maxNavSize = maxNavSize;
		this.firstNavIndex = firstNavIndex;
		this.lastNavIndex = lastNavIndex;
		this.totalSize = totalSize;
		this.setNav(this.totalSize);
	}
	
    /**네비게이션 바 설정
     * 12345 : 1 ~ 5
     * 678910 : 6 ~ 10
     * (7/5)*5 +1
     * (3/5)*5 +1
     */
    public void setNav(int totalSize) {
        //페이지 네비게이션의 최대 사이즈
        this.maxNavSize = (totalSize % recordCountPerPage == 0) ? (totalSize / recordCountPerPage) : (totalSize / recordCountPerPage) + 1;
        this.firstNavIndex = (this.pageNum / pageNavSize) * pageNavSize + 1;
        //마지막 네비게이션 바 인덱스. 전체 글 수가 한 페이지 당 글 수로 나누어 떨어지면 -0 아니면 -1
        int temp = this.firstNavIndex + pageNavSize - 1 ;
        this.lastNavIndex = (temp > maxNavSize) ? maxNavSize : temp ;
        //System.out.println("totalSize : " + totalSize + " , recordCountPerPage : " + recordCountPerPage + " , temp : " + temp + " , ddd " +  (( (totalSize % recordCountPerPage) == 0 )?3:2 ) );
        //System.out.println("firstNavIndex : " + firstNavIndex + " , lastNavIndex : " + lastNavIndex + " , maxNavSize : " + maxNavSize);
    }

    /**전체 글 개수가 변경되었을 때, 네비게이션 바 재설정
     * @param totalSize
     */
    public void setTotalSize(int totalSize) {
    	this.totalSize = totalSize;
    	setNav(totalSize);
    }
	
    /**현재 페이지 번호가 변경되었을 때, startSeq, endSeq 재설정
     * @param pageNum
     */
    public void setPageNum(int pageNum) {
    	this.startSeq = ( ( pageNum - 1 ) * this.getRecordCountPerPage() ) + 1;
		this.endSeq = startSeq + this.getRecordCountPerPage() - 1;
    }
    
    public void setCond(int cond) {
    	this.cond = cond;
    	switch( cond ) {
			case 0: this.condquery="title"; break;
			case 1: this.condquery="content"; break;
			case 2: this.condquery="id"; break;
			case 3: this.condquery="origin_filename"; break;
			default: this.condquery= "all"; break;
    	}	
    }
    
    /** 페이징 정보에 따라 검색옵션 설정
     * @param pageNum
     * @param totalSize
     */
    public void setBasicData(int pageNum, int totalSize) {
    	this.totalSize = totalSize;
		this.pageNum = pageNum;
		this.setRecordCountPerPage(10);
		this.startSeq = ( ( pageNum - 1 ) * this.getRecordCountPerPage() ) + 1;
		this.endSeq = startSeq + this.getRecordCountPerPage() - 1;		
		//페이지 네비게이션의 최대 사이즈
		this.maxNavSize = (totalSize % recordCountPerPage == 0) ? (totalSize / recordCountPerPage) : (totalSize / recordCountPerPage) + 1;
		this.setNav(totalSize);
		this.cond = 4;
		this.keyword = "";
		switch( cond ) {
			case 0: this.condquery="title"; break;
			case 1: this.condquery="content"; break;
			case 2: this.condquery="id"; break;
			case 3: this.condquery="origin_filename"; break;
			default: this.condquery= "all"; break;
		}		
	}
    
    /**검색조건, 페이징정보에 따라 정보 세팅
     * @param cond
     * @param keyword
     * @param pageNum
     * @param totalSize
     */
    public void setBasicData(int cond, String keyword, int pageNum, int totalSize) {
    	this.totalSize = totalSize;
		this.pageNum = pageNum;
		this.setRecordCountPerPage(10);
		this.startSeq = ( ( pageNum - 1 ) * this.getRecordCountPerPage() ) + 1;
		this.endSeq = startSeq + this.getRecordCountPerPage() - 1;		
		//페이지 네비게이션의 최대 사이즈
		this.maxNavSize = (totalSize % recordCountPerPage == 0) ? (totalSize / recordCountPerPage) : (totalSize / recordCountPerPage) + 1;
		this.setNav(totalSize);
		this.cond = cond;
		this.keyword = keyword;
		switch( cond ) {
			case 0: this.condquery="title"; break;
			case 1: this.condquery="content"; break;
			case 2: this.condquery="id"; break;
			case 3: this.condquery="origin_filename"; break;
			default: this.condquery= "all"; break;
		}		
	}
}

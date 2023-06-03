package kr.or.ddit.mapper;

import kr.or.ddit.vo.NoticeVO;

public interface NoticeMapper {

	public int insertNotice(NoticeVO noticeVO);

	public NoticeVO selectNotice(int boNo);

	public void incrementHit(int boNo);

	public int updateNotice(NoticeVO notice);

}

package kr.or.ddit.mapper;

import java.util.List;

import kr.or.ddit.vo.MemberAuth;
import kr.or.ddit.vo.MemberVO;

public interface MemberMapper {

	public void register(MemberVO member);

	public void createAuth(MemberAuth memberAuth);

	public List<MemberVO> list();

	public MemberVO read(int userNo);

}

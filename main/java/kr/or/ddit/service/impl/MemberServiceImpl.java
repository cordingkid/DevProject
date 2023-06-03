package kr.or.ddit.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.ddit.mapper.MemberMapper;
import kr.or.ddit.service.IMemberService;
import kr.or.ddit.vo.MemberAuth;
import kr.or.ddit.vo.MemberVO;

@Service
public class MemberServiceImpl implements IMemberService{
	
	@Inject
	private MemberMapper mapper;
	
	@Override
	public void register(MemberVO member) {
		mapper.register(member);
		MemberAuth memberAuth = new MemberAuth();
		memberAuth.setUserNo(member.getUserNo());
		memberAuth.setAuth("ROLE_USER");
		mapper.createAuth(memberAuth);
	}
	@Override
	public List<MemberVO> list() {
		return mapper.list();
	}
	
	@Override
	public MemberVO read(int userNo) {
		return mapper.read(userNo);
	}
	
	@Override
	public void modify(MemberVO member) {
		mapper.modify(member); // 일단 맴버를 수정을 한다.
		
		int userNo = member.getUserNo(); // 그리고 번호를 가지고
		mapper.deleteAuth(userNo); // 모든 역할을 다 날려버린다.
		
		List<MemberAuth> authList = member.getAuthList();	// 선택한 역할 리스트를 뽑아
		for (MemberAuth memberAuth : authList) { // 반복문을 돌리고
			String auth = memberAuth.getAuth(); 
			if(auth == null) {
				continue;
			}
			if(auth.trim().length() == 0) {
				continue;
			} // 널이거나 공백일때 실행 밑에문장을 실행 안하게 하고
			// 그게 아닐때 번호를 셋팅해서 역할을 다시 부여해준다.
			memberAuth.setUserNo(userNo);
			mapper.createAuth(memberAuth);
		}
	}
	
	@Override
	public void remove(int userNo) {
		mapper.deleteAuth(userNo);
		mapper.delete(userNo);
	}
}

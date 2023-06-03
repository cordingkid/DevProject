package kr.or.ddit.service.impl;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.ddit.mapper.ItextMapper;
import kr.or.ddit.service.ItextService;
import kr.or.ddit.vo.TestUser;

@Service
public class ItextServiceImpl implements ItextService {

	@Inject
	private ItextMapper mapper;
	
	@Override
	public TestUser login(TestUser user) {
		return mapper.login(user);
	}
}

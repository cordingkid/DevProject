package kr.or.ddit.service.impl;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.ddit.mapper.BoardMapper;
import kr.or.ddit.service.IBoardService;
import kr.or.ddit.vo.Board;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BoardServiceImpl implements IBoardService {

	@Inject
	private BoardMapper mapper;

	@Transactional(rollbackFor = IOException.class)
	@Override
	public void register(Board board) throws IOException {
		log.info("레지스터");
		mapper.create(board);
		// CheckeException 계열로 롤백 처리가 되지 않는다.
		if (true) {
			throw new IOException();
		}
		
		// RuntimeException 계열로 롤백 처리가 가능하다.
//		if (true) {
//			throw new NullPointerException();
//		}
		
		
	}

	@Override
	public List<Board> list() {
		log.info("리스트");
		return mapper.list();
	}

	@Override
	public Board read(int boardNo) {
		log.info("리드");
		return mapper.read(boardNo);
	}
	
	@Override
	public void update(Board board) {
		log.info("업데이트");
		mapper.update(board);
	}
	
	@Override
	public void delete(int boardNo) {
		log.info("딜리트");
		mapper.delete(boardNo);		
	}
	
	@Override
	public List<Board> search(Board board) {
		log.info("써치");
		return mapper.search(board);
	}
}

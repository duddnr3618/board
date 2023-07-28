package com.mysite.board.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysite.board.entity.BoardEntity;
import com.mysite.board.repository.BoardRepository;

@Service
public class BoardService {

	@Autowired
	private BoardRepository boardRepository; 
	
	public void wirte (BoardEntity boardEntity) {
		
		boardRepository.save(boardEntity);
		
	}
}

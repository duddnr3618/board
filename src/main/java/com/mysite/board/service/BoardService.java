package com.mysite.board.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysite.board.entity.Board;
import com.mysite.board.repository.BoardRepository;

@Service
public class BoardService {

	@Autowired
	private BoardRepository boardRepository; 
	
	public void wirte (Board board) {
		
		boardRepository.save(board);
		
	}
}

package com.mysite.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.mysite.board.entity.Board;
import com.mysite.board.service.BoardService;

@Controller
public class BoardController {
	
	@Autowired
	private BoardService boardService;

	@GetMapping("/board/write")	//localhost:8002/board/write
	public String boardWriteForm () {
		
		return "boardwrite";
	}
	
	@PostMapping("/board/writepro")
	public String boardWritePro (Board board) {

		boardService.wirte(board);
		
		return "";
	}
	
	@GetMapping("/board/list")
	public String boardList (Model model) {
		
		model.addAttribute("list",boardService.boardList());
		return "boardlist";
	}
	
	
}

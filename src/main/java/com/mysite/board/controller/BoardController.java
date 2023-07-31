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

	//게시글 작성 폼
	@GetMapping("/board/write")	//localhost:8002/board/write
	public String boardWriteForm () {
		
		return "boardwrite";
	}
	
	//게시글 작서후 db에 전송 폼
	@PostMapping("/board/writepro")
	public String boardWritePro (Board board) {

	boardService.wirte(board);

		
		return "";
	}
	
	//게시물의 리스트에 불러오는 폼
	@GetMapping("/board/list")
	public String boardList (Model model) {
		
		model.addAttribute ("list", boardService.boardList());
		return "boardlist";
	}
	
	//상세페이지
	@GetMapping("/board/view")
	public String boaordView(Model model , Integer id) {
		
		model.addAttribute("board" , boardService.boardView(id));
		return "boardview";
	}
	
	//게시글 리스트에서 삭제
	@GetMapping("/board/delete")
	public String boardDelete (Integer id) {
	
		boardService.boardDelete(id);
		
		return "redirect:/board/list";
	}

}

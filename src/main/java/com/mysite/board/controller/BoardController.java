package com.mysite.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	public String boardWritePro (Board board , Model model) {

	boardService.wirte(board);
	
	
		model.addAttribute("message" , "글 작성이 완료되었습니다.");
	
	//	model.addAttribute("message" , "글 작성이 실패되었습니다.");
	
	
	model.addAttribute("searchUrl" , "/board/list");
		
		return "message";
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

	//게시글 수정 폼
	@GetMapping("/board/modify/{id}")
	public String boardModify (@PathVariable("id") Integer id , Model model) {
	
		model.addAttribute("board" , boardService.boardView(id));
		return "boardModify";
	}
	
	 // 수정된 내용 업데이트
    @PostMapping("/board/update/{id}")
    public String boardUpdate(@PathVariable("id") Integer id, Board board) {
       //기존 글 검색
    	Board boardToUpdate = boardService.boardView(id);
        
    	//게시글 내용 board에 담아 덮에써 수정하기
        boardToUpdate.setTitle(board.getTitle());
        boardToUpdate.setContent(board.getContent());
      
        boardService.wirte(boardToUpdate); // 게시글 업데이트 호출
        return "redirect:/board/list"; // 수정 후 목록 페이지로 이동
    }
	
}

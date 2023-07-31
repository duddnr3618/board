package com.mysite.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

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
	public String boardWritePro (Board board , Model model , MultipartFile file) throws Exception {

	boardService.wirte(board , file);
	
	model.addAttribute("message" , "글 작성이 완료되었습니다.");
	
	//	model.addAttribute("message" , "글 작성이 실패되었습니다.");
	
	
	model.addAttribute("searchUrl" , "/board/list");
		
		return "message";
	}
	
	//게시물의 리스트에 불러오는 폼
	@GetMapping("/board/list") //http://localhost:8002/board/list?page=0 : 1페이지 
															//http://localhost:8002/board/list?size=10 : 10개 게시글보여주기
	public String boardList (Model model , @PageableDefault(page=0 , size = 10 , sort = "id" ,
	direction = Sort.Direction.DESC) Pageable pageable) {
		
		//페이지 리스트를 만들어서 넣어줌
		Page<Board> list = boardService.boardList(pageable);
		int nowPage = list.getPageable().getPageNumber() + 1;	//현재 페이지를 가져오기
		int startPage = Math.max( nowPage - 4,1);
		int endPage = Math.min(nowPage + 5,list.getTotalPages());
		
		//변수 넘겨주기
		model.addAttribute ("list", list);
		model.addAttribute("nowPage" , nowPage);
		model.addAttribute("startPage" , startPage);
		model.addAttribute("endPage" , endPage);
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
    public String boardUpdate(@PathVariable("id") Integer id, Board board , MultipartFile file) throws Exception {
       //기존 글 검색
    	Board boardToUpdate = boardService.boardView(id);
        
    	//게시글 내용 board에 담아 덮에써 수정하기
        boardToUpdate.setTitle(board.getTitle());
        boardToUpdate.setContent(board.getContent());
      
        boardService.wirte(boardToUpdate , file); // 게시글 업데이트 호출
        return "redirect:/board/list"; // 수정 후 목록 페이지로 이동
    }
	
}

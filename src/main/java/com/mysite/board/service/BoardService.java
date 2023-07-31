package com.mysite.board.service;

import java.io.File;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mysite.board.entity.Board;
import com.mysite.board.repository.BoardRepository;

@Service
public class BoardService {

	@Autowired
	private BoardRepository boardRepository; 
	
	// 글 작성 처리
	public void wirte (Board board , MultipartFile file) throws Exception{
		
		//프로젝트 경로 담기
		//저장한 파일 경로 지정
		String projectPath = System.getProperty("user.dir") + "//src//main//resources//static//files";
		
		//파일을 담을 객체생성
		UUID uuid = UUID.randomUUID();		//식별자를 통해서 랜덤으로 이름을 붙임
		String fileName = uuid + "_" + file.getOriginalFilename();	//저장될 파일 이름을 생성(식별자_원래이름)
		File saveFile = new File(projectPath , fileName);
		file.transferTo(saveFile);
		
		//DB에 업로드된 파일 경로 저장 
		board.setFilename(fileName);
		
		board.setFilepath("/files/" + fileName);
		
		boardRepository.save(board);
	}
	
	//게시글 리스트 처리 -> 페이징처리 (List -> page)
	public Page<Board> boardList (Pageable pageable) {
		
		return boardRepository.findAll(pageable);
	}
	
	//게시물 검색기능
	public Page<Board> boardSearchList (String searchKeyWord , Pageable pageable) {
		
		return boardRepository.findByTitleContaining(searchKeyWord , pageable);
	}
	
	//특정 게시글 불러오기
	public Board boardView(Integer id) {
		
		return boardRepository.findById(id).get();
	}
	
	//특정 게시글 삭제 폼
	public void boardDelete (Integer id) {
		boardRepository.deleteById(id);
		
	}
	
	
	
}

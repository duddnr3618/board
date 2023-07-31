package com.mysite.board.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mysite.board.entity.Board;


@Repository
public interface BoardRepository extends JpaRepository<Board, Integer>{
	
	//검색기능
	Page<Board> findByTitleContaining(String searchKeyWord , Pageable pageable );
	
}

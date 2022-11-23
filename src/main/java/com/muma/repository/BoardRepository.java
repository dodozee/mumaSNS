package com.muma.repository;

import java.util.Optional;

import com.muma.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Integer> {

	public Optional<Board> findByBoardNo(Integer boardNo);

}

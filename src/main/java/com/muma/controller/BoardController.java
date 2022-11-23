package com.muma.controller;

import java.util.List;
import java.util.stream.Collectors;


import com.muma.constant.MusicGenre;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


import com.muma.entity.Board;
import com.muma.forms.BoardForm;
import com.muma.service.BoardService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(path = "/board")
public class BoardController {

	@Autowired
	private BoardService boardService;

	@RequestMapping(method = RequestMethod.GET, value = {"list", "/", ""})
	public String viewBoardList(Pageable pageable, Model model) {

		Page<Board> pageInfo = boardService.getAllBoard(pageable);
		model.addAttribute("pageInfo", pageInfo);

		return "board/list";
	}


	@RequestMapping(method = RequestMethod.POST, path = "write")
	public String writeCompleteBoard(@ModelAttribute("boardForm") @Validated BoardForm form,
			BindingResult bindingResult, Model model) {

		if (validationForSaveInputValue(bindingResult)) {
			return writeBoard(form, model);
		}
		Integer boardNo = null;
		boardNo = boardService.createBoard(form.toEntity()).getBoardNo();



		return "redirect:/board/list";
	}
	@RequestMapping(method = RequestMethod.GET, path = "write")
	public String writeBoard(@ModelAttribute("boardForm") BoardForm form, Model model) {


		model.addAttribute("musicGenres", MusicGenre.getMusicGenres());

		return "board/write";
	}





	@RequestMapping(method = RequestMethod.GET, path = "update/{boardNo}")
	public String updateBoard(@PathVariable(required = true) int boardNo, Model model) {

		boardService.updateBoardForm(boardNo, model);

		return "board/update";
	}
	@RequestMapping(method = RequestMethod.GET, path = "read/{boardNo}")
	public String viewBoardOne(@PathVariable(required = true) int boardNo, Model model) {

		model.addAttribute("board", boardService.getBoardOne(boardNo));

		return "board/read";
	}

	@RequestMapping(method = RequestMethod.POST, path = "update/{boardNo}")
	public String updateCompleteBoard(
			@PathVariable(required = true) int boardNo,
			@ModelAttribute @Validated BoardForm form,
			BindingResult bindingResult, Model model) {

		validation(bindingResult);


		return "redirect:/board/read/"+boardNo;
	}


	@RequestMapping(method = RequestMethod.GET, path = "delete/{boardNo}")
	public String deleteBoard(@PathVariable int boardNo, Model model) {

		model.addAttribute("board", boardService.getBoardOne(boardNo));

		return "board/delete";
	}


	@RequestMapping(method = RequestMethod.POST, path = "delete")
	public String deleteCompleteBoard(
			@RequestParam(name = "boardNo", required = true) int boardNo,
			Model model) {

		boardService.deleteBoard(boardNo);

		model.addAttribute("message", "Delete Success");
		return "redirect:/board/list";
	}



	private void validation(BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			List<String> errorStrList = bindingResult.getAllErrors()
			.stream()
			.map(e -> e.getDefaultMessage())
			.collect(Collectors.toList());

			log.error("Validation-NG : {} ", errorStrList);


		}
		log.info("Validation-OK");
	}


	private boolean validationForSaveInputValue(BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			List<String> errorStrList = bindingResult.getAllErrors()
			.stream()
			.map(e -> e.getDefaultMessage())
			.collect(Collectors.toList());

			log.error("Validation-NG : {} ", errorStrList);

			return true;
		}
		log.info("Validation-OK");
		return false;
	}




}

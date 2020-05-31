package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TitleService {

	@Autowired
	private TitleRepository titleRepository;

	public List<Title> findAll(){
		return titleRepository.findAll();
	}

	public Title findById(int id){
		return titleRepository.findById(id);
	}

	public void deleteById(int id) {

	}

	public Title saveAndFlush(Title data){
		return titleRepository.saveAndFlush(data);
	}

}

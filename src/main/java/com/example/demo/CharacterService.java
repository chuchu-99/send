package com.example.demo;
import org.springframework.stereotype.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class CharacterService {
	@Autowired
	private CharacterRepository characterRepository;

	public List<Character> findAll(){
		return characterRepository.findAll();
	}

	public Character findById(int charaId){
		return characterRepository.findById(charaId);
	}

	public Character saveAndFlush(Character chara){
		return characterRepository.saveAndFlush(chara);
	}

	public List<Character> findByTitleNo(int titleNo){
		return characterRepository.findByTitleNo(titleNo);
	}

	public Character deleteById(int charaId){
		return characterRepository.deleteById(charaId);
	}

	public List<Character> searchByCharaName(String charaName){
		return characterRepository.searchByCharaName(charaName);
	}

	public Character charaNameFromCharacter(String charaName) {
		return characterRepository.charaNameFromCharacter(charaName);
	}



}

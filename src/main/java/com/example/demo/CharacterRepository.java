package com.example.demo;

import com.example.demo.Character;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CharacterRepository extends JpaRepository<Character, Integer>{

	public Character findById(int id);
	public List<Character> findByTitleNo(int id);
	public Character deleteById(int id);
//	FROM句に記載する抽出先はCharacter.Javaのname欄で記載したもの
//	?1で取得してきていると思われる
//	?の数字の後は引数の順番に応じて変化（2番目なら?2）
//	camelCase記法ではなくsnake_case記法でSQL文を記載
//	TableのほうでcamelCase記法で記載していてもsnake_case記法
	@Query(value = "select * from chara c where chara_name like %?1%" , nativeQuery = true)
	public List<Character> searchByCharaName(String charaName);
	@Query(value = "select * from chara c where chara_name = ?1" , nativeQuery = true)
	public Character charaNameFromCharacter(String charaName);


}

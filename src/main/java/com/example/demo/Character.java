package com.example.demo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name="chara")
public class Character {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	//ID
	@Column
	private int id;
	//タイトルNo
	@Column
	private int titleNo;
	//性別
	@Column
	private String charaGender;
	//キャラクター名
	@Column
	private String charaName;
	//キャラ簡易内容
	@Column
	private String charaContent;
	//得意武器1
	@Column
	private String mainWeaponOne;
	//得意武器2
	@Column
	private String mainWeaponTwo;
	//キャラ詳細
	@Column
	private String charaDetail;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public int getTitleNo() {
		return titleNo;
	}
	public void setTitleNo(int titleNo) {
		this.titleNo = titleNo;
	}

	public String getCharaGender() {
		return charaGender;
	}

	public void setCharaGender(String charaGender) {
		this.charaGender = charaGender;
	}

	public String getCharaName() {
		return charaName;
	}

	public void setCharaName(String charaName) {
		this.charaName = charaName;
	}


	public String getCharaContent() {
		return charaContent;
	}

	public void setCharaContent(String charaContent) {
		this.charaContent = charaContent;
	}

	public String getMainWeaponOne() {
		return mainWeaponOne;
	}

	public void setMainWeaponOne(String mainWeaponOne) {
		this.mainWeaponOne = mainWeaponOne;
	}

	public String getMainWeaponTwo() {
		return mainWeaponTwo;
	}

	public void setMainWeaponTwo(String mainWeaponTwo) {
		this.mainWeaponTwo = mainWeaponTwo;
	}

	public String getCharaDetail() {
		return charaDetail;
	}

	public void setCharaDetail(String charaDetail) {
		this.charaDetail = charaDetail;
	}

}

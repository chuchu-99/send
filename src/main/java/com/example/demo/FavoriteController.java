package com.example.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FavoriteController {

    @Autowired
    private TitleService titleService;
    @Autowired
    private CharacterService characterService;

/*=====初期画面の遷移処理=====*/
    @GetMapping()
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView();
        // 現在DBに格納されているものをリスト形式に格納
        List<Title> list = titleService.findAll();
        //　格納されたものをdata変数に変換？htmlに渡せるようにしてるイメージ
        mav.addObject("data",list);
        mav.setViewName("title/titleList");
        return mav;
    }

    /*キャラ一覧画面への遷移*/
    @GetMapping("/edit")
    ModelAndView edit(@RequestParam int id) {
        ModelAndView mav = new ModelAndView();
        //　IDに応じたタイトル名を抽出
        Title data = titleService.findById(id);
//        List<Character> charaList = characterService.searchByCharaName("リ");
        //　IDに応じたキャラ名を抽出
        List<Character> charaList = characterService.findByTitleNo(id);
        mav.addObject("data",data);
        mav.addObject("chara",charaList);

        mav.setViewName("character/characterList");
        return mav;
    }

    /*タイトル追加画面への遷移*/
    @GetMapping("/titleAdd")
    ModelAndView titleAdd() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("title/titleAdd");
        return mav;
    }

    /*キャラ検索画面への遷移*/
    @GetMapping("/characterSearch")
    ModelAndView charaSearch() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("character/characterSearch");
        return mav;
    }

    /*=====ボタン押下時等々の処理=====*/

    /*CSVファイル読み込み処理*/
    @SuppressWarnings("finally")
	@GetMapping("/reading")
    public ModelAndView reading() throws Exception{
    	try {
			InputStream is = new ClassPathResource("static/csv/追加データ.csv").getInputStream();
	    	BufferedReader br = new BufferedReader(new InputStreamReader(is));
	        Title title = new Title();
	        Character chara = new Character();
	    	String line;

	    	while ((line = br.readLine()) != null) {
	    		if(!line.startsWith("#",0)) {
		    		String[] arrayColumnData = line.split(",",-1);
		    		if(arrayColumnData[0].length() != 0) {
			    		title.setId(Integer.parseInt(arrayColumnData[0]));
			    		title.setTitleName(arrayColumnData[1]);
			    		titleService.saveAndFlush(title);
		    		}
		    	    chara.setId(Integer.parseInt(arrayColumnData[2]));
		    	    chara.setCharaName(arrayColumnData[3]);
		    	    chara.setTitleNo(Integer.parseInt(arrayColumnData[4]));
		    	    chara.setCharaGender(arrayColumnData[5]);
		    	    chara.setCharaContent(arrayColumnData[6]);
		    	    chara.setMainWeaponOne(arrayColumnData[7]);
		    	    chara.setMainWeaponTwo(arrayColumnData[8]);
		    	    chara.setCharaDetail(arrayColumnData[9]);
		    	    characterService.saveAndFlush(chara);
	    		}
	    	}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			return this.list();
		}
    }

    /*タイトル追加処理*/
    @PostMapping("/titleCreate")
    @Transactional(readOnly=false)
    public ModelAndView titleCreate(
    				@ModelAttribute("data")Title data) {
    	titleService.saveAndFlush(data);
        return new ModelAndView("redirect:title/titleList");
    }

    /*キャラ検索処理*/
    @PostMapping("/buttonCharacterSearch")
    @Transactional(readOnly=false)
    public ModelAndView buttonCharacterSearch(
    				@ModelAttribute("chara")Character chara) {
    	ModelAndView mav = new ModelAndView();
    	chara = characterService.charaNameFromCharacter(chara.getCharaName());
    	List<Character> charaList = characterService.searchByCharaName(chara.getCharaName());
    	Title data = titleService.findById(chara.getTitleNo());
    	mav.addObject("data",data);
    	mav.addObject("chara",charaList);
    	mav.setViewName("character/characterDetail");
    	return mav;
    }

    /*削除処理*/
    @PostMapping("/delete")
    @Transactional(readOnly=false)
    public ModelAndView delete(@RequestParam int id) {
    	titleService.deleteById(id);
        characterService.deleteById(id);
        return new ModelAndView("redirect:title/titleList");
    }

/*=====内容確認画面の処理=====*/
    /*登場人物追加画面への遷移(追加)*/
    @GetMapping("/characterAdd")
    ModelAndView characterAdd(@RequestParam int id) {
        ModelAndView mav = new ModelAndView();
        Title data = titleService.findById(id);

        mav.addObject("data",data);
        mav.setViewName("character/characterAdd");
        return mav;
    }

    /*登場人物追加画面への遷移(編集)*/
    @GetMapping("/characterEdit")
    ModelAndView characterEdit(@RequestParam int id) {
        ModelAndView mav = new ModelAndView();
        Character chara = characterService.findById(id);
        Title data = titleService.findById(chara.getTitleNo());

        mav.addObject("data",data);
        mav.addObject("chara",chara);
        mav.setViewName("character/characterEdit");
        return mav;
    }

    /*キャラクター内容追加・変更処理*/
    @PostMapping("/create")
    @Transactional(readOnly=false)
    public ModelAndView create(
    				@ModelAttribute("chara")Character chara) {
    	characterService.saveAndFlush(chara);
        return new ModelAndView("redirect:title/titleList");
    }

    /*詳細ボタン押下時処理*/
    @GetMapping("/characterDetail")
    ModelAndView characterDetail(@RequestParam int id) {
        ModelAndView mav = new ModelAndView();
        Character chara = characterService.findById(id);
        Title data = titleService.findById(chara.getTitleNo());

        mav.addObject("data",data);
        mav.addObject("chara",chara);
        mav.setViewName("character/characterDetail");
        return mav;
    }

    /*初期データ作成*/
    @PostConstruct
    public void init() {
		try {
			InputStream is = new ClassPathResource("static/csv/初期データ.csv").getInputStream();
	    	BufferedReader br = new BufferedReader(new InputStreamReader(is));
	        Title title = new Title();
	        Character chara = new Character();
	    	String line;

	    	while ((line = br.readLine()) != null) {
	    		if(!line.startsWith("#",0)) {
		    		String[] arrayColumnData = line.split(",",-1);
		    		if(arrayColumnData[0].length() != 0) {
			    		title.setId(Integer.parseInt(arrayColumnData[0]));
			    		title.setTitleName(arrayColumnData[1]);
			    		titleService.saveAndFlush(title);
		    		}
		    	    chara.setId(Integer.parseInt(arrayColumnData[2]));
		    	    chara.setCharaName(arrayColumnData[3]);
		    	    chara.setTitleNo(Integer.parseInt(arrayColumnData[4]));
	    	    	chara.setCharaGender(arrayColumnData[5]);
	    	    	chara.setCharaContent(arrayColumnData[6]);
		    	    chara.setMainWeaponOne(arrayColumnData[7]);
		    	    chara.setMainWeaponTwo(arrayColumnData[8]);
		    	    chara.setCharaDetail(arrayColumnData[9]);
	    	    	characterService.saveAndFlush(chara);
	    		}
	    	}
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}

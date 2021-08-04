package com.example.demo.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.Category;
import com.example.demo.entity.Item;
import com.example.demo.entity.ItemsWithCategories;
import com.example.demo.form.GetBoot;
import com.example.demo.form.ItemForm;
import com.example.demo.form.Test1;
import com.example.demo.form.Test2;
import com.example.demo.form.TestReference3;
import com.example.demo.repository.ItemRepository;
import com.example.demo.repository.ItemsWithCategoriesRepository;

@Controller
public class BootController {
	
	@Autowired
	ItemRepository repository;
	
	@Autowired
	ItemsWithCategoriesRepository ItemsWithrepository;
	
	@RequestMapping(path="/test/boot")
	public String showBoot() {
		return "boot";
	}
	
	@PostMapping("/postBoot")
	public String postBoot(GetBoot gb, HttpSession session) {
		session.setAttribute("studentId",gb.getStudentId());
		session.setAttribute("birthday",gb.getBirthday());
		session.setAttribute("password", gb.getPassword());
		return "getBoot";
	}
	
	@GetMapping("/items/findAll") //一覧表示
	public String showAll(Model model) {
		model.addAttribute("items", repository.findAll());
		return "items/item_list";
	}
	
	@GetMapping("items/findAllByOrderByPriceDesc") //価格の大きい順に検索
	public String showAllByPrice(Model model) {
		model.addAttribute("items",repository.findAllByOrderByPriceDesc());
		return "items/item_list";
	}
	
	@GetMapping("/items/getOne/{id}") //主キーに一致したレコードを検索
	public String showRecord(@PathVariable int id, Model model) {
		model.addAttribute("item", repository.getOne(id));
		return "items/item";
	}
	
//	@GetMapping("/items/getTwo") 
//	public String showRecord2(@RequestParam int id, Model model) { //@RequestParam: クエリパラメータ(パラメータ名=値)を受け取る
//		System.out.println(id);
//		model.addAttribute("item", repository.getOne(id));
//		return "items/item";
//	}
	
	@GetMapping("/items/findByPrice/{price}") //主キー以外のカラムと一致したレコードを検索
	public String findByPrice(@PathVariable int price, Model model){
		model.addAttribute("items", repository.findByPrice(price));
		return "items/item_list";
	}
	
	@GetMapping("/items/findByNameAndPrice/{name}/{price}") //カラムの値２つと一致するレコードを取得
	public String findByNameAndPrice(@PathVariable String name, @PathVariable int price, Model model) {
		model.addAttribute("items", repository.findByNameAndPrice(name, price));
		return "items/item_list";
	}
	
	@GetMapping("/items/findByNameLike/{name}") //曖昧検索
	public String findByNameLike(@PathVariable String name, Model model) {
		model.addAttribute("items", repository.findByNameLike("%"+ name +"%"));
		return "items/item_list";
	}
	
	@GetMapping("/items/create/input") //レコード登録用フォーム
	public String getCreate() {
		return "items/create_input";
	}
	
	@PostMapping("/items/create/complete") //レコード登録s
	public String CreateRecord(ItemForm form) {
		Item item = new Item();
		item.setName(form.getName());
		item.setPrice(form.getPrice());
		repository.save(item);
		return "redirect:/items/getOne/" + item.getId();
	}
	
	@GetMapping("/items/update/input/{id}") //更新画面(指定したレコードの現在値をフォームに表示する)
	public String getUpdate(@PathVariable int id, Model model) {
		model.addAttribute("item", repository.getOne(id));
		return "items/update_input";
	}
	
	@PostMapping("/items/update/complete/{id}")//更新処理
	public String UpdateRecord(@PathVariable int id, ItemForm form) {
		Item item=repository.getOne(id);
		item.setName(form.getName());
		item.setPrice(form.getPrice());
		repository.save(item);
		return "redirect:/items/getOne/" + item.getId();
	}
	
	@GetMapping("/items/delete/input") //削除画面表示
	public String getDelete() {
		return "items/delete_input";
	}
	
	@PostMapping("/items/delete/complete") //削除処理
	public String DeleteRecord(ItemForm form) {
		repository.deleteById(form.getId());
		return "redirect:/items/findAll";
	}
	
	@GetMapping("/getLesson0501")//バリデーション付き入力画面取得
	public String getLesson0501( Model model,ItemForm form, BindingResult result) {
		model.addAttribute("itemForm", form);
		return "lesson05_01/new";
	}
	
//	@GetMapping("/getLesson0501") @ModelAttribute :　紐づけたオブジェクト頭文字を小文字にした変数入し、スコープに格納
//	public String getlesson0501(Model model, @ModelAttribute ItemForm form, BindingResult result) {
//		return "lesson05_01/new";
//	}
	
	@PostMapping("/create") //バリデーション付き情報送信
	public String CreateLesson0501(Model model, HttpSession session,@Valid ItemForm form, BindingResult result) {
		if (result.hasErrors()) {
			model.addAttribute("itemForm", form);
			return "lesson05_01/new";
		} else {
			session.setAttribute("itemForm", form);
			return "lesson05_01/result";
		}
	}
	
	@GetMapping("/lesson04_03/index") //countの曖昧検索画面取得
	public String getLesson04_03() {
		return "lesson05_01/lesson04_03";
	}
	
	@PostMapping("/lesson04_03/index")//countの検索
	public String getLesson04_03(Model model, String name) {
		model.addAttribute("countNumber", repository.countByNameLike("%"+name+"%"));
		return "lesson05_01/lesson04_03";
	}
	
	@GetMapping("/items/findAllAndSetDropdown") //ドロップダウン
	public String itemListSetDropdown(HttpSession session) {
		session.setAttribute("items", repository.findAll());
		return "items/item_list_dropdown";
	}
	
	@GetMapping("/practice") //html練習用画面
	public ModelAndView practice(ModelAndView mav,@ModelAttribute TestReference3 test3) {
		mav.addObject("items", repository.findAll());
		mav.setViewName("lesson05_01/HtmlPractice");
		return mav;
	}
	
	@ModelAttribute //returnで返したオブジェクトを最初の文字を小文字にした変数名でスコープに保存
	public Test1 gettest1() { //このコントローラー内の全てのリクエストででFormオブジェクトを参照でき、エラー画面の取得のために引数に設定する手間を省ける。
		return new Test1();
	}
	
	@ModelAttribute
	public Test2 gettest2() {
		return new Test2();
	}
	
	@PostMapping("/test1") //１つのページに複数のformのエラー1
	public String postTest1(@Valid Test1 test1, BindingResult result) { //@ModelAttributeがあるためエラー画面取得のために他のエラーオブジェクトを引数に設定し、スコープに入れる必要がない
		return "lesson05_01/HtmlPractice";
	}
	
	@PostMapping("/test2") //１つのページに複数のformのエラー2
	public String postTest2(@Valid Test2 test2, BindingResult result) { //@ValidがついたFormオブジェクトはバリデーションが行われる
		return "lesson05_01/HtmlPractice";								//1つのフォームオブジェクトに1つのBindingResultが必要s
	}
	
	@PostMapping("/testReference")//外部参照キーを持つレコードの登録
	public String postTest3(@Valid @ModelAttribute TestReference3 test3, BindingResult result) {
		if (result.hasErrors()) {
			System.out.println("エラー");
			return "lesson05_01/HtmlPractice";
		}
		Category category = new Category();
		category.setId(test3.getCategoryId());
		
		ItemsWithCategories itemWith = new ItemsWithCategories();
		itemWith.setName(test3.getName());
		itemWith.setPrice(test3.getPrice());
		itemWith.setCategory(category);
		ItemsWithrepository.save(itemWith);
		return "lesson05_01/HtmlPractice";
	}
	
	@GetMapping("/testHtml")
	public String testHtml1(){
		return "lesson05_01/testHtml1";
	}
	
	@GetMapping("/")
	public String ExceptMain() {
		return "SecurityPractice/index";
	}
}

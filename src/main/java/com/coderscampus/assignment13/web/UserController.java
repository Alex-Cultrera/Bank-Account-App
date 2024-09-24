package com.coderscampus.assignment13.web;

import java.util.Arrays;
import java.util.Set;

import com.coderscampus.assignment13.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.coderscampus.assignment13.domain.User;
import com.coderscampus.assignment13.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private AccountService accountService;
	
	@GetMapping("")
	public String home (ModelMap model) {
		Set<User> users = userService.findAll();

		model.put("users", users);
		if (users.size() == 1) {
			model.put("user", users.iterator().next());
		}

		return "users";
	}

	@GetMapping("/create")
	public String getCreateUser (ModelMap model) {

		model.put("user", new User());

		return "user/create";
	}

	@PostMapping("/create")
	public String postCreateUser (User user) {
		System.out.println(user);
		userService.saveUser(user);
		return "redirect:";
	}

	@GetMapping("/{userId}")
	public String getOneUser (ModelMap model, @PathVariable Long userId) {
		User user = userService.findById(userId);
		model.put("users", Arrays.asList(user));
		model.put("user", user);
		return "user/read";
	}
	
	@PostMapping("/{userId}")
	public String postOneUser (User user) {
		userService.saveUser(user);
		return "redirect:"+user.getUserId();
	}
	
	@PostMapping("/{userId}/delete")
	public String deleteOneUser (@PathVariable Long userId) {
		userService.delete(userId);
		return "redirect:/users";
	}
}

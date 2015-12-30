package com.mm.dev.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mm.dev.entity.User;
import com.mm.dev.entity.UserCreateForm;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

@RestController
@PropertySource("classpath:message.properties")
@Api(basePath = "/user", value = "用户API", description = "用户相关", produces = "application/json")
@RequestMapping("/user")
public class UserController {

	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
	@Autowired
	private RedisTemplate<String, Object> redisObjectTemplate;
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(UserController.class);
	
	@Autowired
	private Environment env;
	
	private Message message = new Message();
	
    @RequestMapping("/api/users")
    public String authorized() {
        return "Hello Secured World";
    }
    
    @RequestMapping(value = "/login/{username}/{password}", method = RequestMethod.GET)
    public String login(@PathVariable("username") String userName, @PathVariable("password") String password) {
    	User user = new User();
    	user.setUsername(userName);
    	user.setPassword(password);
    	/*stringRedisTemplate.opsForValue().set("append", "sskks");
    	int result = stringRedisTemplate.opsForValue().append("append", "dkdkd");
    	stringRedisTemplate.opsForValue().set("append", "sskks");
    	String uuid = UUID.randomUUID().toString();
    	String hashCode = String.valueOf(user.hashCode());
    	redisObjectTemplate.opsForHash().put(uuid, "user", user);
    	redisObjectTemplate.opsForHash().increment(uuid, "user", 1000);
    	System.out.println(stringRedisTemplate.opsForValue().get("append"));*/
    	return null;
    }
    
    @RequestMapping(value = "/getsession", method = RequestMethod.GET)
    @ResponseBody
    public Object getSesssionInfo(HttpServletRequest request) {
    	return request.getSession().getAttribute("user");
    }
    
    @RequestMapping(value = "/updatesession", method = RequestMethod.GET)
    @ResponseBody
    public Object updateSesssionInfo(HttpServletRequest request) {
    	HttpSession session = request.getSession();
    	User user = (User) request.getSession().getAttribute("user");
    	user.setUsername("sksks");
    	session.setAttribute("user", user);
    	return request.getSession().getAttribute("user");
    }
    
    @RequestMapping(value = "/generatesession", method = RequestMethod.GET)
    @ResponseBody
    public Object generateSesssionInfo(HttpServletRequest request) {
    	HttpSession session = request.getSession();
    	User user = new User();
    	user.setUsername("username");
    	user.setPassword("password");
    	session.setAttribute("user", user);
    	return request.getSession().getAttribute("user");
    }
    
    
    @RequestMapping(value = "/invalidatesession", method = RequestMethod.GET)
    @ResponseBody
    public Object invalidateSesssionInfo(HttpServletRequest request) {
    	HttpSession session = request.getSession();
    	session.invalidate();
    	return request.getSession().getAttribute("user");
    }
    
    
    @RequestMapping(value = "/get/token/{token}", method = RequestMethod.GET)
    @ResponseBody
    public Object getToken(@PathVariable("token") String token) {
    	return redisObjectTemplate.opsForHash().get(token, "user");
    }
    
    @ApiOperation(httpMethod = "POST", value = "创建用户(<font color='blue'>release</font>)", notes = "POST请求，根据model设置")
	@ResponseBody
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String handleUserCreateForm(@Valid @RequestBody UserCreateForm form,
			BindingResult bindingResult) {
		LOGGER.debug("Processing user create form={}, bindingResult={}", form,
				bindingResult);
		if (bindingResult.hasErrors()) {
			// failed validation
			return "user_create error: failed validation ";
		}
		try {
			//userService.create(form);
		} catch (DataIntegrityViolationException e) {
			// probably email already exists - very rare case when multiple
			// admins are adding same user
			// at the same time and form validation has passed for more than one
			// of them.
			LOGGER.warn(
					"Exception occurred when trying to save the user, assuming duplicate username",
					e);
			bindingResult.reject("username.exists", "username already exists");
			return "user_create error: username already exists";
		}
		// ok, redirect
		return "create user success";
	}

	/**
	 * 使用 ResponseBody作为结果 200
	 * 
	 * @param id
	 * @return
	 */
	@ApiOperation(httpMethod = "GET", value = "查看用户信息-HTTP200(<font color='blue'>release</font>)", notes = "使用用户id进行查看，如果用户不存在，也返回200。返回用户类实体")
	@ResponseBody
	@RequestMapping(value = "/i/user/{id}", method = RequestMethod.GET)
	public User findByUserId(@PathVariable long id) {
		User user = new User();
		// HttpStatus status = user != null ? HttpStatus.OK :
		// HttpStatus.NOT_FOUND;
		return user;
	}

	/**
	 * 使用ResponseEntity作为返回结果 404
	 * 
	 * @param id
	 * @return
	 */
	@ApiOperation(httpMethod = "GET", value = "查看用户信息-HTTP404(<font color='blue'>release</font>)", notes = "使用用户id进行查看，如果用户不存在，返回404错误。返回用户类实体")
	@RequestMapping(value = "/i/{id}", method = RequestMethod.GET)
	public ResponseEntity<User> findById(@PathVariable long id) {
		User user = new User();
		HttpStatus status = user != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
		return new ResponseEntity<User>(user, status);
	}

	/**
	 * 使用 ResponseEntity 返回自定义错误结果
	 * 
	 * @return
	 */
	@ApiOperation(httpMethod = "GET", value = "查看用户信息(<font color='blue'>release</font>)", notes = "使用用户id进行查看，自定义返回消息类：code，msg，content")
	@RequestMapping(value = "/i/users/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> findByUsersId(@PathVariable long id) {
		
		User user = new User();
		if (user == null) {
			message.setMsg(106, env.getProperty("106"));
			return new ResponseEntity<Message>(message, HttpStatus.NOT_FOUND);
		}
		message.setMsg(101, "Get user info", user);
		return new ResponseEntity<Message>(message, HttpStatus.OK);
	}

	@ApiOperation(httpMethod = "GET", value = "用户信息列表(<font color='blue'>release</font>)", notes = "查看用户信息列表，可分页")
	@ResponseBody
	@RequestMapping(value = "/i/users/list", method = RequestMethod.GET)
	public ResponseEntity<Message> list(Pageable p) {

		message.setMsg(1, "List Users", null);
		return new ResponseEntity<Message>(message, HttpStatus.OK);

	}

	@ApiOperation(httpMethod = "POST", value = "上传用户头像(<font color='blue'>release</font>)", notes = "使用MultipartFile方式")
	@RequestMapping(value = "/i/uploadImage", method = RequestMethod.POST)
	public ResponseEntity<?> uploadImage(@RequestParam MultipartFile file,
			HttpServletRequest request) {
		message.setMsg(1, "upload user image",
				null);
		return new ResponseEntity<Message>(message, HttpStatus.OK);

	}

	@ApiOperation(httpMethod = "POST", value = "用户信息列表(<font color='blue'>供管理界面使用</font>)", notes = "查看用户信息列表，可分页")
	@RequestMapping(value = "/getUserList", method = RequestMethod.POST)
	public Object getUserList(@RequestParam(required = false) int current,
			@RequestParam(required = false) int rowCount,
			@RequestParam(required = false) String searchPhrase) {
		return null;

	}
}
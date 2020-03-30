import java.util.List;

import ua.yurii.zhurakovsky.domain.User;
import ua.yurii.zhurakovsky.service.UserService;
import ua.yurii.zhurakovsky.service.impl.UserServiceImpl;

public class Main {
	public static void main(String[] args) {
		UserService userService = new UserServiceImpl();
		userService.create(new User("test@test.test", "test", "test", "test"));
		List<User> users = userService.readAll();
		users.forEach(System.out::println);
		userService.delete(1);
		users.forEach(System.out::println);
	}
}

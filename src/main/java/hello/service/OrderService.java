package hello.service;

import javax.inject.Inject;

public class OrderService {
    @Inject
    public OrderService(UserService userService) {
        this.userService = userService;
    }

    private UserService userService;

//    public void placeOrder(Integer userId,String item){
//        userService.getUserById(userId);
//    }
}

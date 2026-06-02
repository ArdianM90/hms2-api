package com.hms.api.domain.user;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

  @GetMapping
  public List<String> users() {
    return List.of("A", "B", "C");
  }
}

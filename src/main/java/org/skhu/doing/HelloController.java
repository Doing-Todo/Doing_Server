package org.skhu.doing;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/hello-spring")
    public ResponseEntity<String> helloString(@RequestParam("name") String name) {
        String message = "hello " + name;
        return ResponseEntity.ok(message);
    }

    @GetMapping("/hello-api")
    public ResponseEntity<Hello> helloApi(@RequestParam("name") String name) {
        Hello hello = new Hello();
        hello.setName(name);
        return ResponseEntity.ok(hello);
    }

    class Hello{
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}

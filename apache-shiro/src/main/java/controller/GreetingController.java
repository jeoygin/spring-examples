package controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/auth/greeting")
public class GreetingController {
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequiresRoles("admin")
    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public Greeting greeting(@PathVariable String name) {
        return new Greeting(counter.incrementAndGet(),
                String.format(template, name));
    }

    @RequiresPermissions("user:view")
    @RequestMapping(method = RequestMethod.GET)
    public Greeting getGreeting(@RequestParam(value = "name", required = false, defaultValue = "World") String name) {
        return new Greeting(counter.incrementAndGet(),
                String.format(template, name));
    }

    private static class Greeting {
        private final long id;
        private final String content;

        public Greeting(long id, String content) {
            this.id = id;
            this.content = content;
        }

        public long getId() {
            return id;
        }

        public String getContent() {
            return content;
        }
    }
}

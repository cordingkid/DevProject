package kr.or.ddit.gridstack;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GridStackController {
	
	@GetMapping("/gridstacktest.do")
	public String gridStackTest() {
		return "gridstack/test";
	}
	
	
	@GetMapping("/gridstacktest2.do")
	public String gridStackTest2() {
		return "gridstack/test2";
	}
}

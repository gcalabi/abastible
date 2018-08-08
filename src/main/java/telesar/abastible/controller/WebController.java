package telesar.abastible.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping(value="/")
    public String homepage(){
        return "index";
    }

    @GetMapping(value="/logs")
    public String logpage(){
        return "showLogs";
    }

    @GetMapping(value="/errorLogs")
    public String errorLogpage(){
        return "showErrorLogs";
    }

    @GetMapping(value="/data")
    public String datapage(){
        return "showData";
    }
}
package hello.hellospring.controller;


import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MemberController {

    private final MemberService memberService;


    @Autowired
    public MemberController(MemberService memberService){
        this.memberService=memberService;
    }
    //bean으로 등록시킨 값을 가져옴


    @GetMapping(value = "/members/new")
    public String createForm(){

        return "members/createMemberForm";
        //리턴값을 string으로 하여 members/createMemberForm.html으로 이동하게 만들어줌 스프링과 동일하다
    }
    @PostMapping(value = "/members/new")
    public String create(MemberForm form){
        //똑같은 값이지만 post를 사용하여 restful하도록 만들어준다 get같은경우는 출력만할수있고 crud중에 c는 post를 사용함
        Member member=new Member();
        member.setName(form.getName());
        System.out.println("member="+member.getName());
        memberService.join(member);
        //post로 들어오면 join에서 값을 저장하도록함
        return "redirect:/";

    }
    @GetMapping(value = "/members")
    public String list(Model model){
        List<Member> members=memberService.findMemebers();
        model.addAttribute("member",members);

        return "members/memberList";
    }

}

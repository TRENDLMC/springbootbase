package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    void 회원가입()throws Exception {
        Member member=new Member();
        member.setName("spring");
        Long savaId=memberService.join(member);
        Member findMemeber= memberService.findOne(savaId).get();
        Assertions.assertThat(member.getName()).isEqualTo(findMemeber.getName());
    }

    @Test
    public void 중복_회원_예외()throws Exception{
    Member member1=new Member();
    member1.setName("spring");
    Member member2=new Member();
    member2.setName("spring");
    memberService.join(member1);
    assertThrows(IllegalStateException.class,()->memberService.join(member2));

//    try{
//        memberService.join(member2);
//        fail();
//    }catch (IllegalStateException e){
//        Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원 입니다.");
//    }

    }

}
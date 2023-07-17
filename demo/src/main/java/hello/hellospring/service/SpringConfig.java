package hello.hellospring.service;


import hello.hellospring.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

@Configuration
public class SpringConfig {
    //private DataSource dataSource;
    //데이터 베이스 소스는 applicationproperties에서 가져온다 jdbc 컨넥션풀 을 한것임
    //원래더적어야하지만 여기서는 스프링부트가 다알아서 처리해줌

    private EntityManager em;

   // @Autowired
    //public SpringConfig(DataSource dataSource) {
      //  this.dataSource = dataSource;
    //}
    //값을 autowired하여 new를 사용하지않아도 가져오도록 설정해줌. bean등록은 apllicationproperties에서해줌

    @Autowired
    public SpringConfig(EntityManager em){
        this.em=em;
    }
    @Bean
    public MemberService memberService(){
        return new MemberService(memberRepository());
    }
    //위에서 서비스 라는 어노테이션을 사용하지않고 직접 bean으로 등록을 시켜줌 그래서 new를 사용하지않고 호출가능하게만듬
    @Bean
    public MemberRepository memberRepository(){
        //return new MemoryMemberRepository();
        //여기서 원래 memort에서 값을 저장하던것은 jdbc로 변경하여 값을 스프링부트 자체가아닌 데이터베이스에서 저장하도록함
        //return new JdbcMemberRepository(dataSource);
       // return  new JdbcTemplateMemberRepository(dataSource);
        return new JpaMemberRepository(em);
    }
}

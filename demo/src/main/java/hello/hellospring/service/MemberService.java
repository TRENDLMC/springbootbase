package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Transactional
public class MemberService {
    private final MemberRepository memberRepository;


    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Long join(Member member){
//        Optional<Member> result=memberRepository.findByaName((member.getName()));
//        //Optional의 경우 오브젝트를 생각하면 편하다 모든 타입이 담길수있다 또한 null의 값이들어오면 인식하지않기떄문에
//        // runtime 오류인 nullponint exception의 발생을 피할수있다.
//        result.ifPresent(m->{
//            throw new IllegalStateException("이미 존재하는 회원 입니다.");
//            //위에서 result에서 값을 조회했을떄 값이존재한다면 result에는 값이 저장되므로
//            //isPresent는 값이있으면 true 없으면 fasle를 return하기때문에 있으면 이미존재하는 회원이라는
//            //사용자정의 오류를 출력한다.
//        });

        long start=System.currentTimeMillis();
        try {
            validateDuplicateMember(member);//위에 코드를 따로뺴서 사용자 지정 메소드로 만들어놓음 중복검증 프로그램
            memberRepository.save(member);
            //위에서 값이 없다면 아래로 내려와서 save메소드를 사용하여 값을 저장시킨다.
            return member.getId();

        }finally {
            long finisth=System.currentTimeMillis();
            long timeMs=finisth-start;
            System.out.println("join"+timeMs);
        }
    }
    public List<Member> findMemebers(){
        long start=System.currentTimeMillis();
        try{
        return memberRepository.findAll();
    }finally {
        long finisth=System.currentTimeMillis();
        long timeMs=finisth-start;
        System.out.println("join"+timeMs);
    }
    }
    public Optional<Member> findOne(Long memberid){
        return memberRepository.findById(memberid);
    }
    private void validateDuplicateMember(Member member) {
        memberRepository.findByaName(member.getName())
                        .ifPresent(m->{
                            throw new IllegalStateException("이미 존재하는 회원 입니다.");
                        });
    }
}

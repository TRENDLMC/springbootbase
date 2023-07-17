package hello.hellospring.repository;



import hello.hellospring.domain.Member;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository{

    private final EntityManager em;

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        em.persist(member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
       Member member=em.find(Member.class,id);
       return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByaName(String name) {
        List<Member> result=em.createQuery("select m from Member m where m.name= :name",Member.class)
                .setParameter("name",name)
                .getResultList();
        //id의 경우 pk이기떄문에 하나의 값밖에없기떄문에 간단한쿼리가가능하지만 name의경우 중복이가능하기떄문에
        //값을 리턴시키는 방식과 이름을 셋팅해줘야 검색이 가능하다. 하지만 aop를 사용하면 이것도 이렇게사용하지않아도됀다

        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        return em.createQuery("select m from Member m",Member.class).getResultList();
    }
}

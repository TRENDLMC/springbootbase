package hello.hellospring.domain;


import javax.persistence.*;

//테이블을 만들어준다 라는 entity
@Entity
public class Member {

    @Id//id는 pk를 선언해주는것과같다
    @GeneratedValue(strategy = GenerationType.IDENTITY)//요건 시퀀스와같다번호를 자동증감시킨다
    private Long id;

    //@Column(10) name이라는 컬럼을 만들고 10 이라는 용량을 준다 하지만 사용하지않아도 무관함 default값인 255로 자동으로 입력됌
    private String name;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}

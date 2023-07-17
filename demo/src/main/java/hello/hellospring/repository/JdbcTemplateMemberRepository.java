package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.util.*;

public class JdbcTemplateMemberRepository implements MemberRepository{

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateMemberRepository(DataSource dataSource) {
        this.jdbcTemplate =new JdbcTemplate(dataSource);
        //자동으로 쿼리문을 만들어주는 template 인듯?
    }


    @Override
    public Member save(Member member) {
        SimpleJdbcInsert jdbcInsert=new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("member").usingGeneratedKeyColumns("id");
        //jdbc의 insert문을 쓸껏이고 테이블이름은 member pk칼럼은 id이다라는거를 명시해주는거같은데 굳이?
        Map<String, Object> parameters=new HashMap<>();
        parameters.put("name",member.getName());
        //맵형태로 데이터를 만들어서 name은 키의 이름 이다 db와 동일하게해줘서바로 넣을수있게해주는듯?
        //값은 member에 저장된 이름값을 가져옴
        Number key=jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        //이건 db에 업데이트하고 업데이트후 반환해라 키를 값은 위에 parameters를 기준으로
        member.setId(key.longValue());
        //가져온 number타입의 값을 member에 대입시켜줌 스프링에도 따로저장하는듯?
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        List<Member> result=jdbcTemplate.query("select * from member where id=?",memberRowMapper(),id);
        //query(쿼리문,저장및데이터추출,입력값)
        //쿼리문에 값은 id로 들어가고 쿼리문에서 리턴받은 값은 memberRowMapper에 저장됌.
        return result.stream().findAny();
        //result에서 stream으로 값을 찾고 findAny를 사용하여 값이있으면 리턴한다
        //사실 findAny는 여러개의 값중 제일먼저 찾은 값을 리턴하지만 여기서는 하나의값밖에없기떄문에 값이있으면 리턴한다라고
        //생각하면 됄듯하다
    }

    @Override
    public Optional<Member> findByaName(String name) {
        List<Member> result=jdbcTemplate.query("select * from member where name=?", memberRowMapper(),name);
        //위 주석과동일
        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        return jdbcTemplate.query("select * from member",memberRowMapper());
        //query(쿼리문,저장및데이터추출,입력값)
        //쿼리문에 값은 id로 들어가고 쿼리문에서 리턴받은 값은 memberRowMapper에 저장됌.
    }


    private RowMapper<Member> memberRowMapper(){
        return (rs,rowNum)->{
            Member member=new Member();
            member.setId(rs.getLong("id"));
            member.setName(rs.getString("name"));
            return member;
        };
    }
}

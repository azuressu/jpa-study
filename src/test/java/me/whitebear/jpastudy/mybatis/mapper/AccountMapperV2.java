package me.whitebear.jpastudy.mybatis.mapper;

import me.whitebear.jpastudy.mybatis.vo.AccountMyBatisVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AccountMapperV2 {

    // 쿼리문을 @Select 에너테이션 안에 넣어준다 !
    @Select("SELECT * FROM account WHERE id = #{id}")
    AccountMyBatisVO selectAccount(Integer id);

    @Insert("INSERT INTO ACCOUNT (username, password) VALUES (#{username}, #{password})")
    void insertAccount(AccountMyBatisVO vo);
}

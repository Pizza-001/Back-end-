package com.pet.hospital.mapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import com.pet.hospital.model.entity.BizArticle;

@Mapper
public interface BizArticleMapper {
    List<BizArticle> findAll();
    BizArticle findById(@Param("id") Long id);
    
    int insert(BizArticle article);
    int update(BizArticle article);
    int deleteById(@Param("id") Long id);
}

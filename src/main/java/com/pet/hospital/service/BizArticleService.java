package com.pet.hospital.service;
import java.util.List;
import com.pet.hospital.model.entity.BizArticle;

public interface BizArticleService {
    List<BizArticle> getAllArticles();
    BizArticle getArticleById(Long id);
    
    void addArticle(BizArticle article);
    void updateArticle(BizArticle article);
    void deleteArticle(Long id);
    com.github.pagehelper.PageInfo<BizArticle> getPage(int pageNum, int pageSize);
}

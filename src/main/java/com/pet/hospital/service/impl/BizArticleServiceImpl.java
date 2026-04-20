package com.pet.hospital.service.impl;
import com.pet.hospital.mapper.BizArticleMapper;
import com.pet.hospital.model.entity.BizArticle;
import com.pet.hospital.service.BizArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BizArticleServiceImpl implements BizArticleService {
    @Autowired
    private BizArticleMapper articleMapper;
    
    @Override
    public List<BizArticle> getAllArticles() {
        return articleMapper.findAll();
    }
    
    @Override
    public BizArticle getArticleById(Long id) {
        return articleMapper.findById(id);
    }
    
    @Override
    public void addArticle(BizArticle article) {
        articleMapper.insert(article);
    }
    
    @Override
    public void updateArticle(BizArticle article) {
        articleMapper.update(article);
    }
    
    @Override
    public void deleteArticle(Long id) {
        articleMapper.deleteById(id);
    }
    
    @Override
    public com.github.pagehelper.PageInfo<BizArticle> getPage(int pageNum, int pageSize) {
        com.github.pagehelper.PageHelper.startPage(pageNum, pageSize);
        List<BizArticle> list = articleMapper.findAll();
        return new com.github.pagehelper.PageInfo<>(list);
    }
}

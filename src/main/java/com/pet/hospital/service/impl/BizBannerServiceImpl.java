package com.pet.hospital.service.impl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pet.hospital.common.PageResult;
import com.pet.hospital.mapper.BizBannerMapper;
import com.pet.hospital.model.entity.BizBanner;
import com.pet.hospital.service.BizBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BizBannerServiceImpl implements BizBannerService {
    @Autowired
    private BizBannerMapper bannerMapper;
    
    @Override
    public List<BizBanner> getActiveBanners() {
        return bannerMapper.findActiveBanners();
    }

    @Override
    public PageResult<BizBanner> getPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        // 这里假设 mapper 有一个 findAll 方法，暂时用 findActiveBanners 占位，您可以按需扩展 Mapper
        List<BizBanner> list = bannerMapper.findActiveBanners(); 
        return PageResult.of(new PageInfo<>(list));
    }

    @Override
    public void addBanner(BizBanner banner) {
        bannerMapper.insert(banner);
    }

    @Override
    public void updateBanner(BizBanner banner) {
        bannerMapper.update(banner);
    }

    @Override
    public void deleteBanner(Long id) {
        bannerMapper.deleteById(id);
    }

    @Override
    public BizBanner getById(Long id) {
        return bannerMapper.findById(id);
    }
}

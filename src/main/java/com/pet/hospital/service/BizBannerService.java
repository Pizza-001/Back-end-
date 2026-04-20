package com.pet.hospital.service;

import java.util.List;
import com.pet.hospital.model.entity.BizBanner;
import com.pet.hospital.common.PageResult;

public interface BizBannerService {
    List<BizBanner> getActiveBanners();
    PageResult<BizBanner> getPage(int pageNum, int pageSize);
    void addBanner(BizBanner banner);
    void updateBanner(BizBanner banner);
    void deleteBanner(Long id);
    BizBanner getById(Long id);
}

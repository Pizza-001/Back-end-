package com.pet.hospital.mapper;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.pet.hospital.model.entity.BizBanner;

@Mapper
public interface BizBannerMapper {
    List<BizBanner> findActiveBanners();
    BizBanner findById(Long id);
    int insert(BizBanner banner);
    int update(BizBanner banner);
    int deleteById(Long id);
}

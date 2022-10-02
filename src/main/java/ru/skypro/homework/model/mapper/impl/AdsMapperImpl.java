package ru.skypro.homework.model.mapper.impl;

import org.springframework.stereotype.Component;
import ru.skypro.homework.model.dto.AdsDto;
import ru.skypro.homework.model.entity.Ads;
import ru.skypro.homework.model.mapper.AdsMapper;

@Component
public class AdsMapperImpl implements AdsMapper {

    @Override
    public AdsDto toAdsDto(Ads ads) {
        if ( ads == null ) {
            return null;
        }

        AdsDto adsDto = new AdsDto();

        adsDto.setAuthor( ads.getAuthor().getId() );
        adsDto.setPk( ads.getId() );
        adsDto.setTitle( ads.getTitle() );
        adsDto.setDescription( ads.getDescription() );
        adsDto.setImage( ads.getImage() );
        adsDto.setPrice( ads.getPrice() );

        return adsDto;
    }
}

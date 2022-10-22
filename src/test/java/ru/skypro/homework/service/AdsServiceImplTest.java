package ru.skypro.homework.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.test.context.support.WithMockUser;
import ru.skypro.homework.model.dto.AdsDto;
import ru.skypro.homework.model.dto.ResponseWrapperAds;
import ru.skypro.homework.model.entity.Ads;
import ru.skypro.homework.model.entity.Image;
import ru.skypro.homework.model.entity.User;
import ru.skypro.homework.model.mapper.AdsMapper;
import ru.skypro.homework.model.mapper.AdsMapperImpl;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.service.impl.AdsServiceImpl;

import java.util.Iterator;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;

@ExtendWith(MockitoExtension.class)
public class AdsServiceImplTest {

    @Mock
    private AdsRepository adsRepository;

    @Spy
    private AdsMapper adsMapper = new AdsMapperImpl();

    @InjectMocks
    private AdsServiceImpl adsService;

    @Test
    public void testGetAllAds(){
        when(adsRepository.findAll()).thenReturn(
                List.of(
                        Ads.builder()
                                .id(1)
                                .author(new User("email", "pas", "name","last","phone"))
                                .image(new Image())
                                .title("title1")
                                .price(1)
                                .description("description1")
                                .build(),
                        Ads.builder()
                                .id(2)
                                .author(new User("email", "pas", "name","last","phone"))
                                .image(new Image())
                                .title("title1")
                                .price(1)
                                .description("description1")
                                .build()
                )
        );

        ResponseWrapperAds actual = adsService.getAllAds();


        assertEquals(2, actual.getCount());

        Iterator<AdsDto> ads = actual.getResults().iterator();
        assertEquals(new Integer(1), ads.next().getPk());
        assertEquals(new Integer(2), ads.next().getPk());
    }

//    @Test
//    @WithMockUser //(username = "user@gmail.com", password = "password")
//    public void testGetAdsMe(){
//        User user1 = new User("email", "pas", "name","last","phone");
//        user1.setId(1);
//
//        User user2 = new User("email", "pas", "name","last","phone");
//        user2.setId(2);
//
//        when(adsRepository.findAll()).thenReturn(
//                List.of(
//                        Ads.builder()
//                                .id(1)
//                                .author(user1)
//                                .image(new Image())
//                                .title("title1")
//                                .price(1)
//                                .description("description1")
//                                .build(),
//                        Ads.builder()
//                                .id(2)
//                                .author(user2)
//                                .image(new Image())
//                                .title("title1")
//                                .price(1)
//                                .description("description1")
//                                .build()
//                )
//        );
//
//        ResponseWrapperAds actual = adsService.getAllAds();
//
//
//        assertEquals(2, actual.getCount());
//
//        Iterator<AdsDto> ads = actual.getResults().iterator();
//        assertEquals(new Integer(1), ads.next().getPk());
//        assertEquals(new Integer(2), ads.next().getPk());
//    }

}

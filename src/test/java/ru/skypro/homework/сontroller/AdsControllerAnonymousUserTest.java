package ru.skypro.homework.сontroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.skypro.homework.controller.AdsController;
import ru.skypro.homework.model.dto.AdsDto;
import ru.skypro.homework.model.dto.CreateAds;
import ru.skypro.homework.model.dto.ResponseWrapperAds;
import ru.skypro.homework.model.entity.Image;
import ru.skypro.homework.service.impl.AdsCommentsImpl;
import ru.skypro.homework.service.impl.AdsServiceImpl;
import ru.skypro.homework.service.impl.ImageServiceImpl;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;

@WebMvcTest(controllers = AdsController.class)
//@WithMockUser //(username = "user@gmail.com", password = "password")
@AutoConfigureMockMvc(addFilters = false)

public class AdsControllerAnonymousUserTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AdsServiceImpl adsService;

    @MockBean
    private ImageServiceImpl imageService;

    @MockBean
    private AdsCommentsImpl adsCommentsService;


    @Test
    public void testGetAllAdsEmpty() throws Exception {
        when(adsService.getAllAds())
                .thenReturn(new ResponseWrapperAds(Collections.emptyList()));

        mockMvc.perform(get("/ads"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(0))
                .andExpect(jsonPath("$.results").isArray());
    }

    @Test
    public void testGetAllAdsNonEmpty() throws Exception {
        when(adsService.getAllAds())
                .thenReturn(new ResponseWrapperAds(List.of(
                        AdsDto.builder()
                                .author(1)
                                .image("image")
                                .title("title1")
                                .price(1)
                                .description("description1")
                                .pk(1)
                        .build(),
                        AdsDto.builder()
                                .author(2)
                                .image("image")
                                .title("title2")
                                .price(2)
                                .description("description2")
                                .pk(2)
                        .build()
                )));

        mockMvc.perform(get("/ads"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(2))
                .andExpect(jsonPath("$.results").isArray())
                .andExpect(jsonPath("$.results[0].pk").value(1))
                .andExpect(jsonPath("$.results[0].title").value("title1"))
                .andExpect(jsonPath("$.results[1].pk").value(2))
                .andExpect(jsonPath("$.results[1].title").value("title2"));
    }

    @Test
    public void testGetAdsMe() throws Exception {
        when(adsService.getAdsMe())
                .thenReturn(new ResponseWrapperAds(Collections.emptyList()));

        mockMvc.perform(get("/ads/me"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(0))
                .andExpect(jsonPath("$.results").isArray());
    }

    @Test
    public void testPostAddAds() throws Exception {
        when( imageService.addImage(any())).thenReturn(new Image());
        when(adsService.addAds(any(), any())).thenReturn(AdsDto.builder()
                .author(1)
                .image("image")
                .title("title1")
                .price(1)
                .description("description1")
                .pk(1)
                .build()
        );
//        when(adsService.getAdsMe())
//                .thenReturn(new ResponseWrapperAds(Collections.emptyList()));
        JSONObject body = new JSONObject();
//        body.put("properties", CreateAds.builder()
//                        .title("title")
//                        .description("description")
//                        .price(1)
//                        .build());
        body.put("title","title");
        body.put("description","description");
        body.put("price",1);
//        body.put("file","abc");

        MockMultipartFile file = new MockMultipartFile("image", "test.jpeg", "text/plain", "image file content".getBytes());
//        MockMultipartFile properties = new MockMultipartFile("properties", "", "application/json",body.toString().getBytes());

        MockMultipartFile properties = new MockMultipartFile("properties", "", "application/json",
                objectMapper.writeValueAsString(
                        CreateAds.builder()
                        .title("title")
                        .description("description")
                        .price(1)
                        .build()
                ).getBytes()
        );

        mockMvc.perform(MockMvcRequestBuilders
                                .multipart("/ads")
                                .file(file)
                                .file(properties)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pk").value(1))
                .andExpect(jsonPath("$.title").value("title1"))
                .andExpect(jsonPath("$.description").value("description1"))
                .andExpect(jsonPath("$.image").value("image"))
                .andExpect(jsonPath("$.price").value(1));


        //        mockMvc.perform(post("/ads")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(body.toString())
////                .content(objectMapper.writeValueAsString(CreateAds.builder()
////                        .title("title")
////                        .description("description")
////                        .price(1)
////                        .build()))
////                .with(user("admin").password("pass").roles("USER","ADMIN"))
//                )
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.count").value(0))
//                .andExpect(jsonPath("$.results").isArray());
    }

}

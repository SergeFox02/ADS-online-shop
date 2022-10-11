package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.model.dto.*;
import ru.skypro.homework.model.entity.Ads;
import ru.skypro.homework.model.entity.Image;
import ru.skypro.homework.service.AdsService;
import ru.skypro.homework.service.ImageService;

import javax.validation.Valid;
import java.io.IOException;

@CrossOrigin(
        value = "http://localhost:3000",
        allowCredentials = "true",
        allowedHeaders = "*",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RestController
@RequestMapping("/ads")
public class AdsController {

    private final String TAG_ADS_CONTROLLER = "Объявления";
    Logger logger = LoggerFactory.getLogger(AdsController.class);

    private final AdsService adsService;
    private final ImageService imageService;

    public AdsController(AdsService adsService, ImageService imageService) {
        this.adsService = adsService;
        this.imageService = imageService;
    }

    @Operation(
            summary = "getAllAds",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseWrapperAds.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE)
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE)
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found",
                            content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE)
                    )
            },
            tags = TAG_ADS_CONTROLLER
    )
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseWrapperAds getAllAds(){
        logger.info("Call getAllAds");
        return adsService.getAllAds();
    }

    @Operation(
            summary = "getAdsMe",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseWrapperAds.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE)
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE)
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found",
                            content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE)
                    )
            },
            tags = TAG_ADS_CONTROLLER
    )
    @PreAuthorize("!hasRole('ROLE_ANONYMOUS')")
    @GetMapping("/me")
    public ResponseEntity<?> getAdsMe(){
        logger.info("Call ads/me");
        return ResponseEntity.ok(adsService.getAdsMe());
    }


    @Operation(
            summary = "addAds",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CreateAds.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseWrapperAds.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "201",
                            description = "Created",
                            content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE)
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE)
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE)
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found",
                            content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE)
                    )
            },
            tags = TAG_ADS_CONTROLLER
    )
    @PostMapping
    public ResponseEntity<?> addAds(@Valid @RequestPart("properties") @Parameter(schema = @Schema(type = "string", format = "binary")) CreateAds ads,
                @RequestPart("image") MultipartFile file) {
        Image image;
        try{
            image = imageService.addImage(file);
        }catch (IOException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        logger.info("call addAds in controller");
        return ResponseEntity.ok(adsService.addAds(ads, image));
    }

    @Operation(
            summary = "getAdsComment",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseWrapperAdsComment.class)
                            )
                    )
            },
            tags = TAG_ADS_CONTROLLER
    )
    @GetMapping("/{ad_pk}/comment")
    public ResponseEntity<?> getAdsComments(@PathVariable Long ad_pk){
        return ResponseEntity.ok("Get Ads comments pk = " + ad_pk);
    }

    @Operation(
            summary = "addAdsComments",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AdsComment.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AdsComment.class)
                            )
                    )
            },
            tags = TAG_ADS_CONTROLLER
    )
    @PostMapping("/{ad_pk}/comment")
    public ResponseEntity<?> addAdsComment(@PathVariable Long ad_pk){
        return ResponseEntity.ok("Add Ads comment pk = " + ad_pk);
    }

    @Operation(
            summary = "deleteAdsComment",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseWrapperAdsComment.class)
                            )
                    )
            },
            tags = TAG_ADS_CONTROLLER
    )
    @DeleteMapping("/{ad_pk}/comment/{id}")
    public ResponseEntity<?> deleteAdsComment(@PathVariable Long ad_pk, @PathVariable Long id){
        return ResponseEntity.ok("Delete Ads comment pk = " + ad_pk + "; comment pk = " + id);
    }

    @Operation(
            summary = "getAdsComment",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AdsComment.class)
                            )
                    )
            },
            tags = TAG_ADS_CONTROLLER
    )
    @GetMapping("/{ad_pk}/comment/{id}")
    public ResponseEntity<?> getAdsComment(@PathVariable Long ad_pk, @PathVariable Long id){
        return ResponseEntity.ok("Get Ads comment pk = " + ad_pk + "; comment pk = " + id);
    }

    @Operation(
            summary = "updateAdsComment",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AdsComment.class)
                            )
                    )
            },
            tags = TAG_ADS_CONTROLLER
    )
    @PatchMapping("/{ad_pk}/comment/{id}")
    public ResponseEntity<?> updateAdsComment(@PathVariable Long ad_pk, @PathVariable Long id){
        return ResponseEntity.ok("Update Ads comment pk = " + ad_pk + "; comment pk = " + id);
    }

    @Operation(
            summary = "removeAds",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
                    )
            },
            tags = TAG_ADS_CONTROLLER
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeAds(@PathVariable Long id){
        return ResponseEntity.ok("Remove Ads pk = " + id);
    }

    @Operation(
            summary = "getAds",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = FullAds.class)
                            )
                    )
            },
            tags = TAG_ADS_CONTROLLER
    )
    @GetMapping("/{id}")
    public ResponseEntity<?> getAds(@PathVariable Integer id){
        if (adsService.getFullAds(id) == null){
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.ok(adsService.getFullAds(id));
    }

    @Operation(
            summary = "updateAds",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Ads.class)
                            )
                    )
            },
            tags = TAG_ADS_CONTROLLER
    )
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateAds(@PathVariable Long id){
        return ResponseEntity.ok("Update Ads pk = " + id);
    }

}

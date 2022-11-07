package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.model.dto.*;
import ru.skypro.homework.model.entity.Ads;
import ru.skypro.homework.model.entity.Image;
import ru.skypro.homework.service.AdsCommentsService;
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
@RequiredArgsConstructor
@RequestMapping("/ads")
public class AdsController {

    private final String TAG_ADS_CONTROLLER = "Объявления";
    Logger logger = LoggerFactory.getLogger(AdsController.class);
    private final AdsService adsService;
    private final ImageService imageService;
    private final AdsCommentsService adsCommentsService;

    @Operation(
            summary = "getAllAds",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseWrapperAds.class))
                    ),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            },
            tags = TAG_ADS_CONTROLLER
    )
    @GetMapping
    public ResponseEntity<?> getAllAds(){
        logger.info("Call getAllAds");

        return ResponseEntity.ok(new ResponseWrapper<>(adsService.getAllAds()));
    }

    @Operation(
            summary = "getAdsMe",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseWrapperAds.class))
                    ),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            },
            tags = TAG_ADS_CONTROLLER
    )
    @GetMapping("/me")
    public ResponseEntity<?> getAdsMe(@RequestParam(required = false) Boolean authenticated,
                                      @RequestParam(required = false) String authority,
                                      @RequestParam(required = false) Object credentials,
                                      @RequestParam(required = false) Object details,
                                      @RequestParam(required = false) Object principal) {
        logger.info("Call ads/me");

        return ResponseEntity.ok(adsService.getAdsMe());
    }

    @Operation(
            summary = "addAds",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CreateAds.class))
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseWrapperAds.class))
                    ),
                    @ApiResponse(responseCode = "201", description = "Created"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            },
            tags = TAG_ADS_CONTROLLER
    )
    @PostMapping
    public ResponseEntity<?> addAds(@Valid @RequestPart("properties") @Parameter(schema = @Schema(type = "string", format = "binary")) CreateAds ads,
                                    @RequestPart("image") MultipartFile file) {
        logger.info("Call addAds");
        Image image;
        try{
            image = imageService.addImage(file);
        }catch (IOException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.ok(adsService.addAds(ads, image));
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
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = AdsComment.class))
                    ),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found")

            },
            tags = TAG_ADS_CONTROLLER
    )
    @PostMapping("/{ad_pk}/comments")
    public ResponseEntity<?> addAdsComment(@PathVariable Integer ad_pk, @RequestBody AdsComment adsComment){
        logger.info("Call addAdsComment");

        return ResponseEntity.ok(adsCommentsService.addComment(ad_pk, adsComment));
    }

    @Operation(
            summary = "updateAdsComment",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = AdsComment.class))
                    ),
                    @ApiResponse(responseCode = "204", description = "No Content"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden")

            },
            tags = TAG_ADS_CONTROLLER
    )
    @PatchMapping("/{ad_pk}/comments/{id}")
    public ResponseEntity<?> updateAdsComment(@PathVariable Integer ad_pk,
                                              @PathVariable Integer id,
                                              @RequestBody AdsComment comment){
        logger.info("Call updateAdsComment");

        return ResponseEntity.ok(adsCommentsService.updateComment(ad_pk, id, comment));
    }

    @Operation(
            summary = "getAdsComment",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseWrapperAdsComment.class))
                    ),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            },
            tags = TAG_ADS_CONTROLLER
    )
    @GetMapping("/{ad_pk}/comments")
    public ResponseEntity<?> getAdsComments(@PathVariable Integer ad_pk){
        logger.info("Call getAdsComments");

        return ResponseEntity.ok(adsService.getAdsComments(ad_pk));
    }

    @Operation(
            summary = "deleteAdsComment",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseWrapperAdsComment.class))
                    ),
                    @ApiResponse(responseCode = "204", description = "No Content"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden")
            },
            tags = TAG_ADS_CONTROLLER
    )
    @DeleteMapping("/{ad_pk}/comments/{id}")
    public ResponseEntity<?> deleteAdsComment(@PathVariable Integer ad_pk, @PathVariable Integer id){
        logger.info("Call deleteAdsComment");
        adsCommentsService.deleteComment(ad_pk, id);

        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "getAdsComment",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = AdsComment.class))
                    ),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            },
            tags = TAG_ADS_CONTROLLER
    )
    @GetMapping("/{ad_pk}/comments/{id}")
    public ResponseEntity<?> getAdsComment(@PathVariable Integer ad_pk, @PathVariable Integer id) {
        logger.info("Call getAdsComment");

        return ResponseEntity.ok(adsCommentsService.getComment(ad_pk, id));
    }

    @Operation(
            summary = "removeAds",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
                    ),
                    @ApiResponse(responseCode = "204", description = "No Content"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden")
            },
            tags = TAG_ADS_CONTROLLER
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeAds(@PathVariable Integer id){
        logger.info("Call removeAds");
        adsService.deleteAds(id);

        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "getAds",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = FullAds.class))
                    ),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            },
            tags = TAG_ADS_CONTROLLER
    )
    @GetMapping("/{id}")
    public ResponseEntity<?> getAds(@PathVariable Integer id){
        logger.info("Call getAds");

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
                    ),
                    @ApiResponse(responseCode = "204", description = "No Content"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden")
            },
            tags = TAG_ADS_CONTROLLER
    )
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateAds(@PathVariable Integer id, @RequestBody AdsDto ads){
        logger.info("Call updateAds");

        return ResponseEntity.ok(adsService.updateAds(id, ads));
    }

}

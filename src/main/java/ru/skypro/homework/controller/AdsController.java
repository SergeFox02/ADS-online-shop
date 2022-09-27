package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/ads")
public class AdsController {

    private final String TAG_ADS_CONTROLLER = "Объявления";

    @Operation(
            summary = "getAllAds",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
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
    public ResponseEntity<?> getAllAds(){
        return ResponseEntity.ok("Get All ads");
    }

    @Operation(
            summary = "addAds",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE
//                            schema = @Schema(implementation = CreateAds.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
//                                    schema = @Schema(implementation = ResponseWrapperAds.class)
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
    public ResponseEntity<?> addAds(){
        return ResponseEntity.ok("Add new ads");
    }

    @Operation(
            summary = "getAdsMe",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
//                                    schema = @Schema(implementation = ResponseWrapperAds.class)
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
    @GetMapping("/me")
    public ResponseEntity<?> getAdsMe(){
        return ResponseEntity.ok("Get Ads me");
    }

    @Operation(
            summary = "getAdsComment",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
//                                    schema = @Schema(implementation = ResponseWrapperAdsComment.class)
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
                            mediaType = MediaType.APPLICATION_JSON_VALUE
//                            schema = @Schema(implementation = AdsComment.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
//                                    schema = @Schema(implementation = AdsComment.class)
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
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
//                                    schema = @Schema(implementation = ResponseWrapperAdsComment.class)
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
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
//                                    schema = @Schema(implementation = AdsComment.class)
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
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
//                                    schema = @Schema(implementation = AdsComment.class)
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
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
//                                    schema = @Schema(implementation = FullAds.class)
                            )
                    )
            },
            tags = TAG_ADS_CONTROLLER
    )
    @GetMapping("/{id}")
    public ResponseEntity<?> getAds(@PathVariable Long id){
        return ResponseEntity.ok("Get Ads pk = " + id);
    }

    @Operation(
            summary = "updateAds",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
//                                    schema = @Schema(implementation = Ads.class)
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

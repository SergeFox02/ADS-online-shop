package ru.skypro.homework.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ads")
public class AdsController {

    @GetMapping
    public ResponseEntity<?> getAllAds(){
        return ResponseEntity.ok("Get All ads");
    }

    @PostMapping
    public ResponseEntity<?> addAds(){
        return ResponseEntity.ok("Add new ads");
    }

    @GetMapping("/me")
    public ResponseEntity<?> getAdsMe(){
        return ResponseEntity.ok("Get Ads me");
    }

    @GetMapping("/{ad_pk}/comment")
    public ResponseEntity<?> getAdsComments(@PathVariable Long ad_pk){
        return ResponseEntity.ok("Get Ads comments pk = " + ad_pk);
    }

    @PostMapping("/{ad_pk}/comment")
    public ResponseEntity<?> addAdsComment(@PathVariable Long ad_pk){
        return ResponseEntity.ok("Add Ads comment pk = " + ad_pk);
    }

    @DeleteMapping("/{ad_pk}/comment/{id}")
    public ResponseEntity<?> deleteAdsComment(@PathVariable Long ad_pk, @PathVariable Long id){
        return ResponseEntity.ok("Delete Ads comment pk = " + ad_pk + "; comment pk = " + id);
    }

    @GetMapping("/{ad_pk}/comment/{id}")
    public ResponseEntity<?> getAdsComment(@PathVariable Long ad_pk, @PathVariable Long id){
        return ResponseEntity.ok("Get Ads comment pk = " + ad_pk + "; comment pk = " + id);
    }

    @PatchMapping("/{ad_pk}/comment/{id}")
    public ResponseEntity<?> updateAdsComment(@PathVariable Long ad_pk, @PathVariable Long id){
        return ResponseEntity.ok("Update Ads comment pk = " + ad_pk + "; comment pk = " + id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeAds(@PathVariable Long id){
        return ResponseEntity.ok("Remove Ads pk = " + id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAds(@PathVariable Long id){
        return ResponseEntity.ok("Get Ads pk = " + id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateAds(@PathVariable Long id){
        return ResponseEntity.ok("Update Ads pk = " + id);
    }

}

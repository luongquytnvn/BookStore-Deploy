package com.codegym.controllers;

import com.codegym.models.Language;
import com.codegym.models.Publishing;
import com.codegym.services.PublishingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/publishing")
public class PublishingController {

    @Autowired
    private PublishingService publishingService;

    @GetMapping("")
    public ResponseEntity<List<Publishing>> listAllPublishings() {
        List<Publishing> publishings = (List<Publishing>) publishingService.findAll();
        if (publishings.isEmpty()) {
            return new ResponseEntity<List<Publishing>>(publishings, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Publishing>>(publishings, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Publishing>> getPublishing(@PathVariable("id") long id) {
        System.out.println("Publishing with id " + id);
        Optional<Publishing> publishing = publishingService.findById(id);
        if (!publishing.isPresent()) {
            System.out.println("Publishing with id " + id + "not found");
            return new ResponseEntity<Optional<Publishing>>(publishing, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Optional<Publishing>>(publishing, HttpStatus.OK);
    }

    @PostMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> createPublishing(@RequestBody Publishing publishing, UriComponentsBuilder uriComponentsBuilder) {
        System.out.println("Create Publishing" + publishing.getName());
        publishingService.save(publishing);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriComponentsBuilder.path("/publishings/{id}").buildAndExpand(publishing.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Optional<Publishing>> updatePublishing(@PathVariable("id") long id, @RequestBody Publishing publishing) {
        System.out.println("Update Publishing" + id);

        Optional<Publishing> currentPublishing= publishingService.findById(id);

        if (!currentPublishing.isPresent()) {
            System.out.println("Publishing with id" + id + "not found");
            return new ResponseEntity<Optional<Publishing>>(HttpStatus.NOT_FOUND);
        }

        currentPublishing.get().setName(publishing.getName());
        currentPublishing.get().setBooks(publishing.getBooks());
        publishingService.save(publishing);
        return new ResponseEntity<Optional<Publishing>>(currentPublishing, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Publishing> deletePublishing(@PathVariable("id") long id) {
        System.out.println("Delete Publishing with id" + id);

        Optional<Publishing> publishing = publishingService.findById(id);
        if (!publishing.isPresent()) {
            System.out.println("Unable to delete. Publishing with id" + id + "not found");
            return new ResponseEntity<Publishing>(HttpStatus.NOT_FOUND);
        }
        publishingService.remove(id);
        return new ResponseEntity<Publishing>(HttpStatus.NO_CONTENT);
    }
    @PostMapping("/findAllByName")
    public ResponseEntity<List<Publishing>> findAllByName(@RequestBody String name){
        List<Publishing> publishingList = publishingService.findAllByNameContaining(name);
        if (!publishingList.isEmpty()) {
            return new ResponseEntity<List<Publishing>>(publishingList, HttpStatus.OK);
        }else {
            return new ResponseEntity<List<Publishing>>(HttpStatus.NOT_FOUND);
        }
    }
}

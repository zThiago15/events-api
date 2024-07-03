package com.eventostec.api.service;

import com.amazonaws.services.s3.AmazonS3;
import com.eventostec.api.domain.coupon.Coupon;
import com.eventostec.api.domain.event.Event;
import com.eventostec.api.domain.event.EventDetailsDTO;
import com.eventostec.api.domain.event.EventRequestDTO;
import com.eventostec.api.domain.event.EventResponseDTO;
import com.eventostec.api.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@EnableCaching
public class EventService {

    @Value("${aws.bucket.name}")
    private String bucketName;

    @Autowired
    private AmazonS3 s3Client;

    @Autowired
    private EventRepository repository;

    @Autowired
    private AddressService addressService;

    @Autowired
    private CouponService couponService;

    @Cacheable("events")
    public List<EventResponseDTO> getUpcomingEvents(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Event> eventsPage = repository.findUpcomingEvents(new Date(), pageable);

        return eventsPage.map(event -> new EventResponseDTO(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getDate(),
                event.getAddress() != null ? event.getAddress().getCity() : "",
                event.getAddress() !=null ? event.getAddress().getUf() : "",
                event.getRemote(),
                event.getEvent_url(),
                event.getImg_url()
                )).stream().toList();

    }

    public Event createEvent(EventRequestDTO data) {
        String imgUrl = null;

        if (data.image() != null) {
            imgUrl = this.uploadImg(data.image());
        }

        Event newEvent = new Event();
        newEvent.setTitle(data.title());
        newEvent.setDescription(data.description());
        newEvent.setDate(new Date(data.date()));
        newEvent.setRemote(data.remote());

        newEvent.setImg_url(imgUrl);
        newEvent.setEvent_url(data.eventUrl());

        repository.save(newEvent);

        if(!data.remote()) {
            addressService.createAddress(data, newEvent);
        }

        return newEvent;
    }

    private String uploadImg(MultipartFile image) {
        String imgName = UUID.randomUUID() + "-" + image.getOriginalFilename();
        try {
            File file = this.convertMultipartToFile(image);

            // save image, already converted to a file type, in Amazon S3
            s3Client.putObject(this.bucketName, imgName, file);

            file.delete();

            return s3Client.getUrl(bucketName, imgName).toString();
        } catch (Exception e){
            System.out.println("Erro ao subir arquivo");
            return "";
        }
    }

    private File convertMultipartToFile(MultipartFile multipartFile) throws IOException {

        File convFile = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(multipartFile.getBytes());
        fos.close();

        return convFile;
    }

    public List<EventResponseDTO> getFilteredEvents(
            int page,
            int size,
            String title,
            String city,
            String uf,
            Date startDate,
            Date endDate
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Event> eventsPage = repository.findFilteredEvents(title, city, uf, startDate, endDate, pageable);


        return eventsPage.map(event -> new EventResponseDTO(
                event.getId(), event.getTitle(),
                event.getDescription(),
                event.getDate(),
                event.getAddress() != null ? event.getAddress().getCity() : "",
                event.getAddress() != null ? event.getAddress().getUf() : "",
                event.getRemote(),
                event.getEvent_url(),
                event.getImg_url())
        ).stream().toList();
    }

    @Cacheable("eventid")
    public EventDetailsDTO getEventById(UUID id) {
        Event event = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Event not found"));;

        List<Coupon> coupons = couponService.consultCoupons(id, new Date());

        // Assign returned coupons to a list of EventDetailsDTO
        List<EventDetailsDTO.CouponDTO> couponsDTOs = coupons.stream()
                .map(coupon -> new EventDetailsDTO.CouponDTO(
                        coupon.getCode(),
                        coupon.getDiscount(),
                        coupon.getValid()
                )).collect(Collectors.toList());

        return new EventDetailsDTO(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getDate(),
                event.getAddress() != null ? event.getAddress().getUf() : "",
                event.getAddress() != null ? event.getAddress().getCity() : "",
                event.getRemote(),
                event.getEvent_url(),
                event.getImg_url(),
                couponsDTOs
        );
    }


}

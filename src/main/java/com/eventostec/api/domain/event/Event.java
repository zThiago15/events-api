package com.eventostec.api.domain.event;
import com.eventostec.api.domain.address.Address;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Table(name = "event")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Event {

    @Id
    @GeneratedValue
    private UUID id;

    @Column
    private String title;
    private String description;
    private Date date;
    private boolean remote;
    private String img_url;
    private String event_url;

    @OneToOne(mappedBy = "event", cascade = CascadeType.ALL)
    private Address address;
}

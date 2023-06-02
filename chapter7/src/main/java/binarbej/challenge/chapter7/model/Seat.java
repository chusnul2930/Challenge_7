package binarbej.challenge.chapter7.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "seat")
public class Seat {
    @Id
    @Column(name = "seat_id", length = 36, unique = true)
    private String seatId;

    @Column(name = "no_kursi", length = 5, nullable = false)
    private String noKursi;

    @Column(name = "studio_id", length = 36, columnDefinition = "VARCHAR(36) REFERENCES studio(studio_id)")
    private String studioId;

    @Column(name = "created", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @Column(name = "created_by", length = 100, columnDefinition = "VARCHAR(100) DEFAULT 'SYSTEM'")
    private String createdBy;

    @Column(name = "updated", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated;

    @Column(name = "updated_by", length = 100, columnDefinition = "VARCHAR(100) DEFAULT 'SYSTEM'")
    private String updatedBy;

    @Column(name = "isactive", length = 1, columnDefinition = "VARCHAR(1) DEFAULT 'Y'")
    private String isactive;

}